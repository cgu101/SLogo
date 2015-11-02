package GUI.button;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import GUI.textBox.CommandPromptDisplayBox;
import GUI.textBox.MessageDisplayBoxObserver;
import GUI.turtlepane.TurtleGroupObserver;
import GUI.viewbox.CommandHistoryBox;
import datatransferobjects.UserInputTransferObject;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sharedobjects.UserInput;

public class ButtonFactory {
	private static final String DEFAULT_RESOURCE_BUTTON = "GUI.button.buttons";
	protected static ResourceBundle myResource;

    private Map<String, AButton> myButtons;
    private CommandPromptDisplayBox myCommandBox; 
    private MessageDisplayBoxObserver myMessageBox;
    private CommandHistoryBox myHistoryDisplayBox;
    private TurtleGroupObserver myTurtleGroup;
    private UserInput myUserInputObservable;
    
    public ButtonFactory (CommandPromptDisplayBox commandBox, MessageDisplayBoxObserver messageBox, CommandHistoryBox historyDisplayBox, TurtleGroupObserver turtleGroup, UserInput userInputObservable, BorderPane main) {
        myResource = ResourceBundle.getBundle(DEFAULT_RESOURCE_BUTTON);
        this.myCommandBox = commandBox;
        this.myHistoryDisplayBox = historyDisplayBox;
        this.myMessageBox = messageBox;
        this.myTurtleGroup = turtleGroup;
        this.myUserInputObservable = userInputObservable;
        myButtons = new HashMap<String, AButton>();
        createButtons();
    }
    
    public void createButtons(){
        String[] buttons = myResource.getString("buttons").split(",");
        HashMap<String, EventHandler<ActionEvent>> buttonEventHandle = new HashMap<String, EventHandler<ActionEvent>>();
        buttonEventHandle.put(buttons[0], event->helpButtonEvent());
        buttonEventHandle.put(buttons[1], event->enterButtonEvent());
        buttonEventHandle.put(buttons[2], event->clearButtonEvent());
        buttonEventHandle.put(buttons[3], event->uploadButtonEvent());
        for(String buttonClassName: buttons){
            try {
                Constructor<?> c =
                        Class.forName("GUI.button."+buttonClassName)
                        .getConstructor(EventHandler.class);
                AButton button = (AButton) c.newInstance(buttonEventHandle.get(buttonClassName));
                myButtons.put(buttonClassName, button);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    public Map<String, AButton> getButtons(){
        return myButtons;
    }
    
    private void clearButtonEvent(){
        myMessageBox.clear();
        myCommandBox.clear();
    }
    private void uploadButtonEvent() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        String fileName;
        
        if (selectedFile != null) {
            fileName = selectedFile.getName();
            myTurtleGroup.setImage(new Image(getClass().getClassLoader().getResourceAsStream(fileName)));
            
            myMessageBox.setMessage(fileName + " uploaded");
        }
        else {
            if (selectedFile == null) {
                myMessageBox.setMessage("Upload Cancelled");
            }
        }
    }
            
            
    private void enterButtonEvent(){
        myMessageBox.clear();
        String userInput = myCommandBox.getText();
        myHistoryDisplayBox.setMessage(userInput);
        myUserInputObservable.setUserInput(userInput);
        UserInputTransferObject ut = new UserInputTransferObject(myUserInputObservable.getCurrentLanguage(), myUserInputObservable.getUserInput());
        try{
            myUserInputObservable.notifyObservers(ut);
        }catch(Exception e){
            myMessageBox.setMessage("Error: " + e.getMessage());
        }
    }

	private void helpButtonEvent() {
		Group rootMain = new Group();
		Stage stage = new Stage();
		stage.setTitle("Help Page");
		Scene scene = new Scene(new Group());

		stage.setWidth(1200);
		stage.setHeight(700);
		stage.show();

		WebView browser = new WebView();
		String url = getClass().getClassLoader().getResource("help.html").toExternalForm();

		WebEngine webEngine = browser.getEngine();
		webEngine.load(url); 

		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setContent(browser);
		scrollPane.setPrefSize(1200, 700);
		scrollPane.setFitToHeight(true);
		scrollPane.setFitToWidth(true);

		rootMain.getChildren().add(scrollPane);
		scene.setRoot(rootMain);

		stage.setScene(scene);
		stage.show();

	}
}
