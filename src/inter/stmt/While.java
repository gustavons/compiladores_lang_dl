package inter.stmt;

import inter.expr.Expr;

public class While extends Stmt {

	private Expr expr;
	private Stmt stmt;
	
	public While(Expr e, Stmt s) {
//		if ( !e.type().isBool() )
//			error("esperada uma expressão lógica");
		expr = e;
		stmt = s;
		addChild(expr);
		addChild(stmt);
	}

	@Override
	public void gen() {
		int l1 = code.newLabel();
		int l2 = code.newLabel();
		int l3 = code.newLabel();
		code.emitBreak(l1);
		code.emitLabel(l1);
		expr.jumping(l2, l3);
		code.emitLabel(l2);
		stmt.gen();
		code.emitBreak(l1);
		code.emitLabel(l3);
	}

	@Override
	public String toString() {
		return "while";
	}
}
