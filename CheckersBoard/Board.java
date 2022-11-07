import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Board implements ActionListener{

    private static Square[] squares = new Square[64];
    
    // Image object declaration
    private ImageIcon whiteEmpty = new ImageIcon("empty.png");
    private ImageIcon blackEmpty = new ImageIcon("empty2.png");
    private ImageIcon white = new ImageIcon("white.png");
    private ImageIcon red = new ImageIcon("red.png");
    private ImageIcon whiteKing = new ImageIcon ("white-king.png");
    private ImageIcon redKing = new ImageIcon("red-king.png");

    private Square firstClick = null;

    private boolean redTurn = false;

    private JFrame frame;

    // Constructor goes here
    public Board(){
        
        // Creating 64 Squares
        int counter = 0;
        boolean whiteField = false;

        for(int row = 1 ; row <= 8 ; row++){
            for(int col = 1 ; col <= 8 ; col++){
                ImageIcon image;
                if (whiteField) {
                    if (counter < 24) {
                        image = red;
                    } else if (counter > 39) {
                        image = white;
                    } else {
                        image = whiteEmpty;
                    }
                } else {
                    image = blackEmpty;
                }

                squares[counter] = new Square(row , col , image, whiteField);
                squares[counter].addActionListener(this);

                //whiteField stays the same at row swap
                if(!(counter == 7 || counter == 15 || counter == 23 || counter == 31 || counter == 39 || counter == 47 || counter == 55)){
                    whiteField = !whiteField;
                }

                counter++;
            }
        }

        // Create the 8x8 grid and add squares
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setLayout(new GridLayout(8,8));
        for (Square square : squares) {
            frame.add(square);
        }
        frame.setVisible(true);
    }
    

    // method to check what action is performed goes here
    public void actionPerformed(ActionEvent e){
        if (firstClick == null){
            this.firstClick = (Square)e.getSource();
        } else {
            Square secondClick = (Square)e.getSource();
            if (secondClick.isValidMove(firstClick, false)) {
                this.firstClick.moveTo(secondClick);   
            }
            this.firstClick = null;
        }
    }
    

    // method to get the centre of the square
    public static Square getCentreSquare(int row, int col){
        for(int i=0; i<64; i++){
            if(squares[i].getRow() == row && squares[i].getCol() == col)
                return squares[i];
        }
        return null;
    }

    public int countRed(){
        int counter = 0;
        for(int i=0; i<64; i++){
            if(squares[i].getIcon().toString().startsWith("red"))
                counter++;
        }
        return counter;
    }

    public int countWhite(){
        int counter = 0;
        for(int i=0; i<64; i++){
            if(squares[i].getIcon().toString().startsWith("white"))
                counter++;
        }
        return counter;
    }


    // boolean type method to declare the winner


    // declare your main method here to run and play the game
    public static void main(String args[]) {
        Board board = new Board();
    }
}
