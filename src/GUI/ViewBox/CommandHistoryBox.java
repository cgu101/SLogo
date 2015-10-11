package GUI.ViewBox;

public class CommandHistoryBox extends ViewBox {

    public CommandHistoryBox (String text) {
        super(text);
    }

    @Override
    void setStyle () {
        textArea.setStyle("-fx-border-color: black;");        
    }

    @Override
    void setSize () {
        textArea.setPrefSize(400, 200);        
    }

}
