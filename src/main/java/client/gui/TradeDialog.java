package client.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.function.Consumer;

public class TradeDialog extends Stage {
    private ComboBox<String> playerCombo;
    private TextField offerMoneyField;
    private TextField requestMoneyField;
    private Button sendButton;
    private Button cancelButton;

    public TradeDialog(Consumer<String> onTradeProposed) {
        this.initModality(Modality.APPLICATION_MODAL);
        this.setTitle("Propose Trade");

        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        HBox playerBox = new HBox(10);
        playerBox.setAlignment(Pos.CENTER_LEFT);
        playerCombo = new ComboBox<>();
        playerCombo.getItems().addAll("2", "3", "4");
        playerBox.getChildren().addAll(new Label("Target Player ID:"), playerCombo);

        HBox offerBox = new HBox(10);
        offerBox.setAlignment(Pos.CENTER_LEFT);
        offerMoneyField = new TextField("0");
        offerBox.getChildren().addAll(new Label("Offer Money:"), offerMoneyField);

        HBox requestBox = new HBox(10);
        requestBox.setAlignment(Pos.CENTER_LEFT);
        requestMoneyField = new TextField("0");
        requestBox.getChildren().addAll(new Label("Request Money:"), requestMoneyField);

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        sendButton = new Button("Propose");
        cancelButton = new Button("Cancel");
        buttonBox.getChildren().addAll(sendButton, cancelButton);

        sendButton.setOnAction(e -> {
            String targetId = playerCombo.getValue();
            String offer = offerMoneyField.getText();
            String request = requestMoneyField.getText();

            if (targetId != null && onTradeProposed != null) {
                String command = "TRADE:" + targetId + ":" + offer + ":" + request;
                onTradeProposed.accept(command);
                this.close();
            }
        });

        cancelButton.setOnAction(e -> this.close());

        root.getChildren().addAll(new Label("Trade Proposal"), playerBox, offerBox, requestBox, buttonBox);
        Scene scene = new Scene(root, 300, 250);
        this.setScene(scene);
    }
}
