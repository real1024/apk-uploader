import io.derl.fir.UploadWorker
import io.derl.fir.utils.FirUtils
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class FirDebugTask extends DefaultTask {

    FirDebugTask() {
        group = "upload"
//        dependsOn(":mainModule:assembleDebug")
    }

    @TaskAction
    void doAction() {
        new UploadWorker(project.extensions.firUploader.debug.token,
                FirUtils.getApkPath(true), "").upload()
    }
}