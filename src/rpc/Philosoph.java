package rpc;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.logging.Logger;

public class Philosoph extends Thread {

    private static final Logger LOG = Logger.getLogger(Philosoph.class.getName());
    private static GabelnInterface gabel;
    private boolean hatHunger;
    private int denkenZeit;
    private int essenZeit;
    private int warteZeit;
    private int warteZeitInsgesamt = 0;
    private int philosNummer;
    private int dieLinkeGabel;
    private int DieRechteGabel;
    private boolean gebelGenommen;
    private static final String REMOTE_OBJ = "Gabel"; // unter diesem Name ist dem Client die Schnittstelle verfügbar.

    public Philosoph(int philoNummer, int denkenZeit, int essenZeit) {
        this.philosNummer = philoNummer;
        this.dieLinkeGabel = (philoNummer);
        this.DieRechteGabel = ((philoNummer + 1) % 5);
        this.denkenZeit = denkenZeit;
        this.essenZeit = essenZeit;

    }

    public static void main(String[] args) {
        try {
            // Suchen nach dem Remote Object in der RMIRegistry:
            gabel = (GabelnImpl) Naming.lookup("rmi://localhost/" + REMOTE_OBJ);
            System.out.println("Der Server ist gestartet.");
            Thread philo1 = new Philosoph(0, 100, 500);
            Thread philo2 = new Philosoph(1, 700, 200);
            Thread philo3 = new Philosoph(2, 500, 500);
            Thread philo4 = new Philosoph(3, 1000, 1000);
            Thread philo5 = new Philosoph(4, 100, 100);

            philo1.start();
            philo2.start();
            philo3.start();
            philo4.start();
            philo5.start();

        } catch (MalformedURLException | NotBoundException | RemoteException e) {
            LOG.warning("Fehler bei der Verbindung aufgetreten");
        }

    }

    @Override
    public void run() {

        while (true) {

            try {

                denken();

                do {
                    gebelGenommen = gabel.aufnehmen(dieLinkeGabel); // ausgabe hinzufügen, ob die Gabel genommen wurde.
                    if (gebelGenommen) {
                        gebelGenommen = gabel.aufnehmen(DieRechteGabel);// ausgabe hinzufügen, ob die Gabel genommen wurde.
                        if (gebelGenommen) {

                            if (hatHunger) {
                                System.out.println("Philosoph: " + philosNummer
                                        + " ist hungrig seit " + warteZeitInsgesamt + "ms");
                            }

                            essen();
                            gabel.ablegen(dieLinkeGabel);
                            gabel.ablegen(DieRechteGabel);
                            System.out.println("Philosoph: " + philosNummer
                                    + " hat gegessen.");

                        } else {
                            gabel.ablegen(dieLinkeGabel);
                        }
                    }

                    if (!gebelGenommen) {
                        try {
                            warteZeit = (int) (Math.random() * 450);
                            warteZeitInsgesamt += warteZeit;
                            Thread.sleep(warteZeit);
                            hatHunger = true;
                        } catch (InterruptedException e) {
                        }

                    }

                } while (!gebelGenommen);

            } catch (RemoteException e) {
                System.out.println(e);
            }

        }

    }

    public void essen() {
        System.out.println("Philosop: " + this.philosNummer + " isst. Gegessen in: "
                + essenZeit + "ms");
        try {

            sleep(essenZeit);
        } catch (InterruptedException e) {
        }
    }

    public void denken() {
        System.out.println("Philosop: " + this.philosNummer + " denkt : "
                + denkenZeit + "ms");
        try {

            sleep(denkenZeit);
        } catch (InterruptedException e) {
        }
    }
}
