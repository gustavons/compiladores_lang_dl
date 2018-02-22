package inter.expr;

import lexer.Token;

public class Temp extends Expr {
	private static int count = 1;
	private int number;
	
	public Temp(Token type) {
		super(Token.TEMP, type);
		number = count++;
	}
	
	public int getNumber() {
		return number;
	}
	
	@Override	
	public Expr gen() {
		return this;
	}
	
	@Override
	public String toString() {
		return "%" + number;
	}
}
