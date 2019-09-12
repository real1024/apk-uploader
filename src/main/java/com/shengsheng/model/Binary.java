package com.shengsheng.model;

import net.dongliu.apk.parser.ApkFile;
import net.dongliu.apk.parser.bean.ApkMeta;
import net.dongliu.apk.parser.bean.IconFace;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Binary {
    private String name;
    private String icon;
    private String bundleId;
    private String versionName;
    private String versionCode;
    private String apkPath;

    public Binary(String apkPath){
        this.apkPath = apkPath;
        parseApk(apkPath);
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public String getBundleId() {
        return bundleId;
    }


    public String getVersionName() {
        return versionName;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public String getApkPath() {
        return apkPath;
    }

    private void parseApk(String apkPath){
        ApkFile apkFile;
        try {
            apkFile = new ApkFile(new File(apkPath));
            ApkMeta apkMeta = apkFile.getApkMeta();

            List<IconFace> icons = apkFile.getAllIcons();
            for (IconFace icon : icons) {
                if (icon.getPath().contains("xxxhdpi")) {
                    this.icon = icon.getPath();
                }
            }

            this.versionName = apkMeta.getVersionName();
            this.versionCode = apkMeta.getVersionCode().toString();
            this.bundleId = apkMeta.getPackageName();
            this.name = apkMeta.getLabel();
            apkFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "> [" +
                "name='" + name + '\'' +
                ", versionName='" + versionName + '\'' +
                ", versionCode='" + versionCode + '\'' +
                ']';
    }
}

