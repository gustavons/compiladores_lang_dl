package inter.expr.logical;

import inter.expr.Expr;
import inter.expr.Literal;
import inter.expr.Temp;
import lexer.Token;

public class Or extends Expr {
	protected Expr expr1;
	protected Expr expr2;
	
	public Or(Expr e1, Expr e2) {
		super(Token.OR, Token.BOOL);
		if ( !e1.type().isBool() ||  !e2.type().isBool() )
			error("O operador lógico | só pode ser aplicado entre tipos booleanos");
		expr1 = e1;
		expr2 = e2;
		addChild(expr1);
		addChild(expr2);
	}

	@Override
	public Expr gen() {
		int t = code.newLabel();
		int f = code.newLabel();
		int out = code.newLabel();
		Temp d1 = new Temp(Token.BOOL);
		code.emitAlloca(d1);
		jumping(t, f);
		code.emitLabel(t);
		code.emitStore(d1, Literal.LIT_TRUE );
		code.emitBreak(out);
		code.emitLabel(f);
		code.emitStore(d1, Literal.LIT_FALSE);
		code.emitBreak(out);
		code.emitLabel(out);
		Temp d2 = new Temp(Token.BOOL);
		code.emitLoad(d2, d1);
		return d2;
	}
	
	@Override
	public void jumping(int t, int f) {
		int label = code.newLabel();
		expr1.jumping(t, label);
		code.emitLabel(label);
		expr2.jumping(t, f);
	}	
}
