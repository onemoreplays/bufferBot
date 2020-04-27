package me.inao.discordbot.objects;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import me.inao.discordbot.util.ExceptionCatcher;

import java.util.List;

public class Config {
    @SerializedName("apiKey")
    @Expose
    @Getter
    private String apiKey;

    @SerializedName("prefix")
    @Expose
    @Getter
    private char prefix;

    @SerializedName("botState")
    @Expose
    @Getter
    private int state;

    @SerializedName("features")
    @Expose
    List<JsonObject> features;

    @SerializedName("commands")
    @Expose
    List<JsonObject> commands;

    @SerializedName("server")
    @Expose
    List<JsonObject> server;

    @SerializedName("messages")
    @Expose
    List<JsonObject> messages;

    public boolean isCommandEnabled(String name){
        try{
            for (JsonObject command : commands){
                return command.getAsJsonObject(name).get("enabled").getAsBoolean();
            }
        }catch (Exception e){
            System.out.println("Command not found in config.json :(");
        }
        return false;
    }

    public String getCommandMessage(String name){
        try{
            for (JsonObject command : commands){
                return command.getAsJsonObject(name).get("message").getAsString();
            }
        }catch (Exception e){
            System.out.println("No command found :(");
        }
        return null;
    }
    public String getCommandRoom(String name){
        try{
            for (JsonObject command : commands){
                return command.getAsJsonObject(name).get("channelName").getAsString();
            }
        }catch (Exception e){
            System.out.println("No command found :(");
        }
        return null;
    }
    public String getCommandPerms(String name){
        try{
            for (JsonObject command : commands){
                return command.getAsJsonObject(name).get("perms").getAsString();
            }
        }catch (Exception e){
            System.out.println("No command found :(");
        }
        return null;
    }
    public String getServerProperty(String name){
        try{
            return server.get(0).get(name).getAsString();
        }catch (Exception e){
            new ExceptionCatcher(e);
        }
        return null;
    }
    public boolean isFeatureEnabled(String name){
        try{
            for (JsonObject other : features){
                return other.getAsJsonObject(name).get("enabled").getAsBoolean();
            }
        }catch (Exception e){
            System.out.println("Feature not found in config :(");
        }
        return false;
    }
    public String getFeatureData(String name){
        try{
            for (JsonObject other : features){
                return other.getAsJsonObject(name).get("data").getAsString();
            }
        }catch (Exception e){
            System.out.println("Feature not found in config :(");
        }
        return null;
    }
    public String getMessage(String module, String message){
        try{
            for(JsonObject object : messages){
                return object.getAsJsonObject(module).get(message).getAsString();
            }
        }catch (Exception e){
            System.out.println("Message not found :(");
        }
        return null;
    }
}
