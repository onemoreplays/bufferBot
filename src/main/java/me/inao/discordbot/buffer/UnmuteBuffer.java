package me.inao.discordbot.buffer;

import lombok.RequiredArgsConstructor;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.user.User;

import java.util.TimerTask;

@RequiredArgsConstructor
public class UnmuteBuffer extends TimerTask {
    private final User user;
    private final Role removeRole;
    @Override
    public void run() {
        user.removeRole(removeRole);
    }
}
