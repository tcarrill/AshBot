package org.moonlab.ash.command;

import org.moonlab.ash.AshBot;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by thomas on 9/12/14.
 */
public class MessageBotCommand extends BaseBotCommand implements IRCListener {
    protected Map<String, Map<String, String>> messageMap;

    public MessageBotCommand(AshBot ashBot) {
        super(ashBot);
        messageMap = new HashMap<>();
    }

    @Override
    public String getCommand() {
        return "message";
    }

    @Override
    public String getName() {
        return "MessageBotCommand";
    }

    @Override
    public String execute(String channel, String nick, String login, String host, String[] parameters) {
        if (parameters.length > 2) {

            StringBuilder builder = new StringBuilder();
            for (int i = 2; i < parameters.length; i++) {
                builder.append(parameters[i]).append(" ");
            }

            Map<String, String> messages = messageMap.get(channel);
            if (messages == null) {
                messages = new HashMap<>();
            }

            messages.put(parameters[1], builder.toString());
            messageMap.put(channel, messages);
            System.out.println(parameters[1]+": "+builder);
        }

        return null;
    }

    @Override
    public void onMessage(String channel, String nick, String login, String host, String message) {

    }

    @Override
    public void onJoin(String channel, String nick, String login, String host, String message) {
        if (messageMap != null && messageMap.size() > 0) {
            String msg = messageMap.get(channel).get(nick);
            if (msg != null) {
                ashBot.sendCommand(ircCommand.privMsg(channel, nick + ": This message was left for you '"+msg+"'"));
                messageMap.get(channel).remove(nick);
            }
        }
    }
}
