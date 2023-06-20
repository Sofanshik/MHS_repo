package nure;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.rmi.server.LogStream.log;

public class GsonDateSerelisable implements JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonElement json, Type typeOfT,
                            JsonDeserializationContext context) throws JsonParseException {
        Date date = null;

        try {
            // Take a try
            String dateString = json.getAsJsonPrimitive().getAsString();

            date = buildOddFormat().parse(dateString);

        } catch (Throwable t) {
            // Loop on
            log(" pattern (yyyy-MM-dd  HH:mm:ss) = error = " + t.getMessage());
        }

        if (date == null) {
            try {
                // Take a try
                String dateString = json.getAsJsonPrimitive().getAsString();
                date = buildOldFormat().parse(dateString);
                log(" pattern (MMM dd, yyyy HH:mm:ss) =  SUCCESS " + dateString + " = " + date.toString());
            } catch (Throwable t) {
                // Loop on
                log(" pattern (MMM dd, yyyy HH:mm:ss) = error = " + t.getMessage());
            }

        }
        if (date == null) {
            try {
                // Take a try
                String dateString = json.getAsJsonPrimitive().getAsString();
                date = buildVeryOldFormat().parse(dateString);
                log(" pattern (MMM d, yyyy HH:mm:ss) =  SUCCESS " + dateString + " = " + date.toString());
            } catch (Throwable t) {
                // Loop on
                log(" pattern (MMM d, yyyy HH:mm:ss) = error = " + t.getMessage());
            }

        }
        if (date == null)

            for (SimpleDateFormat pattern : knownPatterns) {
                try {
                    // Take a try
                    if (!pattern.toPattern().contains("Z"))
                        pattern.setTimeZone(TimeZone.getTimeZone("UTC"));
                    String dateString = json.getAsJsonPrimitive().getAsString();
                    if (!pattern.toPattern().contains("Z"))
                        pattern.setTimeZone(TimeZone.getTimeZone("UTC"));
                    date = new Date(pattern.parse(dateString).getTime());
                    log(" pattern (" + pattern.toPattern() + ") =  SUCCESS " + dateString + " = " + date.toString());
                    break;
                } catch (Throwable t) {
                    // Loop on
                    log(" pattern (" + pattern.toPattern() + ") = error = " + t.getMessage());
                }
            }
//            }
        if (date == null) {
            try {
                date = new Date(json.getAsJsonPrimitive().getAsLong());
                log(" Joda =  SUCCESS " + json.getAsJsonPrimitive().getAsString() + " = " + date.toString());
            } catch (Throwable t) {
                log(" pattern (Long) = error = " + t.getMessage());
            }
        }
        if (date == null) {
            try {
                date = DateFormat.getInstance().parse(json.getAsJsonPrimitive().getAsString());
                log(" Joda =  SUCCESS " + json.getAsJsonPrimitive().getAsString() + " = " + date.toString());
            } catch (Throwable t) {
                log(" pattern ( DateFormat.getInstance().parse()) = error = " + t.getMessage());
            }
        }

        if (date == null)
            date = new Date();

        return date;
    }


    private static DateFormat buildIso8601Format() {
        DateFormat iso8601Format = new SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        return iso8601Format;
    }

    private static DateFormat buildOddFormat() {
        DateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        iso8601Format.setTimeZone(TimeZone.getTimeZone("UTC"));
        return iso8601Format;
    }

    private static DateFormat buildOldFormat() {
        DateFormat iso8601Format = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
        iso8601Format.setTimeZone(TimeZone.getTimeZone("UTC"));
        return iso8601Format;
    }

    private static DateFormat buildVeryOldFormat() {
        DateFormat iso8601Format = new SimpleDateFormat("MMM d, yyyy HH:mm:ss");
        iso8601Format.setTimeZone(TimeZone.getTimeZone("UTC"));
        return iso8601Format;
    }
    static List<SimpleDateFormat> knownPatterns = new ArrayList<>(Arrays.asList(
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ"),
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ"),
            new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss"),
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"),
            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    ));
}
