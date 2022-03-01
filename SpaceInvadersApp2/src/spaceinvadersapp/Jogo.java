package spaceinvadersapp;

import spaceinvadersapp.entidades.Canhao;
import spaceinvadersapp.entidades.Invasor;
import spaceinvadersapp.entidades.Tiro;
import spaceinvadersapp.entidades.Barreira;
import java.util.ArrayList;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Classe que define a engine do jogo: contem os métodos mais próximos do
 * jogador como iniciarJogo(), jogarNovamente(), reset() etc. É nesta classe que
 * os elementos são definidos e suas ações são executadas.
 *
 * @author Mireli Damaceno Barbosa
 * @since dezembro, 2020
 * @version 2.0
 */
public class Jogo extends Application {

    //Propriedades do jogo
    private AnimationTimer timer;
    private int espaco = 40;
    private int pontos = 0;
    private int vidas = 3;
    private boolean morte, atirou, nave_aparece, atingido;
    private int invasoresEliminados = 0;
    private double t = 0; //controle do tempo  
    private int direcao = 1;
    private int totalInvasores;
    private int contadorVelocidade;
    private int contador = 0;
    private boolean moverInvasores = false;
    private boolean pulaLinha = false;
    private int fase = 1;

    //variaveis que demarcam tempo
    private double elapsedTime;
    private double navetime = 0.40;

    //posicoes definidas
    private int ALTURA_TELA = 600;
    private int LARGURA_TELA = 800;
    private int barreirasPosY = ALTURA_TELA - 150;
    private int canhaoPosY = ALTURA_TELA - espaco;

    //Itens auxiliares
    private Group barreira1 = new Group();
    private Group barreira2 = new Group();
    private Group barreira3 = new Group();
    private Group barreira4 = new Group();
    private Invasor.Tipos[] tipoPorLinha = {Invasor.Tipos.V, Invasor.Tipos.X, Invasor.Tipos.X, Invasor.Tipos.W, Invasor.Tipos.W}; //Vetor que define quais serão os tipos de invasores (V, X ou W) por linha
    private Invasor[][] invasores = new Invasor[5][11];
    private ArrayList<Barreira> barreiras = new ArrayList<>();
    private ArrayList<Elemento> tirosInvasores = new ArrayList<>();
    private ArrayList<Elemento> tirosCanhao = new ArrayList<>();

    //itens da classe som
    private Som somTiro;
    private Som musica;

    //Itens da interface grafica
    private Group root;
    private Canvas gameCanvas;
    private GraphicsContext gc;
    private Text pontuacaoLabel, label1, gameOverLabel, label2, vidasLabel, label3, fasesLabel;
    private MenuBox menuBox;

    //Elementos do jogo
    private Invasor nave;
    private Canhao canhao;

    //variaveis auxiliares
    private Random rand = new Random();
    private double random;
    private double time = 0.40;

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Space Invaders");
        stage.setResizable(false);
        stage.setOnCloseRequest(e -> {
            timer.stop();
            SpaceInvadersApp.flag = false;
            stage.close();
        });
        root = new Group();
        gameCanvas = new Canvas(LARGURA_TELA, ALTURA_TELA); //canvas e um no que representa uma area para desenhar na tela, ele sera responsavel pela imagem dos elementos 
        gc = gameCanvas.getGraphicsContext2D(); //funcao para

        Scene scene = new Scene(root); //incializa a tela carregando a raiz de todos os elementos
        scene.setFill(Color.BLACK);

        defineLabels(); //para definir os labels da tela
        defineMenus(); //para redefinir os botoes de JOGAR NOVAMENTE
        root.getChildren().addAll(gameCanvas, pontuacaoLabel, label1, label2, label3, vidasLabel, fasesLabel, barreira1, barreira2, barreira3, barreira4); //adiciona todos os itens graficos na tela
        insereMatrizInvasores();
        defineCanhao(); 
        defineBarreiras();
        defineSom();
        musica.play();
        iniciarJogo();

        stage.setScene(scene);
        stage.getScene().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.LEFT) {
                if (!morte && canhao.getPx() > 50) {
                    canhao.moveEsquerda(-250);
                }
            } else if (!morte && e.getCode() == KeyCode.RIGHT) {
                if (canhao.getPx() < LARGURA_TELA - 100) {
                    canhao.moveDireita(250);
                }
            }
        });

        stage.getScene().setOnKeyReleased(e -> {
            if (!morte && e.getCode() == KeyCode.SPACE && !atirou) {
                somTiro.play();
                movimentoTiroCanhao();
                atirou = true;
            } else if (!morte && e.getCode() == KeyCode.LEFT) {
                canhao.setVelX(0);
                canhao.setVelY(0);
            } else if (!morte && e.getCode() == KeyCode.RIGHT) {
                canhao.setVelX(0);
                canhao.setVelY(0);
            }
        });
        stage.show();
    }

    private void defineLabels() {

        pontuacaoLabel = new Text("Pontuação:");
        pontuacaoLabel.setFill(Color.WHITE);
        pontuacaoLabel.setFont(Font.font("Monaco", FontWeight.EXTRA_BOLD, 20));
        pontuacaoLabel.setX(10);
        pontuacaoLabel.setY(30);

        label1 = new Text(Integer.toString(pontos));
        label1.setFill(Color.LIMEGREEN);
        label1.setFont(Font.font("Monaco", FontWeight.EXTRA_BOLD, 20));
        label1.setX(120);
        label1.setY(30);

        label2 = new Text("Vidas:");
        label2.setFill(Color.WHITE);
        label2.setFont(Font.font("Monaco", FontWeight.EXTRA_BOLD, 20));
        label2.setX(340);
        label2.setY(30);

        vidasLabel = new Text(Integer.toString(vidas));
        vidasLabel.setFill(Color.LIMEGREEN);
        vidasLabel.setFont(Font.font("Monaco", FontWeight.EXTRA_BOLD, 20));
        vidasLabel.setX(400);
        vidasLabel.setY(30);

        gameOverLabel = new Text("GAME OVER");
        gameOverLabel.setFill(Color.WHITE);
        gameOverLabel.setFont(Font.font("Monaco", FontWeight.THIN, 30));
        gameOverLabel.setX(LARGURA_TELA / 2 - 2 * espaco);
        gameOverLabel.setY(30);

        label3 = new Text("Fase:");
        label3.setFill(Color.WHITE);
        label3.setFont(Font.font("Monaco", FontWeight.EXTRA_BOLD, 20));
        label3.setX(LARGURA_TELA - 60);
        label3.setY(30);

        fasesLabel = new Text(Integer.toString(fase));
        fasesLabel.setFill(Color.LIMEGREEN);
        fasesLabel.setFont(Font.font("Monaco", FontWeight.EXTRA_BOLD, 20));
        fasesLabel.setX(LARGURA_TELA - 10);
        fasesLabel.setY(30);

    }

    /**
     *Método utilizado para carregar e definir as condições iniciais do canhão
     */
    public void defineCanhao() {
        canhao = new Canhao();
        canhao.define();
        gc.drawImage(canhao.getImagem(), canhao.getPx(), canhao.getPy());
        
    }

    /**
     *Método utilizado para carregar e definir as condições iniciais do barreira
     */
    public void defineBarreiras() {
        //Define as barreiras
        for (int x = 180, i = 0; x < 600; x += 3 * espaco, i++) {
            Barreira barreira = new Barreira();
            int[][] matriz = barreira.getBarreira();
            Group group;
            if (x <= 180) {
                group = barreira1;
            } else if (x <= 300) {
                group = barreira2;
            } else if (x <= 420) {
                group = barreira3;
            } else {
                group = barreira4;
            }
            adicionaBarreira(x, barreira, matriz, group);
        }

    }

    /**
     *Método para adicionar as quatro barreiras criadas na tela
     * @param posicaoX
     * @param barreira
     * @param matriz
     * @param group
     */
    public void adicionaBarreira(int posicaoX, Barreira barreira, int matriz[][], Group group) {
        barreira.setPx(posicaoX);
        barreira.setPy(barreirasPosY);
        barreiras.add(barreira);
        group.getChildren().clear();

        for (int i = 0, y = 450; i < matriz.length; i++, y += 8) {
            for (int j = 0, x = posicaoX; j < matriz[0].length && x <= 600 + espaco; j++, x += 8) {
                if (matriz[i][j] != 0) {
                    Rectangle rect = new Rectangle();
                    rect.setFill(Color.rgb(20, 244, 47)); //cor verde para preencher o retangulo formado por uma barreira
                    rect.setWidth(8);
                    rect.setHeight(8);
                    rect.relocate(x, y);
                    group.getChildren().add(rect);
                }
            }
        }
    }

    /**
     *Método para definir os diferentes sons do jogo
     */
    public void defineSom() {
        somTiro = new Som("shoot.wav");
        musica = new Som("som.wav");
    }

    /**
     *Método para inserir os elementos que representam os invasores na tela do jogo.
     */
    public void insereMatrizInvasores() {
        direcao = -1; //Direcao=1 -> invasores se movimentam pra direita. Direcao=-1 -> invasores se movimentam pra esquerda 
        totalInvasores = invasores.length * invasores[0].length; //verifica o total de invasores na matriz em determinado momento
        contadorVelocidade = 4 * totalInvasores / fase; //Contador que define quando a matriz de invasores vai se mover

        for (int i = 0, y = 80; i < invasores.length && y < ALTURA_TELA / 2 + espaco; i++, y += espaco) { //Objetos que representam os invasores
            for (int j = 0, x = LARGURA_TELA / 3 - (espaco * 3); j < invasores[i].length && x < 660; j++, x += espaco) {
                switch (tipoPorLinha[i]) {
                    case V:
                        invasores[i][j] = defineInvasores(x, y, "invasor_pequeno.png");
                        gc.drawImage(invasores[i][j].getImagem(), x, y);
                        break;
                    case X:
                        invasores[i][j] = defineInvasores(x, y, "invasor_medio.png");
                        gc.drawImage(invasores[i][j].getImagem(), x, y);
                        break;
                    case W:
                        invasores[i][j] = defineInvasores(x, y, "invasor_grande.png");
                        gc.drawImage(invasores[i][j].getImagem(), x, y);
                        break;
                    default:
                        break;
                }

            }
        }
    }

    /**
     *Método utilizado para carregar e definir as condições iniciais dos invasores
     * @param posX
     * @param posY
     * @param filename
     * @return
     */
    public Invasor defineInvasores(int posX, int posY, String filename) {
        Invasor inv = new Invasor(posX, posY, filename);
        inv.define();
        return inv;
    }
    
 /**
     *Método que caracteriza a lógica do jogo, seja por meio da chamada de métodos ou por meio de códigos dentro deste próprio método.
     *<br>Neste método é verificado se a matriz de invasores foi completamente eliminada e, dessa forma, ocorre mudança de fase. 
     * <br> Aqui também é feito o controle de velocidade do movimento dos invasores à medida em que vão sendo eliminados. No começo essa velocidade é muito pequena, pois a matriz ainda tem muitos elementos. Ao sobrar apenas um inimigo essa velocidade é proporcional à do canhão. 
     *<br> O método inicarJogo() também controla o movimento dos invasores na horizontal e, caso haja colisão com as bordas da tela, na vertical. A incidência de tiros dos inimigos também é formulada neste trecho. 
     *<br>Além das ações citadas, este método define a situação inicial da tela, verifica quais foram as teclas pressionadas pelo usuário, imprime a tela do jogo e verifica o modo de execução (automático ou interativo).
     
     */
    public void iniciarJogo() {
        

        timer = new AnimationTimer() {
            
            long lastNanoTime = System.nanoTime();
            
            @Override
            public void handle(long now) {
                elapsedTime = (now - lastNanoTime) / 1000000000.0;
                lastNanoTime = now;

                gc.clearRect(0, 30, LARGURA_TELA, ALTURA_TELA);
                if (!musica.continuar()) {
                    musica.play();
                }
                movimentoInvasores();
                defineNave();
                controleNave();
                controleTiroCanhao();
                controleTiroInvasores();
                controleCanhao();

                time += 1 / (totalInvasores * 1.0);

                if (time >= 1.5 && vidas > 0 && !morte) {
                    movimentoTiroInvasores();
                    time = 0;
                }
                if (morte && vidas > 0) {
                    reset();
                    iniciarJogo();
                } else if (morte && vidas == 0) {  //Game over!!
                    timer.stop();
                    gameOver();
                }

            }
        };
        timer.start();
    }

    /**
     *Método que representa todo o movimento dos invasores, na horizontal ou na vertical
     */
    public void movimentoInvasores() {
        if (contador > contadorVelocidade) {
            moverInvasores = true; //Determina que nesse momento haverá o movimento da matriz de invasores
            contador = 0;
            contadorVelocidade = 4 * totalInvasores + 60 - invasoresEliminados * (1 / 20) - (2 * fase); //A velocidade dos invasores aumenta à medida em que eles sao eliminados e à medida em o jogador passa de fase.               

        } else {
            contador++;

        }
        boolean colideBordas = false;
        for (int i = invasores.length - 1; i >= 0; i--) { //Objetos que representam os invasores
            for (int j = 0; j < invasores[0].length; j++) {
                Invasor inv = invasores[i][j];
                if (invasores[i][j] != null) {
                    if (moverInvasores) {
                        if (pulaLinha) //Se for verificada a colisão, a matriz de invasores pula uma linha pra baixo
                        {
                            inv.setPy(inv.getPy() + espaco);
                        } else {
                            inv.incrementaPx(direcao * espaco); //Se nao for verificada colisao, continua no movimento horizontal pra direita ou pra esquerda, dependendo do valor da variavel direcao 
                        }

                        if (pulaLinha == false && colideBordas == false) { //Verifica se no proximo movimento dos invasores havera colisao com as bordas
                            double pxEsq = inv.getPx() - espaco;
                            double pxDir = inv.getPx() + espaco;

                            if (pxEsq <= 0 || pxDir >= LARGURA_TELA - 50)//Se houve colisao com as bordas da tela, a variavel booleana seta para true
                            {
                                colideBordas = true;
                            }

                        }
                        controleInvasores(invasores[i][j]);
                    }

                }
            }
        }

        if (moverInvasores && pulaLinha) { //Se ocorreu a colisao e foi para a linha de baixo, entao muda o sentido do movimento horizontal dos invasores, mudando a variavel direcao para seu oposto
            direcao *= -1;
            pulaLinha = false;
        } else if (moverInvasores && colideBordas) //Se ocorreu a colisao, seta a variavel novaLinha pra true para posteriormente ir pra linha de baixo
        {
            pulaLinha = true;
        }

        moverInvasores = false;

        for (int i = 0; i < invasores.length; i++) {
            for (int j = 0; j < invasores[i].length; j++) {
                Invasor ini = invasores[i][j];
                if (ini != null) {
                    if (null != tipoPorLinha[i]) {
                        switch (tipoPorLinha[i]) {
                            case V:
                                ini.defineImagem("invasor_pequeno.png");
                                gc.drawImage(ini.getImagem(), ini.getPx(), ini.getPy());
                                break;
                            case X:
                                ini.defineImagem("invasor_medio.png");
                                gc.drawImage(ini.getImagem(), ini.getPx(), ini.getPy());
                                break;
                            case W:
                                ini.defineImagem("invasor_grande.png");
                                gc.drawImage(ini.getImagem(), ini.getPx(), ini.getPy());
                                break;
                            default:
                                break;
                        }
                    }
                }
            }
        }
    }

    /**
     *Método responsável por controlar a posição vertical que a matriz de invasores pode atingir
     * @param inv
     */
    public void controleInvasores(Invasor inv) {
        if (inv.getPy() >= canhaoPosY - inv.getAltura()) {
            timer.stop();
            gameOver();

        }
    }

    /**
     *Método utilizado para carregar e definir as condições iniciais da nave aleatória
     */
    public void defineNave() {
        if (rand.nextDouble() < 0.0003) {
            nave = new Invasor();
            nave.defineImagem("ufo.png");
            nave.setPx(0);
            nave.setPy(40);
            gc.drawImage(nave.getImagem(), nave.getPx(), nave.getPy());
            if (!nave_aparece) { //!nave.isAtivo();
                nave_aparece = true;//nave.setAtivo(true);
                nave.moveDireita(170);
            }
        }
    }

    /**
     *Método para fazer o controle do movimento da nave e se houve interseção dela com o canhão
     */
    public void controleNave() {
        if (nave_aparece && nave.getPx() < LARGURA_TELA) {
            gc.drawImage(nave.getImagem(), nave.getPx(), nave.getPy());
            nave.update(elapsedTime);
            if (atirou) { //verifica se o tiro do canhao colide com a nave
                if (nave.intersects(tirosCanhao.get(0))) {
                    pontos += 100; //pontuacao maior se o jogador acertar a nave aleatoria
                    label1.setText(Integer.toString(pontos)); //muda o score no label de score
                    tirosCanhao.clear();
                    atirou = false;
                    nave = null;
                    nave_aparece = false;
                }
            }
        } else {
            nave = null;
            nave_aparece = false; //nave.setAtivo(false)
        }
    }

   /**
     *Metodo para controlar o movimento vertical para cima do tiro do canhão 
     */
    public void movimentoTiroCanhao() {
        Elemento tiroCanhao = new Tiro();
        tiroCanhao.defineImagem("missil.png");
        tiroCanhao.setPx(canhao.getPx() + 10);
        tiroCanhao.setPy(canhao.getPy() - 20);
        tiroCanhao.setVelX(0);
        tiroCanhao.setVelY(-350);
        gc.drawImage(tiroCanhao.getImagem(), tiroCanhao.getPx(), tiroCanhao.getPy());
        tirosCanhao.add(tiroCanhao);
    }

    /**
     *Método para verificar se o canhão atingiu os invasores ou a barreira
     */
    public void controleTiroCanhao() { //Para saber se o tiro do canhao atinge algum elemento ou chega na parte superior da tela
        if (atirou) {
            Elemento tiroCanhao = tirosCanhao.get(0);
            gc.drawImage(tiroCanhao.getImagem(), tiroCanhao.getPx(), tiroCanhao.getPy());
            tiroCanhao.update(elapsedTime);
            if (colisaoInimigos() || tiroCanhao.getPy() <= 30 || colisaoBarreiras(tiroCanhao)) {
                tirosCanhao.clear();
                atirou = false;
                if (totalInvasores == 0) {
                    fase++;
                    fasesLabel.setText(Integer.toString(fase));
                    insereMatrizInvasores();
                }
            }
        }
    }

    /**
     *Método que verifica o que será feito após a colisão de um tiro do canhão com um invasor
     * @return
     */
    public boolean colisaoInimigos() { //Colisao dos tiros do canhao com os invasores
        for (int i = 0; i < invasores.length; i++) {
            for (int j = 0; j < invasores[0].length; j++) {
                if (invasores[i][j] != null) {
                    if (invasores[i][j].intersects(tirosCanhao.get(0))) {
                        invasores[i][j].setTipo(tipoPorLinha[i]);
                        pontos += invasores[i][j].getPontos();
                        invasoresEliminados++;
                        totalInvasores--;
                        invasores[i][j] = null;
                        label1.setText(Integer.toString(pontos));
                        return true;
                    }
                }
            }
        }
        return false;
    }
    /**
     *Método para controlar o movimento do tiro dos invasores da matriz . 
     */
    public void movimentoTiroInvasores() {
        Elemento tiroInvasor = new Tiro();
        tiroInvasor.defineImagem("missil.png");
        for (int i = (int) (Math.random() * invasores.length - 1); i >= 0; i--) {
            if (atingido) {
                break;
            }
            for (int j = (int) (Math.random() * invasores[i].length); j >= 0; j--) {
                if (invasores[i][j] != null) {
                    tiroInvasor.setPx(invasores[i][j].getPx());
                    tiroInvasor.setPy(invasores[i][j].getPy());
                    tiroInvasor.setVelX(0);
                    tiroInvasor.setVelY(200    );
                    tirosInvasores.add(tiroInvasor);
                    atingido = true;
                    break;
                }
            }
        }
    }

    /**
     *Método para verificar se os tiros dos invasores atingiram o canhão ou as barreiras em algum momento
     */
    public void controleTiroInvasores() { //Para saber se o tiro dos invasores colide com algum elemento ou seja chega no limite inferior da tela
        if (atingido) {
            Elemento tiroInvasores = tirosInvasores.get(0);
            gc.drawImage(tiroInvasores.getImagem(), tiroInvasores.getPx(), tiroInvasores.getPy());
            tiroInvasores.update(elapsedTime);
            if (tiroInvasores.getPy() >= ALTURA_TELA - espaco || colisaoBarreiras(tiroInvasores)) {
                tirosInvasores.clear();
                atingido = false;
            }
            if (colisaoCanhao(tiroInvasores)) //Se o tiro dos invasores colidir com o canhao
            {
                morte = true; //mudar
            }
        }

    }
    /**
     *Método que verifica o que será feito após a colisão de um tiro de um invasor com o canhão.
     * @return
     */
    public boolean colisaoCanhao(Elemento missel) { //verifica se o canhao foi atingido pelo tiro dos invasores. Se foi, ele perde uma vida e o elemento assume valor null
        if (canhao != null && canhao.intersects(missel)) {
            vidas--;
            vidasLabel.setText(Integer.toString(vidas));
            canhao = null;
            return true;
        }
        return false;
    }

    /**
     *Método que verifica o que será feito após a colisão de um tiro do canhão ou dos invasores com alguma barreira.
     * @return
     */
    public boolean colisaoBarreiras(Elemento tiro) {
        for (Barreira barreira : barreiras) {
            int[][] matriz = barreira.getBarreira();
            for (int i = 0; i < matriz.length; i++) {
                for (int j = 0; j < matriz[0].length; j++) {
                    if (matriz[i][j] != 0) {
                        if (tiro.getPx() >= barreira.getPx() - 5
                                && tiro.getPx() <= barreira.getPx() + j * 8 + 5
                                && tiro.getPy() >= barreira.getPy() - 5
                                && tiro.getPy() <= barreira.getPy() + i * 8 + 5) {
                            barreira.destroiBlocos(i, j);
                            Group group;
                            if (barreira.getPx() <= 180) {
                                group = barreira1;
                            } else if (barreira.getPx() <= 300) {
                                group = barreira2;
                            } else if (barreira.getPx() <= 420) {
                                group = barreira3;
                            } else {
                                group = barreira4;
                            }
                            adicionaBarreira((int) (barreira.getPx()), barreira, matriz, group);
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    /**
     *Método para controlar o movimento do canhão, caso ele atinja algum extremo da tela.
     */
    public void controleCanhao() { //Verifica se o canhao chegou no canto esquerdo ou no canto direito da tela e modifica sua posicao e sua velocidade
        if (canhao != null) {
            if (canhao.getPx() < 50) {
                canhao.setPx(50);
                canhao.setPy(canhao.getPy());
                canhao.setVelX(0);
                canhao.setVelY(0);
            } else if (canhao.getPx() > LARGURA_TELA - 100) {
                canhao.setPx(canhao.getPx() - 1);
                canhao.setPy(canhao.getPy());
                canhao.setVelX(0);
                canhao.setVelY(0);
            }
            gc.drawImage(canhao.getImagem(), canhao.getPx(), canhao.getPy());
            canhao.update(elapsedTime);
        }
    }

    /**
     *Método para interromper o jogo e exibir os botões de novo jogo ou de sair.
     */
    public void gameOver() {
        timer.stop();
        try {
            root.getChildren().add(gameOverLabel);
            root.getChildren().add(menuBox);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void jogarNovamente() {
        vidas = 3;
        pontos = 0;
        label1.setText(Integer.toString(pontos));
        vidasLabel.setText(Integer.toString(vidas));
        root.getChildren().remove(gameOverLabel);
        root.getChildren().remove(menuBox);
        insereMatrizInvasores();
        barreiras.clear();
        defineBarreiras();
        reset();
        iniciarJogo();
    }

    /**
     *Método para resetar as variáveis e atributos do programa.
     */
    public void reset() {
        defineCanhao();
        time = 0;
        morte = false;
        atingido = false;
        atirou = false;
        nave_aparece = false;
        tirosCanhao.clear();
        tirosInvasores.clear();
        nave = null;
    }

    /**
     *Método para definir o menu de opção do jogador.
     */
    public void defineMenus() {
        menuBox = new MenuBox(400, 100);
        menuBox.setTranslateX(200);
        menuBox.setTranslateY(300);

        MenuItem startButton = new MenuItem("JOGAR NOVAMENTE", 250);
        startButton.setOnAction(() -> jogarNovamente());

        MenuItem quitGame = new MenuItem("SAIR", 250);
        quitGame.setOnAction(() -> {
            System.exit(0);
        });
        menuBox.addItems(startButton, quitGame);

    }

    /**
     *Método principal
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }
}
