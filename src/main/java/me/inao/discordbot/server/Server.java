package me.inao.discordbot.server;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.inao.discordbot.Main;
import me.inao.discordbot.ifaces.IAction;
import me.inao.discordbot.util.AES;
import me.inao.discordbot.util.ExceptionCatcher;
import org.reflections.Reflections;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Set;

@RequiredArgsConstructor
public class Server extends Thread{
    private final Main instance;
    private volatile boolean run = true;
    @Getter
    private HashMap<String, IAction> actions;
    @Override
    public void run(){
        if(!(Boolean.parseBoolean(instance.getConfig().getServerProperty("enabled")))){
            run = false;
        }
        if(run){
            if(instance.getConfig().getServerProperty("aesKey").equals("null") || instance.getConfig().getServerProperty("aesIv").equals("null")){
                AES aes = new AES(instance);
                System.out.println("Server cannot start without proper AES encryption. Here are generated values");
                System.out.println("aesKey: " + aes.getKey());
                System.out.println("aesIv: " + aes.getIv());
                System.out.println("These values are used to encrypt packets between client and server. Please use the same keys in server and in client.");
                System.out.println("Server part of bot will now shut down.");
                run = false;
            }
            if(!run) return;
            try{
                Reflections refl = new Reflections("me.inao.discordbot.server.actions");
                Set<Class<? extends IAction>> classes = refl.getSubTypesOf(IAction.class);
                actions = new HashMap<>(classes.size());
                for (Class<? extends IAction> clazz : classes) {
                    actions.put(clazz.getSimpleName(), clazz.getDeclaredConstructor().newInstance());
                }
            }catch (Exception e){
                new ExceptionCatcher(e);
            }
            try (ServerSocket socket = new ServerSocket(Integer.parseInt(instance.getConfig().getServerProperty("port")))){
                while(true){
                    new Connection(instance, socket.accept(), this).start();
                }
            }catch (Exception e){
                new ExceptionCatcher(e);
            }
        }
    }
}
