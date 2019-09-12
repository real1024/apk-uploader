package com.shengsheng.model;

import org.apache.http.entity.mime.content.InputStreamBody;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class SearchFile {
    public String url;
    public ZipFile zipFile;

    public SearchFile(String url){
        this.url = url;
    }
    public InputStreamBody query(String name){

        InputStreamBody fileContent = null;
        try {
            zipFile = new ZipFile(new File(url));
            Enumeration<?> enumeration = zipFile.entries();
            ZipEntry zipEntry;

            while (enumeration.hasMoreElements()) {
                zipEntry = (ZipEntry) enumeration.nextElement();
                if (zipEntry.isDirectory()) {

                } else {
                    if (name.equals(zipEntry.getName())) {
                        InputStream inputStream = zipFile.getInputStream(zipEntry);
                        fileContent=new InputStreamBody(inputStream,name);
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContent;
    }
}
