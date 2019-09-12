package com.shengsheng.task


import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class FirReleaseTask extends DefaultTask {

    FirReleaseTask() {
        group = "upload"
        dependsOn(":mainModule:assembleRelease")
    }

    @TaskAction
    void doAction() {
        new com.shengsheng.UploadWorker(project.extensions.firUploader.release.token,
                com.shengsheng.utils.FirUtils.getApkPath(false), "").upload()
    }

}