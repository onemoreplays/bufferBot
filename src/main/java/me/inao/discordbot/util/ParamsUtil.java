package me.inao.discordbot.util;

import me.inao.discordbot.ifaces.IParameter;

import java.util.List;
import java.util.stream.Collectors;

public class ParamsUtil {
    public static List<Object> filterObject(Class<? extends IParameter> cls, List<IParameter> items){
        List<Object> filter = items.stream().filter(cls::isInstance).map(cls::cast).collect(Collectors.toList());
        if(filter.size() < 1){
            return null;
        }
        return filter;
    }
}
