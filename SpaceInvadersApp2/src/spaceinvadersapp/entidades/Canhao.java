/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvadersapp.entidades;
import spaceinvadersapp.Elemento;

/**
 * Classe que representa o jogador, ou seja: o canhão que defende a Terra dos
 * invasores.
 */
public class Canhao extends Elemento {

    
    private int vidas;

    /**
     * O construtor do Canhão é apenas o padrão.
     */
    public Canhao() {
       
    }
    
    /**
     *Método para definir a imagem que representa o canhão
     */
    public void define(){
        setPx(50);
        setPy(560); //posicao vertical do canhao
        defineImagem("canhao.png"); //Define a imagem que representara o canhao pela imagem passada por parametri
    }
  
    

  
    
}
