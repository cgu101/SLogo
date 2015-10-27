package backend.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.regex.Pattern;
import backend.factory.CommandFactory;
import backend.node.Node;
import backend.node.types.Constant;
import datatransferobjects.UserInputTransferObject;
import exceptions.SyntaxException;
import responses.Error;
import responses.Response;
import sharedobjects.Functions;
import sharedobjects.ManipulateController;

/**
 * @author wanning
 *
 */
public class Parser implements Observer {
	private ManipulateController myManiControl;
	private List<Node> myRoots;
	private int myIndex;
	private String myLanguage;
	private List<Entry<TokenType, String>> myTokenList;
	private List<Entry<SyntaxType, String>> mySyntaxList;
	private boolean myListLegal;
	private static final String PARSER_ERROR_RESOURCE = "backend.parser.parserError";
	protected static ResourceBundle myResource;

	private static final HashMap<String, TokenType> tokenMap = new HashMap<String, TokenType>() {
		/**
		* 
		*/
		private static final long serialVersionUID = -1529654805794198192L;

		{
			for (TokenType each : TokenType.values()) {
				put(each.name().toUpperCase(), each);
			}
		}
	};
	private static final HashMap<String, SyntaxType> syntaxMap = new HashMap<String, SyntaxType>() {
		/**
		* 
		*/
		private static final long serialVersionUID = -5307211259661516705L;

		{
			for (SyntaxType each : SyntaxType.values()) {
				put(each.name().toUpperCase(), each);
			}
		}
	};
	private static final HashMap<String, LangType> languageMap = new HashMap<String, LangType>() {
		/**
		* 
		*/
		private static final long serialVersionUID = -263144993076464049L;

		{
			for (LangType each : LangType.values()) {
				put(each.name().toUpperCase(), each);
			}
		}
	};
	private static final List<Entry<TokenType, Pattern>> myTokenPatterns = makeTokenPatterns(
			"resources/languages/Syntax");
	public static final Map<LangType, List<Entry<SyntaxType, Pattern>>> mySyntaxPatterns = makeSyntaxPatterns();

	public Parser(ManipulateController mc) {
		// Call run to start.
		myManiControl = mc;
		init();
		myResource = ResourceBundle.getBundle(PARSER_ERROR_RESOURCE);
	}

	public void init() {
		myRoots = new ArrayList<Node>();
		myIndex = 0;
		myListLegal = false;
	}

