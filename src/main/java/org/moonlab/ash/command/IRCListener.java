package org.moonlab.ash.command;

/**
 * Created by thomas on 9/17/14.
 */
public interface IRCListener {
    public void onMessage(String channel, String nick, String login, String host, String message);
    public void onJoin(String channel, String nick, String login, String host, String message);
}
