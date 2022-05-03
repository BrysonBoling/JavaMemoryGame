import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SetBoard extends JPanel implements ActionListener, MouseListener {

    //delay between each tick in ms
    private final int DELAY = 1;

    public static final int TILE_SIZE = 100;
    public static final int ROWS = 6, COLUMNS = 6;
    //count cards flipped per attempted match
    public static int flips = 0;    
    
    // suppress serialization warning
    private static final long serialVersionUID = 490905409104883233L;

    private Timer timer;

    //private Player player;

    public SetBoard() {
        setPreferredSize(new Dimension(TILE_SIZE * COLUMNS, TILE_SIZE * ROWS));

        setBackground(new Color(232, 232, 232));

        //ini game state;
        //player = new Player();

        timer = new Timer(DELAY, this);
        timer.start();

        //Card.setCards();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //may not need the below statement
        //player.tick();

        //redraws the board state
        if (Card.counter == 2){
            Card.wait(2500);
            Card.checkFlips();
        }
        
        repaint();
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawBackground(g);

        //drawing of each card
        for(int i = 0; i < COLUMNS; i++){
			for(int j = 0; j < ROWS; j++){
                //System.out.println("Value is " + Card.getValue(i, j));
				if (Card.getFace(i, j) == 0)
					drawState(g, "", i, j);
				else
					drawState(g, "" + Card.getValue(i, j), Card.getX(i, j), Card.getY(i, j));

			};
		};

        Toolkit.getDefaultToolkit().sync();
    }

    /*@Override
    public void keyTyped(KeyEvent e) {
        //not used, but is necessary to include for interface
    }*/

    //@Override
    public void mousePressed(MouseEvent e) {
        //same as keyTyped
    }

    //@Override
    public void mouseReleased(MouseEvent e) {
        //same as above
    }

    public void mouseEntered(MouseEvent e) {

    }
    public void mouseExited(MouseEvent e) {

    }

    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        //System.out.println(x + " " + y);
        for (int i = 0; i < COLUMNS; i ++) {
            if (((i + 1) * 100) - 97 < x && x < ((i + 1) * 100) + 3){
                x = i;
            }
        }
        for (int j = 0; j < ROWS; j++) {
            if (((j + 1) * 100) - 97 < y && y < ((j + 1) * 100) + 3){
                y = j;
            }
        }


        if (Card.validFlip() == 1) {
            Card.flipCard(x, y);
        }
        //Card.flipCard(x, y);
        repaint();
        
        //repaint();

    }


    private void drawBackground(Graphics g) {
        g.setColor(new Color(214, 214, 214));

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                // only color every other tile
                if ((row + col) % 2 == 1) {
                    // draw a square tile at the current row/column position
                    g.fillRect(
                        col * TILE_SIZE,
                        row * TILE_SIZE,
                        TILE_SIZE,
                        TILE_SIZE
                    );
                }
            }
        }
    }

    private void drawState(Graphics g, String text, int xVal, int yVal) {
        Graphics2D g2d = (Graphics2D) g;

        //style settings
        g2d.setRenderingHint(
            RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(
            RenderingHints.KEY_RENDERING,
            RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(
            RenderingHints.KEY_FRACTIONALMETRICS,
            RenderingHints.VALUE_FRACTIONALMETRICS_ON);

        g2d.setColor(new Color(30, 201, 139));
        g2d.setFont(new Font("Lato", Font.BOLD, 25));

        //draw individual card

        g2d.drawString(text, xVal, yVal);
    }

    
}