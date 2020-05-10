package me.inao.discordbot.util;

import me.inao.discordbot.Main;
import me.inao.discordbot.exception.NoSuchServerTextChannelException;
import org.apache.logging.log4j.Level;

import java.awt.*;

public class Logger {
    public Logger(Main instance, boolean isConsole, String name, String value, Level level){
        if(isConsole){
            instance.getLogger().log(level, value);
        }
        Color color;
        //fkng switch-case statements cannot compare Log4J enums so I was forced to use this :) :) :)
        if (level == Level.INFO) {
            color = Color.BLUE;
        }else if(level == Level.WARN){
            color = Color.ORANGE;
        } else if (level == Level.ERROR) {
            color = Color.RED;
        } else if(level == Level.FATAL){
            color = Color.BLACK;
        }else{
            color = Color.GRAY;
        }
        // :(
        new MessageSender("Log - " + name, value, color, instance.getApi().getChannelsByName(instance.getConfig().getFeatureData("Logger")).iterator().next().asServerTextChannel().orElseThrow(NoSuchServerTextChannelException::new));
    }
}
