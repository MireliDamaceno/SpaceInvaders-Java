/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spaceinvaders;

/**
 *Classe que representa os tiros dos elementos do jogo: dos invasores ou do canhão
 * @author mireli  
 */
public class Tiro extends Elemento {
    
    /**
    *Atributo booleano que verifica se o tiro é de um invasor ou do jogador
    *
    */
    private boolean inimigo;
    public Tiro(){
        
    }
   
    /**
     *
     * @param inimigo
     * Construtor que recebe o parâmetro booleano que define se o tiro é de um inimigo ou se o tiro é do jogador.
     */
    public Tiro(boolean inimigo){ //verifica se o tiro eh do inimigo
        this();
        this.inimigo=inimigo;
    }
    
    /**
    *Este metodo é implementado de maneira diferente na classe Tiro para diferenciar o caractere que representa o tiro do jogador do caractere que representa o tiro dos inimigos.
    *
    */
    public char defineCaractere(){
        if(inimigo==false)
            this.caractere='^';
        else
            this.caractere='i';
        return caractere;
    }
    
}
