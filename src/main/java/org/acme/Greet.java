package org.acme;

import org.mcp_java.annotations.tools.Tool;
import org.mcp_java.annotations.tools.ToolArg;
import org.wildfly.wasm.api.WasmToolService;

@WasmToolService(wasmToolConfigurationName = "chicory")
public interface Greet {
    @Tool(name = "WasmGreet", description = "Greet from RUST module")
    String greet(@ToolArg(description = "The name of the person to greet") String person);
}
