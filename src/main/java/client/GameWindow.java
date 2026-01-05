package client;

import client.gui.BoardPane;
import client.gui.ControlPanel;
import client.gui.LogPanel;
import client.gui.PlayerPanel;
import javafx.scene.layout.BorderPane;

public class GameWindow extends BorderPane {
    // اجزای گرافیکی
    private BoardPane boardPane;
    private ControlPanel controlPanel;
    private PlayerPanel playerPanel;
    private LogPanel logPanel;

    public GameWindow() {
        // ۱. ساخت پنل‌ها
        boardPane = new BoardPane();
        controlPanel = new ControlPanel();
        playerPanel = new PlayerPanel();
        logPanel = new LogPanel();

        // ۲. چیدمان پنل‌ها در صفحه (وسط، پایین، راست، چپ)
        this.setCenter(boardPane);
        this.setBottom(controlPanel);
        this.setRight(playerPanel);
        this.setLeft(logPanel);
    }

    // متدهای دسترسی (Getter) برای اینکه در ClientMain بتوانیم به دکمه‌ها دسترسی داشته باشیم
    public ControlPanel getControlPanel() {
        return controlPanel;
    }

    public PlayerPanel getPlayerPanel() {
        return playerPanel;
    }

    public LogPanel getLogPanel() {
        return logPanel;
    }

    public BoardPane getBoardPane() {
        return boardPane;
    }

    // متد کمکی برای اضافه کردن سریع لاگ
    public void addLog(String message) {
        logPanel.addLog(message);
    }
}