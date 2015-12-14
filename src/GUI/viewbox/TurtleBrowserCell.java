package GUI.viewbox;

import java.util.Map;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class TurtleBrowserCell extends ListCell<String> {

	private Map<Integer, Image> turtleImages;
	private int ID;
	public TurtleBrowserCell(Map<Integer, Image> turtleImages) {
		this.turtleImages = turtleImages;
	}
	
	public int getID(){
		return ID;
	}
	
	
	@Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null) {
        	ID = Integer.parseInt(String.valueOf(item.charAt(11)));
            setGraphic(makeCell(ID, item));
        }
    }
	
	private Node makeCell(int id, String item){
		HBox box = new HBox(5);
		System.out.println(turtleImages.toString()+" "+id);
		if(turtleImages.containsKey(id)){
			box.getChildren().add(new ImageView(turtleImages.get(id)));
			System.out.println("add image");
		}
		box.setAlignment(Pos.CENTER_LEFT);
		Label label = new Label(item);
		box.getChildren().add(label);
		return box;
	}

}
