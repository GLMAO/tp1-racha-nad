package org.emp.gl.clients;

import org.emp.gl.timer.service.TimerChangeListener;
import org.emp.gl.timer.service.TimerService;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;

public class HorlogeFrame extends JFrame implements TimerChangeListener {

    private JLabel timeLabel;
    private TimerService timerService;

    public HorlogeFrame(TimerService timerService) {
        this.timerService = timerService;
        this.timerService.addTimeChangeListener(this);

        setTitle("Horloge Graphique");
        setSize(250, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        timeLabel = new JLabel("00:00:00", SwingConstants.CENTER);
        timeLabel.setFont(new Font("Arial", Font.BOLD, 36));
        add(timeLabel, BorderLayout.CENTER);

        setVisible(true);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        SwingUtilities.invokeLater(() -> {
            int h = timerService.getHeures();
            int m = timerService.getMinutes();
            int s = timerService.getSecondes();
            timeLabel.setText(String.format("%02d:%02d:%02d", h, m, s));
        });
    }
}
