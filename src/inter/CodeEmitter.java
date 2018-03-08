package inter;

import inter.expr.Expr;
import inter.expr.Temp;
import lexer.Token;

public final class CodeEmitter {
	private StringBuffer code;
	private int label;

	public CodeEmitter() {
		code = new StringBuffer();
		label = 0;
	}

	@Override
	public String toString() {
		return code.toString();
	}

	public void emit(String s) {
		code.append(s + "\n");
	}

	//%id = alloca i32, align 4
	public void emitAlloca(Expr var) {
		emit( var + " = alloca " + codeType(var.type())); //emit( var + " = alloca " + codeType(var.type()) + ", align 4");
	}

	//store i32 %4, i32* %1, align 4
	public void emitStore(Expr dest, Expr value) {
		emit( "store " + codeType(dest.type()) + " " + value + ", " +
				codeType(dest.type()) + "* " + dest);
	}

	//%1 = load i32, i32* %id, align 4
	public void emitLoad(Expr dest, Expr value) {
		emit( dest + " = load " +
				codeType(dest.type()) + ", " +
				codeType(dest.type()) + "* " +
				value);
	}

	//%6 = add i32 %4, %5
	public void emitInstruction(Expr dest, Expr op1, Expr op2, Token instr) {
		emit( dest + " = " + 
				codeInstruction(instr, op1.type()) + " " + 
				codeType(op1.type()) + " " + op1 + ", " + op2 ); 
	}

	//%26 = sitofp i32 1 to double
	public void emitConvert(Expr dest, Expr op) {
		if ( dest.type().isInt() )
			emit( dest + " = " + "fptosi double " + op + " to i32" );
		else
			emit( dest + " = " + "sitofp i32 " + op + " to double" );
	}

	
	public int newLabel() {
		return ++label;
	}

	//L6:
	public void emitLabel(int label) {
		emit("L" + label + ":");
	}

	//br i1 %22, label %L6, label %L7
	public void emitBreak(Expr cond, int lt, int lf) {
		emit("br i1 " + cond + ", label %L" + lt + ", label %L" + lf);
	}

	//br label %L10
	public void emitBreak(int label) {
		emit("br label %L" + label);
	}

	//%24 = call i32 (i8*, ...) @printf(i8* getelementptr inbounds([7 x i8], [7 x i8]* @str_print_double, i32 0, i32 0), double %23) ; var %23
	public void emitWrite(Expr id) {
		String str = "[4 x i8], [4 x i8]* @str_print_int";
		if ( id.type() == Token.REAL )
			str = "[7 x i8], [7 x i8]* @str_print_double";
		Temp tPrint = new Temp(id.type());
		emit( tPrint + " = call i32 (i8*, ...) @printf(i8* getelementptr inbounds"
				+ "(" + str + ", i32 0, i32 0), "
				+ CodeEmitter.codeType(id.type()) + " "
				+ id + ") ; var " + id );
	}
	public void emitRead(Expr id) {
//		String str = "[4 x i8], [4 x i8]* @str_print_int";
//		if ( id.type() == Token.REAL )
//			str = "[7 x i8], [7 x i8]* @str_print_double";
//		Temp tPrint = new Temp(id.type());
//		emit( tPrint + " = call i32 (i8*, ...) @printf(i8* getelementptr inbounds"
//				+ "(" + str + ", i32 0, i32 0), "
//				+ CodeEmitter.codeType(id.type()) + " "
//				+ id + ") ; var " + id );
	}

	/*public static String codeType(Token type) {
		if ( type == Token.BOOL )
			return "i1";
		else if ( type == Token.INT )
			return "i32";
		else if ( type == Token.REAL )
			return "double";
		else
			return "";
	}*/
	
	public static String codeType(Token type) {
		switch (type.tag()) {
		case BOOL: return "i1";
		case INT: return "i32";
		case REAL: return "double";
		default: return "";
		}
	}

	public static String codeInstruction(Token instr, Token type) {
		switch( instr.tag() ) {
		case SUM:
			return (type == Token.REAL) ? "fadd" : "add";
		case SUB:
			return (type == Token.REAL) ? "fsub" : "sub";
		case MUL:
			return (type == Token.REAL) ? "fmul" : "mul";
		case EQ:
			return (type == Token.REAL) ? "fcmp oeq" : "icmp eq";
		case LT:
			return (type == Token.REAL) ? "fcmp olt" : "icmp slt";
		case LE:
			return (type == Token.REAL) ? "fcmp ole" : "icmp sle";
		default: return "";
		}
	}

	public void emitHead(Token name) {
		emit(";LLVM version 3.8.0 (http://llvm.org/)");
		emit(";program " + name.lexeme());
		emit("declare i32 @printf(i8*, ...) nounwind");
		emit("@str_print_int = private unnamed_addr constant [4 x i8] c\"%d\\0A\\00\", align 1");
		emit("@str_print_double = private unnamed_addr constant [7 x i8] c\"%.2lf\\0A\\00\", align 1");
		emit("define i32 @main() nounwind {");
	}

	public void emitFoot() {
		emit("ret i32 0");
		emit("}");
	}


}
