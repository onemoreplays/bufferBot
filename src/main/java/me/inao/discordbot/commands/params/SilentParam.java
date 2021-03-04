package me.inao.discordbot.commands.params;

import lombok.Getter;
import me.inao.discordbot.ifaces.IParameter;
import org.javacord.api.DiscordApi;

public class SilentParam implements IParameter {
    @Getter
    private boolean isProvided = false;

    @Override
    public void onParse(DiscordApi api, String value) {
        isProvided = true;
    }

    @Override
    public String[] getIdentifiers() {
        return new String[]{
                "s", "-silent"
        };
    }
}
