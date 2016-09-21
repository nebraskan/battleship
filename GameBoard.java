package BattleShip;
import java.util.ArrayList;
import java.lang.StringBuilder;

public class GameBoard
{
	int rowCount = 10;
	int colCount = 10;
	
	final String LINE_END = System.getProperty("line.separator"); 
	
	ArrayList< ArrayList< Cell > > cells;
	ArrayList< Ship > myShips = new ArrayList<Ship>();
	
	public GameBoard( int rowCount, int colCount )
	{
		this.rowCount = rowCount;
		this.colCount = colCount;
		
		//create the 2D array of cells
      cells = new ArrayList< ArrayList< Cell > >();
      for (int i = 0; i < rowCount; i++) {
         cells.add(new ArrayList< Cell >());
         for (int j = 0; j < colCount; j++) {
            cells.get(i).add(new Cell());
         }
      }
	}
	
	public String draw()
	{
      StringBuilder s = new StringBuilder();
      s.append(printBorder()); // top border
      s.append(LINE_END);
      int count = 0;
      for (ArrayList <Cell> arC : cells) {
         for (Cell myC : arC) {
            int c = count % colCount;
            if (c == 0) {
               s.append("+");
            } 
            s.append(myC.draw());
            if (c == colCount - 1) {
               s.append("+");
               s.append(LINE_END);
            }
            count++;
         }
      }
      s.append(printBorder()); // bottom border
      s.append(LINE_END);
      return s.toString();
		//draw the entire board... I'd use a StringBuilder object to improve speed
		//remember - you must draw one entire row at a time, and don't forget the
		//pretty border...
	}
  
   // Helper method to print border 
   private String printBorder() {
      StringBuilder s = new StringBuilder();
      s.append("+");
      for (int i = 0; i < colCount; i++) {
         s.append("-");
      }
      s.append("+");
      return s.toString();
   }
	
	//add in a ship if it fully 1) fits on the board and 2) doesn't collide w/
	//an existing ship.
	//Returns true on successful addition; false, otherwise
	public boolean addShip( Ship s , Position sternLocation, HEADING bowDirection ) {  
      int cellDiff = s.getLength();
      int shipX1, shipX2, shipY1, shipY2 = 0;
      ArrayList < Cell > pos = new ArrayList < Cell >();
      // Check if ship is within bounds of board and calculate the cells
      switch(bowDirection) {
         case NORTH:
            shipX1 = sternLocation.x;
            shipX2 = shipX1;
            shipY1 = sternLocation.y - cellDiff - 1;
            shipY2 = sternLocation.y;
            if (!checkShipBounds(shipX1, shipX2, shipY1, shipY2)) {
               return false;
            }
            for (int y = sternLocation.y; y < sternLocation.y + cellDiff; y++) {
               pos.add(cells.get(sternLocation.x).get(y));               
            }
            break;
         case SOUTH:
            shipX1 = sternLocation.x;
            shipX2 = shipX1;
            shipY1 = sternLocation.y;
            shipY2 = sternLocation.y + cellDiff - 1;
            if (!checkShipBounds(shipX1, shipX2, shipY1, shipY2)) {
               return false;
            }
            for (int y = sternLocation.y; y > sternLocation.y - cellDiff; y--) {
               pos.add(cells.get(sternLocation.x).get(y));               
            }
            break;
         case EAST:
            shipX1 = sternLocation.x;
            shipX2 = sternLocation.x + cellDiff - 1;
            shipY1 = sternLocation.y;
            shipY2 = shipY1;
            if (!checkShipBounds(shipX1, shipX2, shipY1, shipY2)) {
               return false;
            }
            for (int x = sternLocation.x; x < sternLocation.x + cellDiff; x++) {
               pos.add(cells.get(x).get(sternLocation.y));               
            }
            break;
         case WEST:
            shipX1 = sternLocation.x;
            shipX2 = sternLocation.x - cellDiff - 1;
            shipY1 = sternLocation.y;
            shipY2 = shipY1;
            if (!checkShipBounds(shipX1, shipX2, shipY1, shipY2)) {
               return false;
            }
            for (int x = sternLocation.x; x > sternLocation.x - cellDiff; x--) {
               pos.add(cells.get(x).get(sternLocation.y));
            }
            break;
      }
      // Check if there is already a ship on any of the given cells
      for (Cell c1 : pos) {
         if (c1.getShip() != null) {
            return false;
         }
      }
      for (Cell c2 : pos) {
         c2.setShip(s);
      }

      // Update ships position and return
      s.setPosition(pos);
      return true;
   }
	
   // Check if ship is within board limits
   private boolean checkShipBounds(int x1, int x2, int y1, int y2) {
      boolean b = true;
      if (x1 < 0 | x2 > (rowCount - 1) | y1 < 0 | y2 > (colCount - 1)) {
         b = false;
      }
      return b;
         
   }
	//Returns A reference to a ship, if that ship was struck by a missle.
	//The returned ship can then be used to print the name of the ship which
	//was hit to the player who hit it.
	//Ensure you handle missiles that may fly off the grid
	public Ship fireMissle( Position coordinate )
	{
		return null;
	}
	
	//Here's a simple driver that should work without touching any of the code below this point
	public static void main( String [] args )
	{
		System.out.println( "Hello World" );
		GameBoard b = new GameBoard( 10, 10 );	
		System.out.println( b.draw() );
      
		
		Ship s = new Cruiser( "Cruiser" );
		if( b.addShip(s, new Position(3,6), HEADING.WEST ) )
			System.out.println( "Added " + s.getName());
		else
			System.out.println( "Failed to add " + s.getName() );
		
		s = new Destroyer( "Vader" );
		if( b.addShip(s, new Position(3,5), HEADING.NORTH ) )
			System.out.println( "Added " + s.getName());
		else
			System.out.println( "Failed to add " + s.getName() );
      
		System.out.println( b.draw() );
		/*
		b.fireMissle( new Position(3,5) );
		System.out.println( b.draw() );
		b.fireMissle( new Position(3,4) );
		System.out.println( b.draw() );
		b.fireMissle( new Position(3,3) );
		System.out.println( b.draw() );
		
		b.fireMissle( new Position(0,6) );
		b.fireMissle( new Position(1,6) );
		b.fireMissle( new Position(2,6) );
		b.fireMissle( new Position(3,6) );
		System.out.println( b.draw() );
		
		b.fireMissle( new Position(6,6) );
		System.out.println( b.draw() );
      */
	}

}
