/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders;

/**
 *Classe que determina a colisão entre os elementos. Ela é uma composição da classe Elemento, já que sem objetos desta última classe, a colisão não existiria. Outro indício da composição é que a classe Colisão (parte) pertence unicamente à classe Elemento (todo).
 */
public class Colisao {
    
    /**
     *Método que verifica se houve colisão entre elementos, principalmente se um deles for do tipo Tiro.
     * @param a
     * @param b
     * @return
     */
    public static boolean houveColisao(Elemento a, Elemento b){
        if(a.isAtivo()==false || b.isAtivo()==false)
            return false;
        if(a.getpX()==b.getpX() && a.getpY() == b.getpY()) //Se as posicoes tanto em x, quanto em y, forem iguais, ha colisao
            return true;
        return false;
    }
    
    /**
     *Método utilizado para verificar se a nave possui a mesma posição do jogador no eixo x o que permite liberar tiros da nave.
     * @param a
     * @param b
     * @return
     */
    public static boolean coincideEixoX(Elemento a, Elemento b){
        if(a.isAtivo()==false || b.isAtivo()==false)
            return false;
        if(a.getpX()==b.getpX())
            return true;
        return false;
    }
}


