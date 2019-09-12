package io.derl.fir


import org.gradle.api.Plugin
import org.gradle.api.Project

class FirUploadPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        def extension = project.extensions.create("firUploader", FirTokenPluginExtension)
        extension.extensions.create('debug', VariantsTokenExtension)
        extension.extensions.create('release', VariantsTokenExtension)

        String changeLog = ""
        if (project.hasProperty("ChangeLog")) {
            changeLog = project.property("ChangeLog")
        }

        project.tasks.register('firDebug') {
            it.group = "upload"
            doFirst {
                new UploadWorker(extension.debug.token, getApkPath(true), changeLog).upload()
            }
        }

        project.tasks.register('firRelease') {
            it.group = "upload"
            doFirst {
                new UploadWorker(extension.release.token, getApkPath(false), changeLog).upload()
            }
        }
    }

    static def getApkPath(boolean isDebug) {
        def dir
        if (isDebug) {
            dir = new File("./mainModule/build/outputs/apk/debug")
        } else {
            dir = new File("./mainModule/build/outputs/apk/release")
        }
        def apk_path = null
        dir.listFiles().each { file ->
            if (file.name.endsWith(".apk")) {
                apk_path = file.getAbsolutePath()
            }
        }
        return apk_path
    }
}