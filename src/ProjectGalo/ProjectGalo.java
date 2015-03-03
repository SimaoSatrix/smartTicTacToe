/**
 * Simão Ramos(29035) Marlene Barroso(30178) EDA I
 */
package ProjectGalo;

import java.util.Random;

/**
 * classe que joga o jogo e o gere
 */
public class ProjectGalo {

    public static void main(String[] args) {
        int jogos = 1000;//numero de jogos a realizar

        play(jogos);

    }

    public static void play(int jogos) {

        JogAcaso aoAcaso = new JogAcaso();//cria o jogador ao acaso
        JogInteli inteli = new JogInteli();//cria o jogador inteligente
        Random quemJoga = new Random();//cria uma variavel para geracao de numeros aleatorios

        int aJogar = -1;//variavel que decide quem joga primeiro
        //regista os resultados
        int vitorias = 0;
        int derrotas = 0;
        int empates = 0;
        //variavel que guarda o resultado do jogo anterior
        int ultimo_resultado = 0;//0-ganhou acaso, 1-ganhou inteligente, 2-empate

        for (int i = 0; i < jogos; i++) {//for-loop responsavel por jogar todos os jogos

            inteli.novoJogo(2);//prepara o jogador inteligente para o novo jogo
            JogoDoGalo jogo = new JogoDoGalo();//cria um novo jogo do galo

            boolean acabou = false;//boolean que controla se o jogo acabou ou nao

            if (i == 0 || ultimo_resultado == 2) {
            //se for o primeiro jogo ou o ultimo jogo ter resultado em empate
                aJogar = quemJoga.nextInt(2);//calcula aleatoriamente quem comeca a jogar
            } else {
                aJogar = ultimo_resultado;//comeca a jogar quem ganhou o ultimo jogo
            }
            if (aJogar == 0) {//se foi o jogador ao Acaso que ganhou o ultimo jogo
                while (!acabou) {
                    aoAcaso.joga(jogo);//joga o jogador ao Acaso
                    //System.out.println(jogo.toString());
                    if (jogo.vencedor() != 0) {//se ganhou o jogo
                        //incrementa e grava a derrota e o jogador inteligente regista o resultado 
                        if (jogo.vencedor() == 1) {
                            jogo.setDerrota(1);
                            derrotas++;
                            ultimo_resultado = 0;
                            inteli.acabou(jogo);
                            acabou = true;
                            break;
                        } else if (jogo.vencedor() == 3) {//se empatou
                            
                            jogo.setEmpate(1);
                            empates++;
                            ultimo_resultado = 2;
                            inteli.acabou(jogo);
                            acabou = true;
                            break;
                        }
                    }
                    inteli.joga(jogo);//jogador inteligente joga
                    //System.out.println(jogo.toString());
                    if (jogo.vencedor() != 0) {
                        if (jogo.vencedor() == 2) {
                            jogo.setVitoria(1);
                            vitorias++;
                            ultimo_resultado = 1;
                            inteli.acabou(jogo);
                            acabou = true;
                            break;
                        } else if (jogo.vencedor() == 3) {
                            jogo.setEmpate(1);
                            empates++;
                            ultimo_resultado = 2;
                            inteli.acabou(jogo);
                            acabou = true;
                            break;
                        }
                    }
                }
            } else {//se o jogador inteligente ganhou o ultimo jogo comeca a jogar
                while (!acabou) {
                    inteli.joga(jogo);//joga o inteligente
                    //System.out.println(jogo.toString());
                    if (jogo.vencedor() != 0) {//se ganhou o inteligente
                        if (jogo.vencedor() == 2) {
                            jogo.setVitoria(1);
                            vitorias++;
                            ultimo_resultado = 1;
                            inteli.acabou(jogo);
                            acabou = true;
                            break;
                        } else if (jogo.vencedor() == 3) {//se empatou
                            jogo.setEmpate(1);
                            empates++;
                            ultimo_resultado = 2;
                            inteli.acabou(jogo);
                            acabou = true;
                            break;
                        }
                    }
                    aoAcaso.joga(jogo);//joga o jogador ao Acaso
                    //System.out.println(jogo.toString());
                    if (jogo.vencedor() != 0) {
                        if (jogo.vencedor() == 1) {//se ganhou o jogador ao Acaso
                            jogo.setDerrota(1);
                            derrotas++;
                            ultimo_resultado = 0;
                            inteli.acabou(jogo);
                            acabou = true;
                            break;
                        } else if (jogo.vencedor() == 3) {//se foi empate
                            jogo.setEmpate(1);
                            empates++;
                            ultimo_resultado = 2;
                            inteli.acabou(jogo);
                            acabou = true;
                            break;
                        }
                    }

                }
            }

        }
        System.out.println("-------Relatorio final -----------");
        System.out.println("Numero de slots: " + inteli.getHashtable().tamanhoHash());//numero de celulas totais com que a Hash acabou
        System.out.println("Numero de entradas: " + inteli.getHashtable().ocupados);//numero de celulas ocupadas
        System.out.println("Factor de carga: " + inteli.getHashtable().factorCarga());//factor de carga final
        System.out.println("Numero de colisoes:" + inteli.getHashtable().colisoes);//numero de colisoes que aconteceram no acesso a Hash
        //floats necessarios para calculo de percentagens 
        System.out.println("JogInteli ganhou " + vitorias + " vezes (" + ((float) vitorias / (float) jogos) * 100 + "%)");//vitorias do jogInteligente
        System.out.println("JogAcaso ganhou " + derrotas + " vezes (" + ((float) derrotas / (float) jogos) * 100 + "%)");//vitorias do jogAcaso
        System.out.println("Os jogadaram empataram " + empates + " vezes (" + ((float) empates / (float) jogos) * 100 + "%)");//empates
        System.out.println("A minha jogada favorita:");
        System.out.println(inteli.jogadaFavorita().toString());//jogada favorita do jogador Inteligente
        System.out.println("Ganhou " + inteli.jogadaFavorita().getVitoria() + " vezes em "
                + inteli.numeroDeVezesVisto(inteli.jogadaFavorita()) + " que é "
                + ((float) inteli.jogadaFavorita().getVitoria() / (float) inteli.numeroDeVezesVisto(inteli.jogadaFavorita())) * 100 + "%");
        //racio de vitorias com essa jogada
    }

}
