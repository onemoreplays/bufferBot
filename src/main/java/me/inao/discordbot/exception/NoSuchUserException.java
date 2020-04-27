package me.inao.discordbot.exception;

public class NoSuchUserException extends RuntimeException{
    public String toString(){
        return "Found problem with user. It looks like there is no such user.";
    }
}
