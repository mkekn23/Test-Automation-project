package ge.tbc.testautomation.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class OffersLocalizationData {
    private final String locale;
    private final String homeUrl;
    private final String offersUrlPart;
    private final String allOffersUrlPart;
    private final String shoppingParamPart;
    private final String offersHeaderText;
    private final String allOffersBtnText;
    private final String shoppingCategoryText;
}