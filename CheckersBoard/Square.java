import javax.imageio.stream.ImageInputStreamImpl;
import javax.lang.model.util.ElementScanner6;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Square extends JButton{
    
    private final int row;
    private final int col;

    // Added Instance Variable
    private final String fieldColor;
   
    //Getter Methods for Instance Variables
    public int getRow(){
        return this.row;
    }

    public int getCol(){
        return this.col;
    }

    public String getFieldColor(){
        return this.fieldColor;
    }


    // intialize private instance objects
    private ImageIcon whiteEmpty = new ImageIcon("empty.png");
    private ImageIcon blackEmpty = new ImageIcon("empty2.png");
    private ImageIcon white = new ImageIcon("white.png");
    private ImageIcon red = new ImageIcon("red.png");
    private ImageIcon whiteKing = new ImageIcon ("white-king.png");
    private ImageIcon redKing = new ImageIcon("red-king.png");
    private ImageIcon yellow = new ImageIcon("selected.png");


    // declare your constructor
    public Square(int row , int col, ImageIcon image, boolean whiteField){
        this.row = row;
        this.col = col;
        this.setIcon(image);

        if(whiteField){
            this.fieldColor = "white";
        }else{
            this.fieldColor = "black";
        }
    }


    private String getString(Square square){
        return square.getIcon().toString();
    }


    // create your moveTo method here
    public void moveTo(Square other){
        ImageIcon temp = (ImageIcon)other.getIcon();
        other.setIcon((ImageIcon)this.getIcon());
        this.setIcon(temp);
    }
    

    public boolean isValidMove(Square firstClick, boolean setSelected){
        // red at top going down, white at bottom going up

        //is first selection a piece
        String firstIcon = getString(firstClick);
        if (firstIcon.equals(whiteEmpty.toString()) || firstIcon.equals(blackEmpty.toString()))
            return false;

        // is second selection empty
        String secondIcon = getString(this);
        if(!secondIcon.equals(whiteEmpty.toString()))
            return false;

        // is it in the right direction
        // for red pieces
        if(firstIcon.equals(red.toString()) || firstIcon.equals(redKing.toString()) || firstIcon.equals(whiteKing.toString())) {
            //single step
            if(firstClick.getRow() + 1 == row){
                if(Math.abs(firstClick.getCol() - col) == 1){
                    if(setSelected)
                        this.setIcon(yellow);
                    return true;
                }
            }

            //leap over
            else if(firstClick.getRow() + 2 == row){
                if(Math.abs(firstClick.getCol() - col) == 2){
                    Square middle = checkMiddleIcon(firstClick);
                    if(middle != null){
                        if(setSelected)
                            this.setIcon(yellow);
                        else
                            middle.removePiece();
                        return true;
                    }
                }
            }
        }

        //for white pieces
        if(firstIcon.equals(white.toString()) || firstIcon.equals(redKing.toString()) || firstIcon.equals(whiteKing.toString())){
            // single step
            if(firstClick.getRow() - 1 == row){
                if(Math.abs(firstClick.getCol() - col) == 1){
                    if(setSelected)
                        this.setIcon(yellow);
                    return true;
                }
            }

            //leap over
            else if(firstClick.getRow() - 2 == row){
                if(Math.abs(firstClick.getCol() - col) == 2){
                    Square middle = checkMiddleIcon(firstClick);
                    if(middle != null){
                        if(setSelected)
                            this.setIcon(yellow);
                        else
                            middle.removePiece();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public Square checkMiddleIcon(Square firstClick){
        int midCol, midRow;
        if(firstClick.getRow() + 2 == row)
            midRow = firstClick.getRow() + 1;
        else    
            midRow = firstClick.getRow() - 1;

        if(col < firstClick.getCol())
            midCol = col + 1;
        else
            midCol = col - 1;

        Square middle = Board.getCentreSquare(midRow, midCol);

        if(middle == null)
            return null;
        String first = getString(firstClick);
        String mid = getString(middle);

        if((first.startsWith("red") && mid.startsWith("white")) || (first.startsWith("white") && mid.startsWith("red")))
            return middle;
        return null;
    }

    public void resetSelected(){
        if(this.getIcon().toString().equals(yellow.toString()))
            this.setIcon(whiteEmpty);
    }

    public void removePiece(){
        this.setIcon(whiteEmpty);
    }
}