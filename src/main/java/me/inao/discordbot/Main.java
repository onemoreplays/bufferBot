package me.inao.discordbot;

import com.google.gson.Gson;
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

    private final UserStatus[] status = {UserStatus.ONLINE, UserStatus.IDLE, UserStatus.DO_NOT_DISTURB, UserStatus.INVISIBLE};
    public static void main(String[] args){
        new Main().starter();
    }
    public void starter(){
        loadConfig();
        api = new DiscordApiBuilder().setToken(config.getApiKey()).login().join();
        loadListeners(api);
        api.updateStatus(status[config.getState()]);
        api.updateActivity(ActivityType.PLAYING, "Powered by Raspberry Pi 3 and Java :)");
        new Server(this).start();
        System.out.println("Loaded. Invite: " + api.createBotInvite());
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
    private void loadListeners(DiscordApi api){
        api.addMessageCreateListener(new MessageEvent(this));
        api.addServerMemberJoinListener(new OnJoinEvent(this));
        api.addServerMemberLeaveListener(new OnLeaveEvent(this));
        api.addMessageEditListener(new MessageEditEvent(this));
    }
}
