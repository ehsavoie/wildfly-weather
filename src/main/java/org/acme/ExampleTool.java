/*
 * Copyright 2025 JBoss by Red Hat.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.acme;

import org.mcp_java.annotations.tools.Tool;
import org.mcp_java.annotations.tools.ToolArg;

public class ExampleTool {

    @Tool(name = "String.length", description = "Calculates the length of a string")
    int stringLength(@ToolArg(description = "the string the length of of which we want to calculate") String s) {
        System.out.println("Called stringLength() with s='" + s + "'");
        return s.length();
    }
}
