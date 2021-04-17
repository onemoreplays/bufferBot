package me.inao.discordbot.ifaces;

import java.util.Map;

public interface IHttpRequest {
    String getUrl();
    Map<String, Object> getArguments();
}
