package org.moonlab.ash.command;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * Created by thomas on 9/4/14.
 */
public class WeatherBotCommand implements BotCommand {

    @Override
    public String getCommand() { return "weather"; }

    @Override
    public String getName() {
        return "WeatherBotCommand";
    }

    @Override
    public String execute(String channel, String nick, String login, String host, String[] parameters) {
        if (parameters.length == 2) {
            Client client = Client.create();

            String city = parameters[1];
            String param = "q=" + city + ",us";
            String apiKey = "&apikey=96163c78b4718d5d6b885774b003e7cb";
            String units = "&units=imperial";

            WebResource webResource = client
                    .resource("http://api.openweathermap.org/data/2.5/weather?" + param + apiKey + units);

            ClientResponse response = webResource.accept("application/json")
                    .get(ClientResponse.class);

            if (response.getStatus() != 200) {
                System.out.println("Oh shit.");
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }

            String output = response.getEntity(String.class);
            JsonElement root = new JsonParser().parse(output);
            String name = root.getAsJsonObject().get("name").getAsString();
            String main = root.getAsJsonObject().get("weather").getAsJsonArray().get(0)
                    .getAsJsonObject()
                    .get("main")
                    .getAsString();
            String temp = root.getAsJsonObject().get("main").getAsJsonObject().get("temp").getAsString();
            return name + ", " + main + " @ " + temp + "F";
        }

        return null;
    }
}
