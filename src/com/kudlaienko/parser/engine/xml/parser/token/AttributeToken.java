package com.kudlaienko.parser.engine.xml.parser.token;

import com.kudlaienko.parser.engine.xml.property.Attribute;
import com.kudlaienko.parser.shell.exceptions.ParseException;
import com.kudlaienko.parser.shell.service.ParseResult;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

final public class AttributeToken extends XmlToken<String, Attribute> {
    private Pattern pattern;

    @Override
    public ParseResult<Attribute> doParse(String content, int start) throws ParseException {
        Matcher matcher = pattern.matcher(content);
        if (!matcher.find(start) || matcher.start() != start)
            throw new ParseException("Property parsing failed at char " + start);

        int parserPos = matcher.start() + matcher.group().length();
        String name = matcher.group(1);
        String value = matcher.group(2);

        return new ParseResult<>(new Attribute(name, value), parserPos);
    }

    @Override
    protected void init() {
        pattern = Pattern.compile("[\\s]*([A-Za-z_0-9]+)[\\s]*[=][\\s]*[\"]([A-Za-z_0-9\\.\\s]*)[\"][\\s]*");
    }
}