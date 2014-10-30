package org.moonlab.ash.command;

import org.moonlab.ash.AshBot;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by thomas on 9/12/14.
 */
public class SeenBotCommand extends BaseBotCommand implements IRCListener {
    protected Map<String, String> lastSeenMap;

    public SeenBotCommand(AshBot ashBot) {
        super(ashBot);
        lastSeenMap = new HashMap<>();
    }

    @Override
    public String getCommand() {
        return "seen";
    }

    @Override
    public String getName() {
        return "SeenBotCommand";
    }

    @Override
    public String execute(String channel, String nick, String login, String host, String[] parameters) {
        if (parameters.length == 2) {
            String message = lastSeenMap.get(parameters[1].toLowerCase());
            return message != null ? message : parameters[1] + " has not been seen";
        }

        return null;
    }

    @Override
    public void onMessage(String channel, String nick, String login, String host, String message) {
        Date date = new Date();
        String lastSeenMessage = nick + " last seen in " + channel + " at " + date + " saying " + message;
        lastSeenMap.put(nick.toLowerCase(), lastSeenMessage);
    }
}
