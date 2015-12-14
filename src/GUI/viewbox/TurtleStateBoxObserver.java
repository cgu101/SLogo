package GUI.viewbox;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import sharedobjects.Turtle;
import sharedobjects.TurtleContainer;

/**
 * @author Inan
 *
 */
public class TurtleStateBoxObserver extends TitledPane implements Observer{

    private ObservableList<String> turtleData;
    private Map<Integer, String> turtleInfo;
    private Map<Integer, Image> turtleImages;
    private ListView<String> listView;
    private final String stringFormat = "Turtle ID: %s\n" + "x=%s, y=%s\n" + "Visible=%s";

    public TurtleStateBoxObserver(Map<Integer,Image> turtleImages){
        this.setText("Turtle State List");
        this.setStyle("-fx-border-color: black;");
        this.setPrefSize(400,200);   
        this.listView = new ListView<String>();
        this.turtleInfo = new HashMap<Integer, String>();
        this.setContent(listView);
        this.turtleImages = turtleImages;
    }
    
    public void updateTurtleStateBox(Turtle turtle){
        listView.setEditable(true);
        turtleInfo.put(turtle.getID(), createTurtleInfoString(turtle));
        turtleData = FXCollections.observableArrayList(new ArrayList<String>(turtleInfo.values()));
        listView.setItems(turtleData);
        listView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
			@Override
			public ListCell<String> call(ListView<String> list) {
				TurtleBrowserCell cell = new TurtleBrowserCell(turtleImages);
				cell.addEventFilter(MouseEvent.MOUSE_PRESSED, event ->uploadTurtleImage(cell.getID()));
				return cell;
			}
		});
        
        
    }
    
    private void uploadTurtleImage (int id) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        String fileName;
        if (selectedFile != null) {
            fileName = selectedFile.getName();
            setImageForTurtle(new Image(getClass().getClassLoader().getResourceAsStream(fileName)), id);
            System.out.println(" uploaded");
        }
        else {
            if (selectedFile == null) {
            	System.out.println(" failed");
            }
        }
    }

	
	private void setImageForTurtle(Image newImage, int turtleID){
		this.turtleImages.put(turtleID, newImage);
	}
	
    private String createTurtleInfoString(Turtle turtle){
        double x = turtle.getPosition()[0];
        double y = -turtle.getPosition()[1];
        return String.format(stringFormat, turtle.getID(), Math.abs(x)<0.5 ? 0:x, Math.abs(y)<0.5 ? 0:y, turtle.isShowing());
    }

    @Override
    public void update (Observable o, Object arg) {
        TurtleContainer turtleContainer = (TurtleContainer) o;
                for(Turtle t: turtleContainer.getAllTurtles().values()){
                        updateTurtleStateBox(t);                       
                }
    }
}
