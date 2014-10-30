package org.moonlab.ash.parser;

import org.moonlab.ash.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: thomas
 * Date: 11/19/12
 * Time: 1:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class ParserImpl implements Parser {

    @Override
    public Map<String, String> parse(String buffer) throws java.io.IOException {

        Map<String, String> map = new HashMap<String, String>();

        int prefixEnd = -1;
        String prefix  = "";
        if (buffer.startsWith(":")) {
            prefixEnd = buffer.indexOf(" ");
            prefix = buffer.substring(1, prefixEnd);
        }

        int trailingStart = buffer.indexOf(" :");
        String trailing = null;
        if (trailingStart >= 0)  {
            trailing = buffer.substring(trailingStart + 2);
        } else {
            trailingStart = buffer.length();
        }

        String[] commandAndParameters = buffer.substring(prefixEnd + 1, trailingStart).split(" ");
        String command = commandAndParameters[0];

        map.put(Constants.PREFIX, prefix);
        map.put(Constants.COMMAND, command);
        if (commandAndParameters.length > 1) {
            map.put(Constants.PARAMETERS, commandAndParameters[1]);
        }
        map.put(Constants.TRAILING, trailing);

        return map;
    }

    public Map<String, String> parsePrefix(String prefix) {
        if (!prefix.contains("!") || !prefix.contains("@")) {
            return null;
        }

        Map<String, String> map = new HashMap<String, String>();

        map.put(Constants.NICK, prefix.split("!")[0]);
        map.put(Constants.LOGIN, prefix.split("!")[1].split("@")[0]);
        map.put(Constants.HOST, prefix.split("@")[1]);

        return map;
    }
}
