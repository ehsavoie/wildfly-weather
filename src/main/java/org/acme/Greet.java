package org.acme;

import org.wildfly.mcp.api.wasm.WasmToolService;

@WasmToolService(wasmToolConfigurationName = "chicory")
public interface Greet {

    public  byte[] greet(String person);
}
