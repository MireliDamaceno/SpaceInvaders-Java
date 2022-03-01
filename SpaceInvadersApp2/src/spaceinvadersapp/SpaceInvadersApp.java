
package spaceinvadersapp;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Classe inicial do projeto, representa a tela inicial do menu de opções.
 *
 * @author mireli
 */
public class SpaceInvadersApp extends Application {

    public static boolean flag;
    private Text nome;

    private Parent createContent(Stage primaryStage) {
        Pane root = new Pane();
        ImageView imageView = new ImageView(new Image(getClass().getResource("background.png").toExternalForm()));
        imageView.setFitWidth(800);
        imageView.setFitHeight(800);
        
        nome = new Text("SPACE INVADERS");
        nome.setFill(Color.WHITE);
        nome.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC,  70));
        nome.setX(70);
        nome.setY(200);
        
        
        
    
   
   

    Rectangle mask = new Rectangle(600, 500);

    mask.setOpacity (

    0);
    mask.setMouseTransparent (
    true);

        MenuBox menuBox = new MenuBox(400, 100);

    menuBox.setTranslateX (

    100);
    menuBox.setTranslateY (
    350);

        MenuItem startButton = new MenuItem("JOGAR", 250);

    startButton.setOnAction ( 
        () -> {
            if (!flag) {
            flag = true;
            FadeTransition ft = new FadeTransition(Duration.seconds(0.8), mask);
            ft.setToValue(1);

            ft.setOnFinished(e -> {
                mask.setOpacity(0);
                Jogo game = new Jogo();
                Stage st = new Stage();
                try {
                    game.start(st);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            });
            ft.play();
        }
    }
    );

        MenuItem quitGame = new MenuItem("SAIR", 250);

    quitGame.setOnAction ( 
        () -> {
            System.exit(0);
    }

    );

    menuBox.addItem (startButton);

    menuBox.addItem (quitGame);

    root.getChildren ()
    .addAll(imageView, menuBox, mask, nome);
    return root ;

}

@Override
        public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent(primaryStage));
        scene.setFill(Color.BLACK);
        primaryStage.setTitle("Space Invaders");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();       
    }

    public static void main(String[] args) {
        launch(args);
    }
}
