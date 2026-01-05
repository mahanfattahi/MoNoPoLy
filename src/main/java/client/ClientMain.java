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
    private int myPlayerId;

    @Override
    public void start(Stage primaryStage) {
        root = new GameLayout();

        // اتصال به سرور
        networkManager = new NetworkManager("localhost", 8080, message -> {
            Platform.runLater(() -> handleServerMessage(message));
        });

        setupButtons();

        Scene scene = new Scene(root, 1100, 700);
        primaryStage.setTitle("Monopoly Client");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setupButtons() {
        root.getControlPanel().getRollButton().setOnAction(e -> networkManager.sendMessage("ROLL"));
        root.getControlPanel().getBuyButton().setOnAction(e -> networkManager.sendMessage("BUY"));
        root.getControlPanel().getEndTurnButton().setOnAction(e -> networkManager.sendMessage("END"));

        Button tradeBtn = new Button("Trade");
        tradeBtn.setOnAction(e -> {
            new TradeDialog(command -> networkManager.sendMessage(command)).show();
        });
        root.getControlPanel().getChildren().add(tradeBtn);
    }

    private void handleServerMessage(String message) {
        System.out.println("Server: " + message);

        if (message.startsWith("WELCOME Player")) {
            String[] parts = message.split(" ");
            myPlayerId = Integer.parseInt(parts[2]);
            ((Stage) root.getScene().getWindow()).setTitle("Monopoly Client - Player " + myPlayerId);
            root.addLog("Connected as Player " + myPlayerId + ". Waiting for others...");
        }
        else if (message.equals("GAME_STARTED")) {
            root.addLog("Game Started! Good luck.");
            // نمایش مهره‌های همه بازیکنان در خانه شروع (0)
            for (int i = 0; i < 4; i++) {
                root.getBoardPane().updateTokenPosition(i, 0);
            }
        }
        else if (message.startsWith("TURN:")) {
            String turnId = message.split(":")[1];
            root.addLog("It is Player " + turnId + "'s turn.");
            // اینجا می‌توانید دکمه‌ها را فعال/غیرفعال کنید
            boolean isMyTurn = String.valueOf(myPlayerId).equals(turnId);
            root.getControlPanel().getRollButton().setDisable(!isMyTurn);
        }
        else if (message.startsWith("MOVED:")) {
            try {
                String[] parts = message.split(":");
                int pId = Integer.parseInt(parts[1]);
                int tileId = Integer.parseInt(parts[3]);
                root.getBoardPane().updateTokenPosition(pId - 1, tileId);
                root.addLog("Player " + pId + " moved to tile " + tileId);
            } catch (Exception e) {
                System.err.println("Error parsing move: " + message);
            }
        }
        else {
            root.addLog(message);
        }
    }

    @Override
    public void stop() {
        if (networkManager != null) networkManager.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}