package org.acme;

import org.wildfly.mcp.api.Tool;
import org.wildfly.mcp.api.ToolArg;
import org.wildfly.mcp.api.wasm.WasmToolService;

@WasmToolService(wasmToolConfigurationName = "pizza", wasmMethodName = "retrievePizzeriaAddresses", argumentSerializer = CityJsonSerializer.class)
public interface Pizza {

    @Tool(name="pizzaRetriever", description = "Get the address for the best hawaian pizzas")
    public String pizzas(@ToolArg(description = "The city where we are looking for Hawaian pizza") String city);
}
