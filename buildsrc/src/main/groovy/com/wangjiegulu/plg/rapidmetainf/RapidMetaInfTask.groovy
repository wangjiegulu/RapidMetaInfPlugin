package com.wangjiegulu.plg.rapidmetainf

import com.android.build.gradle.internal.tasks.DefaultAndroidTask
import org.gradle.api.Action
import org.gradle.api.Task
import org.gradle.api.tasks.TaskAction

public class RapidMetaInfTask extends DefaultAndroidTask {
    public RapidMetaInfTask() {
        setVariantName("rapidmetainf_VariantName")
    }
    private File destDir

    public void setDestDir(File destDir) {
        this.destDir = destDir
    }

    @TaskAction
    def doExecute() {
        println("+-----------------------------------------------------------------------------+")
        println("|                      Rapid Meta Inf Plugin START                            |")
        println("+-----------------------------------------------------------------------------+")

        RapidMetaInfExtension rapidMetaInfExtension = project[RapidMetaInfConstants.EXTENSION_NAME]

        destDir.mkdirs()

        String metaInfName = rapidMetaInfExtension.metaInfName
        if (null == metaInfName || metaInfName.length() <= 0) {
            throw new RuntimeException("metaInfName can not be empty!\n" +
                    "rapidmetainf {\n" +
                    "    metaInfName '[your meta-inf file name]'\n" +
                    "}")
        }
        def vfile = new File(destDir, metaInfName)

//        def stdout = new ByteArrayOutputStream()
//        exec {
//            commandLine 'git', 'log', '-1', '--format=%H'
//            standardOutput = stdout
//        }
//        def git_hash = stdout.toString().trim()
//
//        stdout.reset()
//        exec {
//            commandLine 'git', 'config', 'remote.origin.url'
//            standardOutput = stdout
//        }
//        def remote = stdout.toString().trim()
//
//        def dirty = exec {
//            commandLine 'git', 'diff-index', '--quiet', 'HEAD'
//            ignoreExitValue true
//        }
//        dirty = dirty.getExitValue() == 0 ?
//                ' (Clean workspace)' : '-DIRTY using dirty workspace!'
//
//        def repo_info = "$remote at $git_hash$dirty"

//        vfile.text = "$repo_info\n"

        StringBuilder sb = new StringBuilder()
                .append("Generate-By=RapidMetaInfPlugin").append("\n")
                .append("Plugin-Url=https://github.com/wangjiegulu/RapidMetaInfPlugin").append("\n")
//                .append("pluginVersion=").append("\n")
        String[] metaInfProperties = rapidMetaInfExtension.metaInfProperties
        if (null != metaInfProperties && metaInfProperties.length > 0) {
            for (String metaInfProperty : rapidMetaInfExtension.metaInfProperties) {
                sb.append(metaInfProperty).append('\n')
            }
        } else {
            println("\n[WARN]metaInfProperties is EMPTY!")
        }

        def metaInfStr = sb.toString()
        vfile.text = metaInfStr

        println("\nMETA-INF properties generated in ${destDir.getAbsolutePath()}${File.separator}$metaInfName: \n\n$metaInfStr")

        println("+-----------------------------------------------------------------------------+")
        println("|                       Rapid Meta Inf Plugin END                             |")
        println("+-----------------------------------------------------------------------------+")
    }

    @Override
    Task doLast(Action<? super Task> action) {


        return super.doLast(action)
    }


}