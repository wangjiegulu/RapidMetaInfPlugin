package com.wangjiegulu.plg.rapidmetainf

import com.android.build.gradle.*
import org.gradle.api.Plugin
import org.gradle.api.Project

class RapidMetaInfPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.extensions.create(RapidMetaInfConstants.EXTENSION_NAME, RapidMetaInfExtension)

//        def rapidMetaInfTask = project.tasks.create("rapidMetaInfTask", RapidMetaInfTask.class)
        RapidMetaInfTask rapidMetaInfTask = project.task("rapidMetaInfTask", type: RapidMetaInfTask)

        def resDir = new File(project.buildDir, RapidMetaInfConstants.GENERATE_RES_PATH)
//    def resDir = new File(buildDir, 'generated/res/resValues/debug/')
        def destDir = new File(resDir, 'META-INF/')

        boolean isLibrary = project.plugins.hasPlugin(LibraryPlugin)
        if (isLibrary) {
            project.extensions.getByType(LibraryExtension).sourceSets({
                main.resources.srcDirs += resDir
            })
        } else if (project.plugins.hasPlugin(TestPlugin)) {
            project.extensions.getByType(TestExtension).sourceSets({
                main.resources.srcDirs += resDir
            })
        } else if (project.plugins.hasPlugin(AppPlugin)) {
//            project.extensions.getByType(AppExtension).sourceSets.main.resources.srcDirs += resDir
            project.extensions.getByType(AppExtension).sourceSets({
                main.resources.srcDirs += resDir
            })
        }
        rapidMetaInfTask.setDestDir(destDir)


        project.afterEvaluate {
            project.tasks.findAll { task ->
                task.name.startsWith('generate') && task.name.endsWith('Resources')
            }.each { t ->
                println("each...t: " + t.name)
                t.dependsOn rapidMetaInfTask
            }
        }


    }
}