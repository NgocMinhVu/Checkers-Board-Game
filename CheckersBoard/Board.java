import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Board implements ActionListener{

    private static Square[] squares = new Square[64];
    
    // Initial Stats for sidepannel
    public static int whiteCounter = 12;
    public static int redCounter = 12;
    public static int whiteKingCounter = 0;
    public static int redKingCounter = 0;


    //Image object declaration
    private final ImageIcon whiteEmpty = new ImageIcon("empty.png");
    private final ImageIcon blackEmpty = new ImageIcon("empty2.png");
    private final ImageIcon white = new ImageIcon("white.png");
    private final ImageIcon red = new ImageIcon("red.png");
    private final ImageIcon whiteKing = new ImageIcon ("white-king.png");
    private final ImageIcon redKing = new ImageIcon("red-king.png");

    private Square firstClick = null;
    private boolean redTurn = false;

    private JFrame main = new JFrame();

    //Constructor
    public Board(){
        createSquares();
        createBoard();
        sideFrame();
    }


    //Creating 64 Squares
    private void createSquares(){
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

                squares[counter] = new Square(row , col , image);
                squares[counter].addActionListener(this);

                //whiteField remains the same at row swap
                if(!(counter == 7 || counter == 15 || counter == 23 || counter == 31 || counter == 39 || counter == 47 || counter == 55)){
                    whiteField = !whiteField;
                }

                counter++;
            }
        }
    }


    //Create the 8x8 grid and add squares
    private void createBoard(){
        main = new JFrame("Checkers Board Game");
        main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main.setSize(800, 800);
        main.setResizable(false);
        main.setLocationRelativeTo(null);
        main.setLayout(new GridLayout(8,8));
        //Add squares to main
        for (Square square : squares) {
            main.add(square);
        }
        main.setVisible(true);
    }

    // Creating the Side Frame
    private void sideFrame(){

        JFrame sideFrame = new JFrame();
        sideFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        sideFrame.setSize(200,400);
        sideFrame.setResizable(false);
        sideFrame.setLocation(main.getX() + main.getWidth(), main.getY());
        sideFrame.setLayout(new GridLayout(5,2));
        sideFrame.setVisible(true);
        
        JLabel Image1 = new JLabel();
        JLabel text2 = new JLabel();
        JLabel Image2 = new JLabel();
        JLabel text4 = new JLabel();
        JLabel text5 = new JLabel(" ");
        JLabel text6 = new JLabel(" ");
        JLabel Image3 = new JLabel();
        JLabel text8 = new JLabel();
        JLabel Image4 = new JLabel();
        JLabel text10 = new JLabel();
        
        // Formatting Text Displayed
        text2.setHorizontalAlignment(SwingConstants.CENTER);
        text2.setFont(new Font("Calibri", Font.PLAIN, 26));
        text4.setHorizontalAlignment(SwingConstants.CENTER);
        text4.setFont(new Font("Calibri", Font.PLAIN, 26));
        text8.setHorizontalAlignment(SwingConstants.CENTER);
        text8.setFont(new Font("Calibri", Font.PLAIN, 26));
        text10.setHorizontalAlignment(SwingConstants.CENTER);
        text10.setFont(new Font("Calibri", Font.PLAIN, 26));

        Image1.setIcon(white);
        Image2.setIcon(whiteKing);
        Image3.setIcon(red);
        Image4.setIcon(redKing);

        text2.setText(String.valueOf(whiteCounter));
        text4.setText(String.valueOf(whiteKingCounter));
        text8.setText(String.valueOf(redCounter));
        text10.setText(String.valueOf(redKingCounter));

        // Add Components to Pannel
        sideFrame.add(Image1);
        sideFrame.add(text2);
        sideFrame.add(Image2);
        sideFrame.add(text4);
        sideFrame.add(text5);
        sideFrame.add(text6);
        sideFrame.add(Image3);
        sideFrame.add(text8);
        sideFrame.add(Image4);
        sideFrame.add(text10);

       
    }
    

    public void actionPerformed(ActionEvent e){
        if (firstClick == null){
            Square clicked = (Square)e.getSource();
            if (clicked.isValidPiece(this.redTurn)){
                this.firstClick = clicked;

                //Highlight potential moves
                for (Square square : squares){
                    square.isValidMove(this.firstClick, true);
                }
            }
        } else {
            //Un-hihglight potential moves
            for (Square square : squares){
                square.resetSelected();
            }

            Square secondClick = (Square)e.getSource();
            if (secondClick.isValidMove(firstClick, false)) {
                this.firstClick.moveTo(secondClick, redTurn);   
                
                if (isWinner()){
                    displayWinner();
                } else {
                    this.redTurn = !redTurn;
                }
            }

            //Reset firstClick so that player can pick a different piece to move
            this.firstClick = null; 
        }
    }
    

    //Return true if piece has at least one possible next move
    private boolean nextMove(Square piece){
        int numMove = 0;
        int row = piece.getRow();
        int col = piece.getCol();
        String icon = piece.getIcon().toString();
        Square nextSquare;

        //For white pieces and red king
        if (icon.equals(white.toString()) || icon.equals(redKing.toString())){
            int cl = col;
            int cr = col;

            for (int r = row-1; r >= 1; r--){
                cl--;
                if (cl >= 1){
                    nextSquare = getCentreSquare(r, cl);
                    if (nextSquare.isValidMove(piece, true)){
                        nextSquare.setIcon(whiteEmpty);
                        numMove++;
                    }
                }

                cr++;
                if (cr <= 8){
                    nextSquare = getCentreSquare(r, cr);
                    if (nextSquare.isValidMove(piece, true)){
                        nextSquare.setIcon(whiteEmpty);
                        numMove++;
                    }
                }
            }
        }

        //For red pieces and white king
        if (piece.getString(piece).equals(red.toString()) || piece.getString(piece).equals(whiteKing.toString())){
            int cl = col;
            int cr = col;

            for (int r = row+1; r <= 8; r++){
                cl--;
                if (cl >= 1){
                    nextSquare = getCentreSquare(r, cl);
                    if (nextSquare.isValidMove(piece, true)){
                        nextSquare.setIcon(whiteEmpty);
                        numMove++;
                    }
                }

                cr++;
                if (cr <= 8){
                    nextSquare = getCentreSquare(r, cr);
                    if (nextSquare.isValidMove(piece, true)){
                        nextSquare.setIcon(whiteEmpty);
                        numMove++;
                    }
                }
            }
        }

        if (numMove > 0){
            return true;
        } else {
            return false;
        }
    }


    //Method to get the square
    public static Square getCentreSquare(int row, int col){
        for(int i=0; i<64; i++){
            if(squares[i].getRow() == row && squares[i].getCol() == col)
                return squares[i];
        }
        return null;
    }


    //Boolean type method to declare the winner
    private boolean isWinner(){
        if (redTurn == false){
            //Check opponent red pieces
            for (Square square : squares){
                if (square.getIcon().toString().startsWith("red")){
                    if (nextMove(square)){
                        return false;
                    }
                }
            }
        }

        if (redTurn == true){
            //Check oponent white pieces
            for (Square square : squares){                
                if (square.getIcon().toString().startsWith("white")){
                    if (nextMove(square)){
                        return false;
                    } 
                }
            }
        }
        
        return true;
    }


    //Winning prompt
    private void displayWinner(){
        JFrame winFrame = new JFrame();
        winFrame.setSize(450, 175);
        winFrame.setLocationRelativeTo(main);
        winFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        winFrame.setAlwaysOnTop(true);

        winFrame.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel winMessage = new JLabel();
        if (redTurn == false){
            winMessage.setText("The White Team has won! Congratulations!");
        } else {
            winMessage.setText("The Red Team has won! Congratulations!");
        }
        winMessage.setFont(new Font("Calibri", Font.PLAIN, 16));
        c.gridx = 0;
        c.gridy = 0;
        c.ipady = 30;
        winFrame.add(winMessage, c);
        
        c.ipady = 20;

        JLabel playAgainLabel = new JLabel("Would you like to play again?");
        playAgainLabel.setFont(new Font("Calibri", Font.PLAIN, 13));
        c.gridx = 0;
        c.gridy = 1; 
        winFrame.add(playAgainLabel, c);

        JButton playAgainButton = new JButton("Play again");
        playAgainButton.setPreferredSize(new Dimension(100, 15));
        playAgainButton.setFont(new Font("Calibri", Font.PLAIN, 13));
        c.gridx = 0;
        c.gridy = 2;
        winFrame.add(playAgainButton, c);

        playAgainButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent clickEvent){
                //Close both frames
                winFrame.dispose();
                main.dispose();

                //Reset board
                createSquares();
                createBoard();
                redTurn = false;
            }
        });

        winFrame.setVisible(true);
    }


    //Main method
    public static void main(String args[]) {
        new Board();
    }
}
