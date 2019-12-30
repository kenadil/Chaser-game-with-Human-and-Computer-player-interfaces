import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.event.EventHandler;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
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

public class Map extends Pane{
	private int unit = 50;
	private int size;
	private int[][] map;
	private Position start;
	private Position botStart;
	public Map(String map){
		loadFromFile(new File(map));
		System.out.println(this.map[0][4]);
		for (int i = 0; i<this.map.length; i++){
			for (int j = 0; j<this.map[i].length; j++){
				Rectangle square = new Rectangle((j*unit), (i*unit),unit, unit);
				square.setFill(WHITE);
				square.setStroke(BLACK);
				if (this.map[i][j] == 1){
					square.setFill(BLACK);
				}
				this.getChildren().add(square);
			}
		}
	}
	public void loadFromFile(File file){
		Scanner localScanner;
		try {
			localScanner = new Scanner(file);
			readFromFile(localScanner);
		}
		catch (FileNotFoundException localFileNotFoundException) {
			System.out.println("Such a map does not exist!");
		}
	}
	public void readFromFile(Scanner scanner){
		size = Integer.parseInt(scanner.nextLine());
		map = new int[size][size];
		int index = 0;
		while (scanner.hasNext()){
			String[] temp = scanner.nextLine().split(" ");
			for (int i = 0; i<size; i++){
				map[index][i] = Integer.parseInt(temp[i]);
				if (map[index][i]==2){
					start = new Position(i, index);
					System.out.println("Starting coordinates: " + start.getX() + ", " + start.getY());
				}
			} 
			index++;
		}
	}
		
	
	public int getUnit(){
		return unit;
	}
	public int getSize(){
		return size;
	}
	public int[][] getMap(){
		return map;
	}
	public String toString(){
		return Arrays.deepToString(map);
	}
	public Position getStartPosition(){
		return start;
	}
	public Position getBotPosition(){
		return botStart;
	}
}
					