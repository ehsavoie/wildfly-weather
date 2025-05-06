package org.acme;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.wildfly.mcp.api.Prompt;
import org.wildfly.mcp.api.PromptMessage;
import org.wildfly.mcp.api.Resource;
import org.wildfly.mcp.api.TextContent;
import org.wildfly.mcp.api.TextResourceContents;

public class WildFly {

    @Prompt(name = "Prometheus-metrics-chart", description = "Prometheus metrics chart")
    PromptMessage prometheusMetricsChart() {
        return PromptMessage.withUserRole(new TextContent("using available tools, get Prometheus metrics from wildfly server. "
                + "You will repeat the invocation 3 times, being sure to wait 2 seconds between each invocation. "
                + "After all the 3 invocation has been completed you will organize the data in a table. "
                + "Then you will use this table to create a bar chart to visually compare the data. "
                + "Be sure to use at least 5 different data column and be sure to represent all data as bar in the chart"));
    }

    @Resource(uri = "file:///${jboss.server.log.dir}/server.log", mimeType = "text/plain", name = "server.log")
    TextResourceContents serverLog() throws IOException {
        Path path = new File(System.getProperty("jboss.server.log.dir"), "server.log").toPath();
        System.out.println("ile:///${jboss.server.log.dir}/server.log");
        return TextResourceContents.create("file:///${jboss.server.log.dir}", Files.readString(path));
    }

    @Resource(uri = "http://localhost:8080/index.html", mimeType = "text/html; charset=UTF-8", name = "index.html")
    TextResourceContents index() throws IOException, URISyntaxException {
        try (StringWriter content = new StringWriter();
                InputStream in = new URI("http://localhost:8080/index.html").toURL().openStream();
                Reader reader = new InputStreamReader(in, StandardCharsets.UTF_8)) {
            char[] buffer = new char[1024];
            int charsRead = 0;
            while ((charsRead = reader.read(buffer)) != -1) {
                content.write(buffer, 0, charsRead);
            }
            return TextResourceContents.create("http://localhost:8080/index.html", content.toString());
        }
    }
}
