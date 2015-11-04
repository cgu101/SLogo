// This entire file is part of my masterpiece.
// Inan Tainwala

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

/**
 * @author Inan and Abhishek
 *
 */
public class SlogoView {
	private static final Dimension DEFAULT_SIZE = new Dimension(1200, 730);
	private static final String DEFAULT_RESOURCE_VIEW = "GUI.view";
	private static final String DEFAULT_LANGUAGE = "English";

	protected static ResourceBundle myResource;

	private Scene scene;

	private CanvasObserver myTurtleCanvas;
	private TurtleGroupObserver myTurtleGroup;
	private UserInput myUserInputObservable;
	
	private CommandPromptDisplayBox commandBox;
	private MessageDisplayBoxObserver messageBoxObserver;
	private VariableListBox variableDisplayBox;
	private CommandHistoryBox historyDisplayBox;
	private FunctionListBox functionDisplayBox;
	private BackgroundRectangleObserver backgroundRectangleObserver;

	private Map<String, AButton> myButtons;

	private HBox menuBar;
	private VBox bottomWindow;
	private VBox rightWindow;
	private HBox leftWindow;
	private HBox centerWindow;
	
	
	public SlogoView(CanvasObserver canvas, TurtleGroupObserver turtleGroup) {
	        myResource = ResourceBundle.getBundle(DEFAULT_RESOURCE_VIEW);
	        myTurtleCanvas = canvas;
		myTurtleGroup = turtleGroup;
		factory();
		scene = new Scene(initializeLayout(), DEFAULT_SIZE.width, DEFAULT_SIZE.height);
	}
	
	private BorderPane initializeLayout () {
	        BorderPane root = new BorderPane();
	        root.setMaxSize(DEFAULT_SIZE.getWidth(), DEFAULT_SIZE.getHeight());
	        root.setTop(menu());
	        root.setCenter(centerBox());
	        root.setBottom(bottomBox());
	        root.setRight(rightBox());
	        root.setLeft(leftBox());
                return root;
        }

        private Node leftBox () {
                leftWindow = new HBox();
                return leftWindow;
        }

        private void factory(){
	        commandBox = new CommandPromptDisplayBox();
                messageBoxObserver = new MessageDisplayBoxObserver();
                variableDisplayBox = new VariableListBox(commandBox);
                historyDisplayBox = new CommandHistoryBox(commandBox);
                functionDisplayBox = new FunctionListBox(commandBox);
                myUserInputObservable = new UserInput(DEFAULT_LANGUAGE);
                myButtons = new ButtonFactory(commandBox, messageBoxObserver, historyDisplayBox, myTurtleGroup,
                                              myUserInputObservable).getButtons();
	}

	private Node menu() {
		menuBar = new HBox();
		menuBar.getChildren().addAll(createFileDropDown(), myButtons.get("UploadButton"), createLanguageDropDown(),
				bgColorDropDown(), penColorDropDown(), lineTypeDropDown(), myButtons.get("HelpButton"));
		return menuBar;
	}

	private Node bottomBox() {
	        bottomWindow = new VBox();
	        bottomWindow.getChildren().add(messageAndClearBoxes());
	        bottomWindow.getChildren().add(commandAndEnterBoxes());
		return bottomWindow;
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
			messageBoxObserver.setMessage(text + " executed");
		});
		return fileDropDown;
	}

	private Node lineTypeDropDown() {
		ComboBox<String> lineType = new LineTypeDropdown();
		lineType.setOnAction(event -> {
			String line = lineType.getValue();
			myTurtleCanvas.setLineType(line);
			messageBoxObserver.setMessage("Line type set to " + line);
		});
		return lineType;
	}

	private Node createLanguageDropDown() {
		ComboBox<String> languageDropDown = new LanguageListDropdown();
		languageDropDown.setOnAction(event -> {
			String lang = languageDropDown.getValue();
			myUserInputObservable.setCurrentLanguage(lang);
			messageBoxObserver.setMessage("Language Set to " + lang);
		});
		return languageDropDown;
	}

	private Node bgColorDropDown() {
		ComboBox<String> bgColor = new BackgroundColorDropdown();
		bgColor.setOnAction(event -> {
			String bgColorString = bgColor.getValue();
			String color = bgColorString.substring(3);
			backgroundRectangleObserver.setBackgroundColor(color);
			messageBoxObserver.setMessage("Background Color Set to " + color);
		});
		return bgColor;
	}

	private Node penColorDropDown() {
		ComboBox<String> penColor = new PenColorDropdown();
		penColor.setOnAction(event -> {
			String penColorString = penColor.getValue();
			String color = penColorString.substring(3);
			myTurtleCanvas.setPenColor(color);
			messageBoxObserver.setMessage("Pen Color Set to " + color);
		});
		return penColor;
	}

	private VBox rightBox() {
	    rightWindow = new VBox();
	    rightWindow.getChildren().addAll(variableDisplayBox, historyDisplayBox, functionDisplayBox);
	    return rightWindow;
	}

	private Node centerBox() {
	        centerWindow = new HBox();
		TabPane tabPane = new TabPane();
		AnchorPane mainBox = new AnchorPane();
		Tab tab = new Tab();
		backgroundRectangleObserver = new BackgroundRectangleObserver();
		mainBox.getChildren().addAll(backgroundRectangleObserver, myTurtleCanvas, myTurtleGroup);

		tab.setContent(mainBox);
		tab.setClosable(false);
		tab.setText("New Workspace");
		tabPane.getTabs().add(tab);
		centerWindow.getChildren().add(tabPane);
		return centerWindow;
	}

	private Node messageAndClearBoxes() {
		HBox topHalf = new HBox();
		topHalf.getChildren().addAll(messageBoxObserver, myButtons.get("ClearCommandButton"), imageOpacitySlider());
		return topHalf;
	}

	private Node commandAndEnterBoxes() {
		HBox bottomHalf = new HBox();
		bottomHalf.getChildren().addAll(commandBox, myButtons.get("EnterCommandButton"), lineThicknessSlider());
		return bottomHalf;
	}

	private HBox lineThicknessSlider() {
		HBox thicknessSlider = new HBox();
		LineSlider lineSlider = new LineSlider();
		lineSlider.setValue(myTurtleCanvas.getPenWidth());

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
		OpacitySlider opacitySlider = new OpacitySlider();
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

	public void showError(Exception e) {
		messageBoxObserver.setMessage(e.toString());
	}

	public Observer getMessageBox() {
		return messageBoxObserver;
	}
	
	public Observer getRect() {
		return backgroundRectangleObserver;
	}
	
	
	/**Description: This method acts as an API that can be used to add elements to
	 * the right window of the IDE.
	 * 
	 * @param node A JavaFX node 
	 */
	public void addElementToRightWindow(Node node){
	        rightWindow.getChildren().add(node);
	}
	
	/**Description: This method acts as an API that can be used to add elements to
         * the left window of the IDE.
         * 
         * @param node A JavaFX node
         */
	public void addElementToLefttWindow(Node node){
	        leftWindow.getChildren().add(node);
        }

	/**Description: This method acts as an API that can be used to add elements to
         * the bottom window of the IDE.
         * 
         * @param node A JavaFX node
         */
	public void addElementToBottomWindow(Node node){
	        centerWindow.getChildren().add(node);
	}
	
	/**Description: This method acts as an API that can be used to add elements to
         * the top window of the IDE.
         * 
         * @param node A JavaFX node
         */
	public void addElementToMenu(Node node){
                menuBar.getChildren().add(node);
        }
	
	/**Description: This method acts as an API that can be used to add elements to
         * the center window of the IDE.
         * 
         * @param node A JavaFX node
         */
	public void addElementToCenterWindow(Node node){
                centerWindow.getChildren().add(node);
        }
}
