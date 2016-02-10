import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

@SuppressWarnings("resource")
public class LeitorArquivo {
	/**
	 * Constantes para facilitar legibilidade do código
	 */
	private String entrada;

	/**
	 * Construtor informando o arquivo de entrada de onde serao lidas as
	 * informações da gramática
	 * 
	 * @param entrada
	 */
	public LeitorArquivo(String entrada) {
		this.entrada = entrada;
	}

	/**
	 * Lê as informações de uma gramática a partir de um arquivo de entrada
	 * 
	 * @return gramatica
	 */
	public Gramatica lerArquivo() {
		// Variáveis necessárias
		String palavra, nomeRegra, regra, token;
		int numRegras;
		HashMap<String, ArrayList<String>> gramatica = new HashMap<>();
		ArrayList<String> regras = new ArrayList<>();

		// Arquivo de entrada
		File file = new File(entrada);

		try {

			Scanner scannerFile = new Scanner(file);
			Scanner scannerLine = null;

			// Lê a palavra
			scannerLine = new Scanner(scannerFile.nextLine());
			palavra = scannerLine.next();

			// Lê o número de regras
			scannerLine = new Scanner(scannerFile.nextLine());
			numRegras = scannerLine.nextInt();

			// Lê as regras da gramática
			for (int i = 0; i < numRegras; i++) {
				token = regra = "";
				regras = new ArrayList<String>();
				scannerLine = new Scanner(scannerFile.nextLine());

				// Pega o nome da regra
				nomeRegra = scannerLine.next();

				// Lê todas as regras, de forma concatenada no caso de 2
				// variáveis
				while (scannerLine.hasNext()) {
					token = scannerLine.next();

					// Não faz nada com a seta
					if (token.equals("->")) {
						continue;
					}

					regra = regra + token;
				}

				// Adiciona ao arrayList de regras
				regras.add(regra);

				// Se variável existir na gramática
				if (gramatica.containsKey(nomeRegra)) {
					// Adiciona à regra existente
					gramatica.get(nomeRegra).addAll(regras);
				} else {
					// Cria nova regra
					gramatica.put(nomeRegra, regras);
				}

			}

			// Cria gramática
			Gramatica aux = new Gramatica(gramatica, palavra);

			// Fecha scanners
			scannerLine.close();
			scannerFile.close();

			return aux;
		} catch (Exception e) {
			e.printStackTrace();
			System.out
					.println("Arquivo de entrada não encontrado ou arquivo de entrada fora do padrão especificado");
		}

		// Caso dê algum erro
		return null;
	}
}