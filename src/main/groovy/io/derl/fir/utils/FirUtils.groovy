package io.derl.fir.utils

class FirUtils {

    /**
     * 获取apk路径
     */
    static def getApkPath(boolean isDebug) {
        def apkDir = new File(isDebug ?
                './mainModule/build/outputs/apk/debug' :
                './mainModule/build/outputs/apk/release')
        def apk_path = null
        apkDir.listFiles().each { file ->
            if (file.name.endsWith(".apk")) {
                apk_path = file.getAbsolutePath()
            }
        }
        return apk_path
    }

}