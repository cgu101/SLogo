package GUI.textBox;

import java.util.Observable;
import java.util.Observer;

import javafx.scene.control.TextArea;
import responses.Response;

/**
 * @author Inan
 *
 */
public class MessageDisplayBoxObserver extends TextArea implements Observer{

    public MessageDisplayBoxObserver () {
        super();
        this.setPromptText("Internal Message");
        this.setPrefSize(650, 20);
        this.setStyle("-fx-text-fill: blue; -fx-font-size: 14; -fx-background-color: red;");
        this.setWrapText(true);
        
    }

    public void setMessage(String message){
        this.clear();
        this.setText(message);
    }

	@Override
	public void update(Observable o, Object arg) {
		Response s = (Response) o;
		this.setPromptText(s.toString());
	}
}