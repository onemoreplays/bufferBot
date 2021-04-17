package me.inao.discordbot.task;

import lombok.AllArgsConstructor;
import lombok.Setter;
import me.inao.discordbot.buffer.CaptchaBuffer;
import me.inao.discordbot.event.OnJoinEvent;

import java.util.TimerTask;

@AllArgsConstructor
public class CaptchaTimerTask extends TimerTask {
    @Setter
    private OnJoinEvent event;

    @Override
    public void run() {
        event.setCaptchaBuffer(new CaptchaBuffer());
    }
}
