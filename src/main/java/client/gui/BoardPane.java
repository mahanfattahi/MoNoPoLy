package client.gui;

import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class BoardPane extends Pane {
    private Circle[] playerTokens;
    // تغییر مهم: سایز برد باید دقیقاً مضربی از سایز تایل باشد (11 * 60 = 660)
    private final int TILE_SIZE = 60;
    private final int BOARD_SIZE = 660;

    public BoardPane() {
        this.setPrefSize(BOARD_SIZE, BOARD_SIZE);
        this.setStyle("-fx-background-color: #FDF5E6; -fx-border-color: #444; -fx-border-width: 2;");

        drawCenterInfo();
        drawBoard();
        initPlayerTokens();
    }

    private void drawCenterInfo() {
        // پس‌زمینه وسط
        Rectangle center = new Rectangle(TILE_SIZE, TILE_SIZE, BOARD_SIZE - 2*TILE_SIZE, BOARD_SIZE - 2*TILE_SIZE);
        center.setFill(Color.web("#FAF0E6")); // رنگ کرم روشن
        center.setStroke(Color.LIGHTGRAY);
        this.getChildren().add(center);

        // متن لوگو وسط
        Text title = new Text("MONOPOLY");
        title.setFont(Font.font("Impact", 50));
        title.setFill(Color.web("#C21807"));
        title.setStroke(Color.BLACK);
        title.setStrokeWidth(1);

        Text subTitle = new Text("Student Project Edition");
        subTitle.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        subTitle.setFill(Color.GRAY);

        VBox centerBox = new VBox(10, title, subTitle);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setLayoutX(BOARD_SIZE / 2 - 120); // تنظیم حدودی وسط
        centerBox.setLayoutY(BOARD_SIZE / 2 - 50);

        // راه بهتر برای وسط چین کردن دقیق: استفاده از layout bounds بعد از افزودن نیست،
        // بلکه می‌توانیم دستی تنظیم کنیم. در اینجا برای سادگی هاردکد کردیم.

        // روش دقیق‌تر برای متن:
        title.setX(BOARD_SIZE / 2 - title.getLayoutBounds().getWidth() / 2);
        title.setY(BOARD_SIZE / 2 - 10);
        subTitle.setX(BOARD_SIZE / 2 - subTitle.getLayoutBounds().getWidth() / 2);
        subTitle.setY(BOARD_SIZE / 2 + 20);

        this.getChildren().addAll(title, subTitle);
    }

    private void drawBoard() {
        for (int i = 0; i < 40; i++) {
            double[] coords = getTileCoordinates(i);

            StackPane tileContainer = new StackPane();
            tileContainer.setLayoutX(coords[0]);
            tileContainer.setLayoutY(coords[1]);
            tileContainer.setPrefSize(TILE_SIZE, TILE_SIZE);

            // بدنه اصلی خانه
            Rectangle mainBody = new Rectangle(TILE_SIZE, TILE_SIZE);
            mainBody.setFill(Color.WHITE);
            mainBody.setStroke(Color.BLACK);
            mainBody.setStrokeWidth(1);

            // نوار رنگی (برای املاک)
            Rectangle colorBar = new Rectangle(TILE_SIZE, TILE_SIZE / 4);
            colorBar.setFill(getTileColor(i));
            colorBar.setStroke(Color.BLACK);
            colorBar.setStrokeWidth(0.5);

            if (isSpecialTile(i)) {
                mainBody.setFill(getTileColor(i));
                tileContainer.getChildren().add(mainBody);
            } else {
                tileContainer.getChildren().addAll(mainBody, colorBar);
                StackPane.setAlignment(colorBar, Pos.TOP_CENTER);
            }

            // متن نام خانه
            Text nameText = new Text(getTileName(i));
            nameText.setFont(Font.font("Arial Narrow", FontWeight.BOLD, 9));
            nameText.setWrappingWidth(TILE_SIZE - 2);
            nameText.setTextAlignment(TextAlignment.CENTER);

            if (isSpecialTile(i) && isColorDark(getTileColor(i))) {
                nameText.setFill(Color.WHITE);
            }

            tileContainer.getChildren().add(nameText);

            if (!isSpecialTile(i)) {
                StackPane.setAlignment(nameText, Pos.BOTTOM_CENTER);
                StackPane.setMargin(nameText, new javafx.geometry.Insets(0, 0, 5, 0));
            }

            this.getChildren().add(tileContainer);
        }
    }

    private double[] getTileCoordinates(int index) {
        // محاسبه دقیق بدون گپ
        double endCoord = BOARD_SIZE - TILE_SIZE;
        double x = 0, y = 0;

        if (index < 10) { // پایین (راست به چپ)
            x = endCoord - (index * TILE_SIZE);
            y = endCoord;
        } else if (index < 20) { // چپ (پایین به بالا)
            x = 0;
            y = endCoord - ((index - 10) * TILE_SIZE);
        } else if (index < 30) { // بالا (چپ به راست)
            x = (index - 20) * TILE_SIZE;
            y = 0;
        } else { // راست (بالا به پایین)
            x = endCoord;
            y = (index - 30) * TILE_SIZE;
        }
        return new double[]{x, y};
    }

    // --- متدهای کمکی ثابت ---

    private void initPlayerTokens() {
        playerTokens = new Circle[4];
        Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE};

        for (int i = 0; i < 4; i++) {
            playerTokens[i] = new Circle(8, colors[i]);
            playerTokens[i].setStroke(Color.WHITE);
            playerTokens[i].setStrokeWidth(2);
            playerTokens[i].setVisible(false);
            this.getChildren().add(playerTokens[i]);
        }
    }

    public void updateTokenPosition(int playerIndex, int tileIndex) {
        if (playerIndex < 0 || playerIndex >= 4) return;
        playerTokens[playerIndex].setVisible(true);
        double[] coords = getTileCoordinates(tileIndex);

        double offsetX = (playerIndex % 2 == 0) ? TILE_SIZE * 0.25 : TILE_SIZE * 0.75;
        double offsetY = (playerIndex < 2) ? TILE_SIZE * 0.25 : TILE_SIZE * 0.75;

        playerTokens[playerIndex].setCenterX(coords[0] + offsetX);
        playerTokens[playerIndex].setCenterY(coords[1] + offsetY);
        playerTokens[playerIndex].toFront();
    }

    private String getTileName(int index) {
        String[] names = {
                "GO", "Medit.\nAve", "Comm.\nChest", "Baltic\nAve", "Income\nTax", "Reading\nRR", "Oriental\nAve", "Chance", "Vermont\nAve", "Conn.\nAve",
                "Jail", "St. C\nPlace", "Electric\nCo.", "States\nAve", "Virginia\nAve", "Penn.\nRR", "St. J\nPlace", "Comm.\nChest", "Tenn.\nAve", "NY\nAve",
                "Free\nParking", "KY\nAve", "Chance", "Ind.\nAve", "Ill.\nAve", "B. & O.\nRR", "Atl.\nAve", "Ventnor\nAve", "Water\nWorks", "Marvin\nGdn",
                "Go To\nJail", "Pacific\nAve", "NC\nAve", "Comm.\nChest", "Penn.\nAve", "Short\nLine", "Chance", "Park\nPlace", "Luxury\nTax", "Board\nWalk"
        };
        return (index >= 0 && index < names.length) ? names[index] : "";
    }

    private boolean isSpecialTile(int i) {
        return i % 10 == 0 || i == 4 || i == 38 || i == 5 || i == 15 || i == 25 || i == 35 || i == 12 || i == 28;
    }

    private Color getTileColor(int index) {
        if (index == 0) return Color.web("#CDEAC0");
        if (index == 10) return Color.web("#FFDAB9");
        if (index == 20) return Color.web("#ADD8E6");
        if (index == 30) return Color.web("#F08080");
        if (index == 1 || index == 3) return Color.web("#8B4513");
        if (index == 6 || index == 8 || index == 9) return Color.web("#87CEEB");
        if (index == 11 || index == 13 || index == 14) return Color.web("#FF69B4");
        if (index == 16 || index == 18 || index == 19) return Color.web("#FFA500");
        if (index == 21 || index == 23 || index == 24) return Color.web("#FF0000");
        if (index == 26 || index == 27 || index == 29) return Color.web("#FFFF00");
        if (index == 31 || index == 32 || index == 34) return Color.web("#008000");
        if (index == 37 || index == 39) return Color.web("#0000FF");
        return Color.LIGHTGRAY;
    }

    private boolean isColorDark(Color c) {
        return c.equals(Color.web("#8B4513")) || c.equals(Color.web("#FF0000")) || c.equals(Color.web("#008000")) || c.equals(Color.web("#0000FF"));
    }
}