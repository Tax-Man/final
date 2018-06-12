package Final;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;
public class Button {

    public boolean mine;
    public int surrounding;
    public JButton button;
    public String text;
    private Graphic graphic;
    public int posX;
    public int posY;
    public boolean clicked = false;
    public boolean rClicked = false;
    public boolean marked = false;
    
    public Button(int x, int y, Graphic graphic) {
        this.graphic = graphic;
        button = new JButton("");
        button.setBackground(new Color(200, 200, 200));
        
        button.setBorder(new LineBorder(Color.WHITE));
        button.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                int mouseButton = e.getButton();
                click(mouseButton);
            }
            public void mousePressed(MouseEvent e)  {}
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e)  {}
            public void mouseExited(MouseEvent e)   {}
        });
        
    }

      public void debug(int inputX, int inputY) {
        for (int X = 0; X < inputX; X++) {
            for (int Y = 0; Y < inputY; Y++) {
                if (!mine) {
                    button.setText("" + surrounding);
                } else button.setText("M");
                
            }
        }
    }
    public void autoClick() {
        click(MouseEvent.BUTTON1);
    }
    
    private void click(int mouseButton) {  
        
        if (mouseButton == MouseEvent.BUTTON1) {
            button.setBackground(new Color(150, 150, 150));
            
            switch (surrounding) {
                case 1: button.setForeground(Color.BLUE);
                    break;
                case 2: button.setForeground(Color.GREEN);
                     break;
                case 3: button.setForeground(Color.RED);
                      break;
                case 4: button.setForeground(Color.YELLOW);
                       break;
                case 5: button.setForeground(Color.ORANGE);
                       break;
                case 6: button.setForeground(Color.MAGENTA);
                       break;
                case 7: button.setForeground(Color.CYAN);
                      break;
                case 8: button.setForeground(Color.BLACK);
            }
            
            // left click
            if (clicked || rClicked == true) {}
            
            else if (mine) {
                text = "M";
//                button.setIcon(new ImageIcon("/misctesting/MINE.png"));
                button.setText(text);
                explode();
            } else if (surrounding == 0 && clicked == false) {
                graphic.clearpos(posX, posY);
                clicked = true;
            } else {
                text = "" + surrounding;
                clicked = true;
                graphic.checkForWin();
            }

        }
        else if (mouseButton == MouseEvent.BUTTON3) {
            
            button.setBackground(new Color(175, 175, 175));
            // right click
            
            if (clicked == true) {}
            else if (rClicked == false) {
                text = "*";
                rClicked = true;
                marked = true;
            } else if (rClicked){
                button.setBackground(new Color(200, 200, 200));
                text = "";
                marked = false;
                rClicked = false;
            }
            graphic.checkForWin();
            
        }
        button.setText(text);
        
        
        
        
        
    }

    private void explode() {
        
        int input = JOptionPane.showConfirmDialog(null, "Play again?", "MeinKraft",
               JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE); 
        
        if (input == JOptionPane.YES_OPTION) {
            graphic.dispose();         
            graphic = new Graphic();
        }
        else {
            System.exit(0);
        }
        
    }

    /**% chance that the button is a mine
     * 
     * @param height
     * @param width
     */
    public void isMine(int height, int width) {

        double seed = Math.random();
        double L = (double) 0;
        double H = (double) ((width * height));
        double value = (H + 1) * seed;
        int number = (int)value;
        if (number <= (H * .1)) mine = true;
        else mine = false;
        
    }


}
