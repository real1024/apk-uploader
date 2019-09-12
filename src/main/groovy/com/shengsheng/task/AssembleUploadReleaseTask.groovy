package com.shengsheng.task

import com.shengsheng.UploadWorker
import com.shengsheng.utils.FirUtils
import org.gradle.api.tasks.TaskAction

class AssembleUploadReleaseTask extends BaseTask {

    AssembleUploadReleaseTask() {
        group = "upload"
        dependsOn("assembleRelease")
    }

    @TaskAction
    void doAction() {
        new UploadWorker(project.extensions.apkUploader.release.token,
                FirUtils.getApkPath(project.buildDir, false), "").upload()
    }

}