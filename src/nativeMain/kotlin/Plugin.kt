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

/**
 * @Author CHH2000day
 * @Date 2020/12/14 15:03
 **/
import kotlinx.cinterop.*

@CName("XPluginStart")
expect fun XPluginStart(outName: CPointer<ByteVar>, outSig: CPointer<ByteVar>, outDesc: CPointer<ByteVar>): Int

@CName("XPluginReceiveMessage")
expect fun XPluginReceiveMessage(inFrom: Int, inMsg: Int, param: COpaquePointer)

@Suppress("FunctionName")
@CName("XPluginStop")
fun XPluginStop() {
}

@Suppress("FunctionName")
@CName("XPluginDisable")
fun XPluginDisable() {
}

@Suppress("FunctionName")
@CName("XPluginEnable")
fun XPluginEnable(): Int {
    return 1
}