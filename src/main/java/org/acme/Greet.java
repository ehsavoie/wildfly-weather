package org.acme;

import org.wildfly.mcp.api.Tool;
import org.wildfly.mcp.api.ToolArg;
import org.wildfly.wasm.api.WasmToolService;

@WasmToolService(wasmToolConfigurationName = "chicory")
public interface Greet {
    @Tool(name = "WasmGreet", description = "Greet from RUST module")
    String greet(@ToolArg(description = "The name of the person to greet") String person);
}
