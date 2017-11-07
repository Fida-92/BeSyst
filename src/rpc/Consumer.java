package rpc;





// die Client-Implementierung des Verbrauchers
// mit Nutzung der Server-Schnittstelle
// (wie in "Cubby" festgelegt)

import java.rmi.*;

public class Consumer {

  public static void main (String[] args) {
    Cubby c;
    try {
      // Suchen des "CubbyServer"-Prozesses in der RMIRegistry:
      c = (Cubby)Naming.lookup("rmi://localhost/CubbyServer");
      while (true) {
        System.out.println("Entnahme: " + c.aufnehmen());
        try {
          Thread.sleep((int)(Math.random()*500));
        }
        catch (InterruptedException e) {}
      }
    }
    catch (Exception e){
      System.out.println( "Fehler beim Binden oder Rufen: "+e.getMessage());
    }
  }
}
