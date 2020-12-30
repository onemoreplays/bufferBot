package me.inao.discordbot.objects;

import lombok.RequiredArgsConstructor;
import me.inao.discordbot.Main;
import me.inao.discordbot.server.Packet;
import me.inao.discordbot.server.request.CaptchaRequest;
import me.inao.discordbot.util.MessageSender;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.channel.ServerTextChannelBuilder;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.permission.PermissionsBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;

import java.awt.*;
import java.security.SecureRandom;

@RequiredArgsConstructor
public class Captcha {
    private final Main main;
    private final Server server;
    private final User user;
    public void generate(){
        String code = getCode();
        ServerTextChannel channel = new ServerTextChannelBuilder(server).setName("captcha-"+code)
                .setCategory(server.getChannelCategoriesByName(main.getConfig().getFeatureData("captchaSystem").split(";")[0]).get(0))
                .addPermissionOverwrite(user ,new PermissionsBuilder().setAllowed(PermissionType.READ_MESSAGES).build())
                .create()
                .join();
        Packet packet = new Packet();
        packet.setUser(user.getIdAsString());
        packet.setToken("eJcJrNfp4ArgAFBCSHRTeU6yYJs2E8T7SsFog2ZNHvM8AhFBnZAM5GSd4YzMmxHH8UnuMvD5qBYHT8NS2zjyD4p6wqtjyDkbYjKbVokeLKu4dEjSuZrQbH36CSoKL4EN");
        packet.setCode(code);
        packet.setAction("0");
        new CaptchaRequest().onRequest("https://inao.xn--6frz82g/discord/backend/Swihg5HaGn8F3fp7ZaDcN4zCiFuzdauThySAQFg9sax4YTidg6HGpXKrAMB4eidX5tF4kK8UisoX2TBrjpj28.php", packet);
        String welcome = main.getConfig().getMessage("captcha", "welcome").replace("%_link_%", "https://inao.xn--6frz82g/discord/?code="+packet.getCode());
        new MessageSender("Captcha required", welcome, Color.MAGENTA, channel);
    }
    private String getCode(){
        char[] chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder builder = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for(int i = 0; i < 9; i++){
            builder.append(chars[random.nextInt(chars.length)]);
        }
        return builder.toString();
    }
}
