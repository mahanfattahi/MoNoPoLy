package client;

import client.gui.GameLayout;
import client.gui.TradeDialog;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ClientMain extends Application {
    private NetworkManager networkManager;
    private GameLayout root;

    @Override
    public void start(Stage primaryStage) {
        root = new GameLayout();

        networkManager = new NetworkManager("localhost", 8080, message -> {
            Platform.runLater(() -> handleServerMessage(message));
        });

        setupButtons();

        Scene scene = new Scene(root, 900, 700);
        primaryStage.setTitle("Monopoly Client");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setupButtons() {
        root.getControlPanel().getRollButton().setOnAction(e -> {
            networkManager.sendMessage("ROLL");
        });

        root.getControlPanel().getBuyButton().setOnAction(e -> {
            networkManager.sendMessage("BUY");
        });

        root.getControlPanel().getEndTurnButton().setOnAction(e -> {
            networkManager.sendMessage("END_TURN");
        });

        Button tradeBtn = new Button("Trade");
        tradeBtn.setPrefSize(100, 40);
        tradeBtn.setOnAction(e -> {
            new TradeDialog(command -> networkManager.sendMessage(command)).show();
        });

        root.getControlPanel().getChildren().add(tradeBtn);
    }

    private void handleServerMessage(String message) {
        System.out.println("Server: " + message);
        root.addLog("Server: " + message);

        if (message.startsWith("MOVED:")) {
            String[] parts = message.split(":");
            String pId = parts[1];
            String steps = parts[2];
            String tileId = parts[3];
            root.addLog("Player " + pId + " moved " + steps + " steps to Tile " + tileId);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}