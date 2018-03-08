package lexer;

public enum Tag {
	//Reserved Words
	PROGRAM("PROGRAM"), BEGIN("BEGIN"), END("END"),			/*header and blocks*/
	INT("INT"), REAL("REAL"), BOOL("BOOL"),					/*types*/
	WRITE("WRITE"), IF("IF"), READ("READ"),					/*Statements*/
	//Assign
	ASSIGN("ASSIGN"),
	//Arithmetical Operators
	SUM("SUM"), SUB("SUB"), MUL("MUL"), 
	//Logical Operators
	OR("OR"),
	//Relational Operators
	EQ("EQ"), LT("LT"), LE("LE"),
	//Symbols
	SEMI("SEMI"), DOT("DOT"), LPAREN("LPAREN"), RPAREN("RPAREN"),
	//Literals and Identifiers
	LIT_INT("LIT_INT"), LIT_REAL("LIT_REAL"), ID("ID"),
	TRUE("TRUE"), FALSE("FALSE"), 
	//Others
	EOF("EOF"), UNK("UNK"), TEMP("TEMP");
	
	private String str;
	
	private Tag(String s) {
		this.str = s;
	}
	
	@Override
	public String toString() {
		return str;
	}
}
