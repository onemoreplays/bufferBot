package me.inao.discordbot.task;

import lombok.AllArgsConstructor;
import lombok.Setter;
import me.inao.discordbot.buffer.CaptchaBuffer;
import me.inao.discordbot.event.OnJoinEvent;
import me.inao.discordbot.request.http.Driver;
import me.inao.discordbot.request.http.post.CaptchaCreatePostRequest;
import org.jsoup.Jsoup;

import java.util.TimerTask;

@AllArgsConstructor
public class CaptchaTimerTask extends TimerTask {
    @Setter
    private OnJoinEvent event;

    @Override
    public void run() {
        CaptchaCreatePostRequest captcha = new CaptchaCreatePostRequest();
        if (event.getMain().getConfig().getFeatureValue("captchaSystem", "httpAuth") != null) {
            captcha.getArguments().put("auth", event.getMain().getConfig().getFeatureValue("captchaSystem", "httpAuth"));
        }
        captcha.getArguments().put("discordId", event.getCaptchaBuffer().getIds().toArray());
        String response = Jsoup.parse(new Driver(captcha).postRequestWithResponse()).text();

        event.setCaptchaBuffer(new CaptchaBuffer());
    }
}
