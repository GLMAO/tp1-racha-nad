package org.emp.gl.core.launcher;

import org.emp.gl.clients.Horloge;
import org.emp.gl.clients.HorlogeFrame;
import org.emp.gl.clients.CompteARebours;
import org.emp.gl.timer.service.TimerService;
import org.emp.gl.time.service.impl.DummyTimeServiceImpl;

import java.util.Random;

/**
 * Launcher pour tester le TP
 */
public class App {

    public static void main(String[] args) throws InterruptedException {
        testDuTimeService();
    }

    private static void testDuTimeService() throws InterruptedException {
        // instanciation du service (le Dummy démarre son Timer dans le constructeur)
        TimerService timer = new DummyTimeServiceImpl();

        // Horloges : création + injection
        Horloge h1 = new Horloge("H1");
        h1.setTimerService(timer);

        Horloge h2 = new Horloge("H2");
        h2.setTimerService(timer);

        // un CompteARebours de 5
        new CompteARebours(timer, 5, "C5");

        // 10 comptes aléatoires entre 10 et 20
        Random rnd = new Random();
        for (int i = 0; i < 10; i++) {
            int v = 10 + rnd.nextInt(11); // 10..20
            new CompteARebours(timer, v, "C" + i);
        }
        // Lancement de l’interface graphique Swing dans le thread d’UI
        javax.swing.SwingUtilities.invokeLater(() -> new HorlogeFrame(timer));
        // laisse tourner 60s pour observer
        Thread.sleep(60000);
        System.out.println("Fin du test launcher.");
    }
}
