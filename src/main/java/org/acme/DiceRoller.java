package org.acme;

import org.wildfly.mcp.api.Tool;
import org.wildfly.mcp.api.ToolArg;
import org.wildfly.wasm.api.WasmToolService;

@WasmToolService(wasmToolConfigurationName = "dice", wasmMethodName = "rollDice", argumentSerializer = DiceRollJsonSerializer.class)
public interface DiceRoller {

    @Tool(description = "Roll a number of dices with a set number of faces")
    String roll(@ToolArg(description = "The number of dice") int numberOfDice, @ToolArg(description = "The number of faces for the dice") int numberOfFace);
}