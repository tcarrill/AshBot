package org.moonlab.ash.command;

import java.util.Date;

/**
 * Created by thomas on 9/4/14.
 */
public class TimeBotCommand implements BotCommand {

    @Override
    public String getCommand() {
        return "time";
    }

    @Override
    public String getName() {
        return "TimeBotCommand";
    }

    @Override
    public String execute(String channel, String nick, String login, String host, String[] parameters) {
        return new Date().toString();
    }
}
