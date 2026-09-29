package ge.tbc.testautomation.api.client.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BranchResponse {
    private int id;
    private String name;
    private String address;
    private String regionName;
    private String type;
    private List<String> currencies;
    private List<WorkHour> workHours;
    private boolean worksallday;
}