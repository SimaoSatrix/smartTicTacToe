/**
 * Simão Ramos(29035) Marlene Barroso(30178) EDA I
 */
package ProjectGalo;


/**
 * A classe hashtable é uma estrutura de dados de tabelas de dispersao genérica e
 * abstracta de Elementos. Com os métodos abstractos de procPos e myHash
 *
 * @param <E>
 */
public abstract class Hashtable<E> {

    /**
     * A classe Elemento cria os objectos que irão preencher a hashtable
     *
     * @param <E>
     */
    public static class Elemento<E> {

        public E elemento; // o elemento
        public boolean estaActivo; // false se marcado para apagar

        /**
         * construtor default, com variavel boolean a true
         *
         * @param e
         */
        public Elemento(E e) {
            this(e, true);
        }

        /**
         * Construtor com variavel boolean como argumento
         *
         * @param e
         * @param i boolean do estado do elemento no array
         */
        public Elemento(E e, boolean i) {
            elemento = e;
            estaActivo = i;
        }

        /**
         * método que cacula o hashcode do elemento
         */
        @Override
        public int hashCode() {
            return elemento.hashCode();
        }

        /**
         * retorna o elemento
         */
        public E getElemento() {
            return elemento;
        }

    }

    private static final int TAMANHO_DEFAULT = 11;//tamanho original do array (número primo)
    public Elemento<E>[] array; // array de elementos
    public int ocupados; // numero de celulas ocupadas
    public int colisoes; // numero de vezes que uma celula diferente tenta ocupar uma celula já preenchida

    /**
     * construtor da hashtable com tamanho default
     * ocupados e colisoes inicializados a 0
     */
    public Hashtable() {
        array = new Elemento[TAMANHO_DEFAULT];
        ocupados = 0;
        colisoes = 0;
    }

    /**
     * Construtor da hashtable com o tamanho como argumento
     *ocupados e colisoes inicializados a 0
     * 
     * @param tamanho the approximate initial size.
     */
    public Hashtable(int tamanho) {
        alocarArray(tamanho);//chama o metodo alocar o array que prepara o array 
        //para o tamanho passado como argumento 
        ocupados = 0;
        colisoes = 0;
        
    }
    
    /**
     * retorna o array da Hash
     * @return 
     */
    public Elemento<E>[] getArray() {
        return array;
    }
    
    /**
     * retorna o tamanho do array da hash
     * @return 
     */
    public int tamanhoHash() {
        return array.length;
    }
    
    /**
     * retorna o numero de celulas que actualmente ocupam a hash
     * @return 
     */
    public int ocupados() {
        return ocupados;
    }

    /**
     * retorna o factor de carga - racio entre o numero de celulas totais
     * e as ocupadas
     * @return 
     */
    public float factorCarga() {
        //necessario cast para float para obter os valores decimais e nao aproximacoes
        //evitando que o factor de carga apresente sempre valor de 0
        return (float) ocupados / (float) array.length;
    }

    /**
     * metodo abstracto que procura a posicao do elemento na hash consoante a
     * chave dada pelo hashcode
     * o metodo e depois implementado aquando da implementacao da hash
     * @param s elemento
     * @return 
     */
    protected abstract int procPos(E s);

    /**
     *calcula a chave para a hashtable consoante o hashcode do elemento
     * @param code hashcode do elemento
     * @return 
     */
    protected abstract int myHash(int code);

    /**
     * metodo interno que cria um novo array com o tamanho passado como
     * argumento
     *
     * @param tamanhoarray 
     */
    public void alocarArray(int tamanhoarray) {
        array = new Elemento[proximoPrimo(tamanhoarray)];
    }

    /**
     * torna a hash vazia
     */
    public void tornarVazia() {
        ocupados = 0;//reseta as celulas ocupadas
        for (int i = 0; i < array.length; i++) {//percorre o array e coloca tudo a null
            array[i] = null;
        }
    }

    /**
     * metodo que procura o elemento na hashtable e o retorna
     *
     * @param x 
     * @return 
     */
    public E procurar(E x) {
        int indice = procPos(x);//procura o indice do elemento
        E elemento = (E) array[indice].elemento;//acede a hash
        return elemento;
    }

    /**
     * remove o elemento da hash
     * 
     * @param x
     */
    public void remove(E x) {
        int indice = procPos(x);//procura o indice do elemento
        array[indice].elemento = null;//torna o elemento null
        array[indice].estaActivo = false;//e muda o estado do elemento para false
        ocupados--;//decrementa o numero de celulas ocupadas
    }

    /**
     * Insere um elemento na hash
     * 
     * rehash ocorre quando ocorre uma colisao, inserir numa celula ja ocupada,
     * ou quando o factor de carga passa o valor de 0,5, metada do array ja esta
     * preenchido aumentando a probablidade de colisoes
     *
     * @param x 
     */
    public void insere(E x) {
        int indice = procPos(x);//calcula o indice da celula que o elemento ira ocupar
        if (estaActivo(indice)) {//se a celula estaActiva, significa que esta ocupada
            //e por isso existe uma colisao
            colisoes++;//incrementa o numero de colisoes
            rehash();//faz rehash
        } else {
            ocupados++;//aumenta o numero de ocupados
            array[indice] = new Elemento(x, true);//cria um novo objecto elemento
            //e coloca o no indice
        }
        if (factorCarga() > 0.5) {// se o factor de carga e maior que 0,5
            rehash();// faz rehash
        }
    }

    /**
     * o rehash cria um novo array com o dobro do tamanho (valor primo mais proximo)
     * copia o array antigo para o novo recalculando os indices tendo em conta o
     * novo tamanho do array
     */
    private void rehash() {
        int tamanho = array.length * 2;//duplica o tamanho
        Elemento<E>[] velho = array;//guarda o array antigo
        alocarArray(tamanho);//aloca o array com o tamanho duplicado
        ocupados = 0;//reseta os ocupados
        for (int i = 0; i < velho.length; i++) {//percorre o velho array
            if (velho[i] != null) {//todos as celulas nao nulas, estao ocupadas
                insere(velho[i].elemento);// e sao inseridas no novo array
            }
        }

    }

    /**
     * verifica se o array contem o elemento 
     *
     * @param x 
     * @return 
     */
    public boolean contains(E x) {
        int currentPos = procPos(x);//procura o indice do elemento
        return estaActivo(currentPos);//retorna o estado da celula que corresponde ao indice
    }

    /**
     * verifica se uma celula esta ocupada
     *
     * @param actualPos
     * @return
     */
    private boolean estaActivo(int actualPos) {
        return array[actualPos] != null && array[actualPos].estaActivo;
    }
    /**
     * imprime a hashtable-- indice | elemento --
     */
    public void print() {
        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                System.out.println(i + " " + array[i].elemento.toString());
            }
        }
    }

    /**
     * metodo interno que descobre qual o proximo primo
     *
     * @param n numero positivo inferior ou igual ao proximo numero primo
     * @return 
     */
    private static int proximoPrimo(int n) {
        boolean primo = false;
        while (primo == false) {// enquanto nao for primo
            if (ePrimo(n)) {//verifica se e primo
                primo = true;
                break;
            }
            n++;//incrementa o valor de n
        }
        return n;
    }

    /**
     * metodo interno que verifica se um determinado numero e primo
     */
    private static boolean ePrimo(int n) {
        for (int i = 2; i <= Math.sqrt(n); i++) {//verifica os divisores desde o
            //o primeiro primo dois, ate a raiz quadrada do numero
            if (n % i == 0) {
            //se o resto da divisao do numero por o divisor for 0 entao o numero nao e primo
                return false;
            }
        }
        return true;
    }
    /**
     * main para o teste da hashtable
     */
//    public static void main(String[] args) {
//        int tete = 1;
//        int tata = 2;
//        int titi = 3;
//        int t2ta = 4;
//        int t3te = 5;
//        int t4ta = 6;
//        int t5te = 7;
//        int t6ta = 8;
//        int t7te = 9;
//        int t8ta = 10;
//        int t9te = 11;
//        int t0ta = 12;
//        int repetida = 1;
//        String tete= ("tetetete");
//        Hashtable<String> hash = new Hashtable<String>() {
//
//            @Override
//            protected int procPos(String s) {
//                int pos= s.hashCode();
//                return pos;
//            }
//        };
//        hash.insere(tete);
//        hash.insere(tata);
//        hash.insere(titi);
//        hash.insere(t2ta);
//        hash.insere(t3te);
//        hash.insere(t4ta);
//        hash.insere(t5te);
//        hash.insere(t6ta);
//        hash.insere(t7te);
//        hash.insere(t8ta);
//        hash.insere(t9te);
//        hash.insere(t0ta);
//        hash.remove(t0ta);
//        
//
//        hash.print();
//        System.out.println(hash.array.length);
//
//    }
}
