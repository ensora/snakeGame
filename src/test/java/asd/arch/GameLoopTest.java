package asd.arch;

import javafx.scene.image.Image;
import javafx.scene.layout.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameLoopTest {

    @Test
    void restartIngamemusic() {
    }

    @Test
    void stopIngamemusic() {
    }

    @Test
    void restartGameovermusic() {
    }

    @Test
    void stopGameovermusic() {
    }

    @Test
    void playEatsound() {
    }

    @Test
    void playDeathsound() {
    }

    @Test
    void main() {
    }

    @Test
    void start() {
    }

    @Test
    void setBackground(){
        try {
            Image imgSource = new Image("./media/image/grassTile.png");
            BackgroundImage backgroundImage = new BackgroundImage(imgSource, BackgroundRepeat.REPEAT.REPEAT, BackgroundRepeat.REPEAT.REPEAT,
                    BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
            Background backgroundView = new Background(backgroundImage);
            //backgroundPane.setBackground(backgroundView);

            assertTrue(true);
        }
        catch (Exception ex){
            assertTrue(false);
        }
    }
}