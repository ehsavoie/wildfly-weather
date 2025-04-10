package org.acme;

import org.wildfly.mcp.api.wasm.WasmToolService;

@WasmToolService(wasmToolConfigurationName = "pizza")
public interface Pizza {
    public byte[] retrievePizzeriaAddresses(String city);
}
