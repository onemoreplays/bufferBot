package me.inao.discordbot.util;

import me.inao.discordbot.Main;
import me.inao.discordbot.exception.NoSuchServerTextChannelException;
import org.apache.logging.log4j.Level;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    public Logger(Main instance, String value, Level level) {
        this(instance, true, false, "", value, level);
    }

    public Logger(Main instance, boolean isConsole, boolean isDiscord, String name, String value, Level level) {
        if (isConsole) {
            instance.getLogger().log(level, value);
        }
        Color color;
        switch (level.name().toLowerCase()) {
            case "info":
                color = Color.BLUE;
                break;
            case "warn":
                color = Color.ORANGE;
                break;
            case "error":
                color = Color.RED;
                break;
            case "fatal":
                color = Color.BLACK;
                break;
            default:
                color = Color.GRAY;
        }
        if (isDiscord) {
            new MessageBuilder().setEmbed(
                    new EmbedBuilder()
                            .setTitle(name)
                            .setDescription(value)
                            .setColor(color)
                            .setFooter("Level: " + level.name() + " | At: " + new SimpleDateFormat("HH:mm:ss dd/MM/yyyy").format(new Date()))
            ).send(instance.getApi().getChannelsByName(instance.getConfig().getFeatureChannel("Logger")).iterator().next().asServerTextChannel().orElseThrow(NoSuchServerTextChannelException::new));

        }
    }
}
