package com.timecraft;

import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class YouTubePanel extends VBox {

    private static final String YOUTUBE_MUSIC_URL = "https://music.youtube.com";

    public YouTubePanel() {
        WebView webView = new WebView();
        webView.setPrefHeight(400);
        WebEngine engine = webView.getEngine();
        engine.setUserAgent(
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
            "AppleWebKit/537.36 (KHTML, like Gecko) " +
            "Chrome/120.0.0.0 Safari/537.36"
        );
        engine.load(YOUTUBE_MUSIC_URL);

        getChildren().add(webView);
        setFillWidth(true);
    }
}
