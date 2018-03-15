package inter.stmt;

import inter.expr.Expr;
import inter.expr.Id;

public class Read  extends Stmt {

	private Id id;

	public Read(Id i) {
		//if (!id.getType().isNumericType()) error("Só é possível escrever tipos numéricos");
		id = i;
		addChild(id);
	}

	@Override
	public void gen() {
		Expr e = id.gen();
		code.emitRead(id.getAddress());
	}

	@Override
	public String toString() {
		return "read";
	}
}
