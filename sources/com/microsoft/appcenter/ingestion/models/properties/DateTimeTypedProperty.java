package com.microsoft.appcenter.ingestion.models.properties;

import com.microsoft.appcenter.ingestion.models.json.JSONDateUtils;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

public class DateTimeTypedProperty extends TypedProperty {
    public static final String TYPE = "dateTime";
    private Date value;

    public String getType() {
        return TYPE;
    }

    public Date getValue() {
        return this.value;
    }

    public void setValue(Date date) {
        this.value = date;
    }

    public void read(JSONObject jSONObject) throws JSONException {
        super.read(jSONObject);
        setValue(JSONDateUtils.toDate(jSONObject.getString("value")));
    }

    public void write(JSONStringer jSONStringer) throws JSONException {
        super.write(jSONStringer);
        jSONStringer.key("value").value(JSONDateUtils.toString(getValue()));
    }

    public boolean equals(Object obj) {
        boolean z = true;
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass() || !super.equals(obj)) {
            return false;
        }
        DateTimeTypedProperty dateTimeTypedProperty = (DateTimeTypedProperty) obj;
        if (this.value != null) {
            z = this.value.equals(dateTimeTypedProperty.value);
        } else if (dateTimeTypedProperty.value != null) {
            z = false;
        }
        return z;
    }

    public int hashCode() {
        return (super.hashCode() * 31) + (this.value != null ? this.value.hashCode() : 0);
    }
}
