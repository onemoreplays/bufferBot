package me.inao.discordbot.event;

import lombok.RequiredArgsConstructor;
import me.inao.discordbot.Main;
import org.javacord.api.event.server.role.UserRoleRemoveEvent;
import org.javacord.api.listener.server.role.UserRoleRemoveListener;

@RequiredArgsConstructor
public class UserChangeGroupEvent implements UserRoleRemoveListener {
    private final Main main;
    @Override
    public void onUserRoleRemove(UserRoleRemoveEvent event) {
        
    }
}
