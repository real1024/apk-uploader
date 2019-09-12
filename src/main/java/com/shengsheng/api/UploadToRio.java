package com.shengsheng.api;

import com.shengsheng.model.UploadTicket;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.ArrayList;

public class UploadToRio {
    public static final String FIR_UPLOAD_TOKEN_URL = "http://fir.im/api/v2/info/" ;
    public static final String FIR_UPDATE_APP_INFO = "http://fir.im/api/v2/app/"  ;
    public static final String FIR_BASE_URL = "http://api.fir.im" ;

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public  String versionId;
    public String token;
    public String appName;
    public String appId;
    public String versionName;
    public String versionCode;
    public String changeLog;
    public String appShort;
    public String appOid;
    public UploadTicket uploadTicket;

    public UploadToRio(String appId,String token,String appName,String versionName,String versionCode,String changeLog){
        this.appId = appId;
        this.appName = appName;
        this.versionCode = versionCode;
        this.changeLog = changeLog;
        this.versionName = versionName;
        this.token = token;

        uploadTicket = new UploadTicket(getUploadToken()) ;

    }

    /**
     * 获取上传token
     * @return   JSONObject
     */
    public JSONObject getUploadToken(){
        try {
            HttpClient httpClient = new DefaultHttpClient() ;
            String url = FIR_BASE_URL + "/apps" ;
            String type = "android";
            HttpPost post = new HttpPost(url);

            ArrayList<BasicNameValuePair> postParameters = new ArrayList<BasicNameValuePair>();
            postParameters.add(new BasicNameValuePair("type", type));
            postParameters.add(new BasicNameValuePair("bundle_id", this.appId));
            postParameters.add(new BasicNameValuePair("api_token",this.token));
            post.setEntity(new UrlEncodedFormEntity(postParameters, "UTF-8"));

            HttpResponse response = httpClient.execute(post) ;
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity, "UTF-8");
            return new JSONObject(responseString);
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }

    /**
     * 上传Binary
     * @return
     */
    public JSONObject uploadBinary(){

        return new JSONObject();
    }

    /**
     * 上传icon
     * @return
     */
    public JSONObject uploadIcon(){
        return new JSONObject() ;
    }

}

