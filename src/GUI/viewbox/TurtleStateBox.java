package GUI.viewbox;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import sharedobjects.Turtle;

public class TurtleStateBox extends TitledPane {

    private ObservableList<String> turtleData;
    private Map<Integer, String> turtleInfo;


    private ListView<String> listView;
    private final String stringFormat = "Turtle ID: %s\n" + "x=%s, y=%s\n" + "Visible=%s";

    public TurtleStateBox (){
        this.setText("Turtle State List");
        this.setStyle("-fx-border-color: black;");
        this.setPrefSize(400,200);   
        this.listView = new ListView<String>();
        this.turtleInfo = new HashMap<Integer, String>();
        this.setContent(listView);
    }

    public void updateTurtleStateBox(Turtle turtle){
        listView.setEditable(true);
        turtleInfo.put(turtle.getID(), createTurtleInfoString(turtle));
        turtleData = FXCollections.observableArrayList(new ArrayList<String>(turtleInfo.values()));
        listView.setItems(turtleData);
    }

    private String createTurtleInfoString(Turtle turtle){
        double x = turtle.getPosition()[0];
        double y = -turtle.getPosition()[1];
        return String.format(stringFormat, turtle.getID(), Math.abs(x)<0.5 ? 0:x, Math.abs(y)<0.5 ? 0:y, turtle.isShowing());
    }
}
