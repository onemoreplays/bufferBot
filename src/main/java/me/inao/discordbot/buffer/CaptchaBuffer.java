package me.inao.discordbot.buffer;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class CaptchaBuffer {
    private int counter = 0;
    private ArrayList<String> ids = new ArrayList<>();

    public void addCount() {
        this.counter += 1;
    }
}
