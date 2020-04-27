package me.inao.discordbot.objects;

import lombok.Getter;
import lombok.Setter;
import me.inao.discordbot.Main;
import me.inao.discordbot.buffer.CountgameBuffer;
import me.inao.discordbot.util.MessageSender;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.message.Message;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

@Getter
public class Countgame {
    private final ServerTextChannel channel;
    private final long finish;
    private final Main instance;
    private long count = 0;
    @Setter
    private boolean task = false;

    public Countgame(ServerTextChannel channel, long finish, Main instance, long prev){
        this(channel, finish, instance);
        this.count = prev;
    }

    public Countgame(ServerTextChannel channel, long finish, Main instance){
        this.channel = channel;
        this.finish = finish;
        this.instance = instance;
        new MessageSender("CountGame", "CountGame has been created!", Color.CYAN, channel);
    }
    public void addCount(Message m){
        if(m.getAuthor().isBotUser()){
            return;
        }
        try{
            if(Long.decode(m.getContent()) != (count + 1)){
                m.delete();
                return;
            }
        }catch (Exception e){
            m.delete();
            return;
        }
        if(count+1 == finish){
            channel.updateTopic("");
            new MessageSender("CountGame", "CountGame has been completed!", Color.GREEN, channel);
            instance.setCountgame(null);
            return;
        }
        if(!(task)){
            CountgameBuffer buffer = new CountgameBuffer();
            buffer.setMain(instance);
            this.task = true;
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    buffer.run();
                }
            }, 30000);
        }
        count++;
    }
}
