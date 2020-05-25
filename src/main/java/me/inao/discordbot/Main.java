package me.inao.discordbot;

import com.google.gson.Gson;
import com.vdurmont.emoji.EmojiParser;
import lombok.Getter;
import lombok.Setter;
import me.inao.discordbot.buffer.SkidBuffer;
import me.inao.discordbot.event.MessageEditEvent;
import me.inao.discordbot.event.MessageEvent;
import me.inao.discordbot.event.OnJoinEvent;
import me.inao.discordbot.event.OnLeaveEvent;
import me.inao.discordbot.objects.Config;
import me.inao.discordbot.objects.Countgame;
import me.inao.discordbot.server.Server;
import me.inao.discordbot.util.ExceptionCatcher;
import me.inao.discordbot.util.SQLite;
import me.inao.discordbot.util.ShutdownHook;
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
    private boolean debug = false;

    private final UserStatus[] status = {UserStatus.ONLINE, UserStatus.IDLE, UserStatus.DO_NOT_DISTURB, UserStatus.INVISIBLE};
    public static void main(String[] args){
        new Main().starter();
    }
    public void starter(){
        loadConfig();
        api = new DiscordApiBuilder().setToken(config.getApiKey()).login().join();
        loadListeners(api);
        api.updateStatus(status[config.getState()]);
        if(debug) api.updateActivity(ActivityType.PLAYING, "Launched in debug mode " + EmojiParser.parseToUnicode(":bug:"));
        else api.updateActivity(ActivityType.PLAYING, "with Raspberry and Java" + EmojiParser.parseToUnicode(":yum:"));
        new Server(this).start();
        new me.inao.discordbot.util.Logger(this, true, "Bot start", "Loaded. Invite: " + api.createBotInvite(), Level.INFO);
        Runtime.getRuntime().addShutdownHook(new ShutdownHook(this));
    }
    public void loadConfig(){
        Gson gson = new Gson();
        try (BufferedReader reader = new BufferedReader(new FileReader("config.json"))){
            config = gson.fromJson(reader, Config.class);
        }catch (Exception e){
            new me.inao.discordbot.util.Logger(this, true, "", "Unable to log file", Level.FATAL);
            new ExceptionCatcher(e);
        }
    }
    private void loadListeners(DiscordApi api){
        api.addMessageEditListener(new MessageEditEvent(this));
        api.addMessageCreateListener(new MessageEvent(this));
        api.addServerMemberJoinListener(new OnJoinEvent(this));
        api.addServerMemberLeaveListener(new OnLeaveEvent(this));
    }
}
