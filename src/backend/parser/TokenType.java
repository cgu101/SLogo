package backend.parser;

public enum TokenType{
	COMMENT(0),CONSTANT(1),VARIABLE(2),COMMAND(3),LISTSTART(4),LISTEND(5),GROUPSTART(6),GROUPEND(7);
	private int myCode;
	private TokenType(int code) {
        this.myCode = code;
    }
};