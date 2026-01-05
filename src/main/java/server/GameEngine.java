package server;

import ds.list.Node;
import model.GameState;
import model.Player;
import model.Property;
import model.Tile;
import model.TileType;

public class GameEngine {
    private GameState gameState;
    private TurnManager turnManager;

    public GameEngine(TurnManager turnManager) {
        this.gameState = GameState.getInstance();
        this.turnManager = turnManager;
    }

    public synchronized String executeCommand(int playerId, String command) {
        int playerIndex = playerId - 1;

        if (!gameState.isGameStarted()) {
            return "WAIT: Game has not started yet.";
        }

        if (!turnManager.isTurn(playerIndex)) {
            return "ERROR: It is not your turn.";
        }

        if (command.startsWith("ROLL")) {
            return rollDice(playerId);
        } else if (command.startsWith("BUY")) {
            return buyProperty(playerId);
        } else if (command.startsWith("END")) {
            turnManager.nextTurn();
            return "SUCCESS: Turn ended. Next player: " + (turnManager.getCurrentPlayerIndex() + 1);
        }

        return "ERROR: Unknown command.";
    }

    private String rollDice(int playerId) {
        Player player = gameState.getPlayer(playerId);

        int dice = (int) (Math.random() * 6) + 1 + (int) (Math.random() * 6) + 1;

        Node currentBoardNode = findNodeById(player.getPosition());
        Node newNode = gameState.getBoard().move(currentBoardNode, dice);
        Tile newTile = (Tile) newNode.data;

        player.setPosition(newTile.getId());

        String result = "ROLLED " + dice + ". Moved to " + newTile.getName();

        if (newTile.getType() == TileType.PROPERTY) {
            Property prop = (Property) newTile;
            if (prop.getOwnerId() == -1) {
                result += " (Price: " + prop.getPrice() + ")";
            } else if (prop.getOwnerId() != playerId) {
                int rent = prop.getRent();
                player.setMoney(player.getMoney() - rent);
                Player owner = gameState.getPlayer(prop.getOwnerId());
                if (owner != null) {
                    owner.setMoney(owner.getMoney() + rent);
                }
                result += " (Paid Rent: " + rent + ")";
            }
        } else if (newTile.getType() == TileType.GO) {
            player.setMoney(player.getMoney() + 200);
            result += " (Collected $200)";
        }

        return result;
    }

    private String buyProperty(int playerId) {
        Player player = gameState.getPlayer(playerId);
        Node currentNode = findNodeById(player.getPosition());
        Tile tile = (Tile) currentNode.data;

        if (tile.getType() == TileType.PROPERTY) {
            Property prop = (Property) tile;
            if (prop.getOwnerId() == -1) {
                if (player.getMoney() >= prop.getPrice()) {
                    player.setMoney(player.getMoney() - prop.getPrice());
                    prop.setOwnerId(playerId);
                    return "SUCCESS: Bought " + prop.getName();
                }
                return "ERROR: Not enough money.";
            }
            return "ERROR: Property already owned.";
        }
        return "ERROR: This tile is not for sale.";
    }

    private Node findNodeById(int tileId) {
        Node current = gameState.getBoard().getHead();
        if (current == null) return null;

        do {
            Tile t = (Tile) current.data;
            if (t.getId() == tileId) return current;
            current = current.next;
        } while (current != gameState.getBoard().getHead());

        return gameState.getBoard().getHead();
    }
}