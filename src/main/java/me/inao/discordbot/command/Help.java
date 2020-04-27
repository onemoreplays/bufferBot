package me.inao.discordbot.command;

import me.inao.discordbot.Main;
import me.inao.discordbot.event.MessageEvent;
import me.inao.discordbot.ifaces.ICommand;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;

public class Help implements ICommand{

    @Override
    public void onCommand(Main instance, Message message, String[] args) {

        EmbedBuilder builder = new EmbedBuilder();
        builder.setDescription("Helping everyone is my pleasure, you know :eyes:").setTitle("Help");
        builder.setFooter("Prefix for commands is " + instance.getConfig().getPrefix());

        ((MessageEvent) instance.getApi().getMessageCreateListeners().get(0)).getLoadedCommands().entrySet().iterator().forEachRemaining(st->{
            if(instance.getConfig().isCommandEnabled(st.getKey())){
                if(st.getValue().getUsage() != null){
                    builder.addField(st.getKey(), st.getKey() + " " + st.getValue().getUsage());
                }else{
                    builder.addField(st.getKey(), "No parameters");
                }
            }
        });
        new MessageBuilder().setEmbed(builder).send(message.getChannel());
    }

    @Override
    public String getUsage() {
        return null;
    }

    @Override
    public boolean hasPermission(Main main, Message message) {
        return true;
    }
}
