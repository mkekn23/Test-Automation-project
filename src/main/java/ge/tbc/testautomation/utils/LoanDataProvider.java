package ge.tbc.testautomation.utils;

import ge.tbc.testautomation.database.mappers.LoanDataMapper;
import ge.tbc.testautomation.database.models.LoanCalculatorData;
import org.testng.annotations.DataProvider;

import java.util.List;

public class LoanDataProvider {

    @DataProvider(name = "dbLoanData")
    public static Object[][] getDbLoanData() {
        LoanDataMapper mapper = DataBaseConfig.dbMapper();
        List<LoanCalculatorData> list = mapper.getAllLoanTestData();

        Object[][] data = new Object[list.size()][1];
        for (int i = 0; i < list.size(); i++) {
            data[i][0] = list.get(i);
        }
        return data;
    }
}