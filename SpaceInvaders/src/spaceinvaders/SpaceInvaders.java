/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spaceinvaders;
import java.io.IOException;
import java.io.Reader;
import java.util.Scanner;
import java.util.Random;
import java.util.InputMismatchException;

/**
 *Classe principal. Ela é responsável por instanciar as demais classes do projeto e possuir os métodos que definem as ações do jogo.
 * @author mirel
 */
public class SpaceInvaders {
    /**
    *Atributo que contabiliza as vidas do jogador em um determinado momento.
    */
    private int vidas=3;
    
    /**
    *Atributo que contabiliza a pontuação do jogador em um determinado momento.
    */
    private int pontos; 
    
    /**
    *Atributo que caracteriza a fase do jogo em um determinado momento.
    */
    private int fase=1; //fases do jogo (sao infinitas)
    
    /**
    *Atributo que contabiliza a quantidade de invasores em um determinado momento.
    */
    private int totalInvasores;
    
    /**
    *Atributo que contabiliza a quantidade de invasores que já foram destruídos em um determinado momento.
    */
    private int invasoresEliminados=0; 
    
    /**
    *Atributo que representa a largura da tela do jogo.
    */
    private int larguraTela=40;
    
    /**
    *Atributo que representa a altura da tela do jogo.
    */
    private int alturaTela=23;
    
    /**
    *Atributo que representa a largura da tela do jogo.
    */
    private char [][] tela = new char[larguraTela][alturaTela]; //Tela do jogo
    private Invasor[][] invasores = new Invasor[5][11]; //matriz de invasores
    
    /**
    *Atributo que faz o controle do movimento dos invasores na horizontal (esquerda ou direita).
    */
    private int direcao; 
    
    /**
    *Atributo booleano que verifica se houve um pulo de linha no movimento dos invasores.
    */
    boolean pulaLinha; 
    
    /**
    *Atributo que verifica se haverá movimento dos invasores).
    */
    boolean moverInvasores; //variavel que define se havera movimento dos invasores.
    
    /**
    *Atributo que define uma velocidade para o movimento dos invasores em um determinado instante.
    */
    private int contadorVelocidade;
    
    /**
    *Variável que auxilia na determinação do movimento da matriz de invasores.
    */
    int contador;
    
    /**
    *Variavel que faz define se haverá interação com o usuário no jogo ou se ele será apresentado em modo automático.
    */
    static int automatico;
    
    /**
    *Variável que recebe a tecla pressionada pelo jogador.
    */
    char tecla;
    
    Scanner texto = new Scanner(System.in); 
    
    /**
    *Variável que recebe um valor aleatório. Essa propriedade é útil em alguns momentos do jogo.
    */
    private Random rand = new Random();
    
    /*Elementos do jogo*/
    
    /**
    *Atributo do tipo Elemento responsável por representar o jogador.
    */
    private Elemento canhao;
    
    /**
    *Atributo do tipo Elemento responsável por representar os tiros do jogador.
    */
    private Elemento tiroCanhao;
    
    /**
    *Atributo do tipo Elemento responsável por representar os tiros dos invasores.
    */
    private Elemento [] tirosInvasores = new Tiro[3]; //Tiros dos invasores   
    
    /**
    *Atributo do tipo Invasor responsável por representar a nave que aparece eventualmente no jogo.
    */
    private Invasor nave;
    
    /**
    *Atributo do tipo Elemento responsável por representar o tiro da nave que aparece eventualmente no jogo.
    */
    private Elemento tiroNave;    
    
    /**
    *Atributo que representa o tipo de invasor em uma determinada linha da matriz, uma vez que temos três tipos na matiz.
    */
    private Invasor.Tipos[] tipoPorLinha = {Invasor.Tipos.V, Invasor.Tipos.X,Invasor.Tipos.X,Invasor.Tipos.W,Invasor.Tipos.W}; //Vetor que define quais serão os tipos de invasores (V, X ou W) por linha
   
    /**
     *Método utilizado para carregar e definir as condições iniciais dos elementos do jogo.
     */
    public void defineElementos(){      
        
        canhao=new Canhao(); //Objeto para representar o canhao
        canhao.setAtivo(true);
        canhao.setpX(8); //Posicao inicial do canhao no eixo x = 20% da largura da tela = 20% de 40 = 8
        canhao.setpY(alturaTela-1); //Posicao inicial do canhao no eixo y
        canhao.setCaractere(canhao.defineCaractere()); //O caractere do canhao sempre sera este
        
        tiroCanhao = new Tiro();  //Objeto para representar o tiro do canhao
        tiroCanhao.setCaractere(tiroCanhao.defineCaractere());
        
        nave = new Invasor(Invasor.Tipos.N); //Objeto para representar a nave que se move na horizontal e aparece eventualmente
        
        tiroNave = new Tiro(true); //Objeto para representar o tiro da nave que aparece eventualmente
        tiroNave.setCaractere(tiroNave.defineCaractere());
        
        for(int i=0; i<tirosInvasores.length; i++){  
            tirosInvasores[i] =new Tiro(true);  //Objetos que representam os tiros dos invasores
            tirosInvasores[i].setCaractere(tirosInvasores[i].defineCaractere()); 
        }        
        
        for(int i=0; i<invasores.length; i++){ //Objetos que representam os invasores
            for(int j=0; j<invasores[i].length; j++){
                Invasor ini = new Invasor(tipoPorLinha[i]); 
                ini.setAtivo(true);                
                ini.setpX(15+j); //A posicao inicial em x sera 15, para que a matriz de invasores fique no meio da tela
                ini.setpY(3+i); //A posicao inicial em y sera 3, para que a matriz de invasores fique na parte de cima da tela
                ini.setCaractere(ini.defineCaractere()); //Chama a funcao para definir qual sera o caractere associado ao objeto instanciado
                invasores[i][j] = ini; //Salva o objeto em uma variavel do tipo Invasor para usa-lo depois               
            }
        }        
        
        direcao=-1; //Direcao=1 -> invasores se movimentam pra direita. Direcao=-1 -> invasores se movimentam pra esquerda 
        totalInvasores=invasores.length * invasores[0].length; //verifica o total de invasores na matriz em determinado momento
        contadorVelocidade=50; //Contador que define quando a matriz de invasores vai se mover
    }
       
    /**
     *Método que caracteriza a lógica do jogo, seja por meio da chamada de métodos ou por meio de códigos dentro deste próprio método.
     *<br>Neste método é verificado se a matriz de invasores foi completamente eliminada e, dessa forma, ocorre mudança de fase. 
     * <br> Aqui também é feito o controle de velocidade do movimento dos invasores à medida em que vão sendo eliminados. No começo essa velocidade é muito pequena, pois a matriz ainda tem muitos elementos. Ao sobrar apenas um inimigo essa velocidade é proporcional à do canhão. 
     *<br> O método inicarJogo() também controla o movimento dos invasores na horizontal e, caso haja colisão com as bordas da tela, na vertical. A incidência de tiros dos inimigos também é formulada neste trecho. 
     *<br>Além das ações citadas, este método define a situação inicial da tela, verifica quais foram as teclas pressionadas pelo usuário, imprime a tela do jogo e verifica o modo de execução (automático ou interativo).
     
     */
    public void iniciarJogo(){
        while(true){
            inicializaTela();
            
            if (invasoresEliminados == totalInvasores) { //Verifica se a matriz de invasores foi totalmente destruida e então, se estiver, incrementa a fase
		invasoresEliminados = 0;
		fase++;
		defineElementos();
		continue;
            }
            
            if(contador>contadorVelocidade){
                    moverInvasores=true; //Determina que nesse momento haverá o movimento da matriz de invasores
                    contador=0;
                    contadorVelocidade=totalInvasores-invasoresEliminados-(fase*fase); //A velocidade dos invasores aumenta à medida em que eles sao eliminados e à medida em o jogador passa de fase.               
            }
            else{
                contador++;
            }
           
            if(canhao.isAtivo()){
                if(tecla=='a') //Se houve o pressionamento da tecla pra mover o canhao pra esquerda
                    canhao.setpX(canhao.getpX() - 1);  //O canhao da 1 passo na horizontal pra esquerda
                else if(tecla=='d')  //Se houve o pressionamento da tecla pra mover o canhao pra direita
                    canhao.setpX(canhao.getpX() + 1); //O canhao da 1 passo na horizontal pra direita 
            }
            
            if(tecla=='w' && !tiroCanhao.isAtivo()) {
            tiroCanhao.setpX(canhao.getpX());
            tiroCanhao.setpY(canhao.getpY());      
            tiroCanhao.setAtivo(true);   
            tiroCanhao.setCaractere(tiroCanhao.defineCaractere());
            }

             controleCanhao();
             movimentoNave();
          
            boolean colideBordas=false;
            for(int i=invasores.length-1; i>=0; i--){ //inverte a matriz dos invasores em relação ao eixo x para que os tiros do canhão peguem primeiramente os invasores da última linha
                for(int j=0; j<invasores[i].length; j++){       
                    Invasor inv=invasores[i][j];
                    
                    if(Colisao.houveColisao(tiroCanhao, inv)){ //Verifica se o tiro do canhao atingiu algum invasor da matriz
                            tiroCanhao.setAtivo(false);
                            inv.setAtivo(false);                           
                            tiroCanhao.setCaractere(' ');
                            inv.setCaractere(' ');
                            invasoresEliminados++; //se atingiu, o invasor e eliminado
                            pontos=pontos+inv.getPontos();      
                    }
                                           
                    
                    if(moverInvasores){
                        if(pulaLinha) //Se for verificada a colisão, a matriz de invasores pula uma linha pra baixo
                            inv.setpY(inv.getpY() + 1);
                                               
                        else
                            inv.incrementaPx(direcao); //Se nao for verificada colisao, continua no movimento horizontal pra direita ou pra esquerda, dependendo do valor da variavel direcao 
                        
                        
                        if(pulaLinha==false && colideBordas==false){ //Verifica se no proximo movimento dos invasores havera colisao com as bordas
                            int pxEsq=inv.getpX()-1; 
                            int pxDir=inv.getpX()+1;    
                            
                            if(pxEsq<=0 || pxDir>=larguraTela)//Se houve colisao com as bordas da tela, a variavel booleana seta para true
                                colideBordas=true;                               
                            
                        }
                        
                        /*Define de onde sairao os tiros dos invasores em direcao ao canhao */                       
                        if(!tirosInvasores[0].isAtivo() && inv.isAtivo() && inv.getpX()< canhao.getpX()){  //um tiro aleatorio a esquerda do canhao
                            adicionaTiroInimigo(inv, tirosInvasores[0]);
                            
                        }
                        else if(!tirosInvasores[1].isAtivo() && inv.isAtivo() && inv.getpX()==canhao.getpX()){ //o tiro sera na direcao do jogador
                            adicionaTiroInimigo(inv, tirosInvasores[1]);
                            
                        }
                        else if(!tirosInvasores[2].isAtivo() && inv.isAtivo() && inv.getpX()> canhao.getpX()){ //um tiro aleatorio a direita do canhao
                            adicionaTiroInimigo(inv, tirosInvasores[2]);
                            
                        }
                        
                        if(nave.isAtivo()==false && rand.nextInt(50)>47){
                            nave.setAtivo(true);
                            nave.setpX(0);
                            nave.setpY(1);
                            nave.setCaractere(nave.defineCaractere());
                        }
                            
                    }
                }

            }
            
            if(moverInvasores && pulaLinha){ //Se ocorreu a colisao e foi para a linha de baixo, entao muda o sentido do movimento horizontal dos invasores, mudando a variavel direcao para seu oposto
                direcao*=-1;
                pulaLinha=false;
            }
            else if(moverInvasores && colideBordas) //Se ocorreu a colisao, seta a variavel novaLinha pra true para posteriormente ir pra linha de baixo
                pulaLinha=true;
            
            moverInvasores=false;            
            
            movimentoTiroCanhao();
            movimentoTiroNave();
            movimentoTiroDosInvasores();
            insereMatrizInvasores();
            
            
                
                
            imprimeTela();    
            if(vidas==0){                
                imprimeScore();
                System.exit(0);
            }   
            if(automatico!=1)
                tecla=texto.next().charAt(0);
            else
                 jogoAutomatico();               
            
        }                                   
    }
    
    /**
     *Metodo para inicializar a tela do jogo com espacos em branco.
     */
    public void inicializaTela(){ 
        for (int i = 0; i < larguraTela; i++) {
            for (int j = 0; j< alturaTela; j ++) {
                    tela[i][j]=' ';
            }
        }
    }
    
    /**
     *Método para limitar o movimento do canhão e também atribuir seu caractere à matriz de caracteres que representa a tela do jogo.
     */
    public void controleCanhao(){ //Metodo para controlar ate onde pode ser o movimento do canhao             
           if(canhao.getpX()==0)   //Se o canhao chegou na lateral esquerda da tela, nao se move mais
               canhao.setpX(1);
           if(canhao.getpX()==larguraTela-1) //Se o canhao chegou na lateral direita da tela, nao se move mais
               canhao.setpX(larguraTela-2);
           tela[canhao.getpX()][canhao.getpY()]=canhao.getCaractere();                        
    }
    
    /**
     *Metodo para controlar o movimento vertical para cima do tiro do canhão e para verificar se ele atingiu a nave.
     */
    public void movimentoTiroCanhao(){        
        if(tiroCanhao.isAtivo()){
            tiroCanhao.incrementaPy(-1);
            
            if(Colisao.houveColisao(tiroCanhao, nave)){ //Verifica se houve colisao do canhao com a nave que aparece eventualmente
                pontos=pontos+nave.getPontos();
                tiroCanhao.setAtivo(false);
                tiroCanhao.setCaractere(' ');
                nave.setAtivo(false);
                nave.setCaractere(' ');
            }
            else if(tiroCanhao.getpY()==0){
               tiroCanhao.setAtivo(false);
               tiroCanhao.setCaractere(' ');                            
           }
            
           tela[tiroCanhao.getpX()][tiroCanhao.getpY()]=tiroCanhao.getCaractere();
        }         
    }
    
    /**
     *Método para controlar o movimento do tiro da nave que aparece eventualmente no jogo. 
     */
    public void movimentoTiroNave(){
        if(tiroNave.isAtivo()){
            tiroNave.incrementaPy(1);
            
            if(Colisao.houveColisao(tiroNave, canhao)){
                vidas--;
                tiroNave.setAtivo(false);
                tiroNave.setCaractere(' ');
            }
            else if(tiroNave.getpY()==alturaTela-1){
                tiroNave.setAtivo(false);
                tiroNave.setCaractere(' ');
            }
                tela[tiroNave.getpX()][tiroNave.getpY()]=tiroNave.getCaractere();
                
        }
    }
    
    /**
     *Método para controlar o movimento da nave que aparece eventualmente no jogo. Os tiros dessa entidade só serão permitidos quando sua posição horizontal coincidir com a posição horizonal do canhão. No método iniciarJogo() foram definidos números aleatórios para indicar a aparição da nave.
     */
    public void movimentoNave(){
        if(nave.isAtivo()){
            nave.incrementaPx(1);
            if(!tiroNave.isAtivo() && Colisao.coincideEixoX(nave, canhao))
                adicionaTiroInimigo(nave, tiroNave);
            else if(nave.getpX()==larguraTela-1){
                nave.setAtivo(false);
                nave.setCaractere(' ');
            }
            tela[nave.getpX()][nave.getpY()]=nave.getCaractere();
        }
    }
    
    /**
     *Método para controlar o movimento do tiro dos invasores da matriz e verificar se atingiram o canhão em algum momento. 
     */
    public void movimentoTiroDosInvasores(){
        for(int i=0; i<tirosInvasores.length; i++){
            if(tirosInvasores[i].isAtivo()){
                tirosInvasores[i].incrementaPy(1);
                    
                if(Colisao.houveColisao(tirosInvasores[i], canhao)){ //Se os tiros dos invasores atingirem o canhao
                    vidas--;  //Diminui uma vida do jogador
                    tirosInvasores[i].setAtivo(false); 
                    tirosInvasores[i].setCaractere(' ');
                    canhao.setpX(8);                    
                }
                
                else if(tirosInvasores[i].getpY()==alturaTela-1){ //Se os tiros dos invasores chegaram à parte inferior da tela
                    tirosInvasores[i].setAtivo(false);
                    tirosInvasores[i].setCaractere(' ');
                }
                tela[tirosInvasores[i].getpX()][tirosInvasores[i].getpY()] = tirosInvasores[i].getCaractere();               
            }
                    
        }
    }
    
    /**
     *Método para inserir os caracteres que representam os invasores na tela do jogo.
     */
    public void insereMatrizInvasores(){
        for(int i=0; i<invasores.length; i++){
            for(int j=0; j<invasores[i].length; j++){
                Invasor ini;
                ini=invasores[i][j];
                if(ini.getpY()==alturaTela-1)
                    System.exit(0);
                tela[ini.getpX()][ini.getpY()]=ini.getCaractere();
            }                   
        }
    }
    
    /**
     * Método para obter os tiros dos inimigos.
     * 
     */
    public void adicionaTiroInimigo(Elemento inimigo, Elemento tiro){ 
        tiro.setAtivo(true);
        tiro.setpX(inimigo.getpX());
        tiro.setpY(inimigo.getpY()+1);
        tiro.setCaractere(tiro.defineCaractere());
        
    }
    
    /**
     *Método que exibe a tela do jogo a cada detecção de tecla pressionada pelo jogador.
     */
    public void imprimeTela(){ 
        System.out.println("   Pontuação: " + pontos + "   Vidas: " + vidas + "   Fase: " + fase);
        for (int j = 0; j < alturaTela; j ++) {
            System.out.print("|");
            for (int i = 0; i < larguraTela; i++) {
                
                System.out.print(tela[i][j]);
            }
            System.out.print("|");
            System.out.println("");
        }       
    }
    
    /**
    *Método utilizado para automatizar o jogo e deixá-lo visualmente mais atrativo. O jogador não vai interagir neste método, pois o pressionamento das teclas é controlado por números aleatórios.
    */
    public void jogoAutomatico(){
        int numero;
        numero=rand.nextInt(5);
        if(numero==0 || numero==1)
            tecla='a';
        else if(numero==2 || numero==3)
            tecla='d';
        else
            tecla='w';
        
        try{
            Thread.sleep(250);
        }
        catch(Exception e){
            
        }
    }
    
    /**
     *Método para imprimir a tela final de pontuação do jogo.
     */
    public void imprimeScore(){
        System.out.println("\n \n \n \n \n \n               Vidas esgotadas.");
        System.out.println("\n \n \n             O mundo foi dominado por Space Invaders.");
        System.out.println("\n \n \n \n \n \n               Pontuação final: " + pontos);
    }
    
    /**
     * @param args the command line arguments<br>
     * Método principal, cuja função é instanciar as classes. Para a classe Som é passado por parâmetro a localização do arquivo de aúdio que será reproduzido durante a execução do programa. Este método também interage com o usuário imprimindo na tela, brevemente, as regras do jogo, como por exemplo as teclas que devem ser pressionadas. Além dessas funções, este método instancia a própria classe para poder utilizar os métodos definidos nela.
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner entrada =new Scanner(System.in);
        String arquivo="C:\\Users\\mirel\\3D Objects\\OneDrive\\Documentos\\NetBeansProjects\\SpaceInvaders\\src\\spaceinvaders\\som.wav";
        Som som = new Som();
        som.playSound(arquivo);
        System.out.println("\n \n     Bem-vindo, jogador! \n \n \n \n");
        
        
        try{
            System.out.println("\n \n  Se preferir ver o jogo em modo automático tecle 1. Se deseja jogar, tecle 0 ");
            automatico=entrada.nextInt();
        }
        catch(Exception e){ /*tratamento de exececao para entradas com tipos diferentes do tipo inteiro*/
            entrada.reset();           
        }
        
        System.out.println("  Use o seu teclado para combater seus inimigos \n");
        System.out.println("a: Move o canhão para a direita");
        System.out.println("d: Move o canhão para a esquerda");
        System.out.println("w: Libera munição contra os invasores   \n \n \n \n");
        System.out.println("  A Terra está dependendo de você. \n \n \n \n");
        System.out.println("Pressione uma tecla para começar...");
        
        Scanner entrada2 = new Scanner(System.in);
        char c; 
        c=entrada2.next().charAt(0);
        SpaceInvaders sp = new SpaceInvaders();
        sp.defineElementos();
        sp.iniciarJogo();
                
        
    }

}
