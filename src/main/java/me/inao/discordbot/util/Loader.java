package me.inao.discordbot.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.inao.discordbot.Main;
import me.inao.discordbot.ifaces.ICommand;
import me.inao.discordbot.ifaces.IListener;
import org.apache.logging.log4j.Level;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.listener.GloballyAttachableListener;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
public class Loader {
    @Getter
    private Map<String, ICommand> loadedCommands;
    private final Main main;
    private final DiscordApiBuilder builder;
    public void loadListeners(String prefix) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Reflections refl = new Reflections(prefix);
        Set<Class<? extends IListener>> classes = refl.getSubTypesOf(IListener.class);
        for (Class<? extends IListener> listener : classes) {
            builder.addListener((GloballyAttachableListener) listener.getDeclaredConstructor(new Class[]{Main.class}).newInstance(main));
            new Logger(main, true, false, "Loaded event", "Loaded event: " + listener.getSimpleName(), Level.DEBUG);
        }
    }
    public void loadCommands(String prefix) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Reflections refl = new Reflections(prefix);
        Set<Class<? extends ICommand>> classes = refl.getSubTypesOf(ICommand.class);
        loadedCommands = new HashMap<>(classes.size());
        for (Class<? extends ICommand> command : classes) {
            if(main.getConfig().isCommandEnabled(command.getSimpleName())){
                loadedCommands.put(command.getSimpleName(), command.getDeclaredConstructor().newInstance());
                new Logger(main, true, false, "Loaded command", "Loaded command: " + command.getSimpleName(), Level.DEBUG);
            }
        }
    }
}
