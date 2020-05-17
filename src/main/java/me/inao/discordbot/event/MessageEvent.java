package me.inao.discordbot.event;

import lombok.Getter;
import me.inao.discordbot.Main;
import me.inao.discordbot.exception.NoSuchServerException;
import me.inao.discordbot.exception.NoSuchServerTextChannelException;
import me.inao.discordbot.exception.NoSuchUserException;
import me.inao.discordbot.ifaces.ICommand;
import me.inao.discordbot.util.ExceptionCatcher;
import me.inao.discordbot.util.MessageSender;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.reflections.Reflections;

import java.awt.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Getter
public class MessageEvent implements MessageCreateListener {
	private final Main instance;

	private Map<String, ICommand> loadedCommands;

	public MessageEvent(Main instance) {
		this.instance = instance;
		try {
			this.loadCommands();
		} catch (Exception e1) {
			new ExceptionCatcher(e1);
		}
	}

	private void loadCommands() throws Exception {
		Reflections refl = new Reflections("me.inao.discordbot.command");
		Set<Class<? extends ICommand>> classes = refl.getSubTypesOf(ICommand.class);
		loadedCommands = new HashMap<>(classes.size());

		for (Class<? extends ICommand> clazz : classes) {
			if(instance.getConfig().isCommandEnabled(clazz.getSimpleName())){
				loadedCommands.put(clazz.getSimpleName(), clazz.getDeclaredConstructor().newInstance());
			}
		}
	}

	@Override
	public void onMessageCreate(MessageCreateEvent e) {
		if(e.getMessage().isPrivateMessage()){
			return;
		}
		if(e.getMessageAuthor().isBotUser()){
			return;
		}
		e.getServer().orElseThrow(NoSuchServerException::new).getRolesByName(instance.getConfig().getFeatureData("captchaSystem").split(";")[1]).iterator().forEachRemaining(role ->{
			try{
				if(role.getUsers().contains(e.getMessageAuthor().asUser().orElseThrow(NoSuchUserException::new))) {
					PreparedStatement stmnt = instance.getSqlite().openConnection().prepareStatement("SELECT solution FROM captcha WHERE userid = ?");
					stmnt.setString(1, e.getMessageAuthor().getIdAsString());
					ResultSet resultSet = instance.getSqlite().getResults(stmnt);
					resultSet.next();
					if (e.getMessageContent().equalsIgnoreCase(resultSet.getString("solution"))) {
						resultSet.close();
						e.getMessageAuthor().asUser().orElseThrow(NoSuchUserException::new).addRole(e.getServer().get().getRolesByName(instance.getConfig().getFeatureData("captchaSystem").split(";")[2]).get(0));
						e.getMessageAuthor().asUser().orElseThrow(NoSuchUserException::new).removeRole(e.getServer().get().getRolesByName(instance.getConfig().getFeatureData("captchaSystem").split(";")[1]).get(0));
						e.getServerTextChannel().orElseThrow(NoSuchServerTextChannelException::new).delete();
						stmnt = instance.getSqlite().openConnection().prepareStatement("DELETE FROM captcha WHERE userid = ?");
						stmnt.setString(1, e.getMessageAuthor().getIdAsString());
						instance.getSqlite().execute(stmnt);
						stmnt.getConnection().close();
					} else {
						e.getMessage().delete();
						new MessageSender("Captcha error", instance.getConfig().getMessage("captcha", "error"), Color.RED, e.getChannel());
					}
					stmnt.close();
					stmnt.getConnection().close();
				}
				}catch (Exception exception){
					new ExceptionCatcher(exception);
				}
		});


		if(instance.getConfig().isFeatureEnabled("channelLimit") || instance.getConfig().isCommandEnabled("Count")){
			if(e.getMessageContent().startsWith(String.valueOf(instance.getConfig().getPrefix()))){
				if(!(e.getMessageAuthor().canManageMessagesInTextChannel()) && !(e.getServerTextChannel().orElseThrow(NoSuchServerTextChannelException::new).getName().equals(instance.getConfig().getFeatureData("channelLimit")))){
					return;
				}
			}if(e.getServerTextChannel().orElseThrow(NoSuchServerTextChannelException::new).getName().equals(instance.getConfig().getCommandRoom("Count"))){
				if(e.getMessage().getAuthor().isBotUser()) return;
				else if(instance.getCountgame() == null) return;
				instance.getCountgame().addCount(e.getMessage());
			}
		}
		if (!e.getMessageContent().isEmpty() && e.getMessage().getContent().startsWith(String.valueOf(instance.getConfig().getPrefix()))) {
			String cmd = e.getMessageContent().split(" ")[0].substring(1);
			String[] args = (e.getMessageContent().length() <= cmd.length() + 2) ? new String[0] : e.getMessageContent().substring(cmd.length() + 2).split(" ");

			Optional<ICommand> command = loadedCommands.entrySet()
					.stream()
					.filter(entry -> cmd.equalsIgnoreCase(entry.getKey()))
					.map(Map.Entry::getValue)
					.findAny();

			if(command.isPresent()) {
				command.get().onCommand(instance, e.getMessage(), args);
			} else {
				e.getChannel().sendMessage("Unknown command!");
			}
		}
	}
}
