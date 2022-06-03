package AnalisadorLexico;

import java.util.ArrayList;

public class ParserSeman {

	public ParserSeman() {}
	
	public void parserSeman(ArrayList<String> usedVar, ArrayList<String> decVar, ArrayList<Token> decTipo, ArrayList<Token> checkType) {
		//declaracao das variaveis
		ArrayList<String> cpDecVar = new ArrayList<String>(decVar);
		ArrayList<Token> cpCheckType = new ArrayList<Token>(checkType);
		ArrayList<String> cpUsedVar = new ArrayList<String>(usedVar);
		ArrayList<Token> typeLine = new ArrayList<Token>();
		
		boolean chave;
		int aux = 0, tam = decVar.size();
		//inicio da comparacao 
		for(int i = 0; i < tam; i++) {
			chave = false;
			while(aux < cpUsedVar.size()) {
				if(cpUsedVar.get(aux).equals(cpDecVar.get(0))) {
					chave = true;
					cpUsedVar.remove(cpUsedVar.get(aux));
				}else {
					aux++;
				}
			}
			aux = 0;
			if(chave) {
				cpDecVar.remove(cpDecVar.get(0));
			}	
		}
		
		//declarou e nao usou
		if(!cpDecVar.isEmpty()) {
			for(String i : cpDecVar) {
				System.out.println("WARNING: variavel " + i + " declarada e nao usada");
			}
		}
		//usou e nao declarou
		if(!cpUsedVar.isEmpty()) {
			for(String i : cpUsedVar) {
				System.out.println("ERROR: variavel " + i + " nao declarada");
			}
		}
		//verifica tipo
		//System.out.println("checkType " + cpCheckType);
		int x = 0, cont = 0, tamanho = cpCheckType.size();
		int linha = cpCheckType.get(0).getlinha();
		
		while(cont < tamanho) {
			while(cpCheckType.get(x).getlinha() == linha && cont < cpCheckType.size()) {
				//System.out.println("checkType indice " + x + " -> "+cpCheckType.get(x));
				typeLine.add(cpCheckType.get(x));
				if((x + 1) < cpCheckType.size()) {
					x++;
					cont++;
				}else cont++;
			}
			if(verificaTipo(decVar, typeLine, decTipo)) {
				linha = cpCheckType.get(x).getlinha();
				int get = typeLine.size();
				for(int i = 0; i < get; i++) {
					typeLine.remove(0);
				}
			}else {
				System.out.println("ERROR: Variaveis de tipos diferentes na linha " + linha);
				System.exit(1);
			}
			if(verificaTam(checkType)) {
				System.out.println("Deu tudo certo");
			}
		}
	}
	
	public boolean verificaTipo(ArrayList<String> decVar, ArrayList<Token> typeLine, ArrayList<Token> decTipo) {
		boolean retorno;
		int contador = 0;
		
		ArrayList<String> tipo = new ArrayList<String>();
		for(int i = 0; i < typeLine.size(); i++) {
			contador = 0;
			if(typeLine.get(i).getnome() == TipoToken.Var) {
				while(!(typeLine.get(i).getlex().equals(decVar.get(contador))) && contador < decVar.size()) {
					contador++;
				}
				tipo.add(decTipo.get(contador).getlex());
			}else if(typeLine.get(i).getnome() == TipoToken.NumInt) {

				tipo.add("INT");
			}else if(typeLine.get(i).getnome() == TipoToken.NumReal) {

				tipo.add("REAL");
			}
		}
		int tam = tipo.size();
		String aux = tipo.get(0);
		//System.out.println("Itens da linha " + typeLine.get(0).getlinha() + " -> " + tipo);
		for(int i = 0; i < tam; i++) {
			if(aux.equals(tipo.get(0))) {
				tipo.remove(0);
			}
		}
		if(!tipo.isEmpty()) {
			retorno = false;
		}else retorno = true;
		return retorno;
	}
	
	public boolean verificaTam(ArrayList<Token> checkType) {
		boolean retorno = false;
		for(int i = 0; i < checkType.size(); i++) {
			if(checkType.get(i).getnome() == TipoToken.NumInt) {
				double check = Double.parseDouble(checkType.get(i).getlex());
				//System.out.println(checkType.get(i).getlex());
				if(check > 2147483647 || check < -2147483648) {
					System.out.println("ERROR: overflow na linha " + checkType.get(i).getlinha());
					System.exit(1);
				}
			}else if(checkType.get(i).getnome() == TipoToken.NumReal) {
				System.out.println(checkType.get(i).getlex());
				double check = Double.parseDouble(checkType.get(i).getlex());
				if(check > Math.pow(10, 18) || check < Math.pow(10, -18)) {
					System.out.println("ERROR: overflow na linha " + checkType.get(i).getlinha());
					System.exit(1);
				}
			}
			//garante precisao de 8 digitos depois da virgula 
			if(checkType.get(i).getlex().contains(".")) {
				String aux = checkType.get(i).getlex();
				String aux2 = aux;
				if(aux.length() > aux.indexOf('.') + 8) {
					aux2 = "";
					for(int r = 0; r < aux.indexOf('.') + 9; r++) {
						aux2 = aux2.concat(Character.toString(aux.charAt(r)));
					}
				}
				checkType.get(i).setlex(aux2);
				System.out.println(aux2);
			}
			retorno = true;
		}
		return retorno;
	}
}
