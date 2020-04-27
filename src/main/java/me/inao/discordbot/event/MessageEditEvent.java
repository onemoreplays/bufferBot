package me.inao.discordbot.event;

import me.inao.discordbot.Main;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.listener.message.MessageEditListener;

public class MessageEditEvent implements MessageEditListener {
    private Main main;
    public MessageEditEvent(Main main){
        this.main = main;
    }
    @Override
    public void onMessageEdit(org.javacord.api.event.message.MessageEditEvent e) {
        if(!(e.getChannel() instanceof ServerTextChannel)){
            return;
        }
    }
}
