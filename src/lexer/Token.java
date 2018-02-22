package lexer;

public class Token {
	//Reserved Words
	public static final Token PROGRAM = new Token(Tag.PROGRAM, "programa");
	public static final Token BEGIN = new Token(Tag.BEGIN, "inicio");
	public static final Token END = new Token(Tag.END, "fim");
	public static final Token WRITE = new Token(Tag.WRITE, "escreva");
	public static final Token IF = new Token(Tag.IF, "se");
	public static final Token TRUE = new Token(Tag.TRUE, "verdadeiro");
	public static final Token FALSE = new Token(Tag.FALSE, "falso");	
	//Types
	public static final Token INT = new Token(Tag.INT, "inteiro");
	public static final Token REAL = new Token(Tag.REAL, "real");
	public static final Token BOOL = new Token(Tag.BOOL, "booleano");
	//Assign
	public static final Token ASSIGN = new Token(Tag.ASSIGN, "=");
	//Arithmetical Operators
	public static final Token SUM = new Token(Tag.SUM, "+");
	public static final Token SUB = new Token(Tag.SUB, "-");
	public static final Token MUL = new Token(Tag.MUL, "*");
	//Logical Operators
	public static final Token OR = new Token(Tag.OR, "|");
	//Relational Operators
	public static final Token EQ = new Token(Tag.EQ, "==");
	public static final Token LT = new Token(Tag.LT, "<");
	public static final Token LE = new Token(Tag.LE, "<=");
	//Symbols
	public static final Token SEMI = new Token(Tag.SEMI, ";");
	public static final Token DOT = new Token(Tag.DOT, ".");
	public static final Token LPAREN = new Token(Tag.LPAREN, "(");
	public static final Token RPAREN = new Token(Tag.RPAREN, ")");
	//EoF
	public static final Token EOF = new Token(Tag.EOF, "EoF");
	//Lexical Tokens
	public static final Token TEMP = new Token(Tag.TEMP, "t");

	private Tag tag;
	private String lexeme;

	public Token(Tag t, String l) {
		tag = t;
		lexeme = l;
	}

	public Tag tag() {
		return tag;
	}

	public String lexeme() {
		return lexeme;
	}

	@Override
	public String toString() {
		String s = "<" + tag + ", '" + lexeme + "'>";
		return s;
	}
	
	public boolean isInt() {
		return tag == Tag.INT;
	}

	public boolean isReal() {
		return tag == Tag.REAL;
	}
	
	public boolean isBool() {
		return tag == Tag.BOOL;
	}
	
	public boolean isNum() {
		return (isInt() || isReal());
	}

	public boolean isType() {
		return isNum() || isBool();
	}

	public static Token maxNumType( Token t1, Token t2 ) {
		if ( !t1.isNum() || !t2.isNum() )
			return null;
		else if ( t1.isReal() || t2.isReal() )
			return Token.REAL;
		else
			return Token.INT;
	}
}