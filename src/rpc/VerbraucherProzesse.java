package rpc;


public class VerbraucherProzesse {

    /**
     * Die Klasse VerbraucherProzesse ist wegen der Teilaufgabe 2 erzeugt worden. Man könnte 
     * auf diese neue Klasse verzichten und die 2 neuen Verbraucher in derselben Klasse aufrufen. Aber 
     * Modularitäts- und Übersichlichkeitshalber wurde die Klasse erstellt.
     * @param args 
     */
    public static void main(String[] args) {
        // Erzeugen des ersten Verbrauchers
        ConsumerThread consumerThread_1 = new ConsumerThread(1);
        // Erzeugen des zweiten Verbrauchers
        ConsumerThread consumerThread_2 = new ConsumerThread(2);
        ConsumerThread consumerThread_3 = new ConsumerThread(3);
        // Starten der Threads.
        consumerThread_1.start();
        consumerThread_2.start();
        consumerThread_3.start();
    }
}
