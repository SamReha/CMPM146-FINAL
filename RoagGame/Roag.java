import java.util.ArrayList;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class Roag implements KeyListener{
	
	static char[][] world = new char[12][12];
	static int playerx, playery;
	static boolean placed = false;
	static boolean playing = true;

	public static void main(String[] args){
		loadWorld();
		setPlayerNearestOpen(6, 6);
		printWorld();

		world.addKeyListener(this);
	}

	public static void generateWorld(){
		for (int i = 0; i < 12; i++){
			for (int j = 0; j < 12; j++){
				if (i == 0 || i == 11 || j == 0 || j == 11){
					world[i][j] = '#';
				}
				else {
					if ((int)(Math.random() * 20) == 0){
						world[i][j] = 'T';
					}
					else {
						world[i][j] = 'w';
					}
				}
			}
		}
	}

	public static void printWorld(){
		for (int i = 0; i < 12; i++){
			for (int j = 0; j < 12; j++){
				if (i == playerx && j == playery){
					System.out.print('P');
				} else {
					System.out.print(world[i][j]);
				}
			}
			System.out.println();
		}
	}

	public static void setPlayerNearestOpen(int x, int y){
		if (world[x][y] != 'T' && (x != 0 || y != 0)){
			playerx = x;
			playery = y;
			placed = true;
		} else {
			if (!placed){
				if (x != 1)
					setPlayerNearestOpen(x - 1, y);
				else if (x != 11)
					setPlayerNearestOpen(x + 1, y);
				else if (y != 1)
					setPlayerNearestOpen(x, y - 1);
				else if (y != 11)
					setPlayerNearestOpen(x, y + 1);	
			}
			
		}
	}

	public void keyPressed(KeyEvent e){
		int id = e.getID();
		if (id == KeyEvent.VK_UP){
			movePlayer(0, -1);
		} else if (id == KeyEvent.VK_DOWN){
			movePlayer(0, 1);
		} else if (id == KeyEvent.VK_LEFT){
			movePlayer(-1, 0);
		} else if (id == KeyEvent.VK_RIGHT){
			movePlayer(1, 0);
		}
	}

	public void movePlayer(int x, int y){
		int tx = playerx + x;
		int ty = playery + y;
		if (tx != 0 && tx != 12 && ty != 0 && ty != 12){
			if (world[tx][ty] != 'T'){
				playerx = tx;
				playery = ty;
			}
		}

		printWorld();
	}

	public void keyReleased(KeyEvent e){

	}

	public void keyTyped(KeyEvent e){

	}
}