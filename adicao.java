
import java.util.Random;
import java.util.Scanner;


class Registro { // Classe que representará os elementos presentes na tabela
    private int elemento;

    public Registro(int elemento) {
        this.elemento = elemento;
    }

    public int pegarElemento() {
        return elemento;
    }
}


class Tabela { // Classe principal da tabela
    private Registro[] tabelaHash; // vetor principal da tabela Hash
    private int tamanho;  
    private int contador;
    private int colisoes; 

    public Tabela(int tamanho) { // Cria tabela com o tamanho especificado
        this.tamanho = tamanho;
        this.tabelaHash = new Registro[tamanho];
    }

    public void imprimirTabela() { // Imprime a tabela Hash
        for (int i = 0; i < tamanho; i++) {
            if (tabelaHash[i] != null) {
                System.out.println("Posição " + i + ": " + tabelaHash[i].pegarElemento());
            } else {
                System.out.println("Posição " + i + ": Vazia");
            }
        }
    }

    public int hash(int elemento) { // Função hash de resto de adição
        int soma = 0;
        while (elemento != 0) {
            soma += elemento % 10;
            elemento /= 10;
        }
        return soma % tamanho;
    }

    public void insere_hashing(Registro registro) { // Função para inserir elemento na tabela
        
        int i = hash(registro.pegarElemento());
        
        while (tabelaHash[i] != null) {
            i = (i + 1) % tamanho; // Lida com colisões por meio de rehashing
            colisoes++;
            if (contador >= tamanho) {
                System.out.println("Tabela hash está cheia");
                break;
            }
        }
        tabelaHash[i] = registro; // Insere o elemento no índice
        contador++;
    }

    public int pegarColisoes() { // Pega os valores de colisão
        return colisoes;
    }

    
    public Registro buscar_hashing(int elemento) { // Busca o registro pelo elemento
        if (elemento < 100000000 || elemento > 999999999) { // Lida com chaves inválidas inseridas pelo usuário
            System.out.println("Elemento inválido");
        }

        int index = hash(elemento);
        int comparacoes = 0;
        int i = 0;

        while (tabelaHash[index] != null && i < tamanho) { // Verifica comparações
            comparacoes++;
            if (tabelaHash[index].pegarElemento() == elemento) {
                System.out.println("Número de comparações: " + comparacoes);
                return tabelaHash[index];
            }
            index = (index + 1) % tamanho; // Lida com colisões por meio de rehashing
            i++;
        }

        if (i == tamanho) {
            System.out.println("Elemento não encontrado");
        }
        return null;
    }
}

public class adicao {
    public static void main(String[] args) {
        
        Random random = new Random(1706); // Cria números aleatórios de acordo com a seed
        int numChaves = 20000; // Quantidade de elementos que serão inseridos no vetor

        Registro[] registros = new Registro[numChaves]; 
        
        for (int i = 0; i < numChaves; i++) { // Insere os números nos registros
            registros[i] = new Registro(100000000 + random.nextInt(900000000)); 
        }

        Tabela tabela = new Tabela(20000); // Cria uma tabela hash com tamanho para 20000 elementos

        long insercaoNanoInicio = System.nanoTime(); // Começa a medir o tempo em nanossegundos
        long insercaoMSInicio = System.currentTimeMillis(); // Começa a medir o tempo em milissegundos

        for (Registro registro : registros) {
            tabela.insere_hashing(registro);
        }

        long insercaoNanoFinal = System.nanoTime(); // Para de medir os nanossegundos
        long insercaoMSFinal = System.currentTimeMillis();// para de medir os milissegundos

        System.out.println("FUNCÃO ADIÇÃO - " + numChaves + " - " + "Tamanho do vetor: 20.000");
        System.out.println("Colisões feitas: " + tabela.pegarColisoes());
        System.out.println("Tempo de inserção: " + (insercaoNanoFinal - insercaoNanoInicio) + " nanossegundos - " + (insercaoMSFinal - insercaoMSInicio) + " milissegundos");

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Digite uma elemento para buscar (Digite 0 para encerrar o programa):");

            int elemento = scanner.nextInt();

            if (elemento == 0) { // Encerra o programa caso insira 0
                break;
            }

            long buscaNanoInicio = System.nanoTime();// Começa a medir a busca em nanossegundos
            long buscaMSInicio = System.currentTimeMillis(); // Começa a medir a busca em milissegundos
            tabela.buscar_hashing(elemento);
            long buscaNanoFinal = System.nanoTime();// Para de medir os nanossegundos
            long buscaMSFinal = System.currentTimeMillis();// Para de medir os milissegundos

            System.out.println("Tempo de busca: " + (buscaNanoFinal - buscaNanoInicio) + " nanossegundos - " + (buscaMSFinal - buscaMSInicio) + " milissegundos");
        }
        scanner.close();
    }
}