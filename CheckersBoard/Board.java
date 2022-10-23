import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Board implements ActionListener
{
    
    // create 64 instance of your squre object using the square class constructor

    // image object declaration
    private ImageIcon whiteEmpty = new ImageIcon("empty.png");
    private ImageIcon blackEmpty = new ImageIcon("empty2.png");
    private ImageIcon white = new ImageIcon("white.png");
    private ImageIcon red = new ImageIcon("red.png");
    private  ImageIcon whiteKing = new ImageIcon ("white-king.png");
    private ImageIcon redKing = new ImageIcon("red-king.png");

    private Square firstClick = null;

    private boolean redTurn = false;

    private JFrame frame;

    // Constructor goes here

    

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
