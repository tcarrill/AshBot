package org.moonlab.ash.parser;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: thomas
 * Date: 11/19/12
 * Time: 1:15 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Parser {
    public Map<String, String> parse(String line) throws java.io.IOException;
    public Map<String, String> parsePrefix(String prefix);
}
