package me.inao.discordbot.request.http;

import me.inao.discordbot.ifaces.IHttpRequest;

import java.util.HashMap;
import java.util.Map;

public class Captcha implements IHttpRequest {
    private final Map<String, String> args = new HashMap<>();
    @Override
    public String getUrl() {
        return "http://127.0.0.1:8000/endpoint/captcha/";
    }

    @Override
    public Map<String, String> getArguments() {
        return args;
    }
}
