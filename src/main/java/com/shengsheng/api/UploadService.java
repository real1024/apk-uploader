package com.shengsheng.api;

import com.shengsheng.model.Binary;
import com.shengsheng.model.CustomMultiPartEntity;
import com.shengsheng.model.SearchFile;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.File;
import java.nio.charset.Charset;


/**
 * 上传服务
 */
public class UploadService implements CustomMultiPartEntity.ProgressListener {


    /**
     * Used to notify the status of the upload action
     */
    public UploadServiceDelegate uploadServiceDelegate;
    public CustomMultiPartEntity iconMultipartEntity;
    public CustomMultiPartEntity multipartEntity;
    public HttpPost post;

    /**
     * 向FIR上传文件
     *
     * @param filePath
     * @param apiToken
     * @param binary
     * @param appChanglog
     * @param delegate
     */
    public void sendBuild(final String filePath, final String apiToken, final Binary binary, final String appChanglog, UploadServiceDelegate delegate) {

        uploadServiceDelegate = delegate;

        UploadToRio uploadToRio = new UploadToRio(binary.getBundleId(), apiToken, binary.getName(), binary.getVersionName(), binary.getVersionCode(), appChanglog);
        String url1 = uploadToRio.uploadTicket.binaryUploadUrl;
        try {
            HttpClient client;
            client = new DefaultHttpClient();

            post = new HttpPost(url1);
            /*****************************************upload icon***********************************************/
            System.out.println("> Upload Icon...");
            SearchFile searchFile = new SearchFile(filePath);
            try {
                if (!binary.getIcon().isEmpty()) {
                    InputStreamBody iconToUpload = searchFile.query(binary.getIcon());
                    iconMultipartEntity = new CustomMultiPartEntity(UploadService.this);
                    // set the api token
                    iconMultipartEntity.addPart("key", new StringBody(uploadToRio.uploadTicket.iconKey));
                    iconMultipartEntity.addPart("token", new StringBody(uploadToRio.uploadTicket.iconToken));
                    iconMultipartEntity.addPart("file", iconToUpload);

                    if (uploadServiceDelegate != null) {
                        // send the full package size
                        uploadServiceDelegate.onPackageSizeComputed(iconMultipartEntity.getContentLength());
                    } else {
                        return;
                    }

                    post.setEntity(iconMultipartEntity);

                    // POST the build
                    HttpResponse iconResponse = client.execute(post);
                    HttpEntity iconEntity = iconResponse.getEntity();
                    String iconResponseString = EntityUtils.toString(iconEntity, "UTF-8");

                    JSONObject iconJsonObject = new JSONObject(iconResponseString);

                    if (iconResponse.getStatusLine().getStatusCode() == 200) {
                        System.out.println("> Upload Icon Done.");
                    }
                    searchFile.zipFile.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            /*****************************************upload file***********************************************/
            System.out.println("> Upload Apk...");
            // get the apk file
            File fileToUpload = new File(filePath);

            multipartEntity = new CustomMultiPartEntity(UploadService.this);
            // set the api token
            multipartEntity.addPart("key", new StringBody(uploadToRio.uploadTicket.binaryKey));
            multipartEntity.addPart("token", new StringBody(uploadToRio.uploadTicket.binaryToken));
            multipartEntity.addPart("file", new FileBody(fileToUpload));
            multipartEntity.addPart("x:name", new StringBody(uploadToRio.appName, Charset.forName("UTF-8")));
            multipartEntity.addPart("x:version", new StringBody(uploadToRio.versionName, Charset.forName("UTF-8")));
            multipartEntity.addPart("x:build", new StringBody(uploadToRio.versionCode));
            multipartEntity.addPart("x:changelog", new StringBody(uploadToRio.changeLog, Charset.forName("UTF-8")));

            if (uploadServiceDelegate != null) {
                // send the full package size
                uploadServiceDelegate.onPackageSizeComputed(multipartEntity.getContentLength());
            }

            post.setEntity(multipartEntity);

            // POST the build
            HttpResponse response = client.execute(post);
            HttpEntity entity = response.getEntity();
            String responseString = EntityUtils.toString(entity, "UTF-8");
            JSONObject jsonObject = new JSONObject(responseString);

            if (response.getStatusLine().getStatusCode() == 200) {
                System.out.println("> Upload Apk Done.");
                System.out.println("> Go to http://fir.im/"+uploadToRio.uploadTicket.appShort);
                if (uploadServiceDelegate != null) {
                    // send success upload status
                    uploadServiceDelegate.onUploadFinished(true);
                }

            } else {
                System.out.println("> Upload Apk Failed. Please try again later.");
                if (uploadServiceDelegate != null) {
                    // send failed upload status
                    uploadServiceDelegate.onUploadFinished(false);
                }
            }


        } catch (Exception e) {
            // Ups! error occurred
            e.printStackTrace();
            if (uploadServiceDelegate != null) {
                // send failed upload status
                uploadServiceDelegate.onUploadFinished(false);
            }
        }


    }

    @Override
    public void transferred(long num) {

        if (uploadServiceDelegate != null) {
            uploadServiceDelegate.onProgressChanged(num);
        }

    }

    /**
     * Upload service callback interface used to notify uploading actions like status or progress
     */
    public interface UploadServiceDelegate {

        /**
         * Called when the upload is done, even if an error occurred
         *
         * @param finishedSuccessful this flag is true if the upload was made successfully, false otherwise
         */
        public void onUploadFinished(boolean finishedSuccessful);

        public void onPackageSizeComputed(long totalSize);

        public void onProgressChanged(long progress);

    }

}
