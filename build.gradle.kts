/*
 * Copyright 2020 CHH2000day.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


@file:Suppress("UNUSED_VARIABLE")

plugins {
    kotlin("multiplatform") version "1.4.21"
}
group = "com.chh2000day"
version = "1.0"

repositories {
    mavenCentral()
}
//mingwX64 and Mac can only be built in their respective platform
val hostOs: String = System.getProperty("os.name")
val isMingwX64 = hostOs.startsWith("Windows")
val isMac = hostOs == "Mac OS X"
kotlin {
    @Suppress("SpellCheckingInspection")
    fun org.jetbrains.kotlin.gradle.plugin.mpp.DefaultCInteropSettings.setup() {
        compilerOpts("-DXPLM200=1 -DXPLM210=1 -DXPLM300=1 -DXPLM301=1 -DXPLM302=1 -DXPLM303=1")
        headers(File("${project.rootDir}/src/nativeInterop/cinterop/SDK/CHeaders/XPLM").listFiles()!!)
        headers(File("${project.rootDir}/src/nativeInterop/cinterop/SDK/CHeaders/Widgets").listFiles()!!)
//        Wrappers are c++ files,which aren't supported by Kotlin Native at this moment
//        headers(File("${project.rootDir}/src/nativeInterop/cinterop/SDK/CHeaders/Wrappers").listFiles()!!
//        { _, name ->
//            //We only want headers
//            name?.endsWith(
//                ".h"
//            ) ?: false
//        }!!
//        )
        includeDirs(
            "${project.rootDir}/src/nativeInterop/cinterop/SDK/CHeaders/XPLM",
            "${project.rootDir}/src/nativeInterop/cinterop/SDK/CHeaders/Widgets",
            "${project.rootDir}/src/nativeInterop/cinterop/SDK/CHeaders/Wrappers"
        )
    }
    linuxX64 {
        compilations["main"].cinterops {
            @Suppress("SpellCheckingInspection")
            val sdk by creating {
                setup()
            }
        }
        binaries {
            sharedLib {
                linkerOpts("-Lsdk")
            }
        }
    }
    //Platform associated config
    if (isMingwX64) {
        mingwX64 {
            compilations["main"].cinterops {
                @Suppress("SpellCheckingInspection")
                val sdk by creating {
                    setup()
                }
            }
            binaries {
                sharedLib {
                    linkerOpts(
                        "-Lsdk",
                        "-L${project.rootDir}/src/nativeInterop/cinterop/SDK/Libraries/Win/XPLM_64.lib",
                        "-L${project.rootDir}/src/nativeInterop/cinterop/SDK/Libraries/Win/XPWidgets_64.lib"
                    )
                }
            }
        }
    }
    if (isMac) {
        macosX64 {
            compilations["main"].cinterops {
                @Suppress("SpellCheckingInspection")
                val sdk by creating {
                    setup()
                }
            }
            binaries {
                sharedLib {
                    linkerOpts(
                        "-Lsdk",
                        "-L${project.rootDir}/src/nativeInterop/cinterop/SDK/Libraries/Mac/XPLM.framework/XPLM",
                        "-L${project.rootDir}/src/nativeInterop/cinterop/SDK/Libraries/Max/XPWidgets.framework/XPWidgets"
                    )
                }
            }
        }
    }
    sourceSets {
        val commonMain by getting
        val commonTest by getting
        val nativeMain by creating {
            dependsOn(commonMain)
        }
        val linuxX64Main by getting {
            dependsOn(nativeMain)
        }
        val linuxX64Test by getting
        //Platform associated config
        if (isMingwX64) {
            val mingwX64Main by getting {
                dependsOn(nativeMain)
            }
            val mingwX64Test by getting
        }
        if (isMac) {
            val macosX64Main by getting {
                dependsOn(nativeMain)
            }
            val macosX64Test by getting
        }


    }
}
