package inter.stmt;

import inter.expr.Id;
import inter.expr.Literal;
import inter.expr.Temp;
import lexer.Token;

public class Decl extends Stmt {
	private Id id;

	public Decl(Id i) {
		id = i;
		addChild(id);
	}

	@Override
	public void gen() {
		Literal init = (id.type() == Token.REAL) ? Literal.LIT_ZERO_REAL : Literal.LIT_ZERO_INT;
		id.setAddress(new Temp(id.type()));
		code.emitAlloca(id.getAddress());
		code.emitStore(id.getAddress(), init);
	}

	@Override
	public String toString() {
		return "decl";
	}
}
