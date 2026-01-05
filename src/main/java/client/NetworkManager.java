package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.function.Consumer;

public class NetworkManager {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private Consumer<String> onMessageReceived;
    private boolean isRunning;

    public NetworkManager(String host, int port, Consumer<String> onMessageReceived) {
        this.onMessageReceived = onMessageReceived;
        try {
            this.socket = new Socket(host, port);
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.isRunning = true;

            // ترد جداگانه برای گوش دادن به پیام‌های سرور
            new Thread(this::listen).start();

        } catch (IOException e) {
            System.err.println("Connection failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void sendMessage(String msg) {
        if (out != null) {
            out.println(msg);
        }
    }

    private void listen() {
        try {
            String msg;
            while (isRunning && (msg = in.readLine()) != null) {
                if (onMessageReceived != null) {
                    onMessageReceived.accept(msg);
                }
            }
        } catch (IOException e) {
            if (isRunning) {
                System.err.println("Disconnected from server.");
            }
        }
    }

    public void close() {
        isRunning = false;
        try {
            if (socket != null) socket.close();
            if (in != null) in.close();
            if (out != null) out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}