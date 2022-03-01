/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvaders;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

/**
 *
 * Classe responsável pelas definições de áudio do jogo.
 */



public class Som {

   

    /**
     * @param localizacao the name of the file that is going to be played
     * Método que possui funções das bibliotecas de áudio importadas. Dentre essas funções estão abri o arquivo de som, executá-lo e deixá-lo em loop.
     */
    public void playSound(String localizacao){
        
       try{
           File musica = new File(localizacao);
           
           if(musica.exists()){
               AudioInputStream audio = AudioSystem.getAudioInputStream(musica);
               Clip clip = AudioSystem.getClip();
               clip.open(audio);
               clip.start();
               clip.loop(Clip.LOOP_CONTINUOUSLY);
               
              
           }
           else{
               System.out.println("Não pode achar o arquivo!");               
           }
               
           
       }
       catch(Exception e){
           e.printStackTrace();
       }
               
        
    } 
}
    
