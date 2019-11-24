package sample;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import org.junit.Test;
import java.io.File;

import static org.junit.Assert.*;

public class GameLoopTest {
    static File splashFile = new File("src/media/splash.mp4");
    @Test
    public void restartIngamemusic() {
    }

    @Test
    public void restartGameovermusic() {
    }

    @Test
    public void playEatsound() {
    }

    @Test
    public void playDeathsound() {
    }

    @Test
    public void main() {
    }

    @Test
    public void start() {

    }

    @Test
    public void checkIfFileExist() {

        //check if file exist
        assertEquals(splashFile.exists(),true);

    }
}