package org.emp.gl.clients;

import org.emp.gl.timer.service.TimerService;
import org.emp.gl.timer.service.TimerChangeListener;

import java.beans.PropertyChangeEvent;
import java.time.LocalTime;

/**
 * Horloge cliente : s'inscrit au TimerService et affiche l'heure
 * à chaque changement de seconde (ou minute/heure).
 */
public class Horloge implements TimerChangeListener {

    private final String name;
    private TimerService timerService;

    public Horloge(String name) {
        this.name = name;
        System.out.println("Horloge " + name + " initialized!");
    }

    // Injection de dépendance (abstraction)
    public void setTimerService(TimerService timerService) {
        this.timerService = timerService;
        if (this.timerService != null) {
            this.timerService.addTimeChangeListener(this);
        }
    }

    public void afficherHeure() {
        if (timerService != null) {
            System.out.println(name + " affiche " +
                    String.format("%02d:%02d:%02d (dix=%d)",
                            timerService.getHeures(),
                            timerService.getMinutes(),
                            timerService.getSecondes(),
                            timerService.getDixiemeDeSeconde()));
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String prop = evt.getPropertyName();
        // on peut choisir d'afficher seulement sur la seconde pour éviter trop d'affichage
        if (TimerChangeListener.SECONDE_PROP.equals(prop)) {
            afficherHeure();
        }
        // si tu veux afficher également aux autres propriétés, ajoute les conditions
    }
}
