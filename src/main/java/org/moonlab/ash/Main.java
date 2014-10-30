package org.moonlab.ash;

import java.io.IOException;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: thomas
 * Date: 11/18/12
 * Time: 5:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    public static void main(String args[]) {
        System.out.println("Starting program.");
        try {
            Properties properties = new Properties();
            properties.load(Main.class.getResourceAsStream("/client.properties"));

            Client client = new Client();
            client.setServer(properties.getProperty(Constants.SERVER));
            client.setPort(new Integer(properties.getProperty(Constants.PORT)));
            client.setNick(properties.getProperty(Constants.NICK));
            client.setName(properties.getProperty(Constants.NAME));
            client.setUser(properties.getProperty(Constants.USER));
            client.setChannel(properties.getProperty(Constants.CHANNEL));

            Ash ash = new Ash(client);
            ash.setIsDebug(true);
            ash.start();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }
}
