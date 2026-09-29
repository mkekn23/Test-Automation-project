package ge.tbc.testautomation.api.client.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeRateResponse {

    private String iso1;
    private String iso2;
    private Double buyRate;
    private Double sellRate;
}