/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spaceinvaders;

/**
 *Classe genérica que possui métodos e atributos comuns aos elementos desse jogo: Canhão, Invasores, Tiro e Barreira.
 * @author Mireli Damaceno Barbosa
 * @since setembro, 2020
 * @version 1.0
 */
public class Elemento {
    
    /**
    *Atributo booleano que verifica se o elemento está ativo ou em algum momento foi desativado.
    *
    */
    private boolean ativo;
    /**
    *Atributo que caracteriza a posicão no eixo horizontal do elemento.
    *
    */
    private int pX;
    /**
    *Atributo que caracteriza a posicão no eixo vertical do elemento.
    *
    */
    
    private int pY;
    
    /**
    *Atributo que representa o caractere escolhido para o elemento.
    *
    */
    protected char caractere;
    /**
     *Construtor padrão da classe Elemento.
     */
    public Elemento(){
        
    }
    /**
     *Construtor da classe Elemento que inicializa os atributos da classe com os valores passados por parâmetro.
     */
    public Elemento(int pX, int pY, char caractere){
        this.pX=pX;  //Posicao do elemento no eixo x da tela do jogo
        this.pY=pY; //Posicao do elemento no eixo y da tela do jogo
        this.caractere=caractere;       
    }
    
    /**
     *
     * @param x
     * Método que modifica a posição do elemento na horizontal.
     */
    public void incrementaPx(int x){
       pX=pX+x; 
    }
    
    /**
     *
     * @param y
     * Método que modifica a posição do elemento na vertical.
     */
    public void incrementaPy(int y){
        pY=pY+y;
    }
    
    /**
     *
     * Método que verifica se o elemento está ativo no jogo e pronto para ser utilizado.
     */
    public boolean isAtivo(){
        return ativo;
    }
    
    /**
     *
     * @param ativo
     * Método que define a situação do elemento no jogo.
     */
    public void setAtivo(boolean ativo){
        this.ativo=ativo;
    }
    
    /**
     *
     * @param pX
     *Método que define a posição do elemento na horizontal.
     */
    public void setpX(int pX) {
        this.pX = pX;
    }
    
    /**
     *
     * @param pY
     *Método que define a posição do elemento na vertical.
     */
    public void setpY(int pY) {
        this.pY = pY;
    }
    
    
    /**
     *
     * Método que obtém a posição do elemento na horizontal.
     * @return int
     */
    public int getpX() {
        return pX;
    }

    /**
     *
     * Método que obtém a posição do elemento na vertical.
     * @return int
     */
    public int getpY() {
        return pY;
    }
    
    
    /**
     *
     * Método que obtém o caractere que representa o elemento.
     * @return char
     */
    public char getCaractere() {
        return caractere;
    }
    
    /**
     *
     * @param caractere
     *Método que define o caractere que representa o elemento.
     */
    public void setCaractere(char caractere) {
        this.caractere = caractere;
    }
 
    /**
     *Método que retorna o caractere escolhido para representar cada elemento do jogo.
     * @return caractere
     */
    public char defineCaractere(){
        return caractere;
     }
    
}
