package GUI;

import java.io.File;

import GUI.turtlepane.TurtleGroupObserver;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class TurtleStatusView {
	private TurtleGroupObserver turtleGroupObserver;
	private Image newTurtleImage;
	private ImageView turtleImage;
	private HBox myBox;
	private ScrollPane scrollPane;

	public TurtleStatusView() {
		turtleGroupObserver = new TurtleGroupObserver();
		initialize();
	}

	public void initialize(){

		Group rootMain = new Group();
		Stage stage = new Stage();
		stage.setTitle("Turtle Status View");
		Scene scene = new Scene(new Group());

		stage.setWidth(600);
		stage.setHeight(300);
		stage.show();

		scrollPane = new ScrollPane();
		scrollPane.setContent(imageTurtle());
		scrollPane.setPrefSize(600, 300);
		scrollPane.setFitToHeight(true);
		scrollPane.setFitToWidth(true);

		rootMain.getChildren().add(scrollPane);
		scene.setRoot(rootMain);

		stage.setScene(scene);
		stage.show();
	}

	public Node imageTurtle(){
		myBox = new HBox();
		setNewTurtleImage(turtleGroupObserver.getImage());
		Image myTurtleImage = getNewTurtleImage();
		turtleImage = new ImageView(myTurtleImage);
		myBox.getChildren().add(turtleImage);
		myBox.setOnMouseClicked(e->changeImage());
		return myBox;
	}

	public Image getNewTurtleImage() {
		return newTurtleImage;
	}

	public void setNewTurtleImage(Image newTurtleImage) {
		this.newTurtleImage = newTurtleImage;
	}

	private void changeImage(){
		FileChooser fileChooser = new FileChooser();
		File selectedFile = fileChooser.showOpenDialog(null);
		String fileName;

		if (selectedFile != null) {
			fileName = selectedFile.getName();
			myBox.getChildren().clear();
			setNewTurtleImage(new Image(getClass().getClassLoader().getResourceAsStream(fileName)));
			turtleGroupObserver.setImage(new Image(getClass().getClassLoader().getResourceAsStream(fileName)));
			myBox.getChildren().add(new ImageView(getNewTurtleImage()));

		}
		else {
			if (selectedFile == null) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Alert Message");
				String label = null;
				label = "No file selected";
				alert.setContentText(label);
				alert.showAndWait();            }
		}
	}
}

