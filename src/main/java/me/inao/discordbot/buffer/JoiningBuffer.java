package me.inao.discordbot.buffer;

import lombok.RequiredArgsConstructor;
import me.inao.discordbot.event.OnJoinEvent;

import java.util.TimerTask;

@RequiredArgsConstructor
public class JoiningBuffer extends TimerTask {
    private final OnJoinEvent joinEvent;
    @Override
    public void run() {
        joinEvent.setJoins(0);
    }
}
