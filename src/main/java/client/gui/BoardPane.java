package client.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
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
    private final int TILE_SIZE = 60;
    private final int BOARD_SIZE = 660; // 11 * 60 = 660 (بدون فاصله)

    public BoardPane() {
        this.setPrefSize(BOARD_SIZE, BOARD_SIZE);
        this.setStyle("-fx-background-color: #FDF5E6; -fx-border-color: #444; -fx-border-width: 2;");

        drawCenterInfo();
        drawBoard();
        initPlayerTokens();
    }

    private void drawCenterInfo() {
        // پس‌زمینه وسط
        Rectangle center = new Rectangle(TILE_SIZE, TILE_SIZE, BOARD_SIZE - 2 * TILE_SIZE, BOARD_SIZE - 2 * TILE_SIZE);
        center.setFill(Color.web("#FAF0E6"));
        center.setStroke(Color.LIGHTGRAY);
        this.getChildren().add(center);

        // متن لوگو وسط
        VBox centerBox = new VBox(5);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setPrefSize(300, 100);

        // محاسبه دستی وسط صفحه برای قرار دادن VBox
        centerBox.setLayoutX((BOARD_SIZE - 300) / 2.0);
        centerBox.setLayoutY((BOARD_SIZE - 100) / 2.0);

        Text title = new Text("MONOPOLY");
        title.setFont(Font.font("Impact", 50));
        title.setFill(Color.web("#C21807"));
        title.setStroke(Color.BLACK);
        title.setStrokeWidth(1);

        Text subTitle = new Text("Student Project Edition");
        subTitle.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        subTitle.setFill(Color.GRAY);

        centerBox.getChildren().addAll(title, subTitle);
        this.getChildren().add(centerBox);
    }

    private void drawBoard() {
        for (int i = 0; i < 40; i++) {
            double[] coords = getTileCoordinates(i);

            StackPane tileContainer = new StackPane();
            tileContainer.setLayoutX(coords[0]);
            tileContainer.setLayoutY(coords[1]);
            tileContainer.setPrefSize(TILE_SIZE, TILE_SIZE);
            tileContainer.setStyle("-fx-border-color: black; -fx-border-width: 0.5;");

            // 1. پس‌زمینه خانه
            Rectangle bg = new Rectangle(TILE_SIZE, TILE_SIZE);
            bg.setFill(getTileBackgroundColor(i));
            bg.setStroke(null);

            tileContainer.getChildren().add(bg);

            // 2. نوار رنگی (فقط برای املاک)
            if (isProperty(i)) {
                Rectangle colorBar = new Rectangle(TILE_SIZE, TILE_SIZE / 4);
                colorBar.setFill(getTileColor(i));
                colorBar.setStroke(Color.BLACK);
                colorBar.setStrokeWidth(0.5);
                tileContainer.getChildren().add(colorBar);
                StackPane.setAlignment(colorBar, Pos.TOP_CENTER);
            }

            // 3. محتوا (نام + آیکون)
            VBox content = new VBox(0);
            content.setAlignment(Pos.CENTER);
            content.setPrefSize(TILE_SIZE, TILE_SIZE);

            // اگر ملک است، کمی فاصله از بالا (برای نوار رنگی)
            if (isProperty(i)) {
                content.setPadding(new javafx.geometry.Insets(15, 0, 0, 0));
            }

            // الف) آیکون (برای خانه‌های خاص) یا قیمت (برای املاک)
            if (!getTileIcon(i).isEmpty()) {
                Label iconLbl = new Label(getTileIcon(i));
                iconLbl.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
                // رنگ‌بندی آیکون‌ها
                if (i == 7 || i == 22 || i == 36) iconLbl.setTextFill(Color.ORANGERED); // ?
                else if (i == 2 || i == 17 || i == 33) iconLbl.setTextFill(Color.DEEPSKYBLUE); // Box
                else if (isRailroad(i)) iconLbl.setTextFill(Color.BLACK); // Train
                else if (i == 12) iconLbl.setTextFill(Color.GOLDENROD); // Lamp
                else if (i == 28) iconLbl.setTextFill(Color.DARKBLUE); // Water

                content.getChildren().add(iconLbl);
            }

            // ب) نام خانه
            Label nameLbl = new Label(getTileName(i));
            nameLbl.setFont(Font.font("Arial Narrow", FontWeight.BOLD, 9));
            nameLbl.setWrapText(true);
            nameLbl.setTextAlignment(TextAlignment.CENTER);
            nameLbl.setMaxWidth(TILE_SIZE - 2);

            // تنظیم رنگ متن برای خانه‌های تیره (گوشه‌ها)
            if (isCorner(i)) {
                nameLbl.setTextFill(Color.BLACK); // گوشه‌ها الان روشن‌تر شده‌اند
            }

            content.getChildren().add(nameLbl);
            tileContainer.getChildren().add(content);

            this.getChildren().add(tileContainer);
        }
    }

    // --- آیکون‌های گرافیکی (یونیکد) ---
    private String getTileIcon(int i) {
        if (i == 2 || i == 17 || i == 33) return "📦"; // Community Chest
        if (i == 7 || i == 22 || i == 36) return "?";   // Chance
        if (i == 5 || i == 15 || i == 25 || i == 35) return "🚆"; // Railroad
        if (i == 12) return "💡"; // Electric
        if (i == 28) return "💧"; // Water
        if (i == 38 || i == 4) return "💎"; // Tax
        if (i == 10) return "⛓️"; // Jail icon (optional)
        if (i == 20) return "🚗"; // Parking
        if (i == 30) return "👮"; // Go to Jail
        if (i == 0) return "⬅";  // GO arrow
        return "";
    }

    // --- تشخیص نوع خانه ---
    private boolean isProperty(int i) {
        // همه خانه‌ها به جز گوشه‌ها، شانس، صندوق، مالیات، راه‌آهن و یوتیلیتی
        return !isCorner(i) && !isSpecial(i) && !isRailroad(i) && !isUtility(i);
    }

    private boolean isCorner(int i) { return i % 10 == 0; }
    private boolean isRailroad(int i) { return i == 5 || i == 15 || i == 25 || i == 35; }
    private boolean isUtility(int i) { return i == 12 || i == 28; }
    private boolean isSpecial(int i) {
        return i == 2 || i == 7 || i == 17 || i == 22 || i == 33 || i == 36 || i == 4 || i == 38;
    }

    // --- رنگ پس‌زمینه کل مربع ---
    private Color getTileBackgroundColor(int i) {
        if (isCorner(i)) return Color.web("#D8E6F3"); // گوشه‌ها آبی خیلی کمرنگ
        if (isRailroad(i)) return Color.web("#F0F0F0"); // راه‌آهن خاکستری خیلی روشن
        if (isUtility(i)) return Color.WHITE;
        return Color.WHITE; // بقیه سفید (چون نوار رنگی دارند)
    }

    // --- رنگ نوار بالای املاک ---
    private Color getTileColor(int index) {
        if (index == 1 || index == 3) return Color.web("#8B4513");
        if (index == 6 || index == 8 || index == 9) return Color.web("#87CEEB");
        if (index == 11 || index == 13 || index == 14) return Color.web("#FF69B4");
        if (index == 16 || index == 18 || index == 19) return Color.web("#FFA500");
        if (index == 21 || index == 23 || index == 24) return Color.web("#FF0000");
        if (index == 26 || index == 27 || index == 29) return Color.web("#FFFF00");
        if (index == 31 || index == 32 || index == 34) return Color.web("#008000");
        if (index == 37 || index == 39) return Color.web("#0000FF");
        return Color.TRANSPARENT;
    }

    // --- مختصات ---
    private double[] getTileCoordinates(int index) {
        double endCoord = BOARD_SIZE - TILE_SIZE;
        double x = 0, y = 0;
        if (index < 10) { x = endCoord - (index * TILE_SIZE); y = endCoord; }
        else if (index < 20) { x = 0; y = endCoord - ((index - 10) * TILE_SIZE); }
        else if (index < 30) { x = (index - 20) * TILE_SIZE; y = 0; }
        else { x = endCoord; y = (index - 30) * TILE_SIZE; }
        return new double[]{x, y};
    }

    private String getTileName(int index) {
        String[] names = {
                "GO", "Medit.\nAve", "Comm.\nChest", "Baltic\nAve", "Income\nTax", "Reading\nRR", "Oriental\nAve", "Chance", "Vermont\nAve", "Conn.\nAve",
                "JAIL", "St. C\nPlace", "Electric\nCo.", "States\nAve", "Virginia\nAve", "Penn.\nRR", "St. J\nPlace", "Comm.\nChest", "Tenn.\nAve", "NY\nAve",
                "Free\nPark", "KY\nAve", "Chance", "Ind.\nAve", "Ill.\nAve", "B. & O.\nRR", "Atl.\nAve", "Ventnor\nAve", "Water\nWorks", "Marvin\nGdn",
                "Go To\nJail", "Pacific\nAve", "NC\nAve", "Comm.\nChest", "Penn.\nAve", "Short\nLine", "Chance", "Park\nPlace", "Luxury\nTax", "Board\nWalk"
        };
        return (index >= 0 && index < names.length) ? names[index] : "";
    }

    // --- مهره‌ها (بدون تغییر) ---
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
}