package AnalisadorLexico;

public class Token{
  public TipoToken nome;
  public String lexema;
  public int linha;

  public Token(TipoToken nome, String lexema, int linha){
    this.nome = nome;
    this.lexema = lexema;
    this.linha = linha;
  }

  public String toString(){
    return "<"+nome+","+lexema+","+linha+">";
  }
  
  public TipoToken getnome() {
	  return this.nome;
  }
  public int getlinha() {
	  return this.linha;
  }
  public String getlex() {
	  return this.lexema;
  }
  public void setlex(String x) {
	  this.lexema = x;
  }
}