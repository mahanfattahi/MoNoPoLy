package server;

import model.GameState;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerMain {
    private static final int PORT = 8080;
    private static final int MAX_PLAYERS = 4;
    // لیستی برای نگهداری اتصال تمام بازیکنان جهت ارسال پیام همگانی
    private static List<ClientHandler> connectedClients = new ArrayList<>();

    public static void main(String[] args) {
        GameState.getInstance();
        TurnManager turnManager = new TurnManager(MAX_PLAYERS);
        GameEngine gameEngine = new GameEngine(turnManager);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT);
            System.out.println("Waiting for 4 players...");

            while (connectedClients.size() < MAX_PLAYERS) {
                Socket clientSocket = serverSocket.accept();

                // ساخت هندلر و اضافه کردن به لیست
                int pId = connectedClients.size() + 1;
                ClientHandler handler = new ClientHandler(clientSocket, pId, gameEngine);
                connectedClients.add(handler);
                handler.start();

                String playerName = "Player " + pId;
                GameState.getInstance().addPlayer(pId, playerName);
                System.out.println(playerName + " connected.");

                // اگر ۴ نفر تکمیل شدند
                if (connectedClients.size() == MAX_PLAYERS) {
                    System.out.println("Game Full! Starting...");
                    GameState.getInstance().startGame();
                    broadcast("GAME_STARTED"); // خبر دادن به همه کلاینت‌ها
                    broadcast("TURN:1"); // اعلام نوبت نفر اول
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // متد کمکی برای ارسال پیام به همه
    public static void broadcast(String msg) {
        for (ClientHandler client : connectedClients) {
            client.sendMessage(msg);
        }
    }
}