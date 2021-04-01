package me.inao.discordbot.server.thread;

import lombok.RequiredArgsConstructor;
import me.inao.discordbot.server.Server;

import java.util.Date;

@RequiredArgsConstructor
public class SessionCleanerThread extends Thread {
    private final Server server;
    @Override
    public void run() {
        if(server.getSessions().size() > 1){
            server.getSessions().forEach((identifier, session) -> {
                if(session.getValidity().after(new Date())){
                    server.getSessions().remove(identifier);
                }
            });
        }
    }
}
