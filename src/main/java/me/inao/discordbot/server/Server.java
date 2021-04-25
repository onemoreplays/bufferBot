package me.inao.discordbot.server;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.inao.discordbot.Main;
import me.inao.discordbot.ifaces.IResponse;
import me.inao.discordbot.server.thread.SessionCleanerThread;
import me.inao.discordbot.util.ExceptionCatcher;
import me.inao.discordbot.util.Logger;
import org.apache.logging.log4j.Level;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.reflections.Reflections;

import java.net.ServerSocket;
import java.security.Security;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class Server extends Thread {
    private final Main instance;
    private volatile boolean run = true;
    @Getter
    private HashMap<String, IResponse> actions;
    @Getter
    private HashMap<String, Session> sessions = new HashMap<>();

    @Override
    public void run() {
        if (!(Boolean.parseBoolean(instance.getConfig().getServerProperty("enabled")))) {
            run = false;
        }
        if (!run) return;
        try {
            ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
            executorService.scheduleAtFixedRate(new SessionCleanerThread(this), 0, 30, TimeUnit.MINUTES);
            new Logger(instance, true, false, "Session cleaner", "Session cleaner set for 30 minutes. Invalid sessions will be destoryed when client tries to connect, otherwise after 30 minutes.", Level.INFO);
            Security.addProvider(new BouncyCastleProvider());
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