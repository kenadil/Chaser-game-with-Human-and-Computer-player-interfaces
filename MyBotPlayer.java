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

class MyBotPlayer implements BotPlayer{
	private Circle ball;
	private Map map;
	private Position position;
	private Food food;
	private int[][] cells;
	private Thread thread;
	public MyBotPlayer(Map map){
		this.map = map;
		cells = map.getMap();
		createBot();
		ball = new Circle(position.getX()*map.getUnit()+25, position.getY()*map.getUnit()+25, map.getUnit()/2);
		ball.setFill(BLUE);
		ball.setStroke(BLACK);
		this.map.getChildren().add(ball);
	}
	
	public Position getPosition(){
		return position;
	}
	public void moveRight(){
		try{
			Thread.sleep(100);
		}catch (Exception e){
			e.printStackTrace();
		}
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
		try{
			Thread.sleep(100);
		}catch (Exception e){
			e.printStackTrace();
		}
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
		try{
			Thread.sleep(50);
		}catch (Exception e){
			e.printStackTrace();
		}
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
		try{
			Thread.sleep(100);
		}catch (Exception e){
			e.printStackTrace();
		}
		position.setY(position.getY()+1);
		if (position.getY()>map.getSize()-1) position.setY(map.getSize()-1);
		if (map.getMap()[position.getY()][position.getX()] != 1)
		{
			ball.setCenterY(position.getY()*map.getUnit()+25);
		}else{
			position.setY(position.getY()-1);
		}
	}
	public void createBot(){
		int x;
		int y;
		Random gen = new Random();
		do{
			x = gen.nextInt(map.getSize());
			y = gen.nextInt(map.getSize());
		}while (this.map.getMap()[y][x]==1);
		this.position = new Position(x, y);
	}
	public void feed(Food food){
		this.food = food;
		new Thread(()->{
			while (true){
				find();
				/*int timer = 5;
				while (timer > 0){
					if (food.getPosition().equals(position)){
						break;
					}
					System.out.println(timer--);
					try{
						Thread.sleep(1000);
					}
					catch(Exception e){
						e.printStackTrace();
					}
				}*/
			}
		}).start();
		//eat();
	}
	public void eat(){
		thread = new Thread(() -> {
			while (true){
				Position mustFollow = food.getPosition();
				int x = position.getX();
				int y = position.getY();
				int fx = mustFollow.getX();
				int fy = mustFollow.getY();
				if (Math.abs(fx-x)>Math.abs(fy-y)){
					if (x<fx){
						while ((x != fx)){
							x++;
							moveRight();
						}
					}
					else{
						while ((x != fx)){
							x--;
							moveLeft();
						}
					}
					if (y<fy){
						while ((y != fy)){
							y++;
							moveDown();
						}
					}
					else{
						while ((y != fy)){
							y--;
							moveUp();
						}
					}
				}
				else{
					if (y<fy){
						while ((y != fy)){
							y++;
							moveDown();
						}
					}
					else{
						while ((y != fy)){
							y--;
							moveUp();
						}
					}
					if (x<fx){
						while ((x != fx)){
							x++;
							moveRight();
						}
					}
					else{
						while ((x != fx)){
							x--;
							moveLeft();
						}
					}
				}
				System.out.println(mustFollow.getX() + ", " + mustFollow.getY() + " : " + position.getX() + ", " + position.getY());
			}
		});thread.start();
	}
	
	public void find(){
		Lee lee = new Lee(map);
		ArrayList<Position> positions = lee.wavePath(position,food.getPosition());
		Collections.reverse(positions);
		//System.out.println(positions.toString());
		for (Position pos : positions){
			position = pos;
			try{
				Thread.sleep(50);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			ball.setCenterY(position.getY()*map.getUnit()+25);
			ball.setCenterX(position.getX()*map.getUnit()+25);
		}
			System.out.println("Yammy");
			System.out.println("Onto the next one...");
		
			
	}
	
}

class Lee{
	private int size;
	private int[][] cells;
	private boolean[][] cellVisited;
	private ArrayList<Position> posList = new ArrayList<Position>();
	private int obstacle = -1;
	private Map map;
	public Lee(Map map){
		this.map = map;
		size = map.getSize();
		cells = new int[size][size];
		for (int i = 0; i<size; i++){
			for (int j = 0; j<size; j++){
				cells[i][j] = map.getMap()[i][j];
			}
		}
		cellVisited = new boolean[size][size];
		for (int i = 0; i<size; i++){
			for (int j = 0; j<size; j++){
				if (cells[i][j] == 2){
					cells[i][j] = 0;
				}
				if (map.getMap()[i][j] == 1){
					cells[i][j] = obstacle;
					cellVisited[i][j] = true;
				}
			}
		}
	}
	
	public ArrayList<Position> wavePath(Position start, Position goal){
		if (posList.isEmpty()){
			posList.add(start);
			cellVisited[start.getY()][start.getX()] = true;
		}
		for (int i = 1; i<1000; i++){
			posList = findNeighbors(posList, i+1);
			if (cells[goal.getY()][goal.getX()] != 0){
				System.out.println("Searching paths - path exists!");
				break;
			}
			if (i == 1000-1){
				//System.out.println("Object is unnavailable");
			}
		}
		
		ArrayList<Position> pathList = backTrace(goal, start);
		posList.clear();
		cellVisited = new boolean[size][size];
		cells = map.getMap();
		return pathList;
	}
	
	protected ArrayList<Position> findNeighbors(ArrayList<Position> posList, int iteration){
		ArrayList<Position> neighbors = new ArrayList<Position>();
		Position pos;
		for (Position position : posList) {
			if (position.getY() + 1 < cells.length && !cellVisited[position.getY()+1][position.getX()]){
				pos = new Position(position.getX(), position.getY() + 1);
				neighbors.add(pos);
				cellVisited[pos.getY()][pos.getX()] = true;
				cells[pos.getY()][pos.getX()] = iteration;
			}
			if (position.getY() >= 1 && !cellVisited[position.getY()-1][position.getX()]){
				pos = new Position(position.getX(), position.getY() - 1);
				neighbors.add(pos);
				cellVisited[pos.getY()][pos.getX()] = true;
				cells[pos.getY()][pos.getX()] = iteration;
			}
			if (position.getX() + 1 < cells.length && !cellVisited[position.getY()][position.getX()+1]){
				pos = new Position(position.getX()+1, position.getY());
				neighbors.add(pos);
				cellVisited[pos.getY()][pos.getX()] = true;
				cells[pos.getY()][pos.getX()] = iteration;
			}
			if (position.getX() >= 1 && !cellVisited[position.getY()][position.getX()-1]){
				pos = new Position(position.getX()-1, position.getY());
				neighbors.add(pos);
				cellVisited[pos.getY()][pos.getX()] = true;
				cells[pos.getY()][pos.getX()] = iteration;
			}
		}
		return neighbors;
	}
	
	private ArrayList<Position> backTrace(Position goal, Position start){
		ArrayList<Position> pathList = new ArrayList<Position>();
		pathList.add(goal);
		Position current = null;
		
		while (!pathList.get(pathList.size()-1).equals(start)){
			current = pathList.get(pathList.size()-1);
			Position n = receive(current);
			pathList.add(n);
			n = current;
		}
		return pathList;
	}
	
	private Position receive(Position current){
		ArrayList<Position> possibleNeighbors = new ArrayList<Position>();
		Position pos;
		if (current.getY() + 1 < cells.length && cellVisited[current.getY()+1][current.getX()] && cells[current.getY()+1][current.getX()]!=obstacle){
			pos = new Position(current.getX(), current.getY()+1);
			pos.setValue(cells[pos.getY()][pos.getX()]);
			possibleNeighbors.add(pos);
		}
		if (current.getY() >= 1 && cellVisited[current.getY()-1][current.getX()] && cells[current.getY()-1][current.getX()]!=obstacle){
			pos = new Position(current.getX(), current.getY()-1);
			pos.setValue(cells[pos.getY()][pos.getX()]);
			possibleNeighbors.add(pos);
		}
		if (current.getX() + 1 < cells.length && cellVisited[current.getY()][current.getX()+1] && cells[current.getY()][current.getX()+1]!=obstacle){
			pos = new Position(current.getX() + 1, current.getY());
			pos.setValue(cells[pos.getY()][pos.getX()]);
			possibleNeighbors.add(pos);
		}
		if (current.getX() >= 1 && cellVisited[current.getY()][current.getX()-1] && cells[current.getY()][current.getX()-1]!=obstacle){
			pos = new Position(current.getX() - 1, current.getY());
			pos.setValue(cells[pos.getY()][pos.getX()]);
			possibleNeighbors.add(pos);
		}
		Collections.sort(possibleNeighbors, new Comparator<Position>() {
			@Override
			public int compare(Position first, Position second){
				return first.getValue() - second.getValue();
			}
		});
		
		try{
			Position n = possibleNeighbors.remove(0);
			return n;
		}
		catch(Exception ex){
			System.out.println("Object is unnavailable");
			System.exit(0);
			return null;
		}
	}
}
		
			
	
	
