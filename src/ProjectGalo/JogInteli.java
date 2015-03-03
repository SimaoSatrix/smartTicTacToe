/**
 * Simão Ramos(29035) Marlene Barroso(30178) EDA I
 */
package ProjectGalo;

import java.util.ArrayList;
import java.util.Random;

/**
 * classe que cria o JogInteli e gere as jogadas inteligentes usa uma hashtable
 * para armazenar as jogadas ja feitas e decidir qual a melhor jogada para fazer
 */
public class JogInteli {

    Random jogada_aleatoria;// variavel para as jogadas aleatorias

    public ArrayList<JogoDoGalo> movimentosUsados;
    //Arraylist de configuracoes de tabuleiros anteriores a configuracao actual
    //serve para armazenar todas as configuracoes ate o resultado ser descoberto

    public Hashtable<JogoDoGalo> hashtable;
    //hashtable com todas as configuracoes do tabuleiro do conjunto de todos os jogos
    //jogador inteligente acede para ver jogadas anteriores mais sucedidas com configuracoes
    //iguais a actual do tabuleiro

    /**
     * construtor do jogador inteligente inicializa a jogada aleatoria, o
     * arraylist dos movimentos do jogo actual e a hashtable implementa tambem
     * os metodos abstratos da hastable - procPos() e myHash()
     */
    public JogInteli() {
        movimentosUsados = new ArrayList<>();
        jogada_aleatoria = new Random();
        hashtable = new Hashtable<JogoDoGalo>() {//19642 é a jogada com valor de base 3 mais alto (25717)

            @Override
            protected int procPos(JogoDoGalo s) {
                int posicao = myHash(s.hashCode());
                //descobre a posicao passando o hashCode do jogo do galo como argumento para o metodo myHash
                return posicao;
            }

            /**
             *a funcao hash do jogo do galo retorna um Integer na base 3, o metodo myHash transforma
             * o valor em base de dez e faz o mod desse valor pelo tamanho da hashtable
             */
            @Override
            protected int myHash(int code) {
                String auxiliarKey = Integer.toString(code);//faz um cast para string do hashcode do jogodogalo
                int keyBase10 = Integer.parseInt(auxiliarKey, 3);//converte o inteiro de base 3 para base 10
                int chave = keyBase10 % hashtable.getArray().length;//calculada o mod do inteiro base 10 e o tamanho da hash
                return chave;
            }
        };

    }
    
    /**
     * retorna a hastable do jogador inteligente
     */
    public Hashtable<JogoDoGalo> getHashtable() {
        return hashtable;
    }

    //prepara-se para um novo jogo, i.e. prepara-se para registar os tabuleirod do jogo que se vai iniciar
    //deve ser chamado quando um novo jogo vai comear
    //deve registar qual o jogador que JogInteli  (X ou O)
    /**
     * o metodo novojogo limpa o arraylist dos movimentos usados
     * preparando-o para o novo jogo, como convencao o jogador inteligente e 
     * sempre representado pela O (numero 2 no tabuleiro do jogo)
     */
    public void novoJogo(int jogador) {
        movimentosUsados.clear();

    }

    //faz uma jogada baseada na experiencia passada, devendo ser escolhido o movimento com maior % de ganhos
    //#ganhou/(#ganhou+#perdeu+#empatou), em caso de empate decida-se aleatoriamnte
    /**
     * metodo responsavel por fazer a jogada do jogador inteligente
     * verifica as jogadas na hashtable e decide a mais eficiente(com mais vitorias)
     * se nao existir nenhuma joga aleatoriamente Depois da jogada armazena a nova
     * configuracao do teclado no arraylist dos movimentos usados
     * @param t
     */
    public void joga(JogoDoGalo t) {
        JogoDoGalo a_fazer = null;//variavel que memoriza a jogada a fazer
        float percentagem_vitoria = 0;//float para comparacoes entre jogadas
        JogoDoGalo[] sucessores = sucessores(t);
        //array com as configuracoes do tabuleiro das jogadas possiveis e ja feitas anteriormente pelo jogador inteligente

        if (sucessores.length == 0) {//se o nao existirem jogadas no array
            boolean jogada_impossivel = true;
            while (jogada_impossivel) {//enquanto uma jogada possivel nao for encontrada
                int random = jogada_aleatoria.nextInt(9);//procura uma posica random para jogar
                if (t.getTabuleiro().get(random) == 0) {//se o tabuleiro estiver livre nessa posicao
                    t.setO(random);// joga nessa posicao random
                    movimentosUsados.add(new JogoDoGalo(t));
                    //guarda no array de movimentos para este jogo a configuracao do tabuleiro com a jogada
                    jogada_impossivel = false;
                }
            }
        } else {
            int posicao = -1;//variavel que guarda a posicao a jogar
            int posicao_jogo_sucessores = -1;//variavel que armazena a posicao da jogada presente no array sucessores
            int contador = 0;//variavel que conta o numero modificacoes sobre o array de sucessores
            while (posicao == -1) {//enquanto uma posicao para jogar nao for encontrada
                for (int i = 0; i < sucessores.length; i++) {//percorre o array de sucessores
                    if (sucessores[i] != null) {//se contiver uma jogada 
                        JogoDoGalo possivel = sucessores[i];//armazena a jogada possivel
                        float calculo_percentagem_vitoria = ((float) possivel.getVitoria())
                                / ((float) possivel.getVitoria()) + ((float) possivel.getEmpate()) + ((float) possivel.getDerrota());
                        //calcula a percentagem de vitorias dessa jogada vitorias/total de jogos em que a jogada este presente
                        if (calculo_percentagem_vitoria > percentagem_vitoria) {//se a percentagem de vitorias for superior a uma verificada anteriormente
                            a_fazer = possivel;//guarda a configuracao do tabuleiro para a jogada a fazer
                            percentagem_vitoria = calculo_percentagem_vitoria;//guarda a percentagem de vitoria para posterior comparacao com outras jogadas
                            posicao_jogo_sucessores = i;//guarda a posicao do array sucessores dessa jogada 
                        }
                    }
                }

                if (a_fazer == null || sucessores.length == contador) {//se nao existir uma jogada a partida ou no final dos sucessores
                    boolean jogada_impossivel = true;
                    while (jogada_impossivel) {
                        int random = jogada_aleatoria.nextInt(9);//gera uma jogada aleatoria
                        if (t.getTabuleiro().get(random) == 0) {//verifica se e possivel de se fazer
                            posicao = random;//igual a posicao a jogar ao valor aleatorio
                            jogada_impossivel = false;
                        }
                    }
                } else {
                    for (int i = 0; i < t.getTabuleiro().size(); i++) {//percorre o tabuleiro da configuracao do jogo actual
                        if (a_fazer.getTabuleiro().get(i) != t.getTabuleiro().get(i) && t.getTabuleiro().get(i) == 0) {
                            //compara com o mesmo indice no tabuleiro da jogada a fazer verificando se e diferente e se o valor
                            //no tabuleiro está vazio
                            posicao = i;//guarda a posicao
                        }
                    }
                    //se nenhuma jogada foi decidida
                    sucessores[posicao_jogo_sucessores] = null;//retira a jogada dos sucessores
                    contador++;//incrementa o numero de modificacoes do array de sucessores
                }
            }
            t.setO(posicao);//faz a jogada na posicao decidida
            movimentosUsados.add(new JogoDoGalo(t));//memoriza a configuracao apos a jogada e nova actual
        }

    }
    
    
    //dado que terminal  uma configurao em que o jogo acabou as configuraes que constituem todas as
    //jogadas do jogo devem ser registadas como respondentes a voitria, empate ou derrota
    /**
     * metodo responsavel por tratar as jogadas armazenadas pelo jogador inteligente ao longo do jogo
     * @param terminal
     */
    public void acabou(JogoDoGalo terminal) {
        for (JogoDoGalo movimento : movimentosUsados) {//percorre o arraylist dos movimentosusados 
            if (hashtable.contains(movimento)) {//verifica se o movimento ja esta presente na hashtable
                JogoDoGalo existente = hashtable.procurar(movimento);//recupera o jogo do galo com essa configuracao da hash
                hashtable.remove(movimento);//remove da hashtable
                //actualiza as vitorias,derrotas e empates consoante o resultado armazenado na configuracao terminal do jogo
                existente.setVitoria(existente.getVitoria() + terminal.getVitoria());
                existente.setDerrota(existente.getDerrota() + terminal.getDerrota());
                existente.setEmpate(existente.getEmpate() + terminal.getEmpate());
                //insere de novo o jogo actualizado na hash 
                hashtable.insere(existente);
            } else {
                //realiza as mesmas operacoes para uma configuracao do jogo nao existente na hash
                JogoDoGalo n_existente = movimento;
                n_existente.setVitoria(n_existente.getVitoria() + terminal.getVitoria());
                n_existente.setDerrota(n_existente.getDerrota() + terminal.getDerrota());
                n_existente.setEmpate(n_existente.getEmpate() + terminal.getEmpate());
                hashtable.insere(n_existente);
            }
        }

    }

    // numero de vezes que a configuracao t foi vista //vitorias+derrotas+empates
    /**
     * metodo responsavel por calcular a frequencia com que uma determinada configuracao
     * do tabuleiro foi feita pelo jogador Inteligente 
     * @param t
     * @return 
     */ 
    public int numeroDeVezesVisto(JogoDoGalo t) {
        JogoDoGalo jogado = hashtable.procurar(t);//encontra a configuracao do jogo do galo
        int numerovezes = jogado.getVitoria() + jogado.getEmpate() + jogado.getDerrota();
        //calcula e retorna o numero de vezes que essa configuracao foi vista (vitorias+empates+derrotas)
        return numerovezes;
    }

    /**
     * extensao do metodo numeroDeVezesVisto(), percorre a extensao da hashtable
     * e descobre a jogada mais frequente de todas analisando as configuracoes do
     * tabuleiro
     * @return 
     */
    public JogoDoGalo jogadaFavorita() {
        JogoDoGalo favorito = null;//variavel para armazenar a configuracao mais frequente
        int nr_vezes = -1;//variavel que memoriza o nr de vezes que determinada configuracao foi vista
        for (int i = 0; i < hashtable.tamanhoHash(); i++) {//percorre a hashtable
            if (hashtable.getArray()[i] != null) {//se encontrar um jogo do galo
                int nr_vezes_aux = numeroDeVezesVisto(hashtable.array[i].getElemento());
                //calcula o numero de vezes que essa configuracao foi vista
                if (nr_vezes_aux > nr_vezes) {//se o numero de vezes visto for maior que valores anteriores
                    favorito = hashtable.array[i].getElemento();//memoriza a jogada
                    nr_vezes = nr_vezes_aux;//memoriza o nr de vezes vista
                }
            }

        }
        return favorito;//retorna a jogada mais frequente
    }

    // retorna todas as configuracoes que foram jogadas apos a configuracao t
    /**
     * metodo responsavel por descobrir as jogadas que o jogador Inteligente ja
     * fez apos a configuracao actual do jogo do galo, tendo em conta os movimentos
     * possiveis apos essa configuracao
     * @param t
     * @return 
     */
    public JogoDoGalo[] sucessores(JogoDoGalo t) {
        JogoDoGalo[] sucessores_aux = new JogoDoGalo[9];
        //cria um array com tamanho 9, uma vez que no maximo dos casos 9 jogadas sao possiveis
        JogoDoGalo[] sucessores;//cria o array final
        int contador = 0;//memoriza o numero de jogadas a colocar no array
        ArrayList<JogoDoGalo> movimentospossiveis = t.MovimentoPossiveisTabuleiro();
        //calcula as jogadas possiveis
        for (JogoDoGalo movimento : movimentospossiveis) {//percorre as jogadas possiveis
            if (hashtable.contains(movimento)) {//se a hash contem uma jogada possivel
                sucessores_aux[contador] = hashtable.procurar(movimento);//coloca no array auxiliar
                contador++;//incrementa o numero de jogadas
            }
        }
        if (sucessores_aux.length == 0) {//se o array auxiliar esta vazio -  nao ha jogadas
            sucessores = new JogoDoGalo[0];//cria um array sem length
        } else {//se nao esta vazio
            sucessores = new JogoDoGalo[contador];//inicializa o array de sucessores com o tamanho exacto de jogadas
            for (int i = 0; i < contador; i++) {
                sucessores[i] = sucessores_aux[i];//copia o auxiliar para o final
            }
        }

        return sucessores;
    }

}
