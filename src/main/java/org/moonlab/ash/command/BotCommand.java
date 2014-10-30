package org.moonlab.ash.command;

/**
 * Created with IntelliJ IDEA.
 * User: thomas
 * Date: 11/19/12
 * Time: 2:02 PM
 * To change this template use File | Settings | File Templates.
 */
public interface BotCommand {
    public String getCommand();
    public String getName();
    public String execute(String channel, String nick, String login, String host, String[] parameters);
}
