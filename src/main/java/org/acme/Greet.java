package org.acme;

import org.wildfly.mcp.api.Tool;
import org.wildfly.mcp.api.ToolArg;
import org.wildfly.mcp.api.wasm.WasmToolService;

@WasmToolService(wasmToolConfigurationName = "chicory")
public interface Greet {
    @Tool(name = "WasmGreet", description = "Greet from RUST module")
    String greet(@ToolArg(description = "The name of the person to greet") String person);
}
