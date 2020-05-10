package me.inao.discordbot.util;

import lombok.AllArgsConstructor;
import me.inao.discordbot.Main;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

@AllArgsConstructor
public class ShutdownHook extends Thread{
    private final Main instance;
    @Override
    public void run(){
        instance.getLogger().log(Level.WARN, "Bot is shutting down!");
        LogManager.shutdown();
    }
}
