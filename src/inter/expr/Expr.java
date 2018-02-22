package inter.expr;

import inter.Node;
import lexer.Token;

public abstract class Expr extends Node {
	protected Token op;
	protected Token type;

	public Expr(Token op, Token type) {
		this.op = op;
		this.type = type;
	}

	public Token op() {
		return op;
	}

	public Token type() {
		return type;
	}
	
	public void jumping(int t, int f) {
	}

	public abstract Expr gen(); //gera o IC de uma expressão, retornando o endreço que conterá o valor ou o próprio valor	

	@Override
	public String toString() {
		return op.lexeme();
	}
	
	public static Expr widen(Expr e, Token type) {
		if ( e.type == type || e.type().isReal() )
			return e;
		else if ( e.type().isInt() ) {
			Temp t = new Temp(Token.REAL);
			code.emitConvert(t, e);
			return t;
		}
		error("Tipos incompatíveis");
		return null;
	}
}
