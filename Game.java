import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.event.EventHandler;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.shape.Rectangle;
import static javafx.scene.paint.Color.*;
import javafx.stage.Stage;
import javafx.animation.*;
import javafx.scene.shape.*;
import javafx.util.Duration;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.net.*;
import java.io.*;
import java.util.*;

public class Game extends Application{
	private static Map map;
	private static Player player;
	private static Food food;
	private MediaPlayer media;
	public void start(Stage primaryStage){
		Thread thread = new Thread(() -> {
			Media moon = new Media(new File("moon.mp3").toURI().toString());
			media = new MediaPlayer(moon);
			media.setVolume(0.3);
			media.play();
		});thread.start();
		player = new MyPlayer(map);
		food = new Food(map, player);
		Scene scene = new Scene(map);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Project 3");
		primaryStage.show();
		scene.setOnKeyPressed(e -> {
			switch (e.getCode()) {
				case RIGHT:
					player.moveRight();
					break;
				case LEFT:
					player.moveLeft();
					break;
				case UP:
					player.moveUp();
					break;
				case DOWN:
					player.moveDown();
					break;
			}
		});
	}
	public static void main(String[] args){
		try{
			map = new Map(args[0]);
			launch(args);
		}
		catch (IndexOutOfBoundsException e){
			System.out.println("Such file doesn't exist!");
			System.out.print("Please, enter the map file: ");
			Scanner input = new Scanner(System.in);
			map = new Map(input.next());
		}
	}
}