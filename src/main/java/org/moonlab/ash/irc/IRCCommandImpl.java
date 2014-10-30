package org.moonlab.ash.irc;

/**
 * Created with IntelliJ IDEA.
 * User: thomas
 * Date: 11/19/12
 * Time: 9:45 AM
 * To change this template use File | Settings | File Templates.
 */
public class IRCCommandImpl implements IRCCommand {

    @Override
    public String registerNick(String nick) {
        return NICK + " " + nick + END;
    }

    @Override
    public String registerUser(String user, String name) {
        return USER + " " + user + " - - :" + name + END;
    }

    @Override
    public String joinChannel(String channel) {
        return JOIN + " " + channel + END;
    }

    @Override
    public String leaveChannel(String channel, String message) {
        return PART + " " + channel + " " + message + END;
    }

    @Override
    public String pong(String host) {
        return PONG + " :" + host + END;
    }

    @Override
    public String quit(String message) {
        return QUIT + " :" + message + END;
    }

    @Override
    public String privMsg(String channel, String message) {
        return PRIVMSG + " " + channel + " :" + message + END;
    }

    @Override
    public String giveOp(String channel, String nick) {
        return MODE + " " + channel + " +o " + nick + END;
    }

    @Override
    public String notice(String channel, String message) {
        return NOTICE  + " " + channel + " :" + message + END;
    }

    @Override
    public String changeChannelTopic(String channel, String topic) {
        return TOPIC + " " + channel + " :" + topic + END;
    }
}
