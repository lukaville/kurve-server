package game;

import model.UserProfile;
import websocket.message.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * nickolay, 21.02.15.
 */
public class Room {
    enum RoomState {
        WAITING,
        GAME
    }


    private List<Player> players;
    private RoomState roomState = RoomState.WAITING;
    private int readyPlayersCount;
    private GameField gameField;

    private int getPointsByDeathId(int deathId) {
        int[] deathPoints = {1, 2, 3, 4, 5};
        if(deathId >= 0 && deathId < 5) return deathPoints[deathId];
        return 0;
    }

    public void onPlayerDeath(int playerId, int deathId){
       players.get(playerId).setPoints(getPointsByDeathId(deathId));
    }

    public Room() {
        players = new ArrayList<>();
        readyPlayersCount = 0;
    }

    public void onNewPlayer(Player player) {

        if(roomState != RoomState.WAITING) return;

        players.add(player);
        player.sendMessage(new RoomPlayersMessage(this));
        broadcastMessageExceptUser(new ConnectedPlayerMessage(player,
                getPlayerIdByUser(player.getUserProfile())), player.getUserProfile());
    }

    public void onPlayerReady(Player player, boolean isReady) {
        if(roomState != RoomState.WAITING) return;
        player.setReady(isReady);
        if(isReady) readyPlayersCount++;
            else readyPlayersCount--;

        if (readyPlayersCount == players.size()) {
            startGame();
            System.out.println("**started game**");
        } else {
            broadcastMessageExceptUser(
                    new ReadyMessage(player, isReady),
                    player.getUserProfile()
            );
        }
    }

    public void onKeyEvent(boolean isLeft, boolean isUp, UserProfile user) {
        if(roomState != RoomState.GAME) return;

        int sender = players.indexOf(getPlayerByUser(user));
        System.out.println(sender);
        broadcastMessageExceptUser(
                new KeyMessage(isLeft, isUp, sender), user
        );
        if(isLeft){
            if(isUp){
                gameField.leftUp(sender);
            } else {
                gameField.leftDown(sender);
            }
        } else {
            if(isUp){
                gameField.rightUp(sender);
            } else {
                gameField.rightDown(sender);
            }
        }

    }
    public void endGame(){
        broadcastMessage(new GameOverMessage(this));
    }
    public void startGame() {
        if(roomState != RoomState.WAITING) return;
        roomState = RoomState.GAME;
        gameField = new GameField(players.size(), this);
        for(int i = 0; i < players.size(); i++) {
            players.get(i).sendMessage(new StartGameMessage(this, i));
        }

        gameField.play();
    }

    public void onPlayerDisconnect(Player player) {
        broadcastMessageExceptUser(
                new DisconnectedPlayerMessage(player),
                player.getUserProfile()
        );
        players.remove(player);
    }

    public void broadcastMessage(Message message) {
        for(Player player : players) {
            player.sendMessage(message);
        }
    }

    public void broadcastMessageExceptUser(Message message, UserProfile user) {
        for(Player player : players) {
            String roomUserLogin = player.getUserProfile().getLogin();
            if (!roomUserLogin.equals(user.getLogin())) {
                player.sendMessage(message);
            }
        }
    }

    public Player getPlayerByUser(UserProfile userProfile) {
        for(Player player : players) {
            if (player.getUserProfile().getLogin().equals(userProfile.getLogin())) {
                return player;
            }
        }
        return null;
    }

    public int getPlayerIdByUser(UserProfile userProfile) {
        int index = 0;
        for(Player player : players) {
            if (player.getUserProfile().getLogin().equals(userProfile.getLogin())) {
                return index;
            }
            index++;
        }
        return -1;
    }

    public boolean isColorUsed(Color color) {
        for(Player p : players) {
            if (p.getColor().equals(color)) {
                return true;
            }
        }
        return false;
    }

    public int getPlayerCount() {
        return players.size();
    }

    public int getReadyPlayerCount() {
        int readyUserCount = 0;
        for(Player p : players) {
            if (p.isReady()) {
                readyUserCount++;
            }
        }
        return readyUserCount;
    }

    public List<Player> getPlayers() {
        return players;
    }


    public RoomState getRoomState() {
        return roomState;
    }
}
