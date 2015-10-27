package GUI.viewbox;

import GUI.textBox.CommandPromptDisplayBox;

public class CommandHistoryBox extends AViewBox {

    public CommandHistoryBox (CommandPromptDisplayBox display){
        super(display);
        this.setText("Command History List");

    }

    @Override
    void setStyle () {
        listView.setStyle("-fx-border-color: black;");        
    }

    @Override
    void setSize () {
        listView.setPrefSize(400,200);        
    }

}
