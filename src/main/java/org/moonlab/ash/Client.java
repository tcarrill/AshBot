package org.moonlab.ash;

/**
 * Created with IntelliJ IDEA.
 * User: thomas
 * Date: 11/18/12
 * Time: 4:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class Client {
    private String server;
    private String nick;
    private String user;
    private String name;
    private String channel;
    private int port;
    private boolean isActive;

    public Client() {

    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
