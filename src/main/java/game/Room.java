package game;

import model.UserProfile;

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

    public Room() {
        players = new ArrayList<>();
    }

    public void onNewPlayer(Player player) {
        players.add(player);
    }

    public void onPlayerReady(Player player) {
        player.setReady(true);
    }

    public void onPlayerDisconnect(Player player) {
        players.remove(player);
    }

    public Player getPlayerByUser(UserProfile userProfile) {
        for(Player player : players) {
            if (player.getUserProfile().getLogin().equals(userProfile.getLogin())) {
                return player;
            }
        }
        return null;
    }

    public int getPlayerCount() {
        return players.size();
    }

    private void startGame() {
        roomState = RoomState.GAME;
    }

    public RoomState getRoomState() {
        return roomState;
    }
}
