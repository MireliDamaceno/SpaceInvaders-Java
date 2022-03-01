/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvadersapp;


import javafx.scene.media.AudioClip;
import javax.sound.sampled.AudioSystem;

/**
 *
 * Classe responsável pelas definições de áudio do jogo.
 */



public class Som {

   

    /**
     * @param localizacao the name of the file that is going to be played
     * Método que possui funções das bibliotecas de áudio importadas. Dentre essas funções estão abri o arquivo de som, executá-lo e deixá-lo em loop.
     */
    private AudioClip som;
    
    public Som(String filename){
        som=new AudioClip(getClass().getResource(filename).toExternalForm());
    } 
    public void play(){
        som.play();
    }
    public boolean continuar(){
        return som.isPlaying();
    }
    
}
    
