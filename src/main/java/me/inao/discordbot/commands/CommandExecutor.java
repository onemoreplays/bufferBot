package me.inao.discordbot.commands;

import me.inao.discordbot.ifaces.ICommand;

public class CommandExecutor {
    CommandParser commandParser = new CommandParser();
    //New command structure: !<parent> <command> -<params>
    public void execute(String message){
        String[] commandSplit = commandParser.getParsedCommand(message);

        Class<? extends ICommand> commandClass;
    }
}
