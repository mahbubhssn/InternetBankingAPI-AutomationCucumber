package com.mislbd.ababil.ibapitest;

import com.mislbd.ababil.ibapitest.auth.AuthToken;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.testng.Assert;

import static org.junit.Assert.assertEquals;

public class StepDefinition extends IbApiTestRunner{

    private Logger LOG = LoggerFactory.getLogger("StepDefinition");
    private String actualhasErrorStatus;
    private final AuthToken authToken;

    public StepDefinition(AuthToken authToken) {
        this.authToken = authToken;
    }

    @When("^the user calls auth for token validation$")
    public void the_user_calls_auth_for_token_validation() throws Throwable {
        authToken.doAuth();
    }

    @Then("^the user gets authentication for the service$")
    public void the_user_gets_authentication_for_the_service() throws Throwable {}

    @When("^user do transaction to own account through json file \"([^\"]*)\" to the api \"([^\"]*)\"$")
    public void user_do_transaction_to_own_account_through_json_file_to_the_api(String jsonfile, String api) throws Throwable {
        ResponseEntity<JSONObject> response = executePost(jsonfile, api);
        LOG.info("Response: {}", response.getBody());
        actualhasErrorStatus = response.getBody().get("hasError").toString();
    }

    @Then("^user get hasError status \"([^\"]*)\"$")
    public void user_get_hasError_status(String hasErrorstatus) throws Throwable {
        LOG.info("EXPECTED hasError STATUS: {}", hasErrorstatus);
        LOG.info("ACTUAL hasError STATUS: {}", actualhasErrorStatus);
        if(!hasErrorstatus.equals(actualhasErrorStatus)){ Assert.fail();}
    }
}
