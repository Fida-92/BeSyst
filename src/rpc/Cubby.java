package rpc;




// die Schnittstelle (besser: die Signaturen aller Methoden),
// die der Server remote-Clients anbietet
// als "interface" dann vom Server implementiert.
// Die Clients brauchen aber nur diese
// Interface-Definition (die einmal von javac
// uebersetzt werden muss)

import java.rmi.*;

public interface Cubby extends Remote { // jedes Objekt, das
               // remote sein soll muss das Remote-Interface
               // direkt oder indirekt implementieren
  public String ablegen(int ware) throws RemoteException;
  public String aufnehmen() throws RemoteException;
}
