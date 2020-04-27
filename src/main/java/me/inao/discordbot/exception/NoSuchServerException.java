package me.inao.discordbot.exception;

public class NoSuchServerException  extends RuntimeException{
    public String toString(){
        return "There was a problem with getting Server out of a message. Maybe private message was sent to bot";
    }
}
