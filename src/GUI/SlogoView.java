package GUI;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;
import GUI.button.AButton;
import GUI.button.ButtonFactory;
import GUI.dropdown.BackgroundColorDropdown;
import GUI.dropdown.FileDropdown;
import GUI.dropdown.LanguageListDropdown;
import GUI.dropdown.LineTypeDropdown;
import GUI.dropdown.PenColorDropdown;
import GUI.slider.LineSlider;
import GUI.slider.OpacitySlider;
import GUI.textBox.CommandPromptDisplayBox;
import GUI.textBox.MessageDisplayBoxObserver;
import GUI.turtlepane.BackgroundRectangleObserver;
import GUI.turtlepane.CanvasObserver;
import GUI.turtlepane.TurtleGroupObserver;
import GUI.viewbox.CommandHistoryBox;
import GUI.viewbox.FunctionListBox;
import GUI.viewbox.TurtleStateBox;
import GUI.viewbox.VariableListBox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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

	public SlogoView(CanvasObserver canvas, TurtleGroupObserver turtleGroup) {

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
		ButtonFactory buttonFactory = new ButtonFactory(commandBox, messageBox, historyDisplayBox, myTurtleGroup,
				myUserInputObservable, root);

		myButtons = buttonFactory.getButtons();

		root.setMaxSize(DEFAULT_SIZE.getWidth(), DEFAULT_SIZE.getHeight());

		root.setTop(menu());
		root.setCenter(centerBox());
		root.setBottom(bottomBox());
		root.setRight(rightBox());

		scene = new Scene(root, DEFAULT_SIZE.width, DEFAULT_SIZE.height);
	}

	private Node menu() {
		HBox result = new HBox();
		result.getChildren().addAll(createFileDropDown(), myButtons.get("UploadButton"), createLanguageDropDown(),
				bgColorDropDown(), penColorDropDown(), lineTypeDropDown(), myButtons.get("HelpButton"));

		return result;
	}

	private Node bottomBox() {
		VBox result = new VBox();
		result.getChildren().add(messageAndClearBoxes());
		result.getChildren().add(commandAndEnterBoxes());

		return result;
	}

	public Scene getScene() {
		return scene;
	}

	private Node createFileDropDown() {
		ComboBox<String> fileDropDown = new FileDropdown();
		fileDropDown.setOnAction(event -> {
			String text = fileDropDown.getValue();
			if (text.equalsIgnoreCase("New Workspace")) {
				// centerBox();
			} else if (text.equalsIgnoreCase("Save Workspace")) {
				// centerBox();
			}
			messageBox.setMessage(text + " executed");

		});
		return fileDropDown;
	}

	private Node lineTypeDropDown() {
		ComboBox<String> lineType = new LineTypeDropdown();
		lineType.setOnAction(event -> {
			String line = lineType.getValue();
			myTurtleCanvas.setLineType(line);
			messageBox.setMessage("Line type set to " + line);
		});
		return lineType;
	}

	private Node createLanguageDropDown() {
		ComboBox<String> languageDropDown = new LanguageListDropdown();
		languageDropDown.setOnAction(event -> {
			String lang = languageDropDown.getValue();
			myUserInputObservable.setCurrentLanguage(lang);
			messageBox.setMessage("Language Set to " + lang);
		});
		return languageDropDown;
	}

	private Node bgColorDropDown() {
		ComboBox<String> bgColor = new BackgroundColorDropdown();
		bgColor.setOnAction(event -> {
			String bgColorString = bgColor.getValue();
			String color = bgColorString.substring(3);
			myBackgroundRectangle.setBackgroundColor(color);
			messageBox.setMessage("Background Color Set to " + color);
		});
		return bgColor;
	}

	private Node penColorDropDown() {
		ComboBox<String> penColor = new PenColorDropdown();
		penColor.setOnAction(event -> {
			String penColorString = penColor.getValue();
			String color = penColorString.substring(3);
			myTurtleCanvas.setPenColor(color);
			messageBox.setMessage("Pen Color Set to " + color);
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
		HBox result = new HBox();
		result.getChildren().addAll(messageBox, myButtons.get("ClearCommandButton"), imageOpacitySlider());
		return result;
	}

	private Node commandAndEnterBoxes() {
		HBox result = new HBox();
		result.getChildren().addAll(commandBox, myButtons.get("EnterCommandButton"), lineThicknessSlider());
		return result;
	}

	private HBox lineThicknessSlider() {
		HBox thicknessSlider = new HBox();
		lineSlider = new LineSlider();
		lineSlider.setValue(getTurtlePaneCanvas().getPenWidth());

		Label lineCaption = new Label(" Pen thickness: ");
		lineCaption.setTextFill(Color.BLUE);
		lineSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				lineCaption.setText(" Pen thickness: " + String.format("%.0f  ", newValue));
				myTurtleCanvas.setPenWidth(newValue.doubleValue());
			}
		});
		thicknessSlider.getChildren().addAll(lineSlider, lineCaption);
		return thicknessSlider;
	}

	private HBox imageOpacitySlider() {
		HBox imageSlider = new HBox();
		opacitySlider = new OpacitySlider();
		Label opacityCaption = new Label(" Turtle opacity: ");
		opacityCaption.setTextFill(Color.RED);
		opacitySlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				opacityCaption.setText(" Turtle opacity: " + String.format("%.2f  ", newValue));
				myTurtleGroup.changeOpacity(newValue.doubleValue());
			}
		});
		imageSlider.getChildren().addAll(opacitySlider, opacityCaption);
		return imageSlider;

	}

	private VBox rightBox() {
		VBox result = new VBox();
		result.getChildren().addAll(variableDisplayBox, historyDisplayBox, functionDisplayBox, turtleStateBox);
		return result;
	}

	public List<Observable> getObservables() {
		@SuppressWarnings("serial")
		List<Observable> a = new ArrayList<Observable>() {
			{
				add(myUserInputObservable);
			}
		};
		return a;
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
