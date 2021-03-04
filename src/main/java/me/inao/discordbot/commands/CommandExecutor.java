package me.inao.discordbot.commands;

import me.inao.discordbot.Main;
import me.inao.discordbot.ifaces.ICommand;
import me.inao.discordbot.ifaces.IParameter;
import org.javacord.api.entity.message.Message;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CommandExecutor {
    public void execute(Message message, Main instance){
        CommandParser parser = new CommandParser();
        if(parser.getMap() == null){
            parser.setApi(instance.getApi());
            parser.setMap(instance.getLoader().loadParams("me.inao.discordbot.commands.params"));
            parser.setCommandPrefix(String.valueOf(instance.getConfig().getPrefix()));
        }
        String[] parsed = parser.getParsedCommand(message.getContent());
        List<IParameter> parameterList = parser.getParsedValues(parsed);

        String cmd = "";
        for(String parse : parsed){
            if(parser.checkForCommandPair(parse)){
                cmd = parse;
                break;
            }
        }

        String finalCmd = cmd.replace(String.valueOf(instance.getConfig().getPrefix()), "");
        Optional<ICommand> command = instance.getLoader().getLoadedCommands().entrySet()
                .stream()
                .filter(entry -> finalCmd.equalsIgnoreCase(entry.getKey()))
                .map(Map.Entry::getValue)
                .findAny();

        if(command.isPresent()) {
            command.get().onCommand(instance, message, parameterList);
        } else {
            message.getChannel().sendMessage("Unknown command!");
        }
    }
}
