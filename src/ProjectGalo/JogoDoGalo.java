/**
 * Simão Ramos(29035) Marlene Barroso(30178) EDA I
 */
package ProjectGalo;

import java.util.ArrayList;

/**
 * classe que cria e gere o Jogo do Galo, tabuleiro, decide resultado, e grava
 * as jogadas no tabuleiro do jogo
 */
public class JogoDoGalo {

    private ArrayList<Integer> tabuleiro;//tabuleiro de jogo
    private final int tam_tabuleiro = 9;//tamanho default do tabuleiro de jogo
    //variaveis que gravam os resultados
    private int vitoria;
    private int derrota;
    private int empate;
    //conjunto de tokens de jogo, X - jogador ao Acaso, O - jogador Inteligente
    private final int VAZIO = 0;
    private final int X = 1;
    private final int O = 2;

    /**
     * construtor default de jogo do galo
     */
    public JogoDoGalo() {
        tabuleiro = new ArrayList<Integer>();//inicializa tabuleiro de jogo
        //inicializa resultados
        vitoria = 0;
        derrota = 0;
        empate = 0;

        for (int i = 0; i < tam_tabuleiro; i++) {
            //percorre a extensao do tabuleiro e preenche com vazio
            tabuleiro.add(VAZIO);
        }
    }

    /**
     * construtor que pega numa configuracao de tabuleiro ja existente e cria
     * um novo objecto jogo do galo com ela
     */
    public JogoDoGalo(JogoDoGalo tab) {
        tabuleiro = new ArrayList<>(tab.getTabuleiro());//copia o tabuleiro
        //reseta os resultados
        vitoria = 0;
        derrota = 0;
        empate = 0;
    }

    //getter
    public int getVitoria() {
        return vitoria;
    }

    public int getDerrota() {
        return derrota;
    }

    public int getEmpate() {
        return empate;
    }
    
    public ArrayList<Integer> getTabuleiro() {
        return tabuleiro;
    }
    //setter
    public void setVitoria(int vitoria) {
        this.vitoria = vitoria;
    }

    public void setDerrota(int derrota) {
        this.derrota = derrota;
    }

    public void setEmpate(int empate) {
        this.empate = empate;
    }
    
    //sets que fazem as jogadas no tabuleiro do jogo
    public void setX(int index) {
        tabuleiro.set(index, X);
    }

    public void setO(int index) {
        tabuleiro.set(index, O);
    }

    
    /**
     * metodo que calcula os movimentos possiveis a realizar segundo a 
     * configuracao actual do tabuleiro e retorna um arraylist com os movimentos
     * @return 
     */
    public ArrayList<JogoDoGalo> MovimentoPossiveisTabuleiro() {
        ArrayList<Integer> espacosBranco = new ArrayList<>();
        //cria um arraylist de espacos em Branco
        ArrayList<JogoDoGalo> jogadasPosiveis = new ArrayList<>();
        //cria o arraylist de configuracoes de tabuleiro das jogadas possiveis

        for (int i = 0; i < tam_tabuleiro; i++) {
            //preenche o arraylist com os espacos em branco do tabuleiro
            if (tabuleiro.get(i) == VAZIO) {
                espacosBranco.add(i);
            }
        }

        for (int i = 0; i < espacosBranco.size(); i++) {//percorre o array de espacos em branco
            JogoDoGalo possibilidades = new JogoDoGalo(this);//faz uma copia da configuracao actual
            possibilidades.setO(espacosBranco.get(i));
            //coloca uma jogada possivel do jogador Inteligente no espaco em branco para posterior comparacao
            jogadasPosiveis.add(possibilidades);
            //adiciona essa configuracao com a possibilidade de jogada
        }
        return jogadasPosiveis;//retorna o arraylist das jogadas
    }

    

