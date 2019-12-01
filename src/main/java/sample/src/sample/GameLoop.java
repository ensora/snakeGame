package sample.src.sample;


import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.logging.Logger;

public class GameLoop extends Application {

    private static File splashFile = new File("src/media/splash.mp4");
    private static Media splashMedia = new Media(splashFile.toURI().toString());
    private static MediaPlayer splashPlayer = new MediaPlayer(splashMedia);
    private static MediaView splashView = new MediaView(splashPlayer);
    private static File ingamemusicFile = new File("src/media/sound/music/ingame2.mp3");
    private static Media ingamemusicMedia = new Media(ingamemusicFile.toURI().toString());
    private static MediaPlayer ingamemusicPlayer = new MediaPlayer(ingamemusicMedia);
    private static File gameovermusicFile = new File("src/media/sound/music/gameover1.mp3");
    private static Media gameovermusicMedia = new Media(gameovermusicFile.toURI().toString());
    private static MediaPlayer gameovermusicPlayer = new MediaPlayer(gameovermusicMedia);
    private static File eatsoundFile = new File("src/media/sound/eat2.mp3");
    private static Media eatsoundMedia = new Media(eatsoundFile.toURI().toString());
    private static MediaPlayer eatsoundPlayer = new MediaPlayer(eatsoundMedia);
    private static File deathsoundFile = new File("src/media/sound/death1.mp3");
    private static Media deathsoundMedia = new Media(deathsoundFile.toURI().toString());
    private static MediaPlayer deathsoundPlayer = new MediaPlayer(deathsoundMedia);
    private Group root = new Group();
    private Pane backgroundPane = new Pane(); //TODO NEU für Background
    private Group splashscreen = new Group();
    //TODO NEU - Background stuff
    private long lastUpdate = 0; //für Geschwindigkeitssteuerung

    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(GameLoop.class);

    public static void restartIngamemusic() { //Startet Ingame Musik von vorne
        ingamemusicPlayer.seek(Duration.ZERO);
        ingamemusicPlayer.play();
        logger.info("restart ingame");
    }

    public static void stopIngamemusic() {
        ingamemusicPlayer.stop();
        logger.info("stop ingame");
    }

    public static void restartGameovermusic() {
        gameovermusicPlayer.seek(Duration.ZERO);
        gameovermusicPlayer.play();
        logger.info("Game over");
    }

    public static void stopGameovermusic() {
        gameovermusicPlayer.stop();
        logger.info("stop game");
    }

    public static void playEatsound() {
        eatsoundPlayer.seek(Duration.ZERO);
        eatsoundPlayer.play();
        logger.info("collision - play sound");
    }

    public static void playDeathsound() {
        deathsoundPlayer.seek(Duration.ZERO);
        deathsoundPlayer.play();
    }
    //TODO END Background

    public static void main(String[] args) {
        launch(args);
    }

    public void start(final Stage primaryStage) throws Exception {
        final AnimationTimer timer;

        //primaryStage.setWidth(1500);
        //primaryStage.setHeight(700);

        //primaryStage.setMinHeight(50);
        //primaryStage.setMinWidth(50);

        //primaryStage.setMinWidth(50);
 //       logger.debug("This is debug message");

        logger.debug("Start of setup");
        logger.info("Start of setup");
        //System.out.println("Logic executed successfully....");


        //PM: set window according to the monitor size
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        logger.info("visual bounds: "+bounds);
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        logger.info("primary Stage width: "+bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());
        logger.info("primary Stage heigth: "+bounds.getHeight());

        //TODO NEU - Background stuff
        //imgSource = new Image("../media/image/grassTile.png");
        //backgroundImage = new BackgroundImage(imgSource, BackgroundRepeat.REPEAT.REPEAT, BackgroundRepeat.REPEAT.REPEAT,
       //         BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        //backgroundView = new Background(backgroundImage);
        //backgroundPane.setBackground(backgroundView);
        //TODO END Background

        final int offset = 21; //TODO Variable Namen anpassen
        final Gameboard gameboard = new Gameboard(); // TODO NEW
        final Control control = new Control();
        final Snake snake = new Snake(root, primaryStage); //erstellt neues Snake Listen Objekt und getChilded es
        final GameObject food = new GameObject();
        final Score score = new Score(root);
        food.setFood(root, primaryStage);//setzt ein neues Food random ab
        final Scene scene = new Scene(backgroundPane, primaryStage.getWidth(), primaryStage.getHeight(), Color.DARKGREEN);
        backgroundPane.getChildren().add(root); //TODO NEU Background - root (Group) zu backgroundPane als Child added

        Rectangle blackrect = new Rectangle();  //Schwarzer Block der für eine Szenentransition missbraucht wird
        blackrect.setFill(Color.BLACK);
        blackrect.setHeight(primaryStage.getHeight());
        blackrect.setWidth(primaryStage.getWidth());
        final FadeTransition fadeblacktotransparent = new FadeTransition(Duration.millis(700), blackrect);
        fadeblacktotransparent.setFromValue(1.0);
        fadeblacktotransparent.setToValue(0.0);
        root.getChildren().add(blackrect);

        Scene intro = new Scene(splashscreen, primaryStage.getWidth(), primaryStage.getHeight());
        splashscreen.getChildren().add(splashView);
        splashView.setFitHeight(500);
        splashView.setFitWidth(1000);
        intro.setFill(Color.BLACK);
        splashView.setX(400);
        splashView.setY(100);
        primaryStage.setScene(intro);
        primaryStage.setTitle("Rainbow Snake");
        
        gameboard.setStartInfo(root, primaryStage); //PM: Start Info

        primaryStage.show();
        logger.info("show primary Stage");
        splashPlayer.play();
        logger.info("splash player play");

        ingamemusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        /*inp.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                inp.seek(Duration.ZERO);
            }
        });
        */
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {//Keyeventhandler fragt ab obs ein Keyevent gibt
            @Override
            public void handle(KeyEvent keyEvent) {
                control.keyHandler(keyEvent, snake, root, food, score, primaryStage);//control nimmt Keyevent und schaut speziell nach WASD

            }
        });

        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                if (now - lastUpdate >= snake.getframeDelay()) {

                    int dx = 0, dy = 0;

                    snake.collision(food, root, food.getBound(), score, control, primaryStage, gameboard);

                    if (control.getgoUp()) dy += -offset; //offset="speed"
                    else if (control.getgoDown()) dy += offset;
                    else if (control.getgoRight()) dx += offset;
                    else if (control.getgoLeft()) dx += -offset;
                    snake.moveSnake(dx, dy, primaryStage);

                    lastUpdate = now;

                }
            }
        };
        splashPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                primaryStage.setScene(scene);
                fadeblacktotransparent.play();
                logger.info("start animation timer after intro video");
                timer.start(); //Animationtimer startet nun erst nach dem Fade out des Hundevideos
                restartIngamemusic();
            }
        });

    }
}
