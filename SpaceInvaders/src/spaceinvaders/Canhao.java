/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package spaceinvaders;


/**
 *Classe que representa o jogador, ou seja: o canhão que defende a Terra dos invasores.  
 */
public class Canhao extends Elemento {
   
    /**
     *O construtor do Canhão, nesta primeira parte do projeto, é apenas o padrão. 
     */
    public Canhao(){
         
    }
   
    public char defineCaractere(){
        this.caractere='A';
        return caractere;
    }
             
   
     
   
}

