/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpc;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class GabelnImpl extends UnicastRemoteObject implements GabelnInterface {

    // Die Anzahl der Verbraucher "Philosophen" 
    private final int gabelnAnzahl = 5;
    //Speicherung in ein booelan-Array
    boolean[] gabel = new boolean[gabelnAnzahl];
    private int zaehler = 0;
    private boolean frei = true;// die Gabel ist frei
    private boolean nichtFrei = false;// die Gabel ist nicht frei

    public GabelnImpl() throws RemoteException {
        // Die möglichen Fehler sind in der UnicastRemoteObject abgefangen. Daher ist es sind sinnvoll die Möglichkeit zu nutzen.
        super();
        // Initialisierung von Gabeln. Am Anfang sind alle frei.
        while (zaehler < gabel.length) {
            gabel[zaehler] = frei;
            zaehler++;
        } // Ende der While-Schleife.
    }

    /**
     * Ist die Implementierungmethode von GabelnInterface
     *
     * @param gabeln
     * @return
     * @throws java.rmi.RemoteException
     */
    @Override
    public boolean ablegen(int gabeln) throws RemoteException {

        gabel[gabeln] = true; // linke Gabel ablegen.
        gabel[(gabeln + 1) % gabel.length] = true; // rechte Gabel ablegen.

        return frei;// Die Gabeln wurden abgelegt.
    }

    /**
     * Ist die Implementierungmethode von GabelnInterface
     *
     * @param gabeln
     * @return
     * @throws java.rmi.RemoteException
     */
    @Override
    public boolean aufnehmen(int gabeln) throws RemoteException {
        if (!gabel[gabeln] || !gabel[(gabeln + 1) % gabel.length]) {

            gabel[gabeln] = false; // linke Gabel aufnhemen.
            gabel[(gabeln + 1) % gabel.length] = false; // rechte Gabel aufnehmen.
            return nichtFrei; // Die beiden Gabeln oder eine davon sind schon belegt.
        }
        return frei; // Die Gabeln wurden aufgenommen.
    }

}
