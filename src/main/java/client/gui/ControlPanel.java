package client.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class ControlPanel extends HBox {
    private Button rollButton;
    private Button buyButton;
    private Button endTurnButton;

    public ControlPanel() {
        this.setSpacing(20);
        this.setAlignment(Pos.CENTER);
        this.setPadding(new javafx.geometry.Insets(15));
        this.setStyle("-fx-background-color: #333;");

        rollButton = new Button("Roll Dice");
        rollButton.setPrefSize(100, 40);

        buyButton = new Button("Buy Property");
        buyButton.setPrefSize(120, 40);

        endTurnButton = new Button("End Turn");
        endTurnButton.setPrefSize(100, 40);

        this.getChildren().addAll(rollButton, buyButton, endTurnButton);
    }

    public Button getRollButton() { return rollButton; }
    public Button getBuyButton() { return buyButton; }
    public Button getEndTurnButton() { return endTurnButton; }
}