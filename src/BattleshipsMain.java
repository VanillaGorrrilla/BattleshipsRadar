import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

public class BattleshipsMain implements ActionListener{
    Label XLabel, YLabel, RadarCounterLabel, ScoreLabel;
    JTextField Xcoordinates, Ycoordinates, ShipList, RadarCounter, Score;
    JButton Restart, Quit, Help, Shoot, PlaceRadar;
    public static int numRows = 10;
    public static int numCols = 10;
    public static int computerShips;
    public static String[][] grid = new String[numRows][numCols];
    public static int[][] missedGuesses = new int[numRows][numCols];
    int PlayerScore = 0;
    int RadarAmount = 2;
    Map map;



    BattleshipsMain(){  //GUI Design
        map = new Map(numRows, numCols);
        JFrame f= new JFrame("Battleship A");

//-----------------------------------------------------------//

        XLabel=new Label("X Coordinate");
        XLabel.setBounds(20, 400, 100, 20);
        XLabel.setFont(new Font("Serif", Font.PLAIN, 15));

        YLabel=new Label("Y Coordinate");
        YLabel.setBounds(20, 430, 100, 20);
        YLabel.setFont(new Font("Serif", Font.PLAIN, 15));

        RadarCounterLabel=new Label("Radar");
        RadarCounterLabel.setBounds(300, 90, 50, 20);
        RadarCounterLabel.setFont(new Font("Serif", Font.PLAIN, 20));

        ScoreLabel=new Label("Score");
        ScoreLabel.setBounds(300, 25, 50, 40);
        ScoreLabel.setFont(new Font("Serif", Font.PLAIN, 20));

//-----------------------------------------------------------//

        Xcoordinates=new JTextField();
        Xcoordinates.setBounds(130,430,30,20);

        Ycoordinates=new JTextField();
        Ycoordinates.setBounds(130,400,30,20);

        RadarCounter=new JTextField("3");
        RadarCounter.setBounds(360,80,50,40);
        RadarCounter.setEditable(false);

        Score=new JTextField("0");
        Score.setBounds(360,25,100,40);
        Score.setEditable(false);

        ShipList=new JTextField();
        ShipList.setBounds(20,80,260,300);
        ShipList.setEditable(false);

//-----------------------------------------------------------//

        Restart=new JButton("Restart");
        Restart.setBounds(20,20,80,50);

        Quit=new JButton("Quit");
        Quit.setBounds(110,20,80,50);

        Help=new JButton("Help");
        Help.setBounds(200,20,80,50);

        Shoot=new JButton("Shoot");
        Shoot.setBounds(180,400,100,50);

        PlaceRadar=new JButton("Place Radar");
        PlaceRadar.setBounds(290,400,110,50);

//-----------------------------------------------------------//

        Restart.addActionListener(this);
        Quit.addActionListener(this);
        Help.addActionListener(this);
        Shoot.addActionListener(this);
        PlaceRadar.addActionListener(this);

        f.add(XLabel);f.add(YLabel);f.add(RadarCounterLabel);f.add(ScoreLabel);
        f.add(Xcoordinates);f.add(Ycoordinates);f.add(RadarCounter);f.add(Score);f.add(ShipList);
        f.add(Restart);f.add(Quit);f.add(Help);f.add(Shoot);f.add(PlaceRadar);
        f.setSize(600,600);
        f.setLayout(null);
        f.setVisible(true);
    }

//-----------------------------------------------------------//

    public void actionPerformed(ActionEvent e) {  //Button Functionality
        if(e.getSource()==Restart) { //Launches the program again
            new BattleshipsMain();
        }

        else if(e.getSource()==Quit) { //Closes the program
            System.exit(0);
        }

        else if(e.getSource()==Help) { //Creates another GUI to be used as the help feature
            JTextArea HelpText;
            JFrame f2 = new JFrame("Help");

            HelpText=new JTextArea("Welcome to Battleships A, "
                    + "\n"
                    + "\nYou objective is to destory all the ships on the 10 by 10 "
                    + "\nboard as shown in the console."
                    + "There are a total of 5 ships with varying sizes that needs to be destroyed."
                    + "\nYou will need to input the X and Y coordinates that are "
                    + "\nlocated next to the Shoot Button on the Interface"
                    + "\nto be able to fire in hopes you hit a ship or to place down a radar."
                    + "\n"
                    + "\nYou start with the score of 0. Each shot (hit or miss) "
                    + "\ndeducts one point from the score; and each hit adds "
                    + "\none point; when a ship is sunk, a number of points equal "
                    + "\nto the length of that ship multiplied "
                    + "\nby 2 is added to your score"
                    + "\n"
                    + "\nGoodluck!");
            HelpText.setBounds(30,30,320,400);
            HelpText.setLineWrap(true);
            HelpText.setEditable(false);

            f2.add(HelpText);
            f2.setSize(400,500);
            f2.setLayout(null);
            f2.setVisible(true);
        }

        else if(e.getSource()==Shoot ) {
            Shoot();
        }


        else if(e.getSource()==PlaceRadar) {
            Radar();
        }

    }

//-----------------------------------------------------------//

    void Shoot() {
        String Xvalue = Xcoordinates.getText(); //retrieves the values of x and y from user input
        String Yvalue = Ycoordinates.getText();
        int x1 =  Integer.parseInt(Xvalue);
        int y1 =  Integer.parseInt(Yvalue);
        PlayerScore = PlayerScore - 1;
        String ScoreValue = String.valueOf(PlayerScore);

        if(!map.Grid[x1][y1].beenHit) { // if tile has not been hit

            if(map.Grid[x1][y1].ShipIndex == -1) {//no ship
                System.out.println("Sorry, you missed");
                System.out.println();
                map.Grid[x1][y1].Display = "M";
                // --- score
            }
            else {
                if(map.Hit(map.Grid[x1][y1].ShipIndex)) {
                    System.out.println("You sunk a ship");
                    System.out.println();
                    PlayerScore = PlayerScore + map.Ships[map.Grid[x1][y1].ShipIndex].Length * 2; //Gets the ships length and multiplies by 2
                    ShipList.setText(map.ShipList());
                }
                else {
                    System.out.println("You hit a ship");
                    System.out.println();
                    PlayerScore = PlayerScore + 1;
                }

                map.Grid[x1][y1].Display = "H";
            }


        }

        map.printOceanMap();
        Score.setText(String.valueOf(PlayerScore));
    }

    void Radar() {
        String Xvalue = Xcoordinates.getText();
        String Yvalue = Ycoordinates.getText();
        int x1 =  Integer.parseInt(Xvalue);
        int y1 =  Integer.parseInt(Yvalue);
        String RadarValue = String.valueOf(RadarAmount);

        //loop through 3x3
        if(RadarAmount >= 1) {
            RadarAmount = RadarAmount - 1;
            RadarCounter.setText(RadarValue);
            for(int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++){ //3x3 going from -1 (y and x) to 1
                    if(map.Grid[x1 + x][y1 + y].ShipIndex != -1) {
                        for(int i = 0; i < numRows; i++) {
                            for (int j = 0; j < numCols; j++){ //Goes through the board to highlight the same ship found
                                if(map.Grid[i][j].ShipIndex == map.Grid[x1 + x][y1 + y].ShipIndex) {
                                    map.Grid[i][j].Display = map.Ships[map.Grid[x1 + x][y1 + y].ShipIndex].Name; //changes the map to the ship name
                                    //map.Grid[i][j].Display = "?"; // debug radar
                                }
                            }
                        }
                    }
                }
            }
        }
        else {
            map.printOceanMap();
            RadarCounter.setText(RadarValue);
            System.out.println("Cant place anymore Radars");
        }
        map.printOceanMap();
        RadarCounter.setText(RadarValue);
    }


//-----------------------------------------------------------//

    public static void main(String[] args) {
        new BattleshipsMain();

    }

}