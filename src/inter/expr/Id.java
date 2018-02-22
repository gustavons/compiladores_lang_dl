package inter.expr;

import lexer.Token;

public class Id extends Expr {
	protected Temp address;
	
	public Id(Token op, Token type) { 
		super(op, type);
		address = null;
	}
	
	public void setAddress(Temp t) {
		address = t;
	}

	public Temp getAddress() {
		return address;
	}

	@Override
	public Expr gen() {
		Temp d = new Temp(type);
		code.emitLoad(d, address);
		return d;
	}
	
	@Override
	public void jumping(int t, int f) {
		Expr e = this.gen();
		code.emitBreak(e, t, f);
	}
}