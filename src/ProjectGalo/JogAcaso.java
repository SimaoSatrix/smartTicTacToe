/**
 * Simão Ramos(29035) Marlene Barroso(30178) EDA I
 */
package ProjectGalo;

import java.util.Random;


/**
 *classe que cria o jogador e gere as jogadas aleatoria
 */
public class JogAcaso {

    Random jogada_aleatoria;// variavel Random para as jogadas aleatorias
    
    /**
     * construtor default para o jogador aleatorio
     * inicializa a variavel Random
     */
    public JogAcaso() {
        jogada_aleatoria = new Random();
    }

    /**
     * metodo que faz a jogada do jogAcaso no tabuleiro de jogo
     */
    public void joga(JogoDoGalo t) {
        boolean jogada_impossivel = true;//boleano que controla a jogada
        while (jogada_impossivel) {//enquanto uma jogada nao for encontrada
            int random = jogada_aleatoria.nextInt(9);
            //gera um valor aleatorio entre 0 e 8 inclusive que correspondem aos
            //indices do arrayList do tabuleiro do jogo
            if (t.getTabuleiro().get(random) == 0) {
               // se o tabuleiro esta vazio no indice em que vai se realizar a jogada
                t.setX(random);//faz a jogada com X - token correspondente ao jogador ao Acaso
                jogada_impossivel = false;
            }
        }
    }
}
