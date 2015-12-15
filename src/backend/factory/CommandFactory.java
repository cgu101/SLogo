// This entire file is part of my masterpiece.
// WANNING JIANG

package backend.factory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import backend.node.DUVALL;
import backend.node.FENCE;
import backend.node.Node;
import backend.node.commands.ASK;
import backend.node.commands.ASKWITH;
import backend.node.commands.BK;
import backend.node.commands.CS;
import backend.node.commands.FD;
import backend.node.commands.GOTO;
import backend.node.commands.HEADING;
import backend.node.commands.HOME;
import backend.node.commands.HT;
import backend.node.commands.ID;
import backend.node.commands.LT;
import backend.node.commands.PD;
import backend.node.commands.PENDOWNP;
import backend.node.commands.PU;
import backend.node.commands.RT;
import backend.node.commands.SETH;
import backend.node.commands.SHOWINGP;
import backend.node.commands.ST;
import backend.node.commands.TELL;
import backend.node.commands.TOWARDS;
import backend.node.commands.TURTLES;
import backend.node.commands.WRAP;
import backend.node.commands.XCOR;
import backend.node.commands.YCOR;
import backend.node.control.DOTIMES;
import backend.node.control.FOR;
import backend.node.control.GROUPSTART;
import backend.node.control.IF;
import backend.node.control.IFELSE;
import backend.node.control.LISTSTART;
import backend.node.control.MAKEVAR;
import backend.node.control.REPEAT;
import backend.node.control.TOCOMMAND;
import backend.node.control.USERCOMMAND;
import backend.node.control.Variable;
import backend.node.display.CLEARSTAMPS;
import backend.node.display.PC;
import backend.node.display.SETBG;
import backend.node.display.SETPALETTE;
import backend.node.display.SETPC;
import backend.node.display.SETPS;
import backend.node.display.SETSH;
import backend.node.display.SH;
import backend.node.display.STAMP;
import backend.node.operations.AND;
import backend.node.operations.ATAN;
import backend.node.operations.COS;
import backend.node.operations.DIFFERENCE;
import backend.node.operations.EQUALP;
import backend.node.operations.GREATERP;
import backend.node.operations.LESSP;
import backend.node.operations.LOG;
import backend.node.operations.MINUS;
import backend.node.operations.NOT;
import backend.node.operations.NOTEQUALP;
import backend.node.operations.OR;
import backend.node.operations.PI;
import backend.node.operations.POW;
import backend.node.operations.PRODUCT;
import backend.node.operations.QUOTIENT;
import backend.node.operations.RANDOM;
import backend.node.operations.REMAINDER;
import backend.node.operations.SIN;
import backend.node.operations.SUM;
import backend.node.operations.TAN;
import backend.node.types.Constant;
import backend.parser.SyntaxType;

public class CommandFactory {
	private static HashMap<SyntaxType, Class<?>> myRegisteredCommands = new HashMap<SyntaxType, Class<?>>();

