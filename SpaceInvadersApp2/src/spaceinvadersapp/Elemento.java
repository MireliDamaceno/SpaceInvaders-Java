/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvadersapp;

import spaceinvadersapp.entidades.Canhao;
import spaceinvadersapp.entidades.Invasor;
import spaceinvadersapp.entidades.Tiro;
import spaceinvadersapp.entidades.Barreira;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

/**
 * Classe genérica que possui métodos e atributos comuns aos elementos desse
 * jogo: Canhão, Invasores, Tiro e Barreira.
 *
 * @author Mireli Damaceno Barbosa
 * @since dezembro, 2020
 * @version 2.0
 */
public class Elemento {

    boolean ativo;
    boolean removido;

    //Propriedade dos elementos do jogo
    private Image imagem;
    private double largura;
    private double altura;
    private double px;
    private double py;
    private double velX;
    private double velY;

    /**
     *Método para mover o elemento para a esquerda
     * @param valor
     */
    public void moveEsquerda(double valor){
        this.velX=valor;
        this.velY=0;
    }
    
    /**
     *Método para mover o elemento para a direita
     * @param valor
     */
    public void moveDireita(double valor){
        this.velX=valor;
        this.velY=0;
    }

    /**
     *Método para obter a posição horizontal do elemento
     * @return 
     */
    public double getPx() {
        return px;
    }

    /**
     *Método para obter a posição vertical do elemento
     * @return 
     */
    public double getPy() {
        return py;
    }
    
   /**
     *Método para definir a posição horizontal do elemento
     * @param px
     */
    public void setPx(double px) {
        this.px = px;
    }

    /**
     *Método para definir a posição vertical do elemento
     * @param py
     */
    public void setPy(double py) {
        this.py = py;
    }

    /**
     *Método para obter a largura do elemento
     * @return 
     */
    public double getLargura() {
        return largura;
    }

    /**
     *Método para obter a altura do elemento
     * @return 
     */
    public double getAltura() {
        return altura;
    }

    /**
     *Método para obter a imagem que representa o elemento
     * @param imagem
     */
    public void setImage(Image imagem) {
        this.imagem = imagem;
        this.largura = imagem.getWidth();
        this.altura = imagem.getHeight();
    }

    /**
     *Método para definir a imagem que representa elemento
     * @param filename
     */
    public void defineImagem(String filename) { //define  a imagem do elemento
        Image img = new Image(getClass().getResource(filename).toExternalForm());
        Image toReturn = new Image(filename, img.getWidth() / 3, img.getHeight() / 3, true, false);
        setImage(toReturn);
    }
    
    /**
     *Método para obter a imagem do elemento
     * @return 
     */

    public Image getImagem() {
        return imagem;
    }
    
    /**
     *Método para obter a velocidade do elemento na horizontal
     * @return 
     */

    public double getVelX() {
        return velX;
    }
    
     /**
     *Método para obter a velocidade do elemento na vertical
     * @return 
     */

    public double getVelY() {
        return velY;
    }

    /**
     *Método para definir a velocidade do elemento na horizontal
     * @param velX
     */
    public void setVelX(double velX) {
        this.velX = velX;
    }
    
    
    /**
     *Método para definir a velocidade do elemento na horizontal
     * @param velY
     */
    public void setVelY(double velY) {
        this.velY = velY;
    }
    
    /**
     *Método que incrementa a posição horizontal do elemento
     * @param x
     */
    public void incrementaPx(double x){
       px=px+x; 
    }
    
    /**
     *Método para calcular a posição corrente do elemento de acordo com o produto entre sua velocidade e o tempo decorrido
     * @param time
     */
    public void update(double time) {
        px += velX * time;
        py += velY * time;
        
    }

    /**
     *Método para retornar os limites das dimensões de cada elemento
     * @return 
     */
    public Rectangle2D getBoundary() {
        return new Rectangle2D(px, py, largura, altura);
    }

    /**
     *Método para capturar a colisão entre um elemento e outro
     * @param s
     * @return 
     */
    public boolean intersects(Elemento s) {
        return s.getBoundary().intersects(this.getBoundary());
    }

}
