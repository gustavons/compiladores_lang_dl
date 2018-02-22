package inter.expr;

import lexer.Tag;
import lexer.Token;

public class Literal extends Expr {
	public static final Literal LIT_TRUE = new Literal( Token.TRUE, Token.BOOL );
	public static final Literal LIT_FALSE = new Literal( Token.FALSE, Token.BOOL );
	public static final Literal LIT_ZERO_INT = new Literal( new Token(Tag.LIT_INT, "0"), Token.INT );
	public static final Literal LIT_ZERO_REAL = new Literal( new Token(Tag.LIT_REAL, "0.0"), Token.REAL );
	public static final Literal LIT_ONE_INT = new Literal( new Token(Tag.LIT_INT, "1"), Token.INT );
	public static final Literal LIT_ONE_REAL = new Literal( new Token(Tag.LIT_REAL, "1.0"), Token.REAL );
	public static final Literal LIT_NEG_ONE_INT = new Literal( new Token(Tag.LIT_INT, "-1"), Token.INT );
	public static final Literal LIT_NEG_ONE_REAL = new Literal( new Token(Tag.LIT_REAL, "-1.0"), Token.REAL );

	public Literal(Token op, Token type) {
		super(op, type);
	}

	@Override
	public Expr gen() {
		return this;
	}
	
	@Override
	public void jumping(int t, int f) {
		code.emitBreak(this, t, f);
	}
	
	@Override
	public String toString() {
		if ( op.tag() == Tag.TRUE )
			return "true";
		else if ( op.tag() == Tag.FALSE )
			return "false";
		else
			return op.lexeme();
	}
}
