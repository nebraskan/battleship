package BattleShip;

public class Cruiser extends Ship{
   private static final char HIT = 'c';
   private static final char SAFE = 'C';
   private static final int LENGTH = 4;
   
   public Cruiser(String s) {
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