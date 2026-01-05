package client.gui;

import javafx.scene.layout.BorderPane;

public class GameLayout extends BorderPane {
    private BoardPane boardPane;
    private ControlPanel controlPanel;
    private PlayerPanel playerPanel;
    private LogPanel logPanel;

    public GameLayout() {
        boardPane = new BoardPane();
        controlPanel = new ControlPanel();
        playerPanel = new PlayerPanel();
        logPanel = new LogPanel();

        this.setCenter(boardPane);
        this.setBottom(controlPanel);
        this.setRight(playerPanel);
        this.setLeft(logPanel);
    }

    public ControlPanel getControlPanel() { return controlPanel; }
    public PlayerPanel getPlayerPanel() { return playerPanel; }
    public LogPanel getLogPanel() { return logPanel; }
    public BoardPane getBoardPane() { return boardPane; }

    public void addLog(String msg) {
        logPanel.addLog(msg);
    }
}