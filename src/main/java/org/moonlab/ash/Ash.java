package org.moonlab.ash;

import org.moonlab.ash.command.BotCommand;
import org.moonlab.ash.command.IRCListener;

import java.util.Map;

public class Ash extends AshBot {

    public Ash(Client client) {
        super(client);
        admins.add("tcarrill");
    }

    public void onJoin(String channel, String nick, String login, String host) {
        if (admins.contains(nick)) {
            sendCommand(ircCommand.giveOp(channel, nick));
        }

        for (Map.Entry<String, BotCommand> entry : commands.entrySet()) {
            BotCommand command = entry.getValue();
            if (command instanceof IRCListener) {
                ((IRCListener) command).onJoin(channel, nick, login, host, null);
            }
        }
    }

    public void onEndMotd() {
        joinChannel(client.getChannel());
    }

    public void onPrivMsg(String nick, String login, String host, String message) {
        if (admins.contains(nick)) {
            if (message.startsWith("join")) {
                String channel = message.split(" ")[1];
                if (joinChannel(channel)) {
                    sendCommand(ircCommand.privMsg(nick, "Okay, I joined " + channel));
                } else {
                    sendCommand(ircCommand.privMsg(nick, "Sorry, I could not join " + channel));
                }
            } else if (message.startsWith("leave")) {
                String[] parsed = message.split(" ");
                if (parsed.length > 1) {
                    String channel = parsed[1];
                    String leaveMessage = "Leaving...";

                    if (parsed.length > 2) {
                        leaveMessage = Utility.buildStringFromArray(parsed, 2);
                    }

                    if (leaveChannel(channel, leaveMessage)) {
                        sendCommand(ircCommand.privMsg(nick, "I left " + channel));
                    } else {
                        sendCommand(ircCommand.privMsg(nick, "Sorry, I could not leave " + channel));
                    }
                }
            } else if (message.startsWith("quit")) {
                String[] parsed = message.split(" ");
                String quitMessage = "Leaving...";

                if (parsed.length > 1) {
                    quitMessage = Utility.buildStringFromArray(parsed, 1);
                }
                sendCommand(ircCommand.quit(quitMessage));
                System.exit(0);
            } else if (message.startsWith("add admin")) {
                String[] parsed = message.split(" ");
                if (parsed.length > 2) {
                    if (addAdmin(parsed[2])) {
                        sendCommand(ircCommand.privMsg(nick, "Added " + parsed[2] + " as an admin"));
                    } else {
                        sendCommand(ircCommand.privMsg(nick, "Could not add " + parsed[2] + " as an admin"));
                    }
                }
            } else if (message.startsWith("remove admin")) {
                if (admins.size() == 1) {
                    sendCommand(ircCommand.privMsg(nick, "There must be at least 1 admin"));
                } else {
                    String[] parsed = message.split(" ");
                    if (parsed.length > 2) {
                        if (removeAdmin(parsed[2])) {
                            sendCommand(ircCommand.privMsg(nick, "Removed " + parsed[2] + " from admin list"));
                        } else {
                            sendCommand(ircCommand.privMsg(nick, "Could not remove " + parsed[2] + " from admin list"));
                        }
                    }
                }

            } else if (message.startsWith("list admin")) {
                StringBuilder builder = new StringBuilder();
                int i = 0;
                for (String admin : admins) {
                    builder.append(admin);
                    i++;

                    if (i < admins.size()) {
                        builder.append(", ");
                    }
                }
                String admins = "Current admins: " + builder;
                sendCommand(ircCommand.privMsg(nick, admins));
                System.out.println(admins);
            }
        }
    }
}
