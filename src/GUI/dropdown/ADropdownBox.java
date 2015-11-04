package GUI.dropdown;

import java.util.ResourceBundle;

import javafx.scene.control.ComboBox;

/**
 * @author Inan
 *
 */
public abstract class ADropdownBox extends ComboBox<String>{

    private static final String DROPDOWN_RESOURCE_PACKAGE = "GUI.Dropdown/dropdown";

    protected static ResourceBundle myResource;
    public ADropdownBox (String value) {
        super();
        myResource = ResourceBundle.getBundle(DROPDOWN_RESOURCE_PACKAGE);
        this.setValue(value);
        this.setEditable(false);
        this.setStyle("-fx-border-color: black;");
        this.promptTextProperty();
    }

}
