package org.emp.gl.time.service.impl;

import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;
import java.beans.PropertyChangeSupport;

import org.emp.gl.timer.service.TimerChangeListener;
import org.emp.gl.timer.service.TimerService;

/**
 * DummyTimeServiceImpl : génère des tics toutes les 100ms (dixième de seconde)
 * et notifie les listeners via PropertyChangeSupport (sécurisé).
 *
 * @author tina
 */
public class DummyTimeServiceImpl implements TimerService {

    private int dixiemeDeSeconde;
    private int minutes;
    private int secondes;
    private int heures;

    // PropertyChangeSupport gère la liste de listeners de manière sûre
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    /**
     * Constructeur : initialise les valeurs et démarre le Timer.
     */
    public DummyTimeServiceImpl() {
        setTimeValues();
        Timer timer = new Timer(true); // thread daemon
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                timeChanged();
            }
        };
        timer.scheduleAtFixedRate(task, 100, 100); // 100ms => dixième
    }

    private void setTimeValues() {
        LocalTime localTime = LocalTime.now();

        setSecondes(localTime.getSecond());
        setMinutes(localTime.getMinute());
        setHeures(localTime.getHour());
        setDixiemeDeSeconde(localTime.getNano() / 100_000_000); // 0..9
    }

    @Override
    public void addTimeChangeListener(TimerChangeListener pl) {
        // TimerChangeListener extends PropertyChangeListener => compatible
        pcs.addPropertyChangeListener(pl);
    }

    @Override
    public void removeTimeChangeListener(TimerChangeListener pl) {
        pcs.removePropertyChangeListener(pl);
    }

    private void timeChanged() {
        setTimeValues();
    }

    public void setDixiemeDeSeconde(int newDixiemeDeSeconde) {
        if (dixiemeDeSeconde == newDixiemeDeSeconde)
            return;

        int oldValue = dixiemeDeSeconde;
        dixiemeDeSeconde = newDixiemeDeSeconde;

        pcs.firePropertyChange(TimerChangeListener.DIXEME_DE_SECONDE_PROP, oldValue, dixiemeDeSeconde);
    }

    public void setSecondes(int newSecondes) {
        if (secondes == newSecondes)
            return;

        int oldValue = secondes;
        secondes = newSecondes;

        pcs.firePropertyChange(TimerChangeListener.SECONDE_PROP, oldValue, secondes);
    }

    public void setMinutes(int newMinutes) {
        if (minutes == newMinutes)
            return;

        int oldValue = minutes;
        minutes = newMinutes;

        // CORRECTION : on envoit 'minutes' et non 'secondes'
        pcs.firePropertyChange(TimerChangeListener.MINUTE_PROP, oldValue, minutes);
    }

    public void setHeures(int newHeures) {
        if (heures == newHeures)
            return;

        int oldValue = heures;
        heures = newHeures;

        // CORRECTION : on envoit 'heures' et non 'secondes'
        pcs.firePropertyChange(TimerChangeListener.HEURE_PROP, oldValue, heures);
    }

    @Override
    public int getDixiemeDeSeconde() {
        return dixiemeDeSeconde;
    }

    @Override
    public int getHeures() {
        return heures;
    }

    @Override
    public int getMinutes() {
        return minutes;
    }

    @Override
    public int getSecondes() {
        return secondes;
    }
}
