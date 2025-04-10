package org.acme;

import org.wildfly.mcp.api.wasm.WasmToolService;

@WasmToolService(wasmToolConfigurationName = "dice")
public interface DiceRoller {
    public byte[] rollDice(String dice);
}
