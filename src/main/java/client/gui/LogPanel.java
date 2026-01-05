package client.gui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class LogPanel extends VBox {
    private TextArea logArea;

    public LogPanel() {
        this.setPadding(new Insets(10));
        this.setSpacing(10);
        this.setPrefWidth(200);
        this.setStyle("-fx-background-color: #e0e0e0; -fx-border-color: #999; -fx-border-width: 0 1 0 0;");

        Label title = new Label("Game Log");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        logArea = new TextArea();
        logArea.setEditable(false);
        logArea.setWrapText(true);
        logArea.setPrefHeight(500);

        this.getChildren().addAll(title, logArea);
    }

    public void addLog(String message) {
        logArea.appendText(message + "\n");
    }
}