package com.timecraft;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Duration;

public class TimerController {

    private int remainingSeconds = 0;
    private Timeline countdown;
    private final StringProperty displayText = new SimpleStringProperty("00:00");
    private final BooleanProperty finished = new SimpleBooleanProperty(false);

    public TimerController() {
        countdown = new Timeline(new KeyFrame(Duration.seconds(1), e -> tick()));
        countdown.setCycleCount(Animation.INDEFINITE);
    }

    private void tick() {
        if (remainingSeconds > 0) {
            remainingSeconds--;
            updateDisplay();
            if (remainingSeconds == 0) {
                countdown.stop();
                finished.set(true);
            }
        }
    }

    private void updateDisplay() {
        int h = remainingSeconds / 3600;
        int m = (remainingSeconds % 3600) / 60;
        int s = remainingSeconds % 60;
        if (h > 0) {
            displayText.set(String.format("%02d:%02d:%02d", h, m, s));
        } else {
            displayText.set(String.format("%02d:%02d", m, s));
        }
    }

    public void addMinutes(int minutes) {
        finished.set(false);
        remainingSeconds += minutes * 60;
        updateDisplay();
    }

    public void start() {
        if (remainingSeconds > 0) {
            finished.set(false);
            countdown.play();
        }
    }

    public void pause() {
        countdown.pause();
    }

    public void reset() {
        countdown.stop();
        remainingSeconds = 0;
        finished.set(false);
        updateDisplay();
    }

    public boolean isRunning() {
        return countdown.getStatus() == Animation.Status.RUNNING;
    }

    public StringProperty displayTextProperty() {
        return displayText;
    }

    public BooleanProperty finishedProperty() {
        return finished;
    }
}