	public Response parse(String userInput, String lang) {
		myLanguage = lang;
		myTokenList = new ArrayList<Entry<TokenType, String>>();
		Response response = null;
		BufferedReader reader = new BufferedReader(new StringReader(userInput));
		String line;
		// lexicon parsing ("fd :#$@" is wrong)
		try {
			while ((line = reader.readLine()) != null) {
				try {
					buildTokenList(line);
				} catch (LexiconException e) {
					response = new Error(e.getMessage());
					return response;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			buildSyntaxTree();
		} catch (SyntaxException e) {
			response = new Error(e.getMessage());
			return response;
		}
		for (Node each : myRoots) {
			// should add a try catch, and make executor throws execute
			// exception
			response = each.run(myManiControl);
			System.out.println("--> " + response.toString());
			// System.out.println("call exec");
		}
		return response;
	}

	// a helper function to match regex
	public static boolean match(String input, Pattern regex) {
		return regex.matcher(input).matches();
	}

	// return the list of tokens of this line. If there is a comment, discard
	// this line.
	private List<Entry<TokenType, String>> generateToken(String line) throws LexiconException {
		List<Entry<TokenType, String>> result = new ArrayList<Entry<TokenType, String>>();
		StringTokenizer st = new StringTokenizer(line, " ");
		String nodeName;
		boolean matchFlag = false;
		boolean commentFlag = false;
		while (st.hasMoreTokens()) {
			nodeName = st.nextToken();
			for (Entry<TokenType, Pattern> p : myTokenPatterns) {
				if (match(nodeName, p.getValue())) {
					if (p.getKey() == TokenType.COMMENT) {
						commentFlag = true;
						break;
					}
					result.add(new SimpleEntry<TokenType, String>(p.getKey(), nodeName));
					matchFlag = true;
					break;
				}
			}
			if (commentFlag)
				break;
			if (!matchFlag)
				throw new LexiconException(myResource.getString("error_illegaToken"));
		}
		return result;
	}

	private void buildTokenList(String line) throws LexiconException {
		myTokenList.addAll(generateToken(line));
	}

	private void buildSyntaxTree() throws SyntaxException {
		// gengerate a syntax list
		mySyntaxList = new ArrayList<Entry<SyntaxType, String>>();
		for (Entry<TokenType, String> entry : myTokenList) {
			switch (entry.getKey()) {
			case CONSTANT:
			case VARIABLE:
			case LISTSTART:
			case LISTEND:
			case GROUPSTART:
			case GROUPEND:
				mySyntaxList.add(new SimpleEntry<SyntaxType, String>(syntaxMap.get(entry.getKey().name().toUpperCase()),
						entry.getValue()));
				break;
			case COMMAND:
				boolean matchFlag = false;
				for (Entry<SyntaxType, Pattern> p : mySyntaxPatterns.get(languageMap.get(myLanguage.toUpperCase()))) {
					if (Parser.match(entry.getValue(), p.getValue())) {
						mySyntaxList.add(new SimpleEntry<SyntaxType, String>(p.getKey(), entry.getValue()));
						matchFlag = true;
						break;
					}
				}
				if (!matchFlag) {
					mySyntaxList.add(new SimpleEntry<SyntaxType, String>(SyntaxType.USERCOMMAND, entry.getValue()));
				}
				break;
			default:
				throw new SyntaxException(myResource.getString("error_parser"));
			}

		}
		// build syntax tree in a recursive way
		while (myIndex < mySyntaxList.size()) {
			Node root = null;
			root = growTree();
			if (root != null) {
				myRoots.add(root);
			}
		}
	}

	// index after growTree will indicate a token that hasn't been parsed
	// growTree will only generate one root or subroot in the tree
	private Node growTree() throws SyntaxException {
		try {
			// create the node using factory design pattern
			CommandFactory factory = new CommandFactory();
			SyntaxType type = mySyntaxList.get(myIndex).getKey();
			Node root = factory.createNode(type);
			root.setName(mySyntaxList.get(myIndex).getValue());
			myIndex++;
			// check the syntax and move on
			switch (type) {
			// with 0 argument
			case CONSTANT:
				root.setValue(Double.parseDouble(mySyntaxList.get(myIndex - 1).getValue()));
			case VARIABLE:
			case PENDOWN:
			case PENUP:
			case SHOWTURTLE:
			case HIDETURTLE:
			case HOME:
			case CLEARSCREEN:
			case XCOORDINATE:
			case YCOORDINATE:
			case HEADING:
			case ISPENDOWN:
			case ISSHOWING:
			case PI:
			case GETPENCOLOR:
			case GETSHAPE:
			case STAMP:
			case CLEARSTAMPS:
			case ID:
			case TURTLES:
			case DUVALL:
				parseExpression(root, 0);
				break;
			// with 1 argument
			case FORWARD:
			case BACKWARD:
			case LEFT:
			case RIGHT:
			case SETHEADING:
			case MINUS:
			case RANDOM:
			case SINE:
			case COSINE:
			case TANGENT:
			case ARCTANGENT:
			case NATURALLOG:
			case NOT:
			case SETBACKGROUND:
			case SETPENCOLOR:
			case SETPENSIZE:
			case SETSHAPE:
				parseExpression(root, 1);
				break;
			// with 2 arguments
			case SETTOWARDS:
			case SETPOSITION:
			case SUM:
			case DIFFERENCE:
			case PRODUCT:
			case QUOTIENT:
			case REMAINDER:
			case POWER:
			case LESSTHAN:
			case GREATERTHAN:
			case EQUAL:
			case NOTEQUAL:
			case AND:
			case OR:
				parseExpression(root, 2);
				break;
			// with 3 arguments
			case SETPALETTE:
				parseExpression(root, 4);
				break;
			// control structures
			case GROUPSTART:
				root = factory.createNode(mySyntaxList.get(myIndex).getKey());
				root.setName(mySyntaxList.get(myIndex).getValue());
				myIndex++;
				parseControlStruc(root, myResource.getString("error_right2"), (Node r) -> {
					SyntaxType rType = mySyntaxList.get(myIndex - 1).getKey();
					switch (rType) {
					case SUM:
					case EQUAL:
					case DIFFERENCE:
					case PRODUCT:
						Node fakeRoot = r;
						while (mySyntaxList.get(myIndex + 2).getKey() != SyntaxType.GROUPEND) {
							parseExpression(fakeRoot, 1);
							Node c = factory.createNode(rType);
							fakeRoot.addChild(c);
							fakeRoot = c;
						}
						parseExpression(fakeRoot, 2);
						myIndex++;
						break;
					default:
						Node c = growTree();
						r.addChild(c);
						while (mySyntaxList.get(myIndex).getKey() != SyntaxType.GROUPEND) {
							myIndex++;
						}
						myIndex++;
					}
				});
				break;
			case GROUPEND:
				throw new SyntaxException(myResource.getString("error_left2") + root.getName());
			case LISTSTART:
				if (myListLegal) {
					parseControlStruc(root, myResource.getString("error_right1"), (Node r) -> {
						while (mySyntaxList.get(myIndex).getKey() != SyntaxType.LISTEND) {
							Node c = growTree();
							r.addChild(c);
						}
						myIndex++;
					});
					myListLegal = false;
				} else {
					throw new SyntaxException(root.getName() + myResource.getString("error_illegalUsage"));
				}
				break;
			case LISTEND:
				throw new SyntaxException(myResource.getString("error_left1") + root.getName());
			case MAKEVARIABLE:
				parseControlStruc(root, myResource.getString("error_miss"), (Node r) -> {
					if (mySyntaxList.get(myIndex).getKey() != SyntaxType.VARIABLE) {
						throw new SyntaxException(myResource.getString("error_var") + r.getName());
					} else {
						for (int i = 0; i < 2; i++) {
							Node c = growTree();
							r.addChild(c);
						}
					}
				});
				break;
			case REPEAT:
				parseControlStruc(root, myResource.getString("error_miss"), (Node r) -> {
					Node c = growTree();
					r.addChild(c);
					if (mySyntaxList.get(myIndex).getKey() != SyntaxType.LISTSTART) {
						throw new SyntaxException(myResource.getString("error_left1") + r.getName());
					} else {
						// clist must be a LISTSTART syntax type
						myListLegal = true;
						Node clist = growTree();
						r.addChild(clist);
					}
				});
				break;
			case DOTIMES:
				parseControlStruc(root, myResource.getString("error_right1"), (Node r) -> {
					if (mySyntaxList.get(myIndex).getKey() != SyntaxType.LISTSTART) {
						throw new SyntaxException(myResource.getString("error_left1") + r.getName());
					} else {
						myIndex++;
						if (mySyntaxList.get(myIndex).getKey() != SyntaxType.VARIABLE) {
							throw new SyntaxException(myResource.getString("error_var") + r.getName());
						}
						Node c = growTree();
						r.addChild(c);
						c = growTree();
						r.addChild(c);
						if (mySyntaxList.get(myIndex).getKey() != SyntaxType.LISTEND) {
							throw new SyntaxException(myResource.getString("error_right1") + r.getName());
						}
						myIndex++;
					}
					if (mySyntaxList.get(myIndex).getKey() != SyntaxType.LISTSTART) {
						throw new SyntaxException(myResource.getString("error_left1") + r.getName());
					} else {
						myListLegal = true;
						Node c = growTree();
						r.addChild(c);
					}
				});
				break;
			case FOR:
				parseControlStruc(root, myResource.getString("error_miss"), (Node r) -> {
					if (mySyntaxList.get(myIndex).getKey() != SyntaxType.LISTSTART) {
						throw new SyntaxException(myResource.getString("error_left1") + r.getName());
					} else {
						myIndex++;
						if (mySyntaxList.get(myIndex).getKey() != SyntaxType.VARIABLE) {
							throw new SyntaxException(myResource.getString("error_var") + r.getName());
						}
						Node c = null;
						for (int i = 0; i < 4; i++) {
							c = growTree();
							r.addChild(c);
						}
						if (mySyntaxList.get(myIndex).getKey() != SyntaxType.LISTEND) {
							throw new SyntaxException(myResource.getString("error_right1") + r.getName());
						}
						myIndex++;
					}
					if (mySyntaxList.get(myIndex).getKey() != SyntaxType.LISTSTART) {
						throw new SyntaxException(myResource.getString("error_left1") + r.getName());
					} else {
						myListLegal = true;
						Node c = growTree();
						r.addChild(c);
					}
				});
				break;
			case IF:
				parseControlStruc(root, myResource.getString("error_miss"), (Node r) -> {
					Node c = growTree();
					r.addChild(c);
					if (mySyntaxList.get(myIndex).getKey() != SyntaxType.LISTSTART) {
						throw new SyntaxException(myResource.getString("error_left1") + r.getName());
					} else {
						myListLegal = true;
						c = growTree();
						r.addChild(c);
					}
				});
				break;
			case IFELSE:
				parseControlStruc(root, myResource.getString("error_miss"), (Node r) -> {
					Node c = growTree();
					r.addChild(c);
					for (int i = 0; i < 2; i++) {
						if (mySyntaxList.get(myIndex).getKey() != SyntaxType.LISTSTART) {
							throw new SyntaxException(myResource.getString("error_left1") + r.getName());
						} else {
							myListLegal = true;
							c = growTree();
							r.addChild(c);
						}
					}
				});
				break;
			case TELL:
				parseControlStruc(root, myResource.getString("error_miss"), (Node r) -> {
					if (mySyntaxList.get(myIndex).getKey() != SyntaxType.LISTSTART) {
						throw new SyntaxException(myResource.getString("error_left1") + r.getName());
					} else {
						myListLegal = true;
						Node c = growTree();
						r.addChild(c);
					}
				});
				break;
			case ASK:
			case ASKWITH:
				parseControlStruc(root, myResource.getString("error_miss"), (Node r) -> {
					for (int i = 0; i < 2; i++) {
						if (mySyntaxList.get(myIndex).getKey() != SyntaxType.LISTSTART) {
							throw new SyntaxException(myResource.getString("error_left1") + r.getName());
						} else {
							myListLegal = true;
							Node c = growTree();
							r.addChild(c);
						}
					}
				});
				break;
			case MAKEUSERINSTRUCTION:
				parseMakeCmd(root);
				break;
			default:
				Node toCmd = myManiControl.executeOnWorkspaceFunctions((Functions f) -> {
					return f.getCommand(mySyntaxList.get(myIndex - 1).getValue());
				});

				if (toCmd == null)
					throw new SyntaxException(
							myResource.getString("error_undefined") + mySyntaxList.get(myIndex - 1).getValue());
				int numOfArg = toCmd.getChildrenNum() - 1;
				parseExpression(root, numOfArg);
			}
			return root;
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new SyntaxException(myResource.getString("error_miss"));
		}
	}

	private void parseExpression(Node root, int numOfChildren) throws SyntaxException {
		for (int i = 0; i < numOfChildren; i++) {
			if (myIndex >= mySyntaxList.size())
				throw new SyntaxException(myResource.getString("error_miss") + root.getName());
			Node c = growTree();
			root.addChild(c);
		}
	}

	private void parseControlStruc(Node root, String errorMsg, CtlStrucParser cp) throws SyntaxException {
		try {
			cp.parse(root);
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new SyntaxException(errorMsg + root.getName());
		}
	}

	private void parseMakeCmd(Node root) throws SyntaxException {
		int beginIndex = myIndex;
		String name = mySyntaxList.get(myIndex).getValue();
		root.setName(name);
		if (mySyntaxList.get(myIndex).getKey() != SyntaxType.USERCOMMAND) {
			throw new SyntaxException(myResource.getString("error_dup") + root.getName());
		}
		myIndex++;
		try {
			if (mySyntaxList.get(myIndex).getKey() != SyntaxType.LISTSTART) {
				throw new SyntaxException(myResource.getString("error_left1") + root.getName());
			} else {
				myIndex++;
				while (mySyntaxList.get(myIndex).getKey() == SyntaxType.VARIABLE) {
					Node c = growTree();
					root.addChild(c);
				}
				if (mySyntaxList.get(myIndex).getKey() != SyntaxType.LISTEND) {
					throw new SyntaxException(myResource.getString("error_right1") + root.getName());
				}
				myIndex++;
			}
			if (mySyntaxList.get(myIndex).getKey() != SyntaxType.LISTSTART) {
				throw new SyntaxException(myResource.getString("error_left1") + root.getName());
			} else {
				myManiControl.executeOnWorkspaceFunctions((Functions f) -> {
					f.setCommand(root.getName(), root);
					return new Constant(0);
				});

				myListLegal = true;
				Node c = growTree();
				root.addChild(c);
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new SyntaxException(myResource.getString("error_miss") + root.getName());
		}
		int endIndex = myIndex;
		String display = "";
		for (int i = beginIndex; i < endIndex; i++) {
			display += mySyntaxList.get(i).getValue();
			display += " ";
		}

		final String s = display;
		myManiControl.executeOnWorkspaceFunctions((Functions f) -> {
			f.setCommand(s, root.getName(), root);
			return new Constant(0);
		});
	}

	// The following two methods are only used when we first create a parser.
	// They will generate myTokenPatterns, mySyntaxPatterns
	public static List<Entry<TokenType, Pattern>> makeTokenPatterns(String fileName) {
		ResourceBundle resources = ResourceBundle.getBundle(fileName);
		List<Entry<TokenType, Pattern>> patterns = new ArrayList<Entry<TokenType, Pattern>>();
		Enumeration<String> iter = resources.getKeys();
		while (iter.hasMoreElements()) {
			String key = iter.nextElement();
			String regex = resources.getString(key);
			patterns.add(new SimpleEntry<TokenType, Pattern>(tokenMap.get(key.toUpperCase()),
					Pattern.compile(regex, Pattern.CASE_INSENSITIVE)));
		}
		return patterns;
	}

	// how to reduce redundancy? enum can't be extended
	public static Map<LangType, List<Entry<SyntaxType, Pattern>>> makeSyntaxPatterns() {
		Map<LangType, List<Entry<SyntaxType, Pattern>>> result = new HashMap<LangType, List<Entry<SyntaxType, Pattern>>>();
		String fileName;
		for (LangType each : LangType.values()) {
			fileName = "resources/languages/" + each.name().toLowerCase();
			ResourceBundle resources = ResourceBundle.getBundle(fileName);
			List<Entry<SyntaxType, Pattern>> patterns = new ArrayList<Entry<SyntaxType, Pattern>>();
			Enumeration<String> iter = resources.getKeys();
			while (iter.hasMoreElements()) {
				String key = iter.nextElement();
				String regex = resources.getString(key);
				patterns.add(new SimpleEntry<SyntaxType, Pattern>(syntaxMap.get(key.toUpperCase()),
						Pattern.compile(regex, Pattern.CASE_INSENSITIVE)));
			}
			result.put(each, patterns);
		}
		return result;
	}

	@Override
	public void update(Observable o, Object arg) throws SyntaxException {
		init();
		UserInputTransferObject dto = (UserInputTransferObject) arg;
		String input = dto.getUserInput();
		System.out.println("Wanning" + input);
		String lang = dto.getLanguage();
		Response s = parse(input, lang);
		// Notify the frontend
		myManiControl.setReponse(s);

	}
}
