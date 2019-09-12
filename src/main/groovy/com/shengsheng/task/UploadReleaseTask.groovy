package com.shengsheng.task

import com.shengsheng.UploadWorker
import com.shengsheng.utils.FirUtils
import org.gradle.api.tasks.TaskAction

class UploadReleaseTask extends BaseTask {

    UploadReleaseTask() {
        group = "upload"
    }

    @TaskAction
    void doAction() {
        new UploadWorker(project.extensions.apkUploader.release.token,
                FirUtils.getApkPath(project.buildDir, false), "").upload()
    }

}