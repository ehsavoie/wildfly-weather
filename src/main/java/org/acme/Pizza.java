package org.acme;

import org.mcp_java.annotations.tools.Tool;
import org.mcp_java.annotations.tools.ToolArg;
import org.wildfly.wasm.api.WasmToolService;

@WasmToolService(wasmToolConfigurationName = "pizza", wasmMethodName = "retrievePizzeriaAddresses", argumentSerializer = CityJsonSerializer.class)
public interface Pizza {

    @Tool(name="pizzaRetriever", description = "Get the address for the best hawaian pizzas")
    public String pizzas(@ToolArg(description = "The city where we are looking for Hawaian pizza") String city);
}
