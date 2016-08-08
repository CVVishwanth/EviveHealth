package com.example.dell.asnavigation;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by dell on 7/29/2016.
 */
public class ServiceHandler {
    static String response=null;
    public final static int GET=1;
    public final static int POST=2;

    public ServiceHandler(){}

    public String makeSericeCall(String url,int method){
        return this.makeServiceCall(url, method, null);
    }

    public String makeServiceCall(String url,int method, List<NameValuePair> params){
        try{
            DefaultHttpClient hc=new DefaultHttpClient();
            HttpEntity he=null;
            HttpResponse hr=null;
            if(method==POST) {
                HttpPost hp = new HttpPost(url);
                if (params != null) {
                    hp.setEntity(new UrlEncodedFormEntity(params));
                }
                hr = hc.execute(hp);
            }
            else if(method==GET){
                if(params!=null){
                    String ps= URLEncodedUtils.format(params,"utf-8");
                url += "?"+ps;
                }
                HttpGet hg=new HttpGet(url);
                hr=hc.execute(hg);
            }

            he=hr.getEntity();
            response= EntityUtils.toString(he);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
