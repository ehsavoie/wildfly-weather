# WildFly Weather MCP Tool Example

This example demonstrates how to integrate WebAssembly (Wasm) modules as Model Context Protocol (MCP) tools within a WildFly environment.

## Overview

The `wildfly-weather` project showcases a practical application of using Wasm modules to extend the functionality of an MCP server. It provides a weather service that can be invoked as an MCP tool.

## Key Features

*   **WebAssembly Integration**: Shows how to load and execute Wasm modules.
*   **MCP Tool Implementation**: Illustrates the creation of an MCP tool that leverages a Wasm module.
*   **WildFly Deployment**: Provides an example of deploying such a service in a WildFly application server.

## Purpose

This project serves as a reference for developers looking to:
*   Understand how Wasm can be used in Java EE / Jakarta EE applications.
*   Learn how to build and expose functionalities as MCP tools.
*   Explore the integration of Wasm with WildFly.

## Getting Started

Building and starting the MCP server:
```bash

mvn clean install
./target/server/bin/standalone.sh

```

You can use the MCP inspector to test  your tools

```bash

npx @modelcontextprotocol/inspector

```

Once you are on the [MPC inspector UI](http://127.0.0.1:6274), you can connect to to your [WildFlyMCP server](http://localhost:8080/sse) using the Transport Type *SSE* and the URL *http://localhost:8080/sse*.

You should see all those tools.

## Links

Links:
* Wasm Wasi: https://wasi.dev/
* Chicory : https://github.com/dylibso/chicory
* Chicory SDK https://github.com/extism/chicory-sdk
* WildFly AI Feature Pack: https://github.com/wildfly-extras/wildfly-ai-feature-pack
* WildFly Weather MCP demo with WASM tools: https://github.com/ehsavoie/wildfly-weather/tree/wasm_subsystem
* Source of Wasm tools:
    +  https://github.com/ehsavoie/mcp-chicory
    + https://github.com/sea-monkeys/WASImancer/tree/main/examples/roll-dice-project
    + https://github.com/sea-monkeys/WASImancer/tree/main/examples/pizzerias

I would like to thank [Philippe Charrière](https://k33g.hashnode.dev/) and [Andrea Peruffo](https://andreaperuffo.com/) for their support.