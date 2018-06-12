package Final;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;
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

    /**
     * Button constructor
     *
     * @param x The Button's x pos in the array
     * @param y The Button's y pos in the array
     * @param graphic the graphic the Button is in
     */
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

            public void mousePressed(MouseEvent e) {
            }

            public void mouseReleased(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseExited(MouseEvent e) {
            }
        });

    }

    /**
     * displays the numbers and mines without clicking
     *
     * @param inputX The maximum X value
     * @param inputY The maximum Y value
     */
    public void debug(int inputX, int inputY) {
        for (int X = 0; X < inputX; X++) {
            for (int Y = 0; Y < inputY; Y++) {
                if (!mine) {
                    button.setText("" + surrounding);
                } else {
                    button.setText("M");
                }

            }
        }
    }

    /**
     * simulates left clicking
     */
    public void autoClick() {
        click(MouseEvent.BUTTON1);
    }

    /**
     * act when clicked
     *
     * @param mouseButton which button is clicked
     */
    private void click(int mouseButton) {

        if (mouseButton == MouseEvent.BUTTON1) {
            button.setBackground(new Color(150, 150, 150));

            switch (surrounding) {
                case 1:
                    button.setForeground(Color.BLUE);
                    break;
                case 2:
                    button.setForeground(Color.GREEN);
                    break;
                case 3:
                    button.setForeground(Color.RED);
                    break;
                case 4:
                    button.setForeground(Color.YELLOW);
                    break;
                case 5:
                    button.setForeground(Color.ORANGE);
                    break;
                case 6:
                    button.setForeground(Color.MAGENTA);
                    break;
                case 7:
                    button.setForeground(Color.CYAN);
                    break;
                case 8:
                    button.setForeground(Color.BLACK);
            }

            // left click
            if (clicked || rClicked == true) {
            } else if (mine) {
                explode();
            } else if (surrounding == 0 && clicked == false) {
                graphic.clearpos(posX, posY);
                clicked = true;
            } else {
                text = "" + surrounding;
                clicked = true;
                graphic.checkForWin();
            }

        } else if (mouseButton == MouseEvent.BUTTON3) {

            // right click
            if (clicked == true) {
            } else if (rClicked == false) {
                if (mine) {
                    marked = true;
                }
                text = "*";
                rClicked = true;
                button.setBackground(new Color(175, 175, 175));
            } else if (rClicked) {
                if (mine) {
                    marked = false;
                }
                button.setBackground(new Color(200, 200, 200));
                text = "";
                rClicked = false;
            }
            graphic.checkForWin();

        }
        button.setText(text);

    }

    /**
     * display the remaining mines, flags, and prompts a new game
     */
    private void explode() {
        for (int i = 0; i < graphic.inputX; i++) {
            for (int j = 0; j < graphic.inputY; j++) {
                if (graphic.matrix[i][j].mine && !graphic.matrix[i][j].marked) {
                    graphic.matrix[i][j].button.setText("");
                    graphic.matrix[i][j].button.setBackground(Color.RED);
                    graphic.matrix[i][j].button.setIcon(new ImageIcon(getClass().getResource("/Final/MINE.png")));
                    graphic.matrix[i][j].button.setBounds(
                            graphic.matrix[i][j].button.getX(), graphic.matrix[i][j].button.getY(),
                            graphic.BUTTON_SIZE, graphic.BUTTON_SIZE);
                    resizeImage(graphic.matrix[i][j].button);
                } else if (graphic.matrix[i][j].marked) {
                    graphic.matrix[i][j].button.setBackground(Color.RED);
                }
            }
        }

        int input = JOptionPane.showConfirmDialog(null, "Play again?", "MeinKraft",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (input == JOptionPane.YES_OPTION) {
            graphic.dispose();
            graphic = new Graphic();
        } else {
            System.exit(0);
        }

    }

    /**
     * % chance that the button is a mine
     *
     * @param height
     * @param width
     */
    public void isMine(int height, int width) {

        double seed = Math.random();
        double L = (double) 0;
        double H = (double) ((width * height));
        double value = (H + 1) * seed;
        int number = (int) value;
        if (number <= (H * .1)) {
            mine = true;
        } else {
            mine = false;
        }

    }

    /**
     * Resizes the image inside the label to match the size of the label
     *
     * @param imageButton the label to resize
     */
    public void resizeImage(JButton imageButton) {
        int w = imageButton.getWidth();
        int h = imageButton.getHeight();
        ImageIcon originalIcon = (ImageIcon) imageButton.getIcon();
        Image originalImage = originalIcon.getImage();
        Image newImage = originalImage.getScaledInstance(w, h,
                Image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(newImage);
        imageButton.setIcon(newIcon);
    }

}
