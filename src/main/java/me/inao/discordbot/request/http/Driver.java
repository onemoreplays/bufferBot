package me.inao.discordbot.request.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import me.inao.discordbot.ifaces.IHttpRequest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
public class Driver {
    private final IHttpRequest requestClass;

    public String postRequestWithResponse() {
        try {
            HttpURLConnection conn = this.getHttpURLConnection(requestClass.getUrl());
            if(conn == null){
                return null;
            }
            Gson gson = new GsonBuilder().create();
            byte[] json = gson.toJson(requestClass.getArguments()).getBytes(StandardCharsets.UTF_8);
            conn.setFixedLengthStreamingMode(json.length);
            conn.connect();
            OutputStream out = conn.getOutputStream();
            out.write(json);
            out.flush();
            String response = null;
            if(conn.getResponseCode() == 200){
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                response = sb.toString();
            }
            conn.disconnect();
            out.close();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean postRequestWithoutResponse(){
        try{
            HttpURLConnection conn = this.getHttpURLConnection(requestClass.getUrl());
            if(conn == null){
                return false;
            }
            Gson gson = new GsonBuilder().create();
            byte[] json = gson.toJson(requestClass.getArguments()).getBytes(StandardCharsets.UTF_8);
            conn.setFixedLengthStreamingMode(json.length);
            conn.connect();
            OutputStream out = conn.getOutputStream();
            out.write(json);
            out.flush();
            conn.disconnect();
            out.close();
            return true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    private HttpURLConnection getHttpURLConnection(String url){
        try{
            URL urlInside = new URL(url);
            HttpURLConnection conn = ((HttpURLConnection) urlInside.openConnection());
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            return conn;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
