package Final;

import java.awt.Container;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Graphic extends JFrame {

    public Button[][] matrix;
    public int inputX;
    public int inputY;
    public int BUTTON_SIZE;

    /**
     * Creates the Frame from different parts
     */
    public Graphic() {

        getField();

        generate();

        scan();

        setFrame();

        Container container = setCont();

        addControls(container);

        //display the numbers / if its a mine w/o clicking
//        debug(inputX, inputY);

        this.setVisible(true);

    }

    /**
     * Adds a new Button to the 2d array and tells it to determine if it's a
     * mine or not and its position
     */
    private void generate() {
        matrix = new Button[inputX][inputY];
        for (int X = 0; X < inputX; X++) {
            for (int Y = 0; Y < inputY; Y++) {
                matrix[X][Y] = new Button(X, Y, this);
                matrix[X][Y].isMine(inputX, inputY);
                matrix[X][Y].posY = Y;
                matrix[X][Y].posX = X;
            }
        }
    }

    /**
     * checks if any of the surrounding 8 buttons are mines and adds the number
     * to the "surrounding" variable
     *
     */
    public void scan() {
        //check surrounding in array for mines

        //Capital is value of current, lower is for the surrounding
        for (int X = 0; X < inputX; X++) {
            for (int Y = 0; Y < inputY; Y++) {
                if (!matrix[X][Y].mine) {
                    checkpos(X, Y);
                }
            }
        }
    }

    /**
     * sets look and feel of container
     */
    private void setFrame() {

        //so the red x closes the program
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);

        String filename = "/Final/MINE.png";
        ImageIcon icon = new ImageIcon(getClass().getResource(filename));
        Image image = icon.getImage();

        this.setIconImage(image);

        this.setTitle("MeinKraft");
        //default is invisible

    }

    /**
     * creates the empty frame
     *
     * @return the created frame
     */
    private Container setCont() {
        //set controls

        //layout manager
        Container container = this.getContentPane();
        //container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
        container.setLayout(null);
        return container;
    }

    /**
     * Adds the buttons to the container
     *
     * @param container
     */
    private void addControls(Container container) {
        int x, y, w, h;
        w = h = BUTTON_SIZE;
        y = 0;

        for (int X = 0; X < inputX; X++) {
            x = 0;
            for (int Y = 0; Y < inputY; Y++) {
                container.add(matrix[X][Y].button);
                matrix[X][Y].button.setBounds(x, y, w, h);
                x = x + w;
            }
            y = y + h;
        }
    }

    /**
     * checks around the mine to see how many are surrounding it
     *
     * @param X The X position of which the method is checking around
     * @param Y The Y position of which the method is checking around
     */
    private void checkpos(int X, int Y) {
        for (int x = X - 1; x <= X + 1; x++) {
            for (int y = Y - 1; y <= Y + 1; y++) {

                if (x < 0 || y < 0) {
                } else if (x >= inputX || y >= inputY) {
                } else if (matrix[x][y].mine) {
                    matrix[X][Y].surrounding++;
                }
            }
        }
        //Capital is value of current, lower is for the surrounding
    }

    /**
     * if a tile has 0 mines around it, then it can safely clear all tiles
     * around it automatically for the ease of the user
     *
     * @param posX the X position of the tile
     * @param posY the Y position of the tile
     */
    public void clearpos(int posX, int posY) {
        if (matrix[posX][posY].clicked == false) {
            matrix[posX][posY].clicked = true;
            matrix[posX][posY].autoClick();
            for (int x = posX - 1; x <= posX + 1; x++) {
                for (int y = posY - 1; y <= posY + 1; y++) {
                    if (x < 0 || y < 0) {
                    } else if (x >= inputX || y >= inputY) {
                    } else if (matrix[x][y].clicked == false) {
                        matrix[x][y].autoClick();
                    }
                }
            }

        }
    }

    /**
     * calls for the button to display its number w/o clicking it
     *
     * @param inputX the grid X
     * @param inputY the grid Y
     */
    private void debug(int inputX, int inputY) {
        for (int X = 0; X < inputX; X++) {
            for (int Y = 0; Y < inputY; Y++) {
                matrix[X][Y].debug(inputX, inputY);
            }
        }
    }

    /**
     * get the field size that the user wants
     */
    private void getField() {
        String[] options = new String[5];
        options[0] = "Small";
        options[1] = "Medium";
        options[2] = "Large";
        options[3] = "Larger";
        options[4] = "Largest";
        dropdown("Enter field size", "Input", options);
    }

    /**
     * Dialog with a drop down display for the user to select from
     *
     * @param text the text to display in the dialog
     * @param title the title of the dialog
     * @param options all the options to show in the drop down
     */
    public void dropdown(String text, String title, String[] options) {
        Object object = JOptionPane.showInputDialog(null, text, title,
                JOptionPane.PLAIN_MESSAGE, null, options, options[2]);
        while (object == null) {
            object = JOptionPane.showInputDialog(null, text, title,
                    JOptionPane.PLAIN_MESSAGE, null, options, options[2]);
        }
        if (object == "Small") {
            this.inputX = 8;
            this.inputY = 15;
            BUTTON_SIZE = 125;
        } else if (object == "Medium") {
            this.inputX = 20;
            this.inputY = 38;
            BUTTON_SIZE = 50;
        } else if (object == "Large") {
            this.inputX = 29;
            this.inputY = 55;
            BUTTON_SIZE = 35;
        } else if (object == "Larger") {
            this.inputX = 51;
            this.inputY = 96;
            BUTTON_SIZE = 20;
        } else if (object == "Largest") {
            this.inputX = 68;
            this.inputY = 128;
            BUTTON_SIZE = 15;
        }

    }

    /**
     * when the user clicks a button, check if they have won, if they have, then
     * ask present the end dialog
     */
    void checkForWin() {

        int number = inputX * inputY;
        int value = 0;
        for (int i = 0; i < inputX; i++) {
            for (int j = 0; j < inputY; j++) {
                if (matrix[i][j].clicked == true || matrix[i][j].marked == true) {
                    value++;
                }
            }
        }
        if (value == number) {
            int input = JOptionPane.showConfirmDialog(null, "You're Win!\nPlay again?", "MeinKraft",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            if (input == JOptionPane.YES_OPTION) {
                this.dispose();
                Graphic graphic = new Graphic();
            } else {
                System.exit(0);
            }
        }
    }

}
