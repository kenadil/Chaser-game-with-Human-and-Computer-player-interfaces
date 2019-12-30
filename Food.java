import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.control.*;
import javafx.scene.*;
import javafx.application.*;
import java.util.*;
import javafx.scene.paint.*;

public class Food
{
    private Map map;
    private Pane foodPane;
    private Player player;
    private Circle circle;
    private Position foodPosition;
    private Label seconds;
    private final int timer = 5;
    private int numOfCircles;
    private int time;
    private int points;
    private int size;
    private int[][] cells;
    
    public Food(final Map map, final Player player) {
        this.numOfCircles = 10;
        this.map = map;
        this.foodPane = new Pane();
        this.map.getChildren().add(this.foodPane);
        this.player = player;
        this.size = this.map.getSize();
        this.cells = this.map.getMap();
        new Thread(() -> {
            while (this.numOfCircles > 0) {
                this.createFood();
                Platform.runLater(() -> this.foodPane.getChildren().addAll(this.circle, this.seconds ));
                this.time = 5;
                while (this.time > 0) {
                    Platform.runLater(() -> this.seconds.setText("" + this.time));
                    if (this.player.getPosition().equals(this.foodPosition)) {
                        this.points += this.time;
                        break;
                    }
                    else {
                        try {
                            Thread.sleep(1000L);
                        }
                        catch (InterruptedException ex) {}
                        --this.time;
                    }
                }
                try {
                    Thread.sleep(10L);
                }
                catch (InterruptedException ex2) {}
                Platform.runLater(() -> this.foodPane.getChildren().clear());
                --this.numOfCircles;
            }
            this.foodPosition = null;
            System.out.println(this.getPoints());
        }).start();
    }
    
    public int getPoints() {
        return this.points;
    }
    
    private void createFood() {
        final Random random = new Random();
        final double n = this.map.getUnit();
        int nextInt;
        int nextInt2;
        do {
            nextInt = random.nextInt(this.size);
            nextInt2 = random.nextInt(this.size);
        } while (this.player.getPosition().equals(new Position(nextInt, nextInt2)) || this.map.getMap()[nextInt][nextInt2] == 1);
        (this.circle = new Circle(nextInt2 * n + n / 2.0, nextInt * n + n / 2.0, n / 4.0)).setFill((Paint)Color.GREEN);
        this.foodPosition = new Position(nextInt2, nextInt);
        (this.seconds = new Label("5")).setTranslateX(nextInt2 * n);
        this.seconds.setTranslateY(nextInt * n);
    }
    
    public Position getPosition() {
        return this.foodPosition;
    }
}