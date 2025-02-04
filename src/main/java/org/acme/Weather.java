package org.acme;

import jakarta.json.JsonObject;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import org.wildfly.mcp.api.Tool;
import org.wildfly.mcp.api.ToolArg;

public class Weather {

    private static WeatherClient weatherClient = new WeatherClient();

    @Tool(description = "Get weather alerts for a US state.", name = "alerts")
    public String getAlerts(@ToolArg(description = "Two-letter US state code (e.g. CA, NY)") String state) {
        return formatAlerts(weatherClient.getAlerts(state));
//        return weatherClient.getAlertsAsString(state);
    }

    @Tool(description = "Get weather forecast for a location.")
    public String getForecast(@ToolArg(description = "Latitude of the location") double latitude,
            @ToolArg(description = "Longitude of the location") double longitude) {
        var points = weatherClient.getPoints(latitude, longitude);
        var url = points.get("properties").asJsonObject().getString("forecast");

        return formatForecast(weatherClient.getForecast(url));
    }

    String formatForecast(Forecast forecast) {
        return forecast.properties().periods().stream()
                .map(period -> formatPeriod(period))
                .collect(Collectors.joining("\n---\n"));
    }

    static String formatPeriod(Period period) {
        return """
                    Temperature: %d°%s
                    Wind: %s %s
                    Forecast: %s
                """.formatted(period.temperature(), period.temperatureUnit(), period.windSpeed(), period.windDirection(), period.detailedForecast());
    }

    static String formatAlerts(Alerts alerts) {
        return alerts.features().stream()
                .map(feature -> formatProperties(feature.properties()))
                .collect(Collectors.joining("\n---\n"));
    }

    static String formatProperties(Properties p) {
        return """
                    Event: %s
                    Area: %s
                    Severity: %s
                    Description: %s
                    Instructions: %s
               """.formatted(p.event(), p.areaDesc(), p.severity(), p.description(), p.instruction());
    }

    public static class WeatherClient {

        private static final String REST_URI = "https://api.weather.gov";

        private Client client = ClientBuilder.newClient().property("dev.resteasy.client.follow.redirects", "true");

        Alerts getAlerts(String state) {
            try (Response response = client.target(REST_URI)
//                    .register(GeoJsonReaderInterceptor.class)
                    .path("/alerts/active/area/%s".formatted(state))
                    .request(MediaType.APPLICATION_JSON).get();) {
//                JsonObject bean = response.readEntity(JsonObject.class);
                return response.readEntity(Alerts.class);
            }
        }

        String getAlertsAsString(String state) {
            Response response = client.target(REST_URI).register(GeoJsonReaderInterceptor.class)
                    .path("/alerts/active/area/%s".formatted(state))
                    .request(MediaType.APPLICATION_JSON).get();
            return "Hello";
        }

        JsonObject getPoints(double latitude, double longitude) {
            DecimalFormat format = new DecimalFormat("##.####", DecimalFormatSymbols.getInstance(Locale.US));
            System.out.println(REST_URI + "/points/" + format.format(latitude) +","+format.format(longitude));
            Response response =  client.target(REST_URI)
                    .path("/points/" + format.format(latitude) +","+format.format(longitude))
                    .request(MediaType.APPLICATION_JSON)
                    .get();
            return response.readEntity(JsonObject.class);
        }

        Forecast getForecast(String url) {
            return client.target(url)
                    .request(MediaType.APPLICATION_JSON)
                    .get(Forecast.class);
        }
    }

    public record Properties(
            String id,
            String areaDesc,
            String event,
            String severity,
            String description,
            String instruction) {

    }

    public record Feature(
            String id,
            String type,
            Object geometry,
            Properties properties) {

    }

    public record Alerts(
            List<String> context,
            String type,
            List<Feature> features,
            String title,
            String updated) {

    }

    public record Period(
            String name,
            int temperature,
            String temperatureUnit,
            String windSpeed,
            String windDirection,
            String detailedForecast) {

    }

    public record ForecastProperties(
            List<Period> periods) {

    }

    public record Forecast(
            ForecastProperties properties) {

    }
}
