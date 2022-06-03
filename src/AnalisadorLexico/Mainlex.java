// link video no drive: https://drive.google.com/file/d/1LbTCy4rK0PCYfxo4ggvn8PvUfLkizpIO/view?usp=sharing

package AnalisadorLexico;

public class Mainlex{
  public static void main(String[] args){
	 GyhLex lex = new GyhLex();
	 lex.ident_tipotoken("C:\\Users\\Anderson S. Silva\\lista2\\projeto\\GyhLex\\src\\programa1.gyh");
	 
	 ParserSintat sintat = new ParserSintat();
	 sintat.parser(lex.getArraytoken());
	 
	 ParserSeman seman = new ParserSeman();
	 seman.parserSeman(sintat.getusedVar(), sintat.getdecVar(), sintat.getdecTipo(), sintat.getCheckType());
  
  }
} 