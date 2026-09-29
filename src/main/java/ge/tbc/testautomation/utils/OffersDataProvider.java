package ge.tbc.testautomation.utils;

import ge.tbc.testautomation.data.OffersLocalizationData;
import org.testng.annotations.DataProvider;

public class OffersDataProvider {

    @DataProvider(name = "offersLocaleData")
    public static Object[][] getOffersLocaleData() {
        return new Object[][]{
                {
                        new OffersLocalizationData(
                                "ka",
                                "https://tbcbank.ge/ka",
                                "/ka/offers",
                                "/ka/offers/all-offers",
                                "Category!Shopping",
                                "შეთავაზებები",
                                "ყველა შეთავაზება",
                                "შოპინგი"
                        )
                },
                // ინგლისური ლოკალიზაცია (EN)
                {
                        new OffersLocalizationData(
                                "en",
                                "https://tbcbank.ge/en",
                                "/en/offers",
                                "/en/offers/all-offers",
                                "Category!Shopping",
                                "Offers",
                                "All offers",
                                "Shopping"
                        )
                }
        };
    }
}