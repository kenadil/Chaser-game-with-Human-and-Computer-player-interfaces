
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

class MyPlayer implements Player{
	private Circle ball;
	private Map map;
	private Position position;
	MyPlayer(Map map){
		this.map = map;
		this.position = map.getStartPosition();
		System.out.println("Starting coordinates: " + position.getX() + ", " + position.getY());
		ball = new Circle(position.getX()*map.getUnit()+25, position.getY()*map.getUnit()+25, map.getUnit()/2);
		ball.setStroke(BLACK);
		this.map.getChildren().add(ball);
		new Thread(()->{
			while(true){
				ball.setFill(RED);
				try{
					Thread.sleep(200);
				}catch(Exception ex){
					ex.printStackTrace();
				}
				ball.setFill(ORANGE);
				try{
					Thread.sleep(200);
				}catch(Exception ex){
					ex.printStackTrace();
				}
				ball.setFill(YELLOW);
				try{
					Thread.sleep(200);
				}catch(Exception ex){
					ex.printStackTrace();
				}
				ball.setFill(GREEN);
				try{
					Thread.sleep(200);
				}catch(Exception ex){
					ex.printStackTrace();
				}
				ball.setFill(LIGHTBLUE);
				try{
					Thread.sleep(200);
				}catch(Exception ex){
					ex.printStackTrace();
				}
				ball.setFill(DARKBLUE);
				try{
					Thread.sleep(200);
				}catch(Exception ex){
					ex.printStackTrace();
				}
				ball.setFill(PURPLE);
				try{
					Thread.sleep(200);
				}catch(Exception ex){
					ex.printStackTrace();
				}
			}
		}).start();
			
	}
	public void moveRight(){
		position.setX(position.getX()+1);
		if (position.getX()>map.getSize()-1) position.setX(map.getSize()-1);
		if (map.getMap()[position.getY()][position.getX()] != 1) 
		{
			ball.setCenterX(position.getX()*map.getUnit()+25);
		}
		else{
			position.setX(position.getX()-1);
		}
	}
	public void moveLeft(){
		position.setX(position.getX()-1);
		if (position.getX()<0) {position.setX(0);}
		if (map.getMap()[position.getY()][position.getX()] != 1)
		{
			ball.setCenterX(position.getX()*map.getUnit()+25);
		}
		else{
			position.setX(position.getX()+1);
		}
	}
	public void moveUp(){
		position.setY(position.getY()-1);
		if (position.getY()<0) position.setY(0);
		if (map.getMap()[position.getY()][position.getX()] != 1)
		{
			ball.setCenterY(position.getY()*map.getUnit()+25);
		}
		else{
			position.setY(position.getY()+1);
		}
	}
	public void moveDown(){
		position.setY(position.getY()+1);
		if (position.getY()>map.getSize()-1) position.setY(map.getSize()-1);
		if (map.getMap()[position.getY()][position.getX()] != 1)
		{
			ball.setCenterY(position.getY()*map.getUnit()+25);
		}else{
			position.setY(position.getY()-1);
		}
	}
	public Position getPosition(){
		return position;
	}
}