package inter.stmt;

import inter.expr.Expr;
import inter.expr.Id;
import inter.expr.Temp;
import lexer.Token;

public class Assign extends Stmt {

	protected Id id;
	protected Expr expr;

	public Assign(Id i, Expr e) {
		if ( check(i.type(), e.type() ) == null )
			error("valor de expressão incompatível com o tipo da variável");
		id = i;
		expr = e;
		addChild(id);
		addChild(expr);
	}
	
	private static Token check(Token idType, Token exprType) {
		if (idType.isReal() && !exprType.isBool())
			return Token.REAL;
		else if (idType.isInt() && exprType.isInt())
			return Token.INT;
		else if (idType.isBool() && exprType.isBool())
			return Token.BOOL;
		else
			return null;
	}

	@Override
	public void gen() {
		Expr e = expr.gen();
		if ( id.type() == e.type() )
			code.emitStore(id.getAddress(), e);
		else {
			Temp t = new Temp( id.type() );
			code.emitConvert(t, e);
			code.emitStore(id.getAddress(), t);
		}
	}

	@Override
	public String toString() {
		return Token.ASSIGN.lexeme();
	}
}
