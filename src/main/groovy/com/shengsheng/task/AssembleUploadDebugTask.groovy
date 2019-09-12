package com.shengsheng.task

import com.shengsheng.UploadWorker
import com.shengsheng.utils.FirUtils
import org.gradle.api.tasks.TaskAction

class AssembleUploadDebugTask extends BaseTask {

    AssembleUploadDebugTask() {
        group = "upload"
        dependsOn("assembleDebug")
    }

    @TaskAction
    void doAction() {
        new UploadWorker(project.extensions.apkUploader.debug.token,
                FirUtils.getApkPath(true), "").upload()
    }
}