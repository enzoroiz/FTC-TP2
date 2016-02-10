import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

@SuppressWarnings("unchecked")
public class Gramatica {
	/**
	 * Constantes e aributos da classe
	 */
	private final int INVALIDO = -1;
	private HashMap<String, ArrayList<String>> regras;
	public String palavra;
	
	/**
	 * Construtor da gramática informando as regras existentes e a palavra a ser verificada
	 * @param regras
	 * @param palavra
	 */
	public Gramatica (HashMap<String, ArrayList<String>> regras, String palavra){
		this.palavra = palavra;
		this.regras = regras;
	}
	
	/**
	 * Verifica se a palavra é gerada ou não pela gramática
	 * OBS.: Matriz avaliada do topo para baixo.
	 * @param palavra a ser verificada
	 */
	public void verificaPalavra(String palavra){
		int tamanho = palavra.length();
		ArrayList<String> [][] matrizDeRegras = new ArrayList[tamanho][tamanho];
		
		//Inicializa a matriz, preenchendo a primeira linha.
		//Coloca na matriz as variáveis que geram cada caracter da palavra
		for (int j = 0; j < tamanho; j++){
			matrizDeRegras[0][j] = (keysOfValue(String.valueOf(palavra.charAt(j))));
		}
		
		//Para cada linha da matriz de regras
		for (int i = 1; i < tamanho; i++){
			//Para coluna da matriz de regras
			for (int j = 0; j < (tamanho - i); j++){
				ArrayList <String> conjuntoRegras = new ArrayList<String>();
				int linhaAtual = i, colunaAtual = j;
				//Para cada uma das linhas preenchidas da matriz
				for (int k = 0; k < i; k++){
					//Gera as combinações possíveis, concatenando variáveis para alcançar as subpalavras de determinado tamanho
					//e  preenche a linha i com as regras que geram as combinações geradas
					ArrayList<String> regraEsquerda = new ArrayList<String>();
					regraEsquerda = matrizDeRegras[k][j];
					
					//Decrementa linha e incrementa coluna para fazer combinações (alcançar palavras de tamanho determinado)
					linhaAtual --;
					colunaAtual ++;
					ArrayList<String> regraDireita = new ArrayList<String>();
					regraDireita = matrizDeRegras[linhaAtual][colunaAtual];
					
					//Insere regras que geram as combinações verificadas num arrayList
					insereCasoNaoExista(concatenaVerificaRegras(regraEsquerda, regraDireita), conjuntoRegras); 
				}
				
				//Insere as regras na matriz
				matrizDeRegras[i][j] = conjuntoRegras;
			}
		}
		
		//Imprime matriz de regras
//		for(int i = 0; i < tamanho; i++){
//			for(int j = 0; j < tamanho - i; j++){
//				System.out.print(matrizDeRegras[i][j].toString() + "\t");
//			}
//			System.out.println();
//		}
		
		// Caso o símbolo de partida esteja na última linha e na posição 0
		// No fundo da matriz, então a palavra é gerada pela gramática
		// Caso contrário, não.
		if(matrizDeRegras[tamanho-1][0].contains("S")){
			System.out.println("SIM");
		} else {
			System.out.println("NAO");
		}
	}

	/**
	 * Dados dois arrayLists com variáveis, gera todas as combinações possíveis para elas e verifica as 
	 * regras que geram cada uma das combinações encontradas
	 * @param regraEsquerda Array com variáveis do lado esquerdo da combinação
	 * @param regraDireita Array com variáveis do lado direito da combinação
	 * @return regras que geram a concatenação das variáveis
	 */
	private ArrayList<String> concatenaVerificaRegras(ArrayList<String> regraEsquerda, ArrayList<String> regraDireita){
		ArrayList<String> regrasExistentes = new ArrayList<String>();
		
		//Se nenhuma das regras for vazia
		if(!regraEsquerda.isEmpty() && !regraDireita.isEmpty()){
			//Faz cada uma das combinações de regras possíveis
			for (String regraEsq : regraEsquerda) {
				for(String regraDir : regraDireita){
					//Regra formada a partir da combinação
					String regraFormada = regraEsq + regraDir;
					
					//Adiciona ao array de regras se ela já não existir
					ArrayList<String> aux = keysOfValue(regraFormada); 
					insereCasoNaoExista(aux, regrasExistentes);
				}
			}
		}
		
		return regrasExistentes;
	}
	
	/**
	 * Dado um HashMap <Regra, (Variáveis ou Terminais)>, retorna as regras que geram as variáveis ou terminais "value"
	 * @param value variável ou terminal que se deseja saber quais regras o gera
	 * @return regras que geram value
	 */
	private ArrayList<String> keysOfValue(String value){
		//Recebe as regras da gramática
		Set<String> keysSet = regras.keySet();
		ArrayList<String> keys = new ArrayList<String>();
		
		//Para cada chave, procura regra
		for (String key : keysSet) {
           if (regras.get(key).contains(value)){
        	   keys.add(key);
           }              
        }
		
		return keys;
	}
	
	/**
	 * Insere itens num arrayList caso os itens já não se encontrem neste
	 * @param aux arrayList com elementos para inserir em arrayListFinal
	 * @param arrayListFinal arrayList no qual os elementos serão inseridos e verificados se já existem
	 */
	private void insereCasoNaoExista(ArrayList<String> aux, ArrayList<String> arrayListFinal){
		//Para cada string do array
		for (String key : aux){
			//Caso a string não exista no vetor, adiciona
			if(arrayListFinal.indexOf(key) == INVALIDO){
				arrayListFinal.add(key);
			}
		}
	}
	
}
