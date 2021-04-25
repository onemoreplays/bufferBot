package me.inao.discordbot.request.http.post;

import me.inao.discordbot.ifaces.IHttpRequest;

import java.util.HashMap;
import java.util.Map;

public class CaptchaCreatePostRequest implements IHttpRequest {
    private final Map<String, Object> args = new HashMap<>();
    @Override
    public String getUrl() {
        return "https://inao.xn--6frz82g/endpoint/captcha/";
    }

    @Override
    public Map<String, Object> getArguments() {
        return args;
    }
}
