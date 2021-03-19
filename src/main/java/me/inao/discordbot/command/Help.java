package me.inao.discordbot.command;

import me.inao.discordbot.Main;
import me.inao.discordbot.ifaces.ICommand;
import me.inao.discordbot.ifaces.IParameter;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;

import java.util.List;

public class Help implements ICommand {
    @Override
    public void onCommand(Main instance, Message message, List<IParameter> args) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setDescription("Helping everyone is my pleasure, you know :eyes:").setTitle("Help");
        builder.setFooter("Prefix for commands is " + instance.getConfig().getPrefix());

        instance.getLoader().getLoadedCommands().entrySet().iterator().forEachRemaining(st -> {
            if (instance.getConfig().isCommandEnabled(st.getKey())) {
                if (!st.getKey().equals(this.getClass().getSimpleName())) {

                    StringBuilder usage = new StringBuilder();
                    if (st.getValue().requiredParameters().length > 0) {
                        for (Class<? extends IParameter> param : st.getValue().requiredParameters()) {
                            try {
                                usage.append(" < -").append(String.join("|-", param.getDeclaredConstructor().newInstance().getIdentifiers())).append(" >");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        builder.addField(st.getKey(), usage.toString());
                    } else {
                        builder.addField(st.getKey(), "No parameters");
                    }
                }
            }
        });
        new MessageBuilder().setEmbed(builder).send(message.getChannel());
    }

    @Override
    public Class<? extends IParameter>[] requiredParameters() {
        return new Class[0];
    }
}
