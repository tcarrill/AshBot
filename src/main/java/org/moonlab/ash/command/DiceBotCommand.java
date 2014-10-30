package org.moonlab.ash.command;

import java.util.Random;

/**
 * Created by thomas on 9/4/14.
 */
public class DiceBotCommand implements BotCommand {
    private final Random random = new Random();
    private final StringBuilder builder = new StringBuilder();

    @Override
    public String getCommand() { return "roll"; }

    @Override
    public String getName() {
        return "DiceBotCommand";
    }

    @Override
    public String execute(String channel, String nick, String login, String host, String[] parameters) {
        if (parameters.length > 1) {
            String dice = parameters[1];
            if (dice.contains("d")) {
                String[] parsedDice = dice.split("d");
                int numOfDice = Integer.parseInt(parsedDice[0]);
                int numOfSides = Integer.parseInt(parsedDice[1]);

                builder.setLength(0);
                for (int i = 0; i < numOfDice; i++) {
                    int result = random.nextInt(numOfSides) + 1;
                    builder.append("d").append(i + 1).append(" [").append(result).append("] ");
                }

                return nick + ": " + builder;
            }
        }

        return null;
    }
}
