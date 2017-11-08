/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rpc;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.logging.Logger;


public class GabelnServer {
    private static final Logger LOG= Logger.getLogger(GabelnServer.class.getName());
    public static final String REMOTE_OBJ="Gabel"; // unter diesem Name ist dem Client die Schnittstelle verfügbar.
    public static void main(String[] args)   {
        try {
            // Polymorphism: Wir erzeugen ein Objekt der serverseitigen Implemntierung der Schnittstelle GabelnInterface.
            GabelnInterface gabelObj = new GabelnImpl();
            // Naming.rebing("Name", Objekt) bietet das Objekt der Implementierung unter der gegebenen Namen. hier "Gabel"
            Naming.rebind(REMOTE_OBJ, gabelObj);
            System.out.println("Server ist gestartet unter Name: "+REMOTE_OBJ); 
        } catch (RemoteException | MalformedURLException  ex) {
            LOG.warning("Es ist ein Fehler aufgetreten ");
        }
    }

}
