package GUI.button;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

/**
 * @author Inan
 *
 */
public abstract class AButton extends Button{

    public AButton (String text, EventHandler<ActionEvent> event) {
        super(text);
        this.setPrefWidth(150);
        this.setStyle("-fx-border-color: black;");
        this.setOnAction(event);
    }
}
