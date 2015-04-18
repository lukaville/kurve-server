package websocket.message.serializer;

import com.google.gson.*;
import game.GameField;
import game.Player;
import model.Snake.Snake;
import websocket.GameWebSocketHandler;
import websocket.message.StartGameMessage;

import java.lang.reflect.Type;

/**
 * nickolay, 18.04.15.
 */
public class StartGameMessageSerializer implements JsonSerializer<StartGameMessage> {
    public JsonElement serialize(StartGameMessage src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject responseObject = new JsonObject();
        responseObject.addProperty("code", GameWebSocketHandler.MessageType.CODE_INIT_STATE_RESPONSE.ordinal());
        responseObject.addProperty("FPS", GameField.FPS);
        responseObject.addProperty("width", GameField.width);
        responseObject.addProperty("height", GameField.height);
        responseObject.addProperty("speed", Snake.defaultSpeed);
        responseObject.addProperty("angleSpeed", Snake.defaultAngleSpeed);
        responseObject.addProperty("partLength", Snake.defaultPartLength);
        responseObject.addProperty("holeLength", Snake.defaultHoleLength);
        responseObject.addProperty("myId", src.getPlayerId());
        JsonArray playersArray = new JsonArray();
        for(int i = 0; i < src.getRoom().getPlayerCount(); ++i) {
            Player player = src.getRoom().getPlayers().get(i);
            JsonElement playerObject = context.serialize(player);
            playersArray.add(playerObject);
        }

        responseObject.add("players", playersArray);

        return responseObject;
    }
}