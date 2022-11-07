import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Board implements ActionListener{

    Square[] squares = new Square[63];
    
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
                squares[counter] = new Square(row , col , whiteField);
                counter++;

                //whiteField stays the same at row swap
                if(!(counter == 8 || counter == 16 || counter == 24 || counter == 32 || counter == 40 || counter == 48 || counter == 56)){
                    whiteField = !whiteField;
                }
            }
        }

        // Now use swing to create an 8 times 8 grid and then assign the black or white field to it.
        // Creating the 8x8 Grid and adding black and white squares to them.
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 700);
        frame.setLayout(new GridLayout(8,8));
        for(int i = 0 ; i < 64 ; i ++){
            if(squares[i].getFieldColor() == false){
                frame.add(new JButton(blackEmpty));
            }else{
                frame.add(new JButton(whiteEmpty));
            }   
        }
        frame.setVisible(true);
    }
    

    // method to check what action is performed goes here
    

    // method to get the centre of the square
    
    public Square getCentreSquare(int row, int col){
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

    // boolean type method to decalre the winner

    

    // decalre your main method here to run and play the game
    
}
