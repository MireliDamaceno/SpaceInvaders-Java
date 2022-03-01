/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spaceinvadersapp.entidades;

import spaceinvadersapp.Elemento;
import javafx.scene.image.Image;

/**
 * Classe que representa os objetos invasores, sejam eles da matriz ou da nave
 * que trafega na horizontal.
 *
 * @author mireli
 */
public class Invasor extends Elemento {

    private Tipos tipo;
    private String filename;
    private double posX;
    private double posY;

    public static enum Tipos {
        V, //pequeno
        X, //medio
        W, //grande
        N; //nave
    }

    /**
     *Construtor padrão da classe invasor
     */
    public Invasor() {

    }
    
    /**
     *Construtor específico da classe Invasor
     * @param posX
     * @param posY
     * @param filename
     */
    public Invasor(int posX, int posY, String filename){
        this.filename=filename;
        this.posX=posX;
        this.posY=posY;
    }

    /**
     *Método que retorna o tipo do invasor: pequeno, médio ou grande.
     * @return
     */
    public Tipos getTipo() {
        return tipo;
    }

    /**
     *Método que define o tipo do invasor
     * @param tipo
     */
    public void setTipo(Tipos tipo) {
        this.tipo = tipo;
    }
    
    /**
     *Método que retorna a pontuação de acordo com o tipo do invasor
     * @return
     */
    public int getPontos() {
        if (tipo == Tipos.N) {
            return 100;
        } else if (tipo == Tipos.V) {
            return 10;
        } else if (tipo == Tipos.X) {
            return 20;
        } else {
            return 30;
        }
    }

    /**
     *Método que define o invasor: a imagem relacionada a ele e sua posição inicial
     */
    public void define(){
        defineImagem(filename);
        setPx(posX);
        setPy(posY);
    }
}
