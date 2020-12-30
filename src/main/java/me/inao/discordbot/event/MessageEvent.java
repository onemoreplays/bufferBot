package me.inao.discordbot.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.inao.discordbot.Main;
import me.inao.discordbot.buffer.UnmuteBuffer;
import me.inao.discordbot.exception.NoSuchServerException;
import me.inao.discordbot.exception.NoSuchServerTextChannelException;
import me.inao.discordbot.exception.NoSuchUserException;
import me.inao.discordbot.ifaces.ICommand;
import me.inao.discordbot.ifaces.IListener;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import java.util.Map;
import java.util.Optional;
import java.util.Timer;

@Getter
@RequiredArgsConstructor
public class MessageEvent implements MessageCreateListener, IListener {
	private final Main instance;


	@Override
	public void onMessageCreate(MessageCreateEvent e) {
		if(e.getMessage().isPrivateMessage()){
			return;
		}
		if(e.getMessageAuthor().isBotUser()){
			return;
		}
		if(instance.getConfig().isFeatureEnabled("channelLimit") || instance.getConfig().isCommandEnabled("Count")){
			if(e.getMessage().getAuthor().isBotUser()) { e.getMessage().delete(); return; }
			if(e.getMessageContent().startsWith(String.valueOf(instance.getConfig().getPrefix()))){
				if(!(e.getMessageAuthor().canManageMessagesInTextChannel()) && !(e.getServerTextChannel().orElseThrow(NoSuchServerTextChannelException::new).getName().equals(instance.getConfig().getFeatureData("channelLimit")))){
					e.getMessage().delete();
					return;
				}
			}
			if(e.getMessage().getChannel().asServerTextChannel().orElseThrow(NoSuchServerTextChannelException::new).getName().equals(instance.getConfig().getCommandRoom("Count"))){
				if(instance.getConfig().isFeatureEnabled("antiLink")){
					Role muteRole = e.getServer().orElseThrow(NoSuchServerException::new).getRolesByName(instance.getConfig().getFeatureData("tempMute")).get(0);
					e.getMessageAuthor().asUser().orElseThrow(NoSuchUserException::new).addRole(muteRole);
					new Timer().schedule(new UnmuteBuffer(e.getMessageAuthor().asUser().orElseThrow(NoSuchUserException::new), muteRole), (1000*60*60));
				}
				if(!(e.getMessageAuthor().canManageMessagesInTextChannel())){
					if(instance.getCountgame() == null) return;
				}
				if(!(e.getMessageContent().contains(String.valueOf(instance.getConfig().getPrefix()))) && !(instance.getCountgame() == null))instance.getCountgame().addCount(e.getMessage());
			}
		}
		if (!e.getMessageContent().isEmpty() && e.getMessage().getContent().startsWith(String.valueOf(instance.getConfig().getPrefix()))) {
			String cmd = e.getMessageContent().split(" ")[0].substring(1);
			String[] args = (e.getMessageContent().length() <= cmd.length() + 2) ? new String[0] : e.getMessageContent().substring(cmd.length() + 2).split(" ");

			Optional<ICommand> command = instance.getLoader().getLoadedCommands().entrySet()
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
