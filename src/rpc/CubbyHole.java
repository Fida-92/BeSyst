package rpc;





// die Server-Implementierung der Schnittstelle "Cubby"

import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

public class CubbyHole extends UnicastRemoteObject implements Cubby {
  // Daten
  int[] buffer = new int[4];
  int in = 0, out = 0, count = 0;
  // Konstruktor gibt ggf. Netzwerk-Fehler weiter
  public CubbyHole() throws RemoteException {
    super();
    try {
      Naming.rebind("rmi:///CubbyServer", this);
      System.out.println("Server unter Name: 'CubbyServer' gebunden");
    }
    catch (Exception e){
      System.out.println("Fehler beim Server-Binding: "+ e.getMessage());
    }
  }
  
  // die eigentlichen Server-Methoden:
  public String ablegen(int w) throws RemoteException {
    if (count < 4) {	// entspricht der Bedingung des selektiven Accept
			// des Vorlesungsbeispiels Erzeuger/Verbraucher (7)
      buffer[in] = w;
      count++;
      in = (in+1) % 4;
      return "Einlagerung: " + w;
    }
    return "Lager voll belegt";
  }
  public String aufnehmen() throws RemoteException {
    int w = 0;
    if (count > 0) {	// entspricht der Bedingung des selektiven Accept
			// des Vorlesungsbeispiels Erzeuger/Verbraucher (7)
      w = buffer[out];
      count--;
      out = (out+1) % 4;
      return "Nr." + w;
    }
    return "Lager leer";
  }

  // in main wird das Server-Objekt angelegt,
  // das dann vom Client gefunden und genutzt werden kann
  public static void main (String[] args) {
    try {
      CubbyHole c = new CubbyHole();
    }
    catch (RemoteException e){
      System.out.println("Fehler beim Anlegen des Server-Objektes: "+e.getMessage());
    }  }
}
