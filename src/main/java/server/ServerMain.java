package server;

import model.GameState;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {
    private static final int PORT = 8080;
    private static final int MAX_PLAYERS = 4;

    public static void main(String[] args) {
        GameState.getInstance();
        TurnManager turnManager = new TurnManager(MAX_PLAYERS);
        GameEngine gameEngine = new GameEngine(turnManager);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT);
            System.out.println("Waiting for players...");

            int connectedPlayers = 0;

            while (connectedPlayers < MAX_PLAYERS) {
                Socket clientSocket = serverSocket.accept();
                connectedPlayers++;

                String playerName = "Player " + connectedPlayers;
                GameState.getInstance().addPlayer(connectedPlayers, playerName);

                System.out.println(playerName + " connected.");

                ClientHandler handler = new ClientHandler(clientSocket, connectedPlayers, gameEngine);
                handler.start();
            }

            System.out.println("All players connected. Game Starting!");
            GameState.getInstance().startGame();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}