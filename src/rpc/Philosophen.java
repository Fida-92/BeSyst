package rpc;

import static java.lang.Thread.sleep;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Logger;

public class Philosophen extends Thread {

    private static final Logger LOG = Logger.getLogger(Philosoph.class.getName());

    private int nummer;
    private int linkeGabel;
    private int rechteGabel;
    private boolean gabelGenommen;//=false Default_Wert
    private static GabelnInterface gabel;
    private int denkenZeit;
    private int essenZeit;
    private int hungrigWarteZeit;
private int hungrigWarteZeitInsgesamt = 0;
    
    private boolean hungrig;
    private static final String REMOTE_OBJ = "Gabel"; // unter diesem Name ist dem Client die Schnittstelle verfügbar.

    public Philosophen(int nummer) {
        this.nummer = nummer;

    }

    public static void main(String[] args) {
        try {

            // Suche nach dem Remote Object in der RMIRegistry
            gabel = (GabelnImpl) Naming.lookup(REMOTE_OBJ);
            System.out.println("Mit dem Server gebunden.");
    
            Thread philosoph1 = new Philosophen(0);
            Thread philosoph2 = new Philosophen(1);
            Thread philosoph3 = new Philosophen(2);
            Thread philosoph4 = new Philosophen(3);
            Thread philosoph5 = new Philosophen(4);

            philosoph1.start();
            philosoph2.start();
            philosoph3.start();
            philosoph4.start();
            philosoph5.start();

        } catch (MalformedURLException | NotBoundException | RemoteException e) {
            LOG.warning("Fehler bei der Verbindung aufgetreten");
        }

    }

    @Override
    public void run() {

        while (true) {

            try {
                denkenBereit();

                while(!gabelGenommen) {
                    gabelGenommen = gabel.aufnehmen(linkeGabel);
                    // System.out.println("Linke Gabel genommen: "+frei);
                    if (gabelGenommen) {
                        gabelGenommen = gabel.aufnehmen(rechteGabel);
                        // System.out.println("Recht Gabel genommen: "+frei);
                        if (gabelGenommen) {

                            if (hungrig) {
                                System.out.println("Philosoph: " + nummer + " ist wartet um zu essen ");
                            }

                            essenBereit();
                            gabel.ablegen(linkeGabel);
                            gabel.ablegen(rechteGabel);
                            System.out.println("Philosoph: " + nummer+ " hat gegessen.");

                        } else {
                            gabel.ablegen(linkeGabel);
                        }
                    }

                    if (!gabelGenommen) {
                        
                            hungrig = true;
                    }
                }

            } catch (RemoteException e) {
                System.out.println(e);
            }
        }
    }

    public void essenBereit() {
        System.out.println("Philosoph: " + nummer + " isst.");
         
    }

    public void denkenBereit() {
        System.out.println("Philosoph: " + this.nummer + " denkt.");
    }
}
