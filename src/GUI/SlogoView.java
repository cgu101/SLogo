// This entire file is part of my masterpiece.
// Abhishek Upadhyaya Ghimire

package GUI;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

import GUI.button.*;
import GUI.dropdown.*;
import GUI.slider.*;
import GUI.textBox.*;
import GUI.turtlepane.*;
import GUI.viewbox.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import sharedobjects.UserInput;

public class SlogoView {

	private static final Dimension DEFAULT_SIZE = new Dimension(1200, 730);
	private static final String DEFAULT_RESOURCE_VIEW = "GUI.view";
	protected static ResourceBundle myResource;
	private static final String DEFAULT_LANGUAGE = "English";

	private Scene scene;

	private CommandPromptDisplayBox commandBox;
	private MessageDisplayBoxObserver messageBox;
	private VariableListBox variableDisplayBox;
	private CommandHistoryBox historyDisplayBox;
	private FunctionListBox functionDisplayBox;

	private CanvasObserver myTurtleCanvas;
	private TurtleGroupObserver myTurtleGroup;
	private BackgroundRectangleObserver myBackgroundRectangle;
	private TurtleStateBox turtleStateBox;
	private LineSlider lineSlider;
	private OpacitySlider opacitySlider;

	private UserInput myUserInputObservable;
	private Map<String, AButton> myButtons;
	private Label space;

	public SlogoView(CanvasObserver canvas, TurtleGroupObserver turtleGroup) {
		space = new Label(" ");
		myTurtleCanvas = canvas;
		myTurtleGroup = turtleGroup;

		myResource = ResourceBundle.getBundle(DEFAULT_RESOURCE_VIEW);
		commandBox = new CommandPromptDisplayBox();
		messageBox = new MessageDisplayBoxObserver();
		variableDisplayBox = new VariableListBox(commandBox);
		historyDisplayBox = new CommandHistoryBox(commandBox);
		functionDisplayBox = new FunctionListBox(commandBox);
		turtleStateBox = new TurtleStateBox();
		myUserInputObservable = new UserInput(DEFAULT_LANGUAGE);
		BorderPane root = new BorderPane();
		ButtonFactory buttonFactory = new ButtonFactory(commandBox, messageBox, historyDisplayBox, myTurtleGroup, myUserInputObservable, root);
		myButtons = buttonFactory.getButtons();

		root.setMaxSize(DEFAULT_SIZE.getWidth(), DEFAULT_SIZE.getHeight());
		root.setTop(menu());
		root.setCenter(centerBox());
		root.setBottom(bottomBox());
		root.setRight(rightBox());

		scene = new Scene(root, DEFAULT_SIZE.width, DEFAULT_SIZE.height);
	}

	public Scene getScene() {
		return scene;
	}

	private Node menu() {
		HBox result = new HBox();
		result.getChildren().addAll(myButtons.get("UploadButton"), createLanguageDropDown(),
				bgColorDropDown(), penColorDropDown(), lineTypeDropDown(), myButtons.get("HelpButton"));
		return result;
	}

	private Node bottomBox() {
		VBox result = new VBox();
		result.getChildren().addAll(messageAndClearBoxes(), commandAndEnterBoxes());
		return result;
	}

	private Node lineTypeDropDown() {
		ComboBox<String> lineType = new LineTypeDropdown();
		lineType.setOnAction(event -> {
			String line = lineType.getValue();
			myTurtleCanvas.setLineType(line);
			messageBox.setMessage(myResource.getString("lineTypeMessage")+ line);
		});
		return lineType;
	}

	private Node createLanguageDropDown() {
		ComboBox<String> languageDropDown = new LanguageListDropdown();
		languageDropDown.setOnAction(event -> {
			String lang = languageDropDown.getValue();
			myUserInputObservable.setCurrentLanguage(lang);
			messageBox.setMessage(myResource.getString("languageTypeMessage")+ lang);
		});
		return languageDropDown;
	}

	private Node bgColorDropDown() {
		ComboBox<String> bgColor = new BackgroundColorDropdown();
		bgColor.setOnAction(event -> {
			String bgColorString = bgColor.getValue();
			String color = bgColorString.substring(3);
			myBackgroundRectangle.setBackgroundColor(color);
			messageBox.setMessage(myResource.getString("backgroundColorMessage") + color);
		});
		return bgColor;
	}

	private Node penColorDropDown() {
		ComboBox<String> penColor = new PenColorDropdown();
		penColor.setOnAction(event -> {
			String penColorString = penColor.getValue();
			String color = penColorString.substring(3);
			myTurtleCanvas.setPenColor(color);
			messageBox.setMessage(myResource.getString("penColorMessage") + color);
		});
		return penColor;
	}

	private Node centerBox() {
		TabPane tabPane = new TabPane();
		AnchorPane mainBox = new AnchorPane();
		Tab tab = new Tab();
		myBackgroundRectangle = new BackgroundRectangleObserver(Integer.parseInt(myResource.getString("canvasWidth")),
				Integer.parseInt(myResource.getString("canvasHeight")));
		mainBox.getChildren().addAll(myBackgroundRectangle, myTurtleCanvas, myTurtleGroup);

		tab.setContent(mainBox);
		tab.setClosable(false);
		tab.setText("New Workspace");
		tabPane.getTabs().add(tab);
		return tabPane;
	}

	private Node messageAndClearBoxes() {
		return horizontalNodes(messageBox, myButtons.get("ClearCommandButton"), imageOpacitySlider());
	}

	private Node commandAndEnterBoxes() {
		return horizontalNodes(commandBox, myButtons.get("EnterCommandButton"), lineThicknessSlider());	
	}

	private HBox horizontalNodes(Node nodeA, Node nodeB, Node nodeC){
		HBox result = new HBox();
		result.getChildren().addAll(nodeA,nodeB,nodeC);
		return result;
	}

	private Node lineThicknessSlider() {
		lineSlider = new LineSlider();
		return horizontalNodes(lineSlider, space, sliderProperty(myResource.getString("lineSliderCaption"), lineSlider));
	}

	private Node imageOpacitySlider() {
		opacitySlider = new OpacitySlider();
		return horizontalNodes(opacitySlider, space, sliderProperty(myResource.getString("opacitySliderCaption"), opacitySlider));
	}

	private Label sliderProperty(String label, Slider slider) {
		Label caption = new Label(label);
		caption.setTextFill(Color.BLUE);
		slider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				caption.setText(label + String.format("%.1f  ", newValue));
				myTurtleCanvas.setPenWidth(newValue.doubleValue());
				myTurtleGroup.changeOpacity(newValue.doubleValue());
			}
		});
		return caption;		
	}

	private VBox rightBox() {
		VBox result = new VBox();
		result.getChildren().addAll(variableDisplayBox, historyDisplayBox, functionDisplayBox, turtleStateBox);
		return result;
	}

	public List<Observable> getObservables() {
		@SuppressWarnings("serial")
		List<Observable> array = new ArrayList<Observable>() {
			{
				add(myUserInputObservable);
			}
		};
		return array;
	}

	public TurtleGroupObserver getTurtlePaneGroup() {
		return myTurtleGroup;
	}

	public CanvasObserver getTurtlePaneCanvas() {
		return myTurtleCanvas;
	}

	public FunctionListBox getFunctionDisplayBox() {
		return functionDisplayBox;
	}

	public VariableListBox getVariableDisplayBox() {
		return variableDisplayBox;
	}

	public TurtleStateBox getTurtleStateBox() {
		return turtleStateBox;
	}

	public void showError(Exception e) {
		messageBox.setMessage(e.toString());
	}

	public Observer getMessageBox() {
		return messageBox;
	}

	public Observer getRect() {
		return myBackgroundRectangle;
	}
}