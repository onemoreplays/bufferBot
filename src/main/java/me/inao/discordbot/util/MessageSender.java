package me.inao.discordbot.util;

import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;

import java.awt.*;
import java.io.File;

public class MessageSender {
    public MessageSender(String title, String message, Color color, User user){
        new MessageBuilder().setEmbed(new EmbedBuilder()
                .setTitle(title)
                .setDescription(message)
                .setColor(color)
        ).send(user);
    }
    public MessageSender(String title, String message, Color color, TextChannel channel){
        new MessageBuilder().setEmbed(new EmbedBuilder()
        .setTitle(title)
        .setDescription(message)
        .setColor(color)).send(channel);
    }
    public MessageSender(String title, String message, File image, Color color, ServerTextChannel channel){
        new MessageBuilder().setEmbed(new EmbedBuilder()
                .setTitle(title)
                .setDescription(message)
                .setColor(color)
                .setImage(image)
        ).send(channel);
    }
}
