package me.inao.discordbot.objects;

import com.google.gson.JsonArray;
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
    private String prefix;

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

    @SerializedName("setup")
    @Expose
    @Getter
    List<JsonObject> setup;

    public boolean isCommandEnabled(String name){
        try{
            for (JsonObject command : commands){
                return command.getAsJsonObject(name).get("enabled").getAsBoolean();
            }
        }catch (Exception ignored){ }
        return true;
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

    public String getCommandDescription(String name){
        try{
            for (JsonObject command : commands){
                return command.getAsJsonObject(name).get("description").getAsString();
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

    public JsonArray getServerTokens(){
        try{
            return server.get(0).get("tokens").getAsJsonArray();
        }catch(Exception e){
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
            System.out.println(name);
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
            System.out.println(name);
            System.out.println("Feature not found in config :(");
        }
        return null;
    }
    public String getFeatureChannel(String name){
        try{
            for (JsonObject object : features){
                return object.getAsJsonObject(name).get("room").getAsString();
            }
        }catch (Exception e) {
            System.out.println(name);
            System.out.println("No room set :(");
        }
        return null;
    }

    public Object getFeatureValue(String feature, String value){
        try{
            for (JsonObject object : features){
                return object.getAsJsonObject(feature).get(value).getAsString();
            }
        }catch (Exception e){
            new ExceptionCatcher(e);
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
