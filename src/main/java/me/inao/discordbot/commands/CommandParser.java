package me.inao.discordbot.commands;


import lombok.Setter;
import me.inao.discordbot.ifaces.IParameter;
import me.inao.discordbot.util.Loader;
import org.javacord.api.DiscordApi;

import java.util.*;

public class CommandParser {
    @Setter
    private DiscordApi api;

    @Setter
    private Loader loader;

    @Setter
    private String commandPrefix;

    public List<IParameter> getParsedValues(String[] parts){
        List<IParameter> parameters = new ArrayList<>();
//        if(loader.getParameterList() == null) return null;
        for (String part : parts){
            if(checkForCommandPair(part)) continue;
            String[] partSplit = part.split("\\s");
            Optional<IParameter> parameter = loader.getParameterList().entrySet().stream().filter(listIParameterEntry -> listIParameterEntry.getKey().stream().anyMatch(entry -> entry.matches(partSplit[0]))).map(Map.Entry::getValue).findAny();
            if(parameter.isPresent()){
                String[] modifiedArray = Arrays.copyOfRange(partSplit, 1, partSplit.length);
                IParameter param = parameter.get();
                param.onParse(this.api, String.join(" ", Arrays.asList(modifiedArray)));
                parameters.add(param);
            }
        }
        return parameters;
    }

    public String[] getParsedCommand(String message){
        return message.split("\\s-");
    }

    public boolean checkForParamPair(String splitMessage){
        return splitMessage.matches("^[\\-].+\\s.+$");
    }

    public boolean checkForCommandPair(String splitMessage){
        return splitMessage.matches("^" + commandPrefix + ".+(\\s.+)?$");
    }
}
