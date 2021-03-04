package me.inao.discordbot.util;

import me.inao.discordbot.ifaces.IParameter;

import java.util.List;
import java.util.stream.Collectors;

public class ParamsUtil {
    public static List<Object> filterObject(Class<? extends IParameter> cls, List<IParameter> items){
        return items.stream().filter(cls::isInstance).map(cls::cast).collect(Collectors.toList());
    }
}
