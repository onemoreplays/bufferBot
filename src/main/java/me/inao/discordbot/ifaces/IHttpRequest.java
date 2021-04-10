package me.inao.discordbot.ifaces;

import java.util.Map;

public interface IHttpRequest {
    public String getUrl();
    public Map<String, String> getArguments();
}
