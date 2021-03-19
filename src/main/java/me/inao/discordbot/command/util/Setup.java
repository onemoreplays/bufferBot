package me.inao.discordbot.command.util;

import com.google.gson.JsonObject;
import me.inao.discordbot.Main;
import me.inao.discordbot.annotation.Permission;
import me.inao.discordbot.ifaces.ICommand;
import me.inao.discordbot.ifaces.IParameter;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.permission.Permissions;
import org.javacord.api.entity.permission.PermissionsBuilder;
import org.javacord.api.entity.permission.RoleBuilder;
import org.javacord.api.entity.server.Server;

import java.awt.*;
import java.util.List;

@Permission
public class Setup implements ICommand {
    Server server;
    public void onCommand(Main instance, Message message, String[] args) {
//        message.getServer().ifPresent(server->{
//            this.server = server;
//        });
//        if(!instance.getPermissionable().hasPermission(message, this.getClass()))
//            new MessageSender("No Permissions", instance.getConfig().getMessage("generic", "no_perms"), Color.RED, message.getChannel());
//        //this.setupGroups(instance.getConfig().getSetup().get(0).getAsJsonObject("groups"));
//        this.setupCategories(instance.getConfig().getSetup().get(0).getAsJsonObject("categories"));
    }

    @Override
    public void onCommand(Main instance, Message message, List<IParameter> args) {

    }

    @Override
    public Class<? extends IParameter>[] requiredParameters() {
        return new Class[0];
    }

    private void setupGroups(JsonObject groups){
        groups.entrySet().forEach(stringJsonElementEntry -> {
            JsonObject json = stringJsonElementEntry.getValue().getAsJsonObject();
            int permissions = json.get("perms").getAsInt();
            RoleBuilder builder = server.createRoleBuilder()
                    .setName(this.capitalize(stringJsonElementEntry.getKey()))
                    .setColor(Color.decode(json.get("color").getAsString()));
            if(permissions != 0){
                builder.setPermissions(Permissions.fromBitmask(permissions));
            }else{
                builder.setPermissions(new PermissionsBuilder().setAllDenied().build());
            }
            try {
                builder.create().get();
            } catch (Exception e){
                System.out.println("Possibly missing server permissions. To fix this, create new role, give it full permissions and use it only for bot.");
            }
        });
    }
    private void setupCategories(JsonObject categories){
        categories.entrySet().forEach(categoryEntry -> this.server.createChannelCategoryBuilder()
                .setName(this.capitalize(categoryEntry.getKey()))
                .create());
    }

    private String capitalize(String str)
    {
        if(str == null) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
