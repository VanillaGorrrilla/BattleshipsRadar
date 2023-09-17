import java.util.Random;

public class Map {

    public Tile Grid[][];
    int Rows, Cols;
    public Ship Ships[] = {new Ship(2, "P", "Patrol Boat"), new Ship(3, "D", "Destroyer"),
            new Ship(3, "S", "Submarine"), new Ship(4,"B", "Battleship"), new Ship(5, "A", "Aircraft Carrier")};
    //Grid
    //make tiles
    //
    Map(int rows, int cols ){
        Rows = rows;
        Cols = cols;
        Grid = new Tile[rows][cols];
        for(int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Grid[i][j] = new Tile();
            }
        }
        AddShips();
        printOceanMap();
    }

    private void AddShips() {
        for(int i = 0; i < Ships.length; i++)
        {
            boolean ShipPlaced = false;
            do {
                boolean isOverLapping = false;
                Random random = new Random();
                int x = random.nextInt(10);
                int y = random.nextInt(10);
                //System.out.print(x + "  " + y);

                for(int j = 0; j < Ships[i].Health; j++) { //check tile positions

                    if(x + j >= Rows) {
                        isOverLapping = true; // outside grid
                        // System.out.print(x + "  " + y);

                    }

                    if(!isOverLapping) {
                        if(Grid[x + j][y].ShipIndex != -1) {
                            //there is a ship there
                            isOverLapping = true;

                        }
                    }
                }

                if(!isOverLapping) {
                    //place Ship
                    PlaceShip(i, Ships[i].Health, x, y);
                    ShipPlaced = true;
                }
            }while(ShipPlaced == false);
        }
    }

    private void PlaceShip(int arrayIndex,int ShipLength, int x, int y) {

        for(int i = 0; i < ShipLength; i++) { //Places the ship and it length onto the board
            Grid[x + i][y].ShipIndex = arrayIndex;
            //Grid[x + i][y].Display = Ships[arrayIndex].Name;  //Debug Display

        }
    }

    public void printOceanMap(){
        System.out.println();
        //First section of Ocean Map (Numbers on top)
        System.out.print("  ");
        for(int i = 0; i < Cols; i++)
            System.out.print(i);
        System.out.println();

        //Middle section of Ocean Map (Numbers on the side and the main board)
        for(int x = 0; x < Rows; x++) {
            System.out.print(x + "|");
            for (int y = 0; y < Cols; y++){


                System.out.print(Grid[x][y].Display);

            }
            System.out.println("|" + x);
        }

        //Last section of Ocean Map (Numbers at the bottom)
        System.out.print("  ");
        for(int i = 0; i < Cols; i++)
            System.out.print(i);
        System.out.println();
    }

    public boolean Hit(int Index) {
        Ships[Index].Health--;

        if(Ships[Index].Health == 0) {
            return true;
        }
        return false;
    }

    public String ShipList() { //Checks if a ship has sunk with its health being equal to 0
        String fName = "";
        for(Ship ship : Ships) {
            if (ship.Health == 0) {
                fName = ship.ShipName + "\n"; //writes the ships name into  the text box chosen
            }
        }
        return fName;
    }

}