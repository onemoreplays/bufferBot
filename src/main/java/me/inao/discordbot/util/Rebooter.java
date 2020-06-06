package me.inao.discordbot.util;

import me.inao.discordbot.Main;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class Rebooter {
    public Rebooter(){
        Timer timer = new Timer();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 1);
        cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) + 1);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    Runtime.getRuntime().exec("screen java -jar " + URLDecoder.decode(Main.class.getProtectionDomain().getCodeSource().getLocation().getPath(), "UTF-8"));
                    System.exit(0);
                } catch (IOException e) {
                    new ExceptionCatcher(e);
                }
            }
        }, cal.getTime());
    }
}