    //diz quem ganhou o jogo
    /**
     * metodo que decide qual o estado do jogo
     * @return 
     */
    public int vencedor() {

        // Verifica se o jogador ao Acaso preencheu alguma linha
        if (tabuleiro.get(0) == X && tabuleiro.get(1) == X && tabuleiro.get(2) == X) {
            return X;
        }
        if (tabuleiro.get(3) == X && tabuleiro.get(4) == X && tabuleiro.get(5) == X) {
            return X;
        }
        if (tabuleiro.get(6) == X && tabuleiro.get(7) == X && tabuleiro.get(8) == X) {
            return X;
        }
        // Vreifica se o jogador ao Acaso preencheu alguma coluna
        if (tabuleiro.get(0) == X && tabuleiro.get(3) == X && tabuleiro.get(6) == X) {
            return X;
        }
        if (tabuleiro.get(1) == X && tabuleiro.get(4) == X && tabuleiro.get(7) == X) {
            return X;
        }
        if (tabuleiro.get(2) == X && tabuleiro.get(5) == X && tabuleiro.get(8) == X) {
            return 1;
        }
        // Verifica se o jogador ao Acaso preencheu alguma diagonal
        if (tabuleiro.get(0) == X && tabuleiro.get(4) == X && tabuleiro.get(8) == X) {
            return X;
        }
        if (tabuleiro.get(2) == X && tabuleiro.get(4) == X && tabuleiro.get(6) == X) {
            return 1;
        }

        // Verifica se o jogador Inteligente preencheu alguma linha
        if (tabuleiro.get(0) == O && tabuleiro.get(1) == O && tabuleiro.get(2) == O) {
            return O;
        }
        if (tabuleiro.get(3) == O && tabuleiro.get(4) == O && tabuleiro.get(5) == O) {
            return O;
        }
        if (tabuleiro.get(6) == O && tabuleiro.get(7) == O && tabuleiro.get(8) == O) {
            return O;
        }
        // Verifica se o jogador Inteligente preencheu alguma coluna
        if (tabuleiro.get(0) == O && tabuleiro.get(3) == O && tabuleiro.get(6) == O) {
            return O;
        }
        if (tabuleiro.get(1) == O && tabuleiro.get(4) == O && tabuleiro.get(7) == O) {
            return O;
        }
        if (tabuleiro.get(2) == O && tabuleiro.get(5) == O && tabuleiro.get(8) == O) {
            return O;
        }
        // Verifica se o jogador Inteligente preencheu alguma diagonal
        if (tabuleiro.get(0) == O && tabuleiro.get(4) == O && tabuleiro.get(8) == O) {
            return O;
        }
        if (tabuleiro.get(2) == O && tabuleiro.get(4) == O && tabuleiro.get(6) == O) {
            return O;
        }
        
        //verifica se nao existe espacos vazios no tabuleiro e sem vencedor, retorna um empate
        if (acabou()) {
            return 3;
        }

        // retorna 0 quando o jogo ainda continua sem resultado
        return 0;
    }

    //retorna o codigo hash do objecto
    /**
     * metodo que calcula o hashcode do jogo do galo, uma vez que todas as jogadas
     * sao diferentes, os arraylist sao unicos, e como sao preenchidos com 0,1 ou 2
     * se criarmos um inteiro com o tabuleiro indice por indice em ordem, obtem se um
     * numero em base 3, que posteriormente pode ser convertido para base 10
     * @return 
     */
    @Override
    public int hashCode() {
        //permite criar variáveis de String modificaveis
        StringBuilder string = new StringBuilder();
        //percorre o tabuleiro e grava indice a indice, sendo que o indice 0
        //e o numero mais significante e o indice 8 o menos significante
        for (int i = 0; i < tam_tabuleiro; i++) {
            string.append(tabuleiro.get(i));
        }
        //converte a String em inteiro base 3
        int hashcode = Integer.valueOf(string.toString());
        return hashcode;
    }

    /**
     * metodo que verifica se nao existem espacos vazios
     * @return 
     */
    public boolean acabou() {
        return !tabuleiro.contains(VAZIO);
    }

    //faz uma jogada na celula indicada se esta vazia, se nao esta vazia retorna false
    /**
     * metodo que faz uma jogada consoante a linha e coluna
     */
    boolean joga(int lin, int col) {
        int posicao = (lin * 3) + col;
        return tabuleiro.get(posicao).equals(VAZIO);
    }

    //retorna o movimento actual na localizacao dada, i.e qual o jogador naquela posicao
    /**
     * metodo que descobre o jogador que jogou em determinada posicao
     */
    int jogador(int lin, int col) {
        int posicao = (lin * 3) + col;
        if (tabuleiro.get(posicao).equals(X)) {
            return 1;
        } else if (tabuleiro.get(posicao).equals(O)) {
            return 2;
        } else {
            return 0;//nenhum jogador na posicao
        }
    }

    //retorna reperesentacao em String do tabuleiro
    /**
     * retorna a String representativa do tabuleiro
     * @return 
     */
    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < tam_tabuleiro; i += 3) {
            if (tabuleiro.get(i) == X) {
                string.append(" X |");
            } else if (tabuleiro.get(i) == O) {
                string.append(" O |");
            } else {
                string.append("   |");
            }
            if (tabuleiro.get(i + 1) == X) {
                string.append(" X ");
            } else if (tabuleiro.get(i + 1) == O) {
                string.append(" O ");
            } else {
                string.append("   ");
            }
            if (tabuleiro.get(i + 2) == X) {
                string.append("| X\n");
            } else if (tabuleiro.get(i + 2) == O) {
                string.append("| O\n");
            } else {
                string.append("| \n");
            }
            if (i != 6) {
                string.append("---------\n");
            }
        }
        return new String(string);
    }
}
