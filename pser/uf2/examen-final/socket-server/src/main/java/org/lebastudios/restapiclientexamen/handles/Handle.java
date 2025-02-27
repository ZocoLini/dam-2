package org.lebastudios.restapiclientexamen.handles;

import java.util.Optional;
import java.util.regex.Matcher;

public interface Handle
{
    String handle(String msg);
    
    static Handle from(String string)
    {
        return switch (string)
        {
            case "PING" -> new PingHandler();
            default -> null;
        };
    }
    
    static Optional<String> handleRquest(String request, Matcher matcher)
    {
        if (!matcher.reset(request).matches()) return Optional.empty();

        Handle handle = Handle.from(matcher.group(1));

        if (handle == null) return Optional.empty();

        return Optional.of(handle.handle(matcher.group(2)));
    }
}
