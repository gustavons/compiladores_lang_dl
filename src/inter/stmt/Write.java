package inter.stmt;

import inter.expr.Expr;
import inter.expr.Id;

public class Write extends Stmt {

	private Id id;

	public Write(Id i) {
		//if (!id.getType().isNumericType()) error("Só é possível escrever tipos numéricos");
		id = i;
		addChild(id);
	}

	@Override
	public void gen() {
		Expr e = id.gen();
		code.emitWrite(e);
	}

	@Override
	public String toString() {
		return "write";
	}
}
