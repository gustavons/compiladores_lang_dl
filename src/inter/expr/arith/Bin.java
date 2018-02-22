package inter.expr.arith;

import inter.expr.Expr;
import inter.expr.Temp;
import lexer.Token;

public class Bin extends Expr {

	protected Expr expr1;
	protected Expr expr2;

	public Bin( Token op, Expr e1, Expr e2 ) {
		super(op, null);
		type = Token.maxNumType( e1.type(), e2.type() );
		if ( this.type == null ) error("tipos incompatíveis");
		expr1 = e1;
		expr2 = e2;
		addChild(expr1);
		addChild(expr2);
	}

	@Override
	public Expr gen() {
		Expr e1 = expr1.gen();
		Expr e2 = expr2.gen();
		Expr op1 = widen(e1, e2.type() );
		Expr op2 = widen(e2, e1.type() );
		Temp d = new Temp(type);
		code.emitInstruction(d, op1, op2, op);
		return d;
	}
}
