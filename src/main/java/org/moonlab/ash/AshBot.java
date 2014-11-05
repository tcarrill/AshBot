package org.moonlab.ash;

import org.moonlab.ash.command.*;
import org.moonlab.ash.irc.IRCCommand;
import org.moonlab.ash.irc.IRCCommandImpl;
import org.moonlab.ash.parser.Parser;
import org.moonlab.ash.parser.ParserImpl;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by thomas on 9/3/14.
 */
public abstract class AshBot implements Runnable {
    protected boolean isDebug = false;
    protected Client client;
    protected IRCCommand ircCommand;
    protected Parser parser;
    protected List<String> joinedChannels;
    protected Map<String, BotCommand> commands;
    protected List<String> admins = new ArrayList<String>();

    private BufferedReader in;
    private BufferedWriter out;

    public AshBot(Client client) {
        this(client, new ParserImpl());
    }

    public AshBot(Client client, Parser parser) {
        this.client = client;
        this.parser = parser;
        ircCommand = new IRCCommandImpl();

        registerCommand(new TimeBotCommand());
        registerCommand(new DiceBotCommand());
        registerCommand(new RemindBotCommand(this));
        registerCommand(new SeenBotCommand(this));
        registerCommand(new UptimeBotCommand());
        registerCommand(new MessageBotCommand(this));

    }

    public Client getClient() {
        return client;
    }

    public IRCCommand getIRCCommand() { return ircCommand; }

    public void setIsDebug(boolean isDebug) {
        this.isDebug = isDebug;
    }

    public void registerCommand(BotCommand command) {
        if (commands == null) {
            commands = new HashMap<>();
        }

        commands.put(command.getCommand(), command);
    }

    protected void start() throws IOException {
        if (client.isActive()) {
            System.out.println("Bot is already active");
            return;
        }

        try {
            Socket socket = new Socket(client.getServer(), client.getPort());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            if (socket.isConnected()) {
                System.out.println("Connected to " + client.getServer());
                sendCommand(ircCommand.registerNick(client.getNick()));
                sendCommand(ircCommand.registerUser(client.getUser(), client.getName()));

                client.setActive(true);
                new Thread(this).start();
            }
        } catch (UnknownHostException e) {
            System.out.println("Unknown host: " + client.getServer());
        }
    }

    public void run() {
        String buffer;
        while (client.isActive()) {
            try {
                while ((buffer = in.readLine()) != null) {
                    if (isDebug) {
                        System.out.println(buffer);
                    }

                    delegateCommands(parser.parse(buffer));
                }
            } catch (IOException e) {
                System.out.println(e.toString());
            }
        }
    }

    private void delegateCommands(Map<String, String> parsedMsg) {
        String prefix = parsedMsg.get(Constants.PREFIX);
        String command = parsedMsg.get(Constants.COMMAND);
        String parameters = parsedMsg.get(Constants.PARAMETERS);
        String trailing = parsedMsg.get(Constants.TRAILING);

        for (Map.Entry<String, BotCommand> entry : commands.entrySet()) {
            if (entry.getValue() instanceof IRCListener) {
                Map<String, String> prefixMap = parser.parsePrefix(prefix);
                if (prefixMap != null) {
                    ((IRCListener) entry.getValue()).onMessage(parameters, prefixMap.get(Constants.NICK), prefixMap.get(Constants.LOGIN), prefixMap.get(Constants.HOST), trailing);
                }
            }
        }

        if (command.equalsIgnoreCase(IRCCommand.PING)) {
            onPing(trailing);
        } else if (command.equalsIgnoreCase(IRCCommand.JOIN)) {
            Map<String, String> prefixMap = parser.parsePrefix(prefix);
            onJoin(parameters, prefixMap.get(Constants.NICK), prefixMap.get(Constants.LOGIN), prefixMap.get(Constants.HOST));

        } else if (command.equalsIgnoreCase(IRCCommand.PART)) {
            Map<String, String> prefixMap = parser.parsePrefix(prefix);
            onPart(parameters, prefixMap.get(Constants.NICK), prefixMap.get(Constants.LOGIN), prefixMap.get(Constants.HOST));

        } else if (command.equalsIgnoreCase(IRCCommand.END_OF_MOTD)) {
            onEndMotd();

        } else if (command.equalsIgnoreCase(IRCCommand.NICKNAME_TAKEN)) {
            onNickTaken();

        } else if (command.equalsIgnoreCase(IRCCommand.TOPIC)) {
            Map<String, String> prefixMap = parser.parsePrefix(prefix);
            onTopicChange(parameters, prefixMap.get(Constants.NICK), prefixMap.get(Constants.LOGIN), prefixMap.get(Constants.HOST), trailing);

        } else if (command.equalsIgnoreCase(IRCCommand.PRIVMSG)) {
            Map<String, String> prefixMap = parser.parsePrefix(prefix);
            if (parameters.equals(client.getNick())) {
                if (trailing.equals(IRCCommand.VERSION)) {
                    onVersion(prefixMap.get(Constants.NICK), prefixMap.get(Constants.LOGIN), prefixMap.get(Constants.HOST));
                } else {
                    onPrivMsg(prefixMap.get(Constants.NICK), prefixMap.get(Constants.LOGIN), prefixMap.get(Constants.HOST), trailing);
                }
            } else {
                onChannelMsg(parameters, prefixMap.get(Constants.NICK), prefixMap.get(Constants.LOGIN), prefixMap.get(Constants.HOST), trailing);
            }
        }
    }

    public boolean sendCommand(String command) {
        if (command != null) {
            try {
                if (isDebug) {
                    System.out.println(command);
                }
                out.write(command);
                out.flush();
                return true;
            } catch (IOException e) {
                System.out.println(e.toString());
            }
        }

        return false;
    }

    public void onPing(String host) {
        sendCommand(ircCommand.pong(host));
    }

    public void onJoin(String channel, String nick, String login, String host) {
    }

    public void onPart(String channel, String nick, String login, String host) {
    }

    public void onEndMotd() {
    }

    public void onPrivMsg(String nick, String login, String host, String message) {
    }

    public void onNickTaken() {
        String nick = client.getNick();
        if (nick.matches("^.+?\\d$"))  {
            int number = Integer.parseInt(nick.substring(nick.length() - 1, nick.length()));
            client.setNick(nick.substring(0, nick.length() - 1) + ++number);
        } else {
            client.setNick(client.getNick() + "0");
        }

        sendCommand(ircCommand.registerNick(client.getNick()));
    }

    public void onVersion(String nick, String login, String host) {
        sendCommand(ircCommand.notice(nick, "AshBot v1.0.0"));
    }

    public void onChannelMsg(String channel, String nick, String login, String host, String message) {
        if (isAddressedToMe(message)) {
            String[] parameters = message.split(": ")[1].split(" ");
            BotCommand command = commands.get(parameters[0]);
            if (command != null) {
                String resultMessage = command.execute(channel, nick, login, host, parameters);
                sendCommand(ircCommand.privMsg(channel, resultMessage));
            }
        }
    }

    public void onTopicChange(String channel, String nick, String login, String host, String message) {

    }

    public boolean isAddressedToMe(String message) {
        return message.startsWith(client.getNick() + ": ");
    }

    public boolean joinChannel(String channel) {
        if (sendCommand(ircCommand.joinChannel(channel))) {
            if (joinedChannels == null) {
                joinedChannels = new ArrayList<>();
            }

            joinedChannels.add(channel);

            return true;
        }

        return false;
    }

    public boolean leaveChannel(String channel, String message) {
        if (joinedChannels.contains(channel)) {
            if (sendCommand(ircCommand.leaveChannel(channel, message))) {
                joinedChannels.remove(channel);
                return true;
            }
        }

        return false;
    }

    public boolean addAdmin(String nick) {
        return admins.add(nick);
    }

    public boolean removeAdmin(String nick) {
        return admins.remove(nick);
    }
}
