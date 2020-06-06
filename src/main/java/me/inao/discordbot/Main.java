package me.inao.discordbot;

import com.google.gson.Gson;
import com.vdurmont.emoji.EmojiParser;
import lombok.Getter;
import lombok.Setter;
import me.inao.discordbot.buffer.SkidBuffer;
import me.inao.discordbot.objects.Config;
import me.inao.discordbot.objects.Countgame;
import me.inao.discordbot.server.Server;
import me.inao.discordbot.util.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.user.UserStatus;

import java.io.BufferedReader;
import java.io.FileReader;

@Getter
public class Main {
    private Config config;
    private DiscordApi api;
    @Setter
    private Countgame countgame = null;
    private final SQLite sqlite = new SQLite();
    @Setter
    private SkidBuffer skidBuffer = new SkidBuffer(this);
    private final Logger logger = LogManager.getLogger("me.inao.discordbot");
    private final boolean debug = false;
    @Getter
    Loader loader;

    private final UserStatus[] status = {UserStatus.ONLINE, UserStatus.IDLE, UserStatus.DO_NOT_DISTURB, UserStatus.INVISIBLE};
    public static void main(String[] args){
        new Main().starter();
    }
    public void starter(){
        loadConfig();
        DiscordApiBuilder apiBuilder = new DiscordApiBuilder().setToken(config.getApiKey());
        loader = new Loader(this, apiBuilder);
        try{
            loader.loadListeners("me.inao.discordbot.event");
            loader.loadCommands("me.inao.discordbot.command");
        }catch (Exception e){
            new ExceptionCatcher(e);
            new me.inao.discordbot.util.Logger(this, true, false, "Loading problem", "Cannot load bot (Event/Command). Shutting down!", Level.FATAL);
            System.exit(0);
        }
        this.api = apiBuilder.login().join();
        api.updateStatus(status[config.getState()]);
        if(debug) api.updateActivity(ActivityType.PLAYING, "Launched in debug mode " + EmojiParser.parseToUnicode(":bug:"));
        else api.updateActivity(ActivityType.PLAYING, "with Raspberry and Java" + EmojiParser.parseToUnicode(":yum:"));
        api.setAutomaticMessageCacheCleanupEnabled(true);
        new Server(this).start();
        api.setMessageCacheSize(15, 3600);
        new me.inao.discordbot.util.Logger(this, true, true, "Bot start", "Loaded. Invite: " + api.createBotInvite(), Level.INFO);
        Runtime.getRuntime().addShutdownHook(new ShutdownHook(this));
        if(config.isFeatureEnabled("midnightRestart")) new Rebooter();
    }
    public void loadConfig(){
        Gson gson = new Gson();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("config.json"));
            config = gson.fromJson(reader, Config.class);
            reader.close();
        }catch (Exception e){
            new ExceptionCatcher(e);
        }
    }
}
