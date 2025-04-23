package org.acme;

import jakarta.json.Json;
import jakarta.json.JsonWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import org.wildfly.mcp.api.wasm.WasmArgumentSerializer;

public class CityJsonSerializer implements WasmArgumentSerializer {

    @Override
    public byte[] serialize(Object[] args) {
        String city = (String) args[0];
        try (StringWriter out = new StringWriter();
                JsonWriter json = Json.createWriter(out)) {
            json.writeObject(Json.createObjectBuilder().add("city", city).build());
            return out.toString().getBytes(StandardCharsets.UTF_8);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}
