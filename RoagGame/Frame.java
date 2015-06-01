import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Frame extends JFrame {

	public static int width = 800;
	public static int height = 800;
	
	public static void main(String[] args){
		String file = args[0];
		JFrame frame = new JFrame("Roag");
		frame.add(new Dungeon(file));
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
