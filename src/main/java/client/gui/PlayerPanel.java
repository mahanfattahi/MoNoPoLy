package client.gui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class PlayerPanel extends VBox {
    private Label[] playerLabels;

    public PlayerPanel() {
        this.setPadding(new Insets(10));
        this.setSpacing(15);
        this.setPrefWidth(200);
        this.setStyle("-fx-background-color: #e0e0e0; -fx-border-color: #999; -fx-border-width: 0 0 0 1;");

        Label title = new Label("Players");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        this.getChildren().add(title);

        playerLabels = new Label[4];
        for (int i = 0; i < 4; i++) {
            playerLabels[i] = new Label("Player " + (i + 1) + "\nWaiting...");
            playerLabels[i].setStyle("-fx-background-color: white; -fx-padding: 10; -fx-background-radius: 5;");
            playerLabels[i].setPrefWidth(180);
            this.getChildren().add(playerLabels[i]);
        }
    }

    public void updatePlayer(int index, String name, int money, int position) {
        if (index >= 0 && index < 4) {
            String status = String.format("%s\nMoney: $%d\nPos: %d", name, money, position);
            playerLabels[index].setText(status);
        }
    }
}