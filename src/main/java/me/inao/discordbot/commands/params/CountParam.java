package me.inao.discordbot.commands.params;

import lombok.Getter;
import me.inao.discordbot.ifaces.IParameter;
import org.javacord.api.DiscordApi;

public class CountParam implements IParameter {
    @Getter
    private int count = 0;

    @Override
    public void onParse(DiscordApi api, String value) {
        try{
            count = Integer.parseInt(value);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Invalid integer provided.");
        }
    }

    @Override
    public String[] getIdentifiers() {
        return new String[]{
                "c", "-count"
        };
    }
}
