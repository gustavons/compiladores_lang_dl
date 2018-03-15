package lexer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;

public class Lexer {
	private static final char EOF_CHAR = (char)-1;
	private static int line = 1;
	private BufferedReader reader;
	private char peek;
	private Hashtable<String, Token> keywords;

	public Lexer(File file) {
		try {
			this.reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		this.peek = ' ';
		keywords = new Hashtable<String, Token>();
		reserve(Token.PROGRAM);
		reserve(Token.BEGIN);
		reserve(Token.END);
		reserve(Token.WRITE);
		reserve(Token.READ);
		reserve(Token.IF);
		reserve(Token.WHILE);
		reserve(Token.TRUE);
		reserve(Token.FALSE);
		reserve(Token.INT);
		reserve(Token.REAL);
		reserve(Token.BOOL);
	}

	public static int line() {
		return line;
	}

	private char nextChar() {
		if ( peek == '\n' ) line++;
		try {
			peek = (char)reader.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return peek;
	}

	private void reserve( Token t ) {
		keywords.put(t.lexeme(), t);
	}

	private static boolean isWhitespace(int c) {
		switch (c) {
		case ' ': case '\t': case '\n':
			return true;
		default: return false;
		}
	}

	private static boolean isIdentifierStart(int c) {
		return ( Character.isAlphabetic(c) || c == '_' );
	}

	private static boolean isIdentifierPart(int c) {
		return (isIdentifierStart(c) || Character.isDigit(c));
	}

	public Token nextToken() {
		while (isWhitespace(peek)) nextChar();
		switch(peek) {
		case '=':
			nextChar();
			if ( peek == '=' ) {
				nextChar();
				return Token.EQ;
			}
			return Token.ASSIGN;
		case '+':
			nextChar();
			return Token.SUM;
		case '-':
			nextChar();
			return Token.SUB;
		case '*':
			nextChar();
			if (peek == '*'){
				nextChar();
				return Token.POW;
			}
			return Token.MUL;
		case '|':
			nextChar();
			return Token.OR;
		case '<':
			nextChar();
			if ( peek == '=' ) {
				nextChar();
				return Token.LE;
			}
			return Token.LT;
		case ';':
			nextChar();
			return Token.SEMI;
		case '.':
			nextChar();
			return Token.DOT;
		case '(':
			nextChar();
			return Token.LPAREN;
		case ')':
			nextChar();
			return Token.RPAREN;
		case EOF_CHAR:
			return Token.EOF;
		default:
			if (Character.isDigit(peek)) {
				String num = "";
				
//				Inteiro
				do {
					num += peek;
					nextChar();
				} while( Character.isDigit(peek) );
				if ( peek != '.' ) return new Token(Tag.LIT_INT, num);
//				Real
				do {
					num += peek;
					nextChar();
				} while ( Character.isDigit(peek) );
				if ( peek != 'e' ) return new Token(Tag.LIT_REAL, num);
//				Cientifico 
				do {
					num += peek;
					nextChar();
				} while ( Character.isDigit(peek)|| peek == '+'||peek == '-');
				return new Token(Tag.LIT_REAL, num);
			} else if ( isIdentifierStart(peek)  ) {
				String id = "";
				do {
					id += peek;
					nextChar();
				} while ( isIdentifierPart(peek) );
				if ( keywords.containsKey(id) )
					return keywords.get(id);
				return new Token(Tag.ID, id );
			}
		}

		String unk = String.valueOf(peek);
		nextChar();
		return new Token(Tag.UNK, unk);
	}

}