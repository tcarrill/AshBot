package org.moonlab.ash.irc;

/**
 * Created with IntelliJ IDEA.
 * User: thomas
 * Date: 11/19/12
 * Time: 9:43 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IRCCommand {
    public static final String END_OF_MOTD = "376";
    public static final String NICKNAME_TAKEN = "433";
    public static final String PING = "PING";
    public static final String PONG = "PONG";
    public static final String PRIVMSG = "PRIVMSG";
    public static final String NOTICE = "NOTICE";
    public static final String USER = "USER";
    public static final String NICK = "NICK";
    public static final String JOIN = "JOIN";
    public static final String PART = "PART";
    public static final String MODE = "MODE";
    public static final String QUIT = "QUIT";
    public static final String TOPIC = "TOPIC";
    public static final String VERSION = "\u0001VERSION\u0001";
    public static final String END = "\r\n";

    public String registerNick(String nick);
    public String registerUser(String user, String name);
    public String joinChannel(String channel);
    public String leaveChannel(String channel, String message);
    public String pong(String host);
    public String privMsg(String channel, String message);
    public String changeChannelTopic(String channel, String topic);
    public String notice(String channel, String message);
    public String giveOp(String channel, String nick);
    public String quit(String message);
}
