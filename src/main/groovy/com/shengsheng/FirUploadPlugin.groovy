package com.shengsheng

import com.shengsheng.extension.FirTokenPluginExtension
import com.shengsheng.extension.VariantsTokenExtension
import com.shengsheng.task.FirReleaseTask
import com.shengsheng.task.FirDebugTask
import org.gradle.api.Plugin
import org.gradle.api.Project

class FirUploadPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        def extension = project.extensions.create("firUploader", FirTokenPluginExtension)
        extension.extensions.create('debug', VariantsTokenExtension)
        extension.extensions.create('release', VariantsTokenExtension)


        project.tasks.create('firDebug', FirDebugTask)
        project.tasks.create('firRelease', FirReleaseTask)

    }
}