package org.moonlab.ash.command;

import org.apache.commons.lang3.time.DurationFormatUtils;

import java.lang.management.ManagementFactory;

/**
 * Created by thomas on 9/15/14.
 */
public class UptimeBotCommand implements BotCommand {

    @Override
    public String getCommand() {
        return "uptime";
    }

    @Override
    public String getName() {
        return "UptimeBotCommand";
    }

    @Override
    public String execute(String channel, String nick, String login, String host, String[] parameters) {
        return DurationFormatUtils.formatDurationWords(ManagementFactory.getRuntimeMXBean().getUptime(), true, true);
    }
}
