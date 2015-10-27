package datatransferobjects;

public class UserInputTransferObject {

    String language;
    String userInput;
    public UserInputTransferObject (String lang, String input) {
        this.language = lang;
        this.userInput = input;
    }
    public String getLanguage () {
        return language;
    }
    
    public String getUserInput () {
        return userInput;
    }   
}
