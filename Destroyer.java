package BattleShip;

public class Destroyer extends Ship{
   private static final char HIT = 'd';
   private static final char SAFE = 'D';
   private static final int LENGTH = 3;
   
   public Destroyer(String s) {
      super(s);
   }
   
   public char drawShipStatusAtCell( boolean isDamaged ) {
      if (isDamaged) {
         return HIT;
      } else {
         return SAFE;
      }
   }
	public int getLength() {
      return LENGTH;
   } 
}