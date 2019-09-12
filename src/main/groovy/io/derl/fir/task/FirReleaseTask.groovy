package io.derl.fir.task

import io.derl.fir.UploadWorker
import io.derl.fir.utils.FirUtils
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class FirReleaseTask extends DefaultTask {

    FirReleaseTask() {
        group = "upload"
        dependsOn(":mainModule:assembleRelease")
    }

    @TaskAction
    void doAction() {
        new UploadWorker(project.extensions.firUploader.release.token,
                FirUtils.getApkPath(false), "").upload()
    }

}