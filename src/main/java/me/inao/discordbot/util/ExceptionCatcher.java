package me.inao.discordbot.util;


import java.util.HashMap;

public class ExceptionCatcher {
    private final HashMap<String, String> exception = new HashMap<>();
    public ExceptionCatcher(Exception e){
        stuff();
        identify(e);
    }
    private void identify(Exception e){
        System.out.println("Oh boi, here we go, I've caught an exception.");
        System.out.println("Report this to the author with text under this.");
        String message = exception.get(e.getClass().getSimpleName());
        if(message != null){
            if(message.contains("%_%")) System.out.println(message.replace("%_%", e.getMessage()));
            else System.out.println(message);
        }
        e.printStackTrace();
    }
    private void stuff(){
        exception.put("FileNotFoundException", "Possibly missing file %_%");
    }
}
