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

import kotlinx.cinterop.*
import platform.posix.strcpy
import sdk.XPLMPluginID

/**
 * @Author CHH2000day
 * @Date 2020/12/14 15:59
 **/
@Suppress("FunctionName", "unused")
@CName("XPluginStart")
actual fun XPluginStart(outName: CPointer<ByteVar>, outSig: CPointer<ByteVar>, outDesc: CPointer<ByteVar>): Int {
    strcpy(outName, NAME)
    strcpy(outSig, OUT_SIG)
    strcpy(outDesc, "Hello World from Kotlin Native for Windows")
    return 1
}

@Suppress("FunctionName", "unused")
@CName("XPluginReceiveMessage")
actual fun XPluginReceiveMessage(inFrom: XPLMPluginID, inMsg: Int, param: COpaquePointer) {

}