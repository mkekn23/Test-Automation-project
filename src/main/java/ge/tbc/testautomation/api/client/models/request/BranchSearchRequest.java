package ge.tbc.testautomation.api.client.models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BranchSearchRequest {
    private List<String> filter;
    private String locale;
    private String keyword;
    private Location myLocation;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Location {
        private double latitude;
        private double longitude;
    }
}