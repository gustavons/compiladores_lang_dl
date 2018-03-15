package inter.expr.arith;

import inter.expr.Expr;
import inter.expr.Literal;
import inter.expr.Temp;
import lexer.Tag;
import lexer.Token;

public class Pow extends Expr {

    protected Expr expr1;
    protected Expr expr2;


    public  Pow(Token op, Expr e1, Expr e2 ) {
        super(op, null);

//        type = Token.maxNumType( e1.type(), e2.type() );
        if ( e1.type().isReal()||e2.type().isReal())
            type = Token.REAL;
        else if ( e1.type().isInt() && e2.type().isInt()){
            type = Token.INT;
        }
        else
            error("tipos incompatíveis");
        expr1 = e1;
        expr2 = e2;
        addChild(expr1);
        addChild(expr2);
    }

    @Override
    public Expr gen() {
        Expr e1 = expr1.gen();
        Expr e2 = expr2.gen();

        Expr op1 = e1;
        Expr op2 = e2;
        if ( e1.type().isInt()) {
            Temp f = new Temp(Token.REAL);
            code.emitConvert(f, op1);
            op1 = f;
        }else if(e2.type().isInt()){
            Temp g = new Temp(Token.REAL);
            code.emitConvert(g, op2);
            op2 = g;
        }

        Temp d = new Temp(type);
        code.emitPow(d, op1, op2, op);

        if(e1.type().isInt() && e2.type().isInt()){
            Temp temp = new Temp(Token.INT);
            code.emitConvert(temp, d);
            d = temp;
        }
        return d;
    }
}
