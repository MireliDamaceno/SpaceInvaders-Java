    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spaceinvadersapp.entidades;
import spaceinvadersapp.Elemento;
import javafx.scene.paint.Color;

/**
 *
 * Classe que representa as barreiras que protegem o jogador dos invasores.
 */
public class Barreira extends Elemento {

    private int[][] barreira;
    private int posicaoX;
    private int posicaoY;
    
    /**
     *  Construtor da classe Barreira
     */
    public Barreira() {

        barreira = new int[][]{  //vetor pixels da barreira
            {0, 0, 1, 1, 1, 1, 1, 1, 0, 0},
            {0, 1, 1, 1, 1, 1, 1, 1, 1, 0},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 0, 0, 0, 0, 1, 1, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 1, 1}
        };
    }

    /**
     *Método para retornar a matriz que define uma barreira
     * @return
     */
    public int[][] getBarreira() {
        return barreira;
    }
    
    /**
     *Método para deletar os bloquinhos responsáveis pelos pixels que definem a barreira.
     * @param row
     * @param col
     */
    public void destroiBlocos(int row, int col) {
        barreira[row][col] = 0;
        if (row < barreira.length - 1) {
            barreira[row + 1][col] = 0;
            if (col < barreira[0].length - 1) {
                barreira[row][col + 1] = 0;
            }
            if (col > 0) {
                barreira[row][col - 1] = 0;
            }
        }
    }

    
    
    

}
