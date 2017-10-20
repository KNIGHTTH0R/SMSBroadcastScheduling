package com.tlkm.broadcast5g.repository.remote;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class GetRequester {


    Logger logger = LoggerFactory.getLogger(GetRequester.class);


    public  String sendRequest(String uri, Map<String, String> params)
    throws IOException{

        OkHttpClient client = new OkHttpClient();


       // uri = uri+"lion/search/best_price";

        HttpUrl.Builder urlBuilder = HttpUrl.parse(uri).newBuilder();

        params.forEach((k,v)->
                {
                    urlBuilder.addQueryParameter(k,v);


                }
        );

        String url = urlBuilder.build().toString();

        logger.debug("url : "+url);
        logger.debug("param : "+params);

        Request request = new Request.Builder().
                url(url).build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}
