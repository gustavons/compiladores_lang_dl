package parser;

import inter.Node;
import inter.expr.Expr;
import inter.expr.Id;
import inter.expr.Literal;
import inter.expr.arith.Bin;
import inter.expr.arith.Unary;
import inter.expr.logical.Or;
import inter.expr.logical.Rel;
import inter.stmt.Assign;
import inter.stmt.Block;
import inter.stmt.Decl;
import inter.stmt.If;
import inter.stmt.Program;
import inter.stmt.Stmt;
import inter.stmt.Write;
import lexer.Lexer;
import lexer.Tag;
import lexer.Token;

public class Parser {
	private Lexer lexer;
	private Token look;
	private	Node root;
	private Env env;

	public Parser(Lexer lex) {
		lexer = lex;
		env = new Env();
		move();
	}

	public String parseTree() {
		return root.strTree();
	}

	public String code() {
		return Node.code();
	}

	private void error(String s) {
		System.err.println("linha " + Lexer.line() + ": " + s);
		System.exit(0);
	}

	private Token move()  {
		Token save = look;
		look = lexer.nextToken();
		return save;
	}

	private Token match(Tag t) {
		if ( look.tag() == t )
			return move();
		error("'" + look.lexeme() + "' inesperado");
		return null;
	}

	private Id findId( Token tokId ) {
		Id id = env.get(tokId); //****** SEMANTIC ******//
		if ( id == null )
			error("a variável '" + tokId.lexeme() + "' não foi declarada"); //****** SEMANTIC ******//
		return id;
	}

	public void parse() {
		Stmt p = program();
		root = p;
		p.gen();
	}

	private Stmt program() {
		match(Tag.PROGRAM);
		Token tokId = match(Tag.ID);
		Stmt b = block();
		match(Tag.DOT);
		match(Tag.EOF);
		return new Program(tokId, (Block)b);
	}

	private Stmt block() {
		Block b = new Block();
		match(Tag.BEGIN);
		while( look.tag() != Tag.END) {
			Stmt s = stmt();
			if (s != null) b.addStmt(s);
			match(Tag.SEMI);
		}
		match(Tag.END);
		return b;
	}

	private Stmt stmt() {
		switch ( look.tag() ) {
		case BEGIN: return block();
		case INT: case REAL: case BOOL: return decl();
		case WRITE: return writeStmt();
		case ID: return assign();
		case IF: return ifStmt();
		default: error("comando inválido");
		}
		return null;
	}
	
	private Stmt decl() {
		Token type = move();
		Token tokId = match(Tag.ID);
		if ( env.get(tokId) == null ) {
			Id id = new Id(tokId, type);
			env.put(tokId, id);
			return new Decl(id);
		}
		error("a variável '" + tokId.lexeme() + "' já foi declarada neste bloco");
		return null;
	}
	
	private Stmt writeStmt() {
		move();
		match(Tag.LPAREN);
		Id id = findId( match(Tag.ID) );
		match(Tag.RPAREN);
		return new Write(id);
	}

	private Stmt assign() {
		Id id = findId( match(Tag.ID) );
		match(Tag.ASSIGN);
		Expr e = expr();
		return new Assign(id, e);
	}
	
	private Stmt ifStmt() {
		match(Tag.IF);
		match(Tag.LPAREN);
		Expr e = expr();
		match(Tag.RPAREN);
		Stmt s1 = stmt();
		return new If(e, s1);
	}

	private Expr expr() {
		Expr e = equality();
		while( look.tag() == Tag.OR ) {
			move();  
			e = new Or(e, equality());
		}
		return e;
	}

	private Expr equality() {
		Expr x = rel();
		while ( look.tag() == Tag.EQ ) {
			Token tok = move();  
			x = new Rel(tok, x, rel());
		}
		return x;
	}

	private Expr rel() {
		Expr e = arith();
		while ( look.tag() == Tag.LT || 
				look.tag() == Tag.LE ) {
			Token op = move();
			e = new Rel(op, e, arith());
		}
		return e;
	}

	private Expr arith() {
		Expr e = term();
		while( look.tag() == Tag.SUM || 
			   look.tag() == Tag.SUB ) {
			Token op = move();
			e = new Bin(op, e, term());
		}
		return e;
	}

	private Expr term() {
		Expr e = unary();
		while(	look.tag() == Tag.MUL ) {
			Token op = move();
			e = new Bin(op, e, unary());
		}
		return e;
	}
		
	private Expr unary() {
		if ( look.tag() == Tag.SUB ) {
			Token op = move();
			return new Unary(op, unary());
		} else
			return factor();
	}

	private Expr factor() {
		Expr e = null;
		switch( look.tag() ) {
		case LPAREN:
			move();
			e = expr();
			match(Tag.RPAREN);
			break;
		case LIT_INT:
			e = new Literal(move(), Token.INT);
			break;
		case LIT_REAL:
			e = new Literal(move(), Token.REAL);
			break;
		case TRUE: case FALSE:
			e = new Literal(move(), Token.BOOL);
			break;
		case ID:
			e = findId( match(Tag.ID) );
			break;
		default:
			error("expressão inválida");
		}
		return e;
	}
}
