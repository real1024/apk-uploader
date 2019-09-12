package io.derl.fir;

import io.derl.fir.api.UploadService;
import io.derl.fir.model.Binary;

public class UploadWorker{

    private String mFirToken;
    private String mApkPath;
    private String mChangeLog;

    public UploadWorker(String firToken, String apkPath, String changeLog){
        mFirToken = firToken;
        mApkPath = apkPath;
        mChangeLog = changeLog;
    }

    public void upload() {
        if (mApkPath == null) {
            System.out.println("> Apk file not found.");
            throw new IllegalArgumentException("Apk file not found.");
        }
        Binary binary = new Binary(mApkPath);
        System.out.println("> Analysis Apk...\n" + binary);
        UploadService uploadService = new UploadService();
        uploadService.sendBuild(mApkPath, mFirToken, binary, mChangeLog, new UploadService.UploadServiceDelegate() {

            private long total;
            private String anim= "|/-\\";
            @Override
            public void onUploadFinished(boolean finishedSuccessful) {
            }

            @Override
            public void onPackageSizeComputed(long totalSize) {
                total = totalSize;
            }

            @Override
            public void onProgressChanged(long progress) {
                if (total == -1) {
                    return;
                }
                float percent = progress * 100.f / total;
                if ((int) percent == 100) {
                    anim = ">";
                }
                String msg = "\r" + anim.charAt((int)percent % anim.length()) + " Progress: [";
                System.out.print(msg);

                int all = 100 / 10 * 2;
                int cost = (int) (percent / 10 * 2);
                for (int i = 0; i < cost; i++) {
                    System.out.print("#");
                }
                for (int i = 0; i < all - cost; i++) {
                    System.out.print(".");
                }
                System.out.print(String.format(" %.1f%%]", percent));
                System.out.flush();
            }
        });
    }


}
