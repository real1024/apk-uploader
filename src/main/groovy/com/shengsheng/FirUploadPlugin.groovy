package com.shengsheng

import com.shengsheng.extension.FirTokenPluginExtension
import com.shengsheng.extension.VariantsTokenExtension
import com.shengsheng.task.UploadDebugTask
import com.shengsheng.task.UploadReleaseTask
import com.shengsheng.task.AssembleUploadReleaseTask
import com.shengsheng.task.AssembleUploadDebugTask
import org.gradle.api.Plugin
import org.gradle.api.Project

class FirUploadPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        def extension = project.extensions.create("apkUploader", FirTokenPluginExtension)
        extension.extensions.create('debug', VariantsTokenExtension)
        extension.extensions.create('release', VariantsTokenExtension)


        project.tasks.create('assembleUploadDebug', AssembleUploadDebugTask)
        project.tasks.create('uploadDebug', UploadDebugTask)
        project.tasks.create('assembleUploadRelease', AssembleUploadReleaseTask)
        project.tasks.create('uploadRelease', UploadReleaseTask)

    }
}