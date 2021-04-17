package me.inao.discordbot.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.inao.discordbot.Main;
import me.inao.discordbot.buffer.CaptchaBuffer;
import me.inao.discordbot.exception.NoSuchServerTextChannelException;
import me.inao.discordbot.ifaces.IListener;
import me.inao.discordbot.request.http.post.CaptchaCreatePostRequest;
import me.inao.discordbot.task.CaptchaTimerTask;
import me.inao.discordbot.util.Logger;
import me.inao.discordbot.util.MessageSender;
import org.apache.logging.log4j.Level;
import org.javacord.api.event.server.member.ServerMemberJoinEvent;
import org.javacord.api.listener.server.member.ServerMemberJoinListener;

import java.awt.*;
import java.util.Timer;

@RequiredArgsConstructor
public class OnJoinEvent implements ServerMemberJoinListener, IListener {
    @Getter
    private final Main main;

    @Getter
    @Setter
    private CaptchaBuffer captchaBuffer;

    @Override
    public void onServerMemberJoin(ServerMemberJoinEvent e) {
        if (main.getConfig().isFeatureEnabled("captchaSystem")) {
            if (captchaBuffer == null) {
                setClearTask();
                captchaBuffer = new CaptchaBuffer();
                captchaBuffer.addCount();
            }
            e.getUser().addRole(e.getServer().getRolesByName(main.getConfig().getFeatureData("captchaSystem").split(";")[1]).get(0));
            if (captchaBuffer.getCounter() > ((int) main.getConfig().getFeatureValue("captchaSystem", "userThreshold"))) {
                CaptchaCreatePostRequest captcha = new CaptchaCreatePostRequest();
                if (main.getConfig().getFeatureValue("captchaSystem", "httpAuth") != null) {
                    captcha.getArguments().put("auth", main.getConfig().getFeatureValue("captchaSystem", "httpAuth"));
                }
                captcha.getArguments().put("discordId", e.getUser().getIdAsString());
            } else {

            }
        } else {
            if (main.getConfig().isFeatureEnabled("joinMessage")) {
                new MessageSender(e.getUser().getDiscriminatedName() + " has joined", main.getConfig().getFeatureData("joinMessage").replace("%_user_%", e.getUser().getDiscriminatedName()), Color.GREEN, e.getServer().getChannelsByName(main.getConfig().getFeatureChannel("joinMessage")).get(0).asServerTextChannel().orElseThrow(NoSuchServerTextChannelException::new));
            }
            e.getUser().addRole(e.getServer().getRolesByName(main.getConfig().getFeatureData("captchaSystem").split(";")[2]).get(0));
        }
        new Logger(main, false, true, "Join", "User " + e.getUser().getDiscriminatedName() + " has joined", Level.INFO);
    }

    private void setClearTask() {
        Timer timer = new Timer();
        long delay = (1000L * 60 * ((int) main.getConfig().getFeatureValue("captchaSystem", "userThresholdTime")));
        timer.schedule(new CaptchaTimerTask(this), delay);
    }
}