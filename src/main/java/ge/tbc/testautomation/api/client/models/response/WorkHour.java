package ge.tbc.testautomation.api.client.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkHour {
    private String startTime;
    private String endTime;
    private String weekDay;
}