package org.moonlab.ash.command;

import org.moonlab.ash.AshBot;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by thomas on 9/4/14.
 */
public class RemindBotCommand extends BaseBotCommand {
    private static final int MS = 1000;
    private static final int MIN_IN_MS = MS * 60;

    public RemindBotCommand(AshBot ashBot) {
        super(ashBot);
    }

    @Override
    public String getCommand() { return "remind"; }

    @Override
    public String getName() {
        return "RemindBotCommand";
    }

    @Override
    public String execute(final String channel, final String nick, String login, String host, final String[] parameters) {
        String time = parameters[1];
        int ms = new Integer(time) * MIN_IN_MS;

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (parameters.length > 2) {
                    StringBuilder builder = new StringBuilder();
                    for (int i = 2; i < parameters.length; i++) {
                        builder.append(parameters[i]).append(" ");
                    }
                    ashBot.sendCommand(ircCommand.privMsg(channel, nick + ": " + builder));
                } else {
                    ashBot.sendCommand(ircCommand.privMsg(channel,  nick + ": This is your reminder!"));
                }
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, ms);

        return nick + ": Okay, I'll remind you in " + time + " minute(s)";
    }
}
