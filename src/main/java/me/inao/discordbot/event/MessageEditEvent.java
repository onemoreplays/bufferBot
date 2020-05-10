package me.inao.discordbot.event;

import lombok.RequiredArgsConstructor;
import me.inao.discordbot.Main;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.listener.message.MessageEditListener;

@RequiredArgsConstructor
public class MessageEditEvent implements MessageEditListener {
    private final Main main;
    @Override
    public void onMessageEdit(org.javacord.api.event.message.MessageEditEvent e) {
        if(!(e.getChannel() instanceof ServerTextChannel)){
            return;
        }
    }
}
