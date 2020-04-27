package me.inao.discordbot.exception;

public class NoSuchServerTextChannelException extends RuntimeException{
    public String toString(){
        return "There was a problem with getting Channel as a ServerTextChannel.";
    }
}
