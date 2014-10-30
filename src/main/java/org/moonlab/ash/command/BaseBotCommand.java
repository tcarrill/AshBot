package org.moonlab.ash.command;

import org.moonlab.ash.AshBot;
import org.moonlab.ash.irc.IRCCommand;

/**
 * Created by thomas on 9/9/14.
 */
public abstract class BaseBotCommand implements BotCommand {
    protected AshBot ashBot;
    protected IRCCommand ircCommand;

    BaseBotCommand(AshBot ashBot) {
        this.ashBot = ashBot;
        ircCommand = ashBot.getIRCCommand();
    }
}
