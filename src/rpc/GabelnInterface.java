/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rpc;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GabelnInterface extends Remote {
    // jedes Objekt, das
    // remote sein soll muss das Remote-Interface
    // direkt oder indirekt implementieren

    public boolean ablegen(int gabel) throws RemoteException;

    public boolean aufnehmen(int gabel) throws RemoteException;

}
