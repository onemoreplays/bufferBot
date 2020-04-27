package me.inao.discordbot.objects;

import com.github.cage.GCage;
import me.inao.discordbot.Main;
import me.inao.discordbot.util.ExceptionCatcher;
import me.inao.discordbot.util.MessageSender;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.channel.ServerTextChannelBuilder;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.permission.PermissionsBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.sql.PreparedStatement;

public class Captcha {
    public void gen(Main main, String id, Server server, User user){
        File img = getFile("./generated/captcha/"+id+".jpg");
        char[] chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder builder = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for(int i = 0; i < 9; i++){
            builder.append(chars[random.nextInt(chars.length)]);
        }
        try{
            PreparedStatement stmnt = main.getSqlite().openConnection().prepareStatement("INSERT INTO captcha VALUES(?, ?)");
            stmnt.setString(1, user.getIdAsString());
            stmnt.setString(2, builder.toString());
            main.getSqlite().execute(stmnt);
            OutputStream stream = new FileOutputStream(img.getPath(), false);
            new GCage().draw(builder.toString(),stream);
            stream.close();
            ServerTextChannel channel = new ServerTextChannelBuilder(server).setName("captcha-"+user.getIdAsString())
                    .setCategory(server.getChannelCategoriesByName(main.getConfig().getFeatureData("captchaSystem").split(";")[0]).get(0))
                    .addPermissionOverwrite(user ,new PermissionsBuilder().setAllowed(PermissionType.READ_MESSAGES).build())
                    .create()
                    .join();
            new MessageSender("Captcha", main.getConfig().getMessage("captcha", "welcome"), img, Color.CYAN, channel);
            Thread.sleep(2000);
            if(img.delete()){
                System.out.println("Deleted captcha image.");
            }
        }catch (Exception e){
            new ExceptionCatcher(e);
        }
    }

    private File getFile(String name) {
        File f = new File(name);
        if (f.isFile()) {
            return f;
        }else{
            try{
                if(f.createNewFile()){
                    return f;
                }
            }catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

}
