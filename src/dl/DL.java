package dl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import lexer.Lexer;
import lexer.Token;
import parser.Parser;

public class DL {

	public static void main(String[] args) {
		//Nome dos arquivos fonte, intermediario e programa
		String sName = "prog.dl";
		String pName = sName.substring(0, sName.lastIndexOf("."));
		String iName = pName +  ".ll";




		//Análise
		File sourceFile = new File(sName);
		Lexer lexer = new Lexer(sourceFile);
		Parser parser = new Parser(lexer);
		parser.parse();

		//Imprimindo a árvore sintática e código intermediário
		System.out.println(parser.parseTree());
		System.out.println(parser.code());
		System.out.println("finalizado");


		try {
			//Construindo arquivos
			File interFile = new File(iName);
			interFile.createNewFile();
			FileWriter fw = new FileWriter(interFile);
			fw.write(parser.code());
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
