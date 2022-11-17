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

    private Square firstClick = null;
    private boolean redTurn = false;

    private JFrame frame;
    private JButton button;

    // Constructor
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

                squares[counter] = new Square(row , col , image);
                squares[counter].addActionListener(this);

                // whiteField remains the same at row swap
                if(!(counter == 7 || counter == 15 || counter == 23 || counter == 31 || counter == 39 || counter == 47 || counter == 55)){
                    whiteField = !whiteField;
                }

                counter++;
            }
        }

        // Create the 8x8 grid and add squares
        this.frame = new JFrame();
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(800, 800);
        this.frame.setLayout(new GridLayout(8,8));
        // Add squares to frame
        for (Square square : squares) {
            this.frame.add(square);
        }
        this.frame.setVisible(true);
    }
    

    public void actionPerformed(ActionEvent e){
        if(e.getSource()==button){
            Board board = new Board();
        }
        
        if (firstClick == null){
            Square clicked = (Square)e.getSource();
            if (clicked.isValidPiece(this.redTurn)){
                this.firstClick = clicked;

                // Highlight potential moves
                for (Square square : squares){
                    square.isValidMove(this.firstClick, true);
                }
            }
        } else {
            // Un-hihglight potential moves
            for (Square square : squares){
                square.resetSelected();
            }

            Square secondClick = (Square)e.getSource();
            if (this.firstClick.canMoveTo(secondClick)) {
                this.firstClick.moveTo(secondClick);   
                this.redTurn = !redTurn;
            }

            // Reset firstClick so that player can pick a different piece to move
            this.firstClick = null; 
            }

            // Check for and Declare Winner
            declareWinner();
        }
    

    // Method to get the centre of the square
    public static Square getCentreSquare(int row, int col){
        for(int i=0; i<64; i++){
            if(squares[i].getRow() == row && squares[i].getCol() == col)
                return squares[i];
        }
        return null;
    }


    // Count number of red pieces on board
    public int countRed(){
        int counter = 0;
        for(int i=0; i<64; i++){
            if(squares[i].getIcon().toString().startsWith("red"))
                counter++;
        }
        return counter;
    }


    // Count number of white pieces on board
    public int countWhite(){
        int counter = 0;
        for(int i=0; i<64; i++){
            if(squares[i].getIcon().toString().startsWith("white"))
                counter++;
        }
        return counter;
    }

    // Method to declare the winner

    // Add in automatic quitting of game when there are no more moves available.
    // 
    public void declareWinner(){
        JFrame frame1;
        
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(800, 800);
        this.frame.setLayout(new GridLayout(8,8));

        if(countRed() == 0){
            System.out.println("The WHITE Team has won!!!");
            
            // Frame to Declare Winner
            frame1 = new JFrame();
            JLabel label = new JLabel();
            JLabel label1 = new JLabel();
            JPanel panel = new JPanel();

            button = new JButton("Play Again!");
            frame1.setLayout(new FlowLayout());
            frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame1.setSize(400, 400);
            frame1.setLocationRelativeTo(frame);
            label.setText("The White Team has won! Congratulations");
            label1.setText("Would you like to play again?");
            button.setBounds(200, 100, 100, 50);
            button.addActionListener(this);
            
            panel.add(button);
            frame1.add(label);
            frame1.add(label1);
            frame1.add(panel);

            frame1.setVisible(true);
            
        }
        else if(countWhite() == 0){
            System.out.println("The RED Team has won!!!");
             
            // Frame to Declare Winner
            frame1 = new JFrame();
            JLabel label = new JLabel();
            JLabel label1 = new JLabel();
            JPanel panel = new JPanel();

            button = new JButton("Play Again!");
            frame1.setLayout(new FlowLayout());
            frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame1.setSize(400, 400);
            frame1.setLocationRelativeTo(frame);
            label.setText("The Red Team has won! Congratulations");
            label1.setText("Would you like to play again?");
            button.setBounds(200, 100, 100, 50);
            button.addActionListener(this);

            panel.add(button);
            frame1.add(label);
            frame1.add(label1);
            frame1.add(panel);
            
            frame1.setVisible(true);
        }
    }

    public static void main(String args[]) {
        Board board = new Board();  
    }
}
