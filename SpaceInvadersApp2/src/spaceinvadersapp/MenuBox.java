package spaceinvadersapp;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Classe que representa os menus que definem as ações que o jogador tomará no jogo: Se deseja jogar, sair ou jogar novamente, por exemplo.
 *
 * @author mireli
 */
public class MenuBox extends Pane {
    private VBox box;

    public MenuBox(int width, int height) {

        box = new VBox(5);
        box.setTranslateX(25);
        box.setTranslateY(25);

        getChildren().add(box);
    }

    public void addItems(MenuItem... items) {
        for (MenuItem item : items)
            addItem(item);
    }

    public void addItem(MenuItem item) {
        box.getChildren().add(item);
    }
}
