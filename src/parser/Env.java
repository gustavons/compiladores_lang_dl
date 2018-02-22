package parser;

import java.util.Hashtable;

import inter.expr.Id;
import lexer.Token;

public class Env {

	private Hashtable<String, Id> table;

	public Env() { 
		table = new Hashtable<String, Id>(); 
	}

	public void put(Token t, Id id) { 
		table.put(t.lexeme(), id); 
	}

	public Id get(Token t) {
		Id found = (Id)(table.get(t.lexeme()));
		return found;
	}
}
