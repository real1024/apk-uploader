package com.shengsheng.task


import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class FirDebugTask extends DefaultTask {

    FirDebugTask() {
        group = "upload"
//        dependsOn(":mainModule:assembleDebug")
    }

    @TaskAction
    void doAction() {
        new com.shengsheng.UploadWorker(project.extensions.firUploader.debug.token,
                com.shengsheng.utils.FirUtils.getApkPath(true), "").upload()
    }
}