package org.emp.gl.clients;

import org.emp.gl.timer.service.TimerService;
import org.emp.gl.timer.service.TimerChangeListener;

import java.beans.PropertyChangeEvent;

/**
 * Compte à rebours : se désinscrit automatiquement quand il arrive à 0.
 */
public class CompteARebours implements TimerChangeListener {

    private int valeur;
    private final TimerService timerService;
    private final String name;

    public CompteARebours(TimerService timerService, int initialValue, String name) {
        this.timerService = timerService;
        this.valeur = initialValue;
        this.name = name;
        this.timerService.addTimeChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (TimerChangeListener.SECONDE_PROP.equals(evt.getPropertyName())) {
            if (valeur > 0) {
                System.out.println("[" + name + "] compte = " + valeur);
                valeur--;
            } else {
                System.out.println("[" + name + "] terminé -> désinscription");
                timerService.removeTimeChangeListener(this);
            }
        }
    }
}
