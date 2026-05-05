# WildFly Weather MCP Tool Example

This example demonstrates how to integrate Model Context Protocol (MCP) tool within a WildFly environment.

## Overview

The `wildfly-weather` project showcases a practical application of using Wasm modules to extend the functionality of an MCP server. It provides a weather service that can be invoked as an MCP tool.

## Key Features

*   **MCP Tool Implementation**: Illustrates the creation of an MCP tool that calls a HTTP service
*   **WildFly Deployment**: Provides an example of deploying such a service in a WildFly application server.

## Purpose

This project serves as a reference for developers looking to:
*   Understand how Wasm can be used in Jakarta EE applications.
*   Learn how to build and expose functionalities as MCP tools.

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

Once you are on the [MPC inspector UI](http://127.0.0.1:6274), you can connect to your [WildFlyMCP server](http://localhost:8080/sse) with the Streamable HTTP transport type using the parameters:

* Transport Type: **Streamable HTTP**
* URL: **http://localhost:8080/stream**

Alterantively, you can connect with the Server-Sent Events (SSE) transport type using the parameters:

* Transport Type: `SSE`
* URL: **http://localhost:8080/stream**

You should see all those tools.

## Links

* WildFly AI Feature Pack: https://github.com/wildfly-extras/wildfly-ai-feature-pack

I would like to thank [Philippe Charrière](https://k33g.hashnode.dev/) and [Andrea Peruffo](https://andreaperuffo.com/) for their support.