
package org.acme;

import org.wildfly.mcp.api.Prompt;
import org.wildfly.mcp.api.PromptMessage;
import org.wildfly.mcp.api.TextContent;


public class WildFly {
 @Prompt(name = "Prometheus-metrics-chart", description = "Prometheus metrics chart")
    PromptMessage prometheusMetricsChart() {
        return PromptMessage.withUserRole(new TextContent("using available tools, get Prometheus metrics from wildfly server. " +
            "You will repeat the invocation 3 times, being sure to wait 2 seconds between each invocation. " + 
            "After all the 3 invocation has been completed you will organize the data in a table. " + 
            "Then you will use this table to create a bar chart to visually compare the data. " + 
            "Be sure to use at least 5 different data column and be sure to represent all data as bar in the chart"));
    }
}
