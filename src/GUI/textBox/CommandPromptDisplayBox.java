package GUI.textBox;

import javafx.scene.control.TextArea;

public class CommandPromptDisplayBox extends TextArea {

    public CommandPromptDisplayBox () {
        super();
        this.setPromptText("Enter command here");
        this.setStyle("-fx-border-color: black;");
        this.setPrefSize(650, 20);
    }
    
}
