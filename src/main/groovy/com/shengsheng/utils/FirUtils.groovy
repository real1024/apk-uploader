package com.shengsheng.utils

class FirUtils {

    /**
     * 获取apk路径
     */
    static def getApkPath(File buildDir, boolean isDebug) {
        def apkDir = new File(buildDir, isDebug ?
                'outputs/apk/debug' :
                'outputs/apk/release')
        def apk_path = null
        apkDir.listFiles().each { file ->
            if (file.name.endsWith(".apk")) {
                apk_path = file.getAbsolutePath()
            }
        }
        return apk_path
    }

}