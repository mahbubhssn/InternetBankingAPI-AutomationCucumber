Feature:

  Scenario: the user calls auth for token validation
    When the user calls auth for token validation
    Then the user gets authentication for the service

  Scenario:
    When user do transaction to own account through json file "FundTransferOwnAccount" to the api "fundtransfer/ownAccount"
    Then user get hasError status "false"
