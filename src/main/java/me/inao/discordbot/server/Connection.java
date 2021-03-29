package me.inao.discordbot.server;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import me.inao.discordbot.Main;
import me.inao.discordbot.util.AES;
import me.inao.discordbot.util.ExceptionCatcher;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
public class Connection extends Thread{
    private final Main instance;
    private final Socket socket;
    private final Server server;
    private Session session = null;

    @Override
    public void run(){
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            if(session == null || session.getValidity().after(new Date())){
                this.session = new Session();
                session.getKeyExchange().initKeys();
                System.out.println(Arrays.toString(session.getKeyExchange().getPair().getPublic().getEncoded()));
                writer.println(session.getKeyExchange().encodeBase64(session.getKeyExchange().getPair().getPublic().getEncoded()));
                String ret = reader.readLine();
                session.setSecret(this.session.getKeyExchange().calculateKey(Base64.getDecoder().decode(ret)));
                System.out.println(new String(Base64.getEncoder().encode(session.getSecret())));
//                writer.println("test");
//                System.out.println(session.getKeyExchange().encrypt("test"));
                return;
            }

            String json = new AES(instance).getDecrypted(reader.readLine());
            Packet packet = new Gson().fromJson(json, Packet.class);
            String[] tokens = instance.getConfig().getServerProperty("tokens").split(";");
            boolean exec = false;
            for(String token : tokens){
                if(token.equals(packet.getToken())){
                    exec = true;
                    break;
                }
            }
            if(exec){
                for(String action : server.getActions().keySet()){
                    if(action.equals(packet.getAction())){
                        server.getActions().get(action).onReceive(instance.getApi(), packet);
                        break;
                    }
                }
            }
        }catch (Exception e){
            new ExceptionCatcher(e);
        }
    }
}
