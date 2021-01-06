package me.inao.discordbot.server.request;

import com.google.gson.Gson;
import me.inao.discordbot.ifaces.IRequest;
import me.inao.discordbot.server.Packet;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class CaptchaRequest implements IRequest {
    @Override
    public void onRequest(String url, Packet packet) {
        String json = new Gson().toJson(packet);
        try{
            URL link = new URL(url);
            HttpURLConnection connection = (HttpURLConnection)link.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setDoOutput(true);
            try(OutputStream os = connection.getOutputStream()) {
                byte[] input = json.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getType() {
        //0 means HTTP REST API
        return 0;
    }
}
