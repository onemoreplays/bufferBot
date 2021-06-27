package me.inao.discordbot.commands.params;

import me.inao.discordbot.ifaces.IParameter;
import org.javacord.api.DiscordApi;

public class SectionParam implements IParameter {
    @Override
    public void onParse(DiscordApi api, String value) {

    }

    @Override
    public String[] getIdentifiers() {
        return new String[]{
                "-se", "--section"
        };
    }
}
