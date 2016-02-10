public class Main {
	public static void main(String[] args) {
		String entrada;

		// Recebe parâmetros pela linha de comando
		try {
			entrada = args[0];
		} catch (Exception e) {
			System.out.println("Erro na passagem de parâmetros!");
			System.exit(0);
			return;
		}

		// Lê arquivo e recebe gramática
		LeitorArquivo leitorArquivo = new LeitorArquivo(entrada);
		Gramatica gramatica = leitorArquivo.lerArquivo();

		// Caso haja algum erro na leitura gramática será null
		if (gramatica == null) {
			System.exit(0);
			return;
		}

		// Faz a verificação se a gramática é gerada pela palavra
		gramatica.verificaPalavra(gramatica.palavra);

	}
}