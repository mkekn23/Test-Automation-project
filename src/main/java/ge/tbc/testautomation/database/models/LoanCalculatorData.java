package ge.tbc.testautomation.database.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoanCalculatorData {
    private int id;
    private String amount;
    private String currency;
    private String period;
}