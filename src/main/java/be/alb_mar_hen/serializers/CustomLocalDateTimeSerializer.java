package be.alb_mar_hen.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.time.LocalDateTime;

public class CustomLocalDateTimeSerializer extends StdSerializer<LocalDateTime> {
	private static final long serialVersionUID = -8469502841443944572L;

	public CustomLocalDateTimeSerializer() {
        super(LocalDateTime.class);
    }

    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        // Créer un objet JSON pour représenter LocalDateTime
        gen.writeStartObject(); // Démarre un objet JSON

        gen.writeNumberField("year", value.getYear());
        gen.writeNumberField("monthValue", value.getMonthValue());
        gen.writeNumberField("dayOfMonth", value.getDayOfMonth());
        gen.writeNumberField("hour", value.getHour());
        gen.writeNumberField("minute", value.getMinute());
        gen.writeNumberField("second", value.getSecond());
        gen.writeNumberField("nano", value.getNano());

        gen.writeEndObject(); // Termine l'objet JSON
    }
}