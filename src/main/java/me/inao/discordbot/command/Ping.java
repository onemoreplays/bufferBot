package me.inao.discordbot.command;

import me.inao.discordbot.Main;
import me.inao.discordbot.annotation.Permission;
import me.inao.discordbot.ifaces.ICommand;
import me.inao.discordbot.ifaces.IParameter;
import me.inao.discordbot.request.http.Driver;
import me.inao.discordbot.request.http.post.CaptchaCreatePostRequest;
import me.inao.discordbot.util.MessageSender;
import org.javacord.api.entity.message.Message;
import org.jsoup.Jsoup;

import java.awt.*;
import java.util.List;

@Permission
public class Ping implements ICommand {
    @Override
    public void onCommand(Main instance, Message message, List<IParameter> args) {
        CaptchaCreatePostRequest captcha = new CaptchaCreatePostRequest();
        if(instance.getConfig().getFeatureValue("captchaSystem", "httpAuth") != null){
            captcha.getArguments().put("auth", instance.getConfig().getFeatureValue("captchaSystem", "httpAuth"));
        }
        captcha.getArguments().put("discordId", new String[]{message.getUserAuthor().get().getIdAsString()});
        String response = new Driver(captcha).postRequestWithResponse();
        if(response != null){
            System.out.println(Jsoup.parse(response).text());
        }
        new MessageSender("pong", "pong", Color.RED, message.getChannel());
    }

    @Override
    public Class<? extends IParameter>[] requiredParameters() {
        return new Class[0];
    }
}
