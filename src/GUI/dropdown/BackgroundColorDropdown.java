package GUI.dropdown;

public class BackgroundColorDropdown extends ADropdownBox {
      
    public BackgroundColorDropdown () {
        super("Background Color");
        this.getItems().addAll(myResource.getString("backgroundColors").split(","));
    }

}
