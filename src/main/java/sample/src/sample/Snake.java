package sample.src.sample;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.util.LinkedList;

public class Snake {

	private static final long START_FRAMEDELAY = 250000000;
	private static final long MAX_FRAMEDELAY = 80000;
	private static final long DECREASE_DELAY = 6000000;  
    public long frameDelay = START_FRAMEDELAY; 
    private Rectangle head = new Rectangle(20, 20); 
    private LinkedList<Rectangle> snake = new LinkedList<Rectangle>();
    


    private static org.apache.log4j.Logger logSnake = org.apache.log4j.Logger.getLogger(Snake.class);

    Snake(Group group, Stage stage) {
        snake.add(head);
        snake.getFirst().relocate(stage.getWidth() / 2, stage.getHeight() / 2);
        group.getChildren().add(snake.getFirst());

    }

    long getframeDelay() {
        return frameDelay;
    }


    void respawn(Group group, GameObject food, Score score, Stage stage, Control control) {
        group.getChildren().clear();
        snake.clear();

        snake.add(head);
        snake.getFirst().relocate(stage.getWidth() / 2, stage.getHeight() / 2);
        group.getChildren().add(snake.getFirst());
        food.setFood(group, stage); 
        score.scoreRespawn(group); 
        logSnake.info("respawn");
        logSnake.debug("respawn");
        frameDelay = START_FRAMEDELAY; 
        logSnake.debug("back to value: "+frameDelay);
        control.stopMovement();

    }

    void snakeDead(Group group, Control control, Stage stage) {

        logSnake.info("snake dead!");
        logSnake.debug("snake dead!");

        group.getChildren().clear();
        snake.clear();
        snake.add(head);
        snake.getFirst().relocate(stage.getWidth() / 2, stage.getHeight() / 2);
        frameDelay = START_FRAMEDELAY; 
        control.stopMovement();

    }


    private void eat(Group group, Score score, GameObject food) {//Added Tail square with color of food
        snake.add(new Rectangle(20, 20));
        logSnake.debug("eat");
        snake.getLast().setFill(Color.color(food.getColor()[0], food.getColor()[1], food.getColor()[2])); //holt sich aus deathsoundMedia GameObject die Color von Food für sein Tail
        group.getChildren().add(snake.getLast()); 
        score.upScoreValue(); 
        if (frameDelay >= MAX_FRAMEDELAY) { 
            frameDelay -= DECREASE_DELAY;
            System.out.println(frameDelay);
        }

    }

    public void collision(GameObject food, Group group, Bounds foodBound, Score score, Control control, Stage stage, Gameboard gameboard) { //gameobject sind obstacles so wie Food, Boundarys für Collisions
        Bounds headBox = head.getBoundsInParent(); 


        if (headBox.intersects(foodBound)) {
        	eat(group, score, food);
            food.setFood(group, stage);
            GameLoop.playEatsound();
        }

        if (head.getLayoutX() <= 0 || head.getLayoutX() >= stage.getWidth() - 30 || // TODO: MAKE THIS CLEARER. Collision with the wall
                head.getLayoutY() <= 0 || head.getLayoutY() >= stage.getHeight() - 54) {
            snakeDead(group, control, stage);
            gameboard.setDeathTouchWall(score);
            GameLoop.playDeathsound();
            GameLoop.stopIngamemusic();
            GameLoop.restartGameovermusic();
        }


        for (int i = 1; i < this.snake.size(); i++) { //Collision with tail
            if (headBox.intersects(this.snake.get(i).getBoundsInParent())) {
                System.err.println("Dead");
                snakeDead(group, control, stage);
                gameboard.setDeathTouchTail(score);
                GameLoop.playDeathsound();
                GameLoop.stopIngamemusic();
                GameLoop.restartGameovermusic();
            }
        }
    }


    public void moveSnake(int dx, int dy, Stage stage) { 

        if (dx != 0 || dy != 0) { 
            LinkedList<Rectangle> snakehelp = new LinkedList<Rectangle>();

            for (int i = 0; i < snake.size(); i++) {

                snakehelp.add(new Rectangle());

                snakehelp.get(i).relocate(snake.get(i).getLayoutX(), snake.get(i).getLayoutY());
            }

            int x = (int) snake.getFirst().getLayoutX() + dx;
            int y = (int) snake.getFirst().getLayoutY() + dy;
            snake.getFirst().relocate(x, y);


            for (int i = 1; i < snake.size(); i++) {

                int helpX = (int) snakehelp.get(i - 1).getLayoutX();
                int helpY = (int) snakehelp.get(i - 1).getLayoutY();
                snake.get(i).relocate(helpX, helpY);
            }
        }
    }
}
