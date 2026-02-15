package com.timecraft;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.awt.Desktop;
import java.net.URI;

public class YouTubePanel extends HBox {

    private static final String YOUTUBE_MUSIC_URL = "https://music.youtube.com";

    public YouTubePanel() {
        setAlignment(Pos.CENTER);
        setPadding(new Insets(15));
        setSpacing(15);

        Label musicIcon = new Label("\u266B");
        musicIcon.getStyleClass().add("music-icon");

        Button openBtn = new Button("Open YouTube Music");
        openBtn.getStyleClass().add("music-btn");
        openBtn.setOnAction(e -> {
            try {
                Desktop.getDesktop().browse(new URI(YOUTUBE_MUSIC_URL));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        getChildren().addAll(musicIcon, openBtn);
    }
}
