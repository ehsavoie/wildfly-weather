package org.acme;

import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import org.wildfly.mcp.api.Tool;
import org.wildfly.mcp.api.ToolArg;
import org.wildfly.wasm.api.WasmInvoker;
import org.wildfly.wasm.api.WasmTool;

public class Weather {

    private static WeatherClient weatherClient = new WeatherClient();

    @Inject
    @WasmTool(value = "chicory")
    WasmInvoker chicory;

    @Tool(description = "Greet from RUST module")
    public String greet(@ToolArg(description = "The name of the person to greet") String person) {
        byte[] output = chicory.call("greet", person.getBytes(StandardCharsets.UTF_8));
        return new String(output, StandardCharsets.UTF_8);
    }

//    @Inject
//    @WasmTool
//    @Named("dice")
//    WasmInvoker dice;
//    @Inject
//    Greet greet;
//
//    @Inject
//    DiceRoller roller;

//    @Inject
//    Pizza pizza;

//    @Tool(description = "Get the address for the best hawaian pizzas")
//    public String pizzas(@ToolArg(description = "The city where we are looking for Hawaian pizza") String city) {
////        StringWriter out = new StringWriter();
////        Json.createWriter(out).writeObject(Json.createObjectBuilder().add("city", city).build());
////        return pizza.retrievePizzeriaAddresses(out.toString());
//        return pizza.retrievePizzeriaAddresses(city);
//    }

    @Tool(description = "Wait for the desired time.", name = "wait")
    public String waitFor(@ToolArg(description = "The time to wait for in milliseconds") long duration) throws InterruptedException {
        Thread.sleep(duration);
        return "Done";
    }

//
//    @Tool(description = "Roll a number of dices with a set number of faces")
//    public String roll(@ToolArg(description = "The number of dice") int numberOfDice, @ToolArg(description = "The number of faces for the dice") int numberOfFace) {
////        try (StringWriter out = new StringWriter()) {
////            Json.createWriter(out).writeObject(Json.createObjectBuilder().add("numFaces", numberOfFace).add("numDice", numberOfDice).build());
////            out.flush();
////            return roller.roll(out.toString());
////        } catch (IOException ex) {
////            throw new RuntimeException(ex);
////        }
//        return roller.roll(numberOfDice, numberOfFace);
//    }
//
//    @Tool(description = "Service Greet from RUST module")
//    public String serviceGreet(@ToolArg(description = "The name of the person to greet") String person) {
//        return greet.greet(person);
//    }

    @Tool(description = "Get weather alerts for a US state with an optional comment parameter.")
    public String getAlerts(@ToolArg(description = "Two-letter US state code (e.g. CA, NY)") String state, @ToolArg(description = "Comment", required = false) String comment) {
        System.out.println("Comment received " + comment);
        return formatAlerts(weatherClient.getAlerts(state));
    }

    @Tool(description = "Get weather alerts for a US state.", name = "alerts")
    public String getAlerts(@ToolArg(description = "Two-letter US state code (e.g. CA, NY)") String state) {
        return formatAlerts(weatherClient.getAlerts(state));
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
            System.out.println(REST_URI + "/points/" + format.format(latitude) + "," + format.format(longitude));
            Response response = client.target(REST_URI)
                    .path("/points/" + format.format(latitude) + "," + format.format(longitude))
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
