package me.inao.discordbot.util;

import me.inao.discordbot.Main;
import org.apache.logging.log4j.Level;

public class Logger {
    public Logger(Main instance, boolean isConsole, String name, String value, Level level){
        if(isConsole){
            instance.getLogger().log(level, value);
        }

    }
}
