class Position{
	int x, y = 0;
	int value;
	Position(){
	}
	Position(int x, int y){
		this.x = x;
		this.y = y;
	}
	Position(int x, int y, int value){
		this.x = x;
		this.y = y;
		this.value = value;
	}
	public int getValue(){
		return value;
	}
	public void setValue(int value){
		this.value = value;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public void setX(int x){
		this.x = x;
	}
	public void setY(int y){
		this.y = y;
	}
	public boolean equals(Position pos){
		if (pos.getX() == x && pos.getY() == y){
			return true;
		}
		return false;
	}
	@Override
	public String toString(){
		return getX() + " " + getY();
	}
}