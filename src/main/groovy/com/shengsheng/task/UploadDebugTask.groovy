package com.shengsheng.task

import com.shengsheng.UploadWorker
import com.shengsheng.utils.FirUtils
import org.gradle.api.tasks.TaskAction

class UploadDebugTask extends BaseTask {

    UploadDebugTask() {
        group = "upload"
    }

    @TaskAction
    void doAction() {
        new UploadWorker(project.extensions.apkUploader.debug.token,
                FirUtils.getApkPath(project.buildDir, true), "").upload()
    }
}