package io.derl.fir

import io.derl.fir.extension.FirTokenPluginExtension
import io.derl.fir.extension.VariantsTokenExtension
import io.derl.fir.task.FirReleaseTask
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