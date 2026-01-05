package client.gui;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class BoardPane extends Pane {

    public BoardPane() {
        this.setPrefSize(600, 600);
        this.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: black; -fx-border-width: 2;");
        drawBoard();
    }

    private void drawBoard() {
        int tileSize = 50;
        int tilesPerSide = 10;

        for (int i = 0; i < 40; i++) {
            double x = 0;
            double y = 0;

            if (i < 10) {
                x = 550 - (i * tileSize);
                y = 550;
            } else if (i < 20) {
                x = 50;
                y = 550 - ((i - 10) * tileSize);
            } else if (i < 30) {
                x = 50 + ((i - 20) * tileSize);
                y = 50;
            } else {
                x = 550;
                y = 50 + ((i - 30) * tileSize);
            }

            Rectangle rect = new Rectangle(x, y, tileSize, tileSize);
            rect.setFill(Color.LIGHTCYAN);
            rect.setStroke(Color.BLACK);

            Text text = new Text(String.valueOf(i));
            text.setFont(new Font(10));
            text.setX(x + 15);
            text.setY(y + 30);

            this.getChildren().addAll(rect, text);
        }
    }
}
