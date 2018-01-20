package com.dingding.notice;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by DELL on 2017/9/15.
 */
@Service
public class HttpHelper {
    private final String POST = "post";
    private final String GET = "get";

    public Object doPost(String url, JSONObject param) {
        return httpInvoke(url, POST, param);
    }

    public Object doGet(String url) {
        return httpInvoke(url, GET, new JSONObject());
    }

    public String composeUrl(String base) {
        if (!base.startsWith("http")) {
            base = "http://" + base;
        }
        if (!base.endsWith("/")) {
            base += "/";
        }
        return base;
    }

    private Object httpInvoke(String url, String method, JSONObject param) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        try {
            HttpUriRequest request = getRequest(method, url, param.toJSONString());
//            return extractArpResponse(httpClient.execute(request, getDefaultHandler()));
            return httpClient.execute(request, getDefaultHandler());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public HttpUriRequest getRequest(String method, String url, String param) throws UnsupportedEncodingException {
        HttpUriRequest request = null;
        if (method.equalsIgnoreCase(POST)) {
            request = new HttpPost(url);
            StringEntity params = new StringEntity(param,"UTF-8");
            params.setContentEncoding("UTF-8");
            request.setHeader("content-type", "application/json");
            ((HttpPost) request).setEntity(params);
        } else if (method.equalsIgnoreCase(GET)) {
            request = new HttpGet(url);
        }
        return request;
    }

    private ResponseHandler<JSONObject> getDefaultHandler() {
        return new ResponseHandler<JSONObject>() {
            public JSONObject handleResponse(HttpResponse httpResponse) throws IOException {
                int status = httpResponse.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = httpResponse.getEntity();
                    return entity != null ? (JSONObject) JSONObject.parse(EntityUtils.toString(entity,"UTF-8")) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            }
        };
    }

    private Object extractArpResponse(JSONObject response) {
        if (response != null) {

            if (response.getString("success").equalsIgnoreCase("true")) {
                return response.get("data");
            } else {
                throw new RuntimeException(response.getString("errormsg"));
            }
        } else {
            throw new RuntimeException("failure to get result;");
        }
    }
}
