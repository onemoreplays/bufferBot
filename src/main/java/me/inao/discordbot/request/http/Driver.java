package me.inao.discordbot.request.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import me.inao.discordbot.ifaces.IHttpRequest;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
public class Driver {
    private final IHttpRequest requestClass;

    public boolean postRequest(){
        try{
            URL url = new URL(requestClass.getUrl());
            HttpURLConnection conn = ((HttpURLConnection)url.openConnection());
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            Gson gson = new GsonBuilder().create();
            byte[] json = gson.toJson(requestClass.getArguments()).getBytes(StandardCharsets.UTF_8);
            conn.setFixedLengthStreamingMode(json.length);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.connect();
            OutputStream out = conn.getOutputStream();
            out.write(json);
            Thread.sleep(500);
            out.flush();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//            System.out.println(reader.readLine());
            conn.disconnect();
            out.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
