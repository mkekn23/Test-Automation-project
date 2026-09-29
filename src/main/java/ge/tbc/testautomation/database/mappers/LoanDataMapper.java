package ge.tbc.testautomation.database.mappers;

import ge.tbc.testautomation.database.models.LoanCalculatorData;
import org.apache.ibatis.annotations.Select;
import java.util.List;

public interface LoanDataMapper {

    @Select("SELECT id, amount, currency, period FROM loan_test_data")
    List<LoanCalculatorData> getAllLoanTestData();
}