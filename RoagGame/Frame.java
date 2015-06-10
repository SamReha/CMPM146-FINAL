import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Frame extends JFrame {

	public static int width = 800;
	public static int height = 800;
	
	public static void main(String[] args){
		JFrame frame = new JFrame("Tears of the Mantis: Roag Colon:");
		frame.add(new Dungeon());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(width,height);
		frame.setVisible(true);
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
}
