package me.inao.discordbot.buffer;

import lombok.Setter;
import me.inao.discordbot.Main;

import java.util.TimerTask;

public class CountgameBuffer extends TimerTask{
    @Setter
    private Main main;
    @Override
    public void run() {
        if(main.getCountgame() == null){
            return;
        }
        long count = main.getCountgame().getCount();
        main.getCountgame().getChannel().updateTopic("Last number: " + (count) + " | Finish: " + main.getCountgame().getFinish());
        main.getCountgame().setTask(false);
    }
}