	static {
		//// #
		//// # Turtle Commands
		//// #
		registerNode(SyntaxType.FORWARD, FD.class);
		registerNode(SyntaxType.BACKWARD, BK.class);
		registerNode(SyntaxType.LEFT, LT.class);
		registerNode(SyntaxType.RIGHT, RT.class);
		registerNode(SyntaxType.SETHEADING, SETH.class);
		registerNode(SyntaxType.SETTOWARDS, TOWARDS.class);
		registerNode(SyntaxType.SETPOSITION, GOTO.class);
		registerNode(SyntaxType.PENDOWN, PD.class);
		registerNode(SyntaxType.PENUP, PU.class);
		registerNode(SyntaxType.SHOWTURTLE, ST.class);
		registerNode(SyntaxType.HIDETURTLE, HT.class);
		registerNode(SyntaxType.HOME, HOME.class);
		registerNode(SyntaxType.CLEARSCREEN, CS.class);
		registerNode(SyntaxType.XCOORDINATE, XCOR.class);
		registerNode(SyntaxType.YCOORDINATE, YCOR.class);

		//// #
		//// # Turtle Queries
		//// #
		registerNode(SyntaxType.XCOORDINATE, XCOR.class);// XCOORDINATE,
		registerNode(SyntaxType.YCOORDINATE, YCOR.class);// YCOORDINATE,
		registerNode(SyntaxType.HEADING, HEADING.class);// HEADING,
		registerNode(SyntaxType.ISPENDOWN, PENDOWNP.class);
		registerNode(SyntaxType.ISSHOWING, SHOWINGP.class);
		// ISSHOWING,
		//// #
		//// # Math Operations
		//// #
		registerNode(SyntaxType.SUM, SUM.class);
		registerNode(SyntaxType.DIFFERENCE, DIFFERENCE.class);
		registerNode(SyntaxType.PRODUCT, PRODUCT.class);
		registerNode(SyntaxType.QUOTIENT, QUOTIENT.class);
		registerNode(SyntaxType.REMAINDER, REMAINDER.class);
		registerNode(SyntaxType.MINUS, MINUS.class);
		registerNode(SyntaxType.RANDOM, RANDOM.class);
		registerNode(SyntaxType.SINE, SIN.class);
		registerNode(SyntaxType.COSINE, COS.class);
		registerNode(SyntaxType.TANGENT, TAN.class);
		registerNode(SyntaxType.ARCTANGENT, ATAN.class);
		registerNode(SyntaxType.NATURALLOG, LOG.class);
		registerNode(SyntaxType.POWER, POW.class);
		registerNode(SyntaxType.PI, PI.class);
		//// #
		//// # Boolean Operations
		//// #
		registerNode(SyntaxType.LESSTHAN, LESSP.class);
		registerNode(SyntaxType.GREATERTHAN, GREATERP.class);
		registerNode(SyntaxType.EQUAL, EQUALP.class);
		registerNode(SyntaxType.NOTEQUAL, NOTEQUALP.class);
		registerNode(SyntaxType.AND, AND.class);
		registerNode(SyntaxType.OR, OR.class);
		registerNode(SyntaxType.NOT, NOT.class);
		//// #
		//// # Variables, Control, and User-Defined Commands
		//// #
		registerNode(SyntaxType.MAKEVARIABLE, MAKEVAR.class);
		registerNode(SyntaxType.REPEAT, REPEAT.class);
		registerNode(SyntaxType.DOTIMES, DOTIMES.class);
		registerNode(SyntaxType.FOR, FOR.class);
		registerNode(SyntaxType.IF, IF.class);
		registerNode(SyntaxType.IFELSE, IFELSE.class);
		registerNode(SyntaxType.MAKEUSERINSTRUCTION, TOCOMMAND.class);
		//// #
		//// # Display Commands
		//// #
		registerNode(SyntaxType.SETBACKGROUND, SETBG.class);
		registerNode(SyntaxType.SETPENCOLOR, SETPC.class);
		registerNode(SyntaxType.SETPENSIZE, SETPS.class);
		registerNode(SyntaxType.SETSHAPE, SETSH.class);
		registerNode(SyntaxType.SETPALETTE, SETPALETTE.class);
		registerNode(SyntaxType.GETPENCOLOR, PC.class);
		registerNode(SyntaxType.GETSHAPE, SH.class);
		registerNode(SyntaxType.STAMP, STAMP.class);
		registerNode(SyntaxType.CLEARSTAMPS, CLEARSTAMPS.class);
		registerNode(SyntaxType.CLEARSCREEN, CS.class);
		//// #
		//// # Multiple Turtle Commands
		//// #
		registerNode(SyntaxType.ID, ID.class);
		registerNode(SyntaxType.TURTLES, TURTLES.class);
		registerNode(SyntaxType.TELL, TELL.class);
		registerNode(SyntaxType.ASK, ASK.class);
		registerNode(SyntaxType.ASKWITH, ASKWITH.class);
		//// #
		//// # Non-Command
		//// #
		registerNode(SyntaxType.VARIABLE, Variable.class);
		registerNode(SyntaxType.CONSTANT, Constant.class);
		registerNode(SyntaxType.LISTSTART, LISTSTART.class);
		// LISTEND,
		registerNode(SyntaxType.GROUPSTART, GROUPSTART.class);// GROUPSTART,
		// GROUPEND,
		registerNode(SyntaxType.USERCOMMAND, USERCOMMAND.class);
		registerNode(SyntaxType.DUVALL, DUVALL.class);
		
		//SPECIAL EXTENSION 
		registerNode(SyntaxType.WRAP, WRAP.class);
		registerNode(SyntaxType.FENCE, FENCE.class);
	}

	public static void registerNode(SyntaxType type, Class<?> nodeClass) {
		myRegisteredCommands.put(type, nodeClass);
	}

	public Node createNode(SyntaxType type) {
		Node result = null;
		Class<?> nodeClass = (Class<?>) myRegisteredCommands.get(type);
		Constructor<?> nodeConstructor = null;
		try {
			nodeConstructor = nodeClass.getDeclaredConstructor(new Class[] {});
			result = (Node) nodeConstructor.newInstance();
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		return result;
	}
}
