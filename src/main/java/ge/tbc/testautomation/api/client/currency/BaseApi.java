package ge.tbc.testautomation.api.client.currency;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

import static ge.tbc.testautomation.data.Constants.TBC_API_BASEURL;

public abstract class BaseApi {

    public static final String VERSION = "/api/v1";

    public static final RequestSpecification REQ_SPEC = new RequestSpecBuilder()
            .setBaseUri(TBC_API_BASEURL)
            .setBasePath(VERSION)
            .setContentType("application/json")
            .addHeader("Accept", "application/json")
            .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36")
            .log(LogDetail.ALL)
            .build()
            .filters(new ResponseLoggingFilter());
}