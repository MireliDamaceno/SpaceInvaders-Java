/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spaceinvaders;

/**
 *Classe que representa os objetos invasores, sejam eles da matriz ou da nave que trafega na horizontal.
 * @author mireli
 */
public class Invasor extends Elemento{

    /**
     *método que retorna uma estrutura enum para classificar os tipos de invasores do jogo.
     */
    public enum Tipos{
        V, X, W, N;//os tres tipos de invasores e a nave;
    }
    public Invasor(){
        
    }
    /**
    *Atributo do tipo do enum criado, que recebe qual o tipo do invasor.
    *
    */
    private Tipos tipo;
    
    /**
     *
     * @param t
     * Construtor da classe Invasor que inicializa o atributo da classe com o valor passado por parâmetro.
     */
    public Invasor(Tipos t) {  //construtor da classe Invasor
        this.tipo = t;            
    }

    public char defineCaractere(){
        if(tipo==Invasor.Tipos.V)
            caractere='V';
            
        else if(tipo==Invasor.Tipos.X)
            caractere='X';
        

        else if(tipo==Invasor.Tipos.W)
            caractere='W';
        
        else if(tipo==Invasor.Tipos.N)
            caractere='N';
        
        return caractere;
    }
    
    /**
     *Método para determinar a pontuação obtida pelo jogador conforme o invasor eliminado.<br>Se o invasor for comum (da matriz) são 100 pontos. Mas se o invasor for a nave, o jogador ganha 200 pontos.
     * @return pontuação
     */
    public int getPontos(){
        if(tipo==Tipos.N)
            return 200;
        else
            return 100;
    }
}
