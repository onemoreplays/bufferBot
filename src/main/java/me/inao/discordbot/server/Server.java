package me.inao.discordbot.server;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.inao.discordbot.Main;
import me.inao.discordbot.ifaces.IResponse;
import me.inao.discordbot.util.ExceptionCatcher;
import org.reflections.Reflections;

import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Set;

@RequiredArgsConstructor
public class Server extends Thread {
    private final Main instance;
    private volatile boolean run = true;
    @Getter
    private HashMap<String, IResponse> actions;

    @Override
    public void run() {
        if (!(Boolean.parseBoolean(instance.getConfig().getServerProperty("enabled")))) {
            run = false;
        }
        if (!run) return;
        try {
            Reflections refl = new Reflections("me.inao.discordbot.server.response");
            Set<Class<? extends IResponse>> classes = refl.getSubTypesOf(IResponse.class);
            actions = new HashMap<>(classes.size());
            for (Class<? extends IResponse> clazz : classes) {
                actions.put(clazz.getSimpleName(), clazz.getDeclaredConstructor().newInstance());
            }
        } catch (Exception e) {
            new ExceptionCatcher(e);
        }
        try (ServerSocket socket = new ServerSocket(Integer.parseInt(instance.getConfig().getServerProperty("port")))) {
            while (true) {
                new Connection(instance, socket.accept(), this).start();
            }
        } catch (Exception e) {
            new ExceptionCatcher(e);
        }
    }
}