package inter.expr.arith;

import inter.expr.Expr;
import inter.expr.Literal;
import inter.expr.Temp;
import lexer.Tag;
import lexer.Token;

public class Unary extends Expr {
	protected Expr expr;

	public Unary(Token op, Expr e) {
		super(op, null);
		if ( !e.type().isNum() )
			error("O operador unário deve ser aplicado a operandos numéricos.");
		expr = e;
		type = e.type();
		addChild(expr);
	}

	@Override
	public Expr gen() {
		Expr e = expr.gen();
		if ( op.tag() == Tag.SUB ) {
			Temp d = new Temp(type);
			Literal one = type.isInt() ? Literal.LIT_NEG_ONE_INT : Literal.LIT_NEG_ONE_REAL;
			code.emitInstruction(d, e, one, Token.MUL);
			return d;
		}
		return e; //A expressão é a mesma com o operador unário '+'
	}
}
