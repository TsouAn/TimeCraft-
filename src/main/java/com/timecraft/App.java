package com.timecraft;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class App extends Application {

    private final TimerController timer = new TimerController();
    private Label timerLabel;
    private Timeline flashTimeline;

    @Override
    public void start(Stage stage) {
        // --- Current system time ---
        Label clockLabel = new Label();
        clockLabel.getStyleClass().add("clock-label");
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm:ss");
        Timeline clockTick = new Timeline(new KeyFrame(Duration.seconds(1), e ->
                clockLabel.setText(LocalTime.now().format(fmt))));
        clockTick.setCycleCount(Animation.INDEFINITE);
        clockTick.play();
        clockLabel.setText(LocalTime.now().format(fmt));

        HBox clockBar = new HBox(clockLabel);
        clockBar.setAlignment(Pos.CENTER_RIGHT);
        clockBar.setPadding(new Insets(10, 20, 0, 20));

        // --- Timer display ---
        timerLabel = new Label();
        timerLabel.getStyleClass().add("timer-display");
        timerLabel.textProperty().bind(timer.displayTextProperty());

        // Flash effect when timer finishes
        flashTimeline = new Timeline(new KeyFrame(Duration.millis(500), e -> {
            if (timerLabel.getStyleClass().contains("flash")) {
                timerLabel.getStyleClass().remove("flash");
            } else {
                timerLabel.getStyleClass().add("flash");
            }
        }));
        flashTimeline.setCycleCount(10);
        flashTimeline.setOnFinished(e -> timerLabel.getStyleClass().remove("flash"));

        timer.finishedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                flashTimeline.playFromStart();
            }
        });

        // --- Quick-add buttons ---
        Button add5   = createAddButton("+5 min",  5);
        Button add10  = createAddButton("+10 min", 10);
        Button add30  = createAddButton("+30 min", 30);
        Button add60  = createAddButton("+1 hr",   60);

        HBox addBtns = new HBox(12, add5, add10, add30, add60);
        addBtns.setAlignment(Pos.CENTER);

        // --- Control buttons ---
        Button startBtn = new Button("Start");
        startBtn.getStyleClass().addAll("ctrl-btn", "start-btn");
        startBtn.setOnAction(e -> timer.start());

        Button pauseBtn = new Button("Pause");
        pauseBtn.getStyleClass().addAll("ctrl-btn", "pause-btn");
        pauseBtn.setOnAction(e -> timer.pause());

        Button resetBtn = new Button("Reset");
        resetBtn.getStyleClass().addAll("ctrl-btn", "reset-btn");
        resetBtn.setOnAction(e -> {
            flashTimeline.stop();
            timerLabel.getStyleClass().remove("flash");
            timer.reset();
        });

        HBox ctrlBtns = new HBox(16, startBtn, pauseBtn, resetBtn);
        ctrlBtns.setAlignment(Pos.CENTER);

        // --- Timer section ---
        VBox timerSection = new VBox(20, clockBar, timerLabel, addBtns, ctrlBtns);
        timerSection.setAlignment(Pos.CENTER);
        timerSection.setPadding(new Insets(20));
        VBox.setVgrow(timerSection, Priority.ALWAYS);

        // --- YouTube Music button ---
        YouTubePanel ytPanel = new YouTubePanel();

        // --- Root layout ---
        VBox root = new VBox(10, timerSection, ytPanel);
        root.setPadding(new Insets(0, 0, 10, 0));

        Scene scene = new Scene(root, 500, 420);
        scene.getStylesheets().add(
                getClass().getResource("/style.css").toExternalForm());

        stage.setTitle("TimeCraft");
        stage.setMinWidth(350);
        stage.setMinHeight(300);
        stage.setScene(scene);
        stage.show();
    }

    private Button createAddButton(String text, int minutes) {
        Button btn = new Button(text);
        btn.getStyleClass().add("add-btn");
        btn.setOnAction(e -> timer.addMinutes(minutes));
        return btn;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
