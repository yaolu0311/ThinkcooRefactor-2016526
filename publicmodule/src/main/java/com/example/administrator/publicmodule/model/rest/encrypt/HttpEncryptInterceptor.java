package com.example.administrator.publicmodule.model.rest.encrypt;

import com.example.administrator.publicmodule.util.log.ThinkcooLog;
import java.io.IOException;
import java.util.List;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Robert.yao on 2016/4/6.
 *
 * 请求加密拦截器
 *
 */
public class HttpEncryptInterceptor implements Interceptor {

    public static  final String TAG = "HttpEncryptInterceptor";

    public static final boolean WORK = true;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String url = request.url().toString();
        if (WORK  && !externalUrl(url)){
            HttpUrl httpUrl = request.url();
            request = createEncryptRequest(httpUrl,request);
        }
        return chain.proceed(request);
    }

    private boolean externalUrl(String url) {
        return url.startsWith("http://api.map.baidu.com/");
    }

    private Request createEncryptRequest(HttpUrl httpUrl, Request request) {

        ThinkcooLog.d(TAG,"=== 加密前整个URL ：" + httpUrl.toString() + " ===");
        String rawEncodedPath = createEncryptEncode(request.url().encodedPathSegments());
        httpUrl = httpUrl.newBuilder().encodedPath(rawEncodedPath).build();
        ThinkcooLog.d(TAG,"=== 加密后整个URL ：" + httpUrl.toString() + " ===");
        request = request.newBuilder().url(httpUrl).build();

        return  request;
    }

    private String createEncryptEncode(List<String> encodedPathSegments) {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder encryptBuilder = new StringBuilder();
        stringBuilder.append("/");
        stringBuilder.append(encodedPathSegments.get(0));
        for (int i = 0 ; i < encodedPathSegments.size() ; i++){
           if (i != 0){
               encryptBuilder.append(encodedPathSegments.get(i));
               if (i != encodedPathSegments.size()  -1){
                   encryptBuilder.append("/");
               }
           }
        }
        String encryptString = UrlDecodeUtil.newVersionUrlEncrypt(encryptBuilder.toString());
        stringBuilder.append("/");
        stringBuilder.append(encryptString);
        return  stringBuilder.toString();
    }
}
