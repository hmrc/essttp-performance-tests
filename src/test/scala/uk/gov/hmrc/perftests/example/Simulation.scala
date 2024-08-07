/*
 * Copyright 2024 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.perftests.example

import uk.gov.hmrc.performance.simulation.PerformanceTestRunner
import uk.gov.hmrc.perftests.example.Requests._

class Simulation extends PerformanceTestRunner {

  setup("start-page-business-epaye", "Start Page - Business - EPAYE")
    .withRequests(getInitialPage, postInitialPageEpaye, getEpayeStartPage, postStartPageBusinessEpaye)

  setup("start-page-individual-epaye", "Start Page - Individual - EPAYE")
    .withRequests(getInitialPage, postInitialPageEpaye, getEpayeStartPage, postStartPageIndividualEpaye)

  setup("start-page-ineligible-epaye", "Start Page - Ineligible - EPAYE")
    .withRequests(getInitialPage, postInitialPageEpaye, getEpayeStartPage, postStartPageIneligibleEpaye)



  setup("start-page-business-sa", "Start Page - Business - SA")
    .withRequests(getInitialPage, postInitialPageSa, getSaStartPage, postStartPageBusinessSa)

  setup("start-page-individual-sa", "Start Page - Individual - SA")
    .withRequests(getInitialPage, postInitialPageSa, getSaStartPage, postStartPageIndividualSa)

  setup("start-page-ineligible-sa", "Start Page - Ineligible - SA")
    .withRequests(getInitialPage, postInitialPageSa, getSaStartPage, postStartPageIneligibleSa)



  setup("start-page-business-vat", "Start Page - Business - VAT")
    .withRequests(getInitialPage, postInitialPageVat, getVatStartPage, postStartPageBusinessVat)

  setup("start-page-individual-vat", "Start Page - Individual - VAT")
    .withRequests(getInitialPage, postInitialPageVat, getVatStartPage, postStartPageIndividualVat)

  setup("start-page-ineligible-vat", "Start Page - Ineligible - VAT")
    .withRequests(getInitialPage, postInitialPageVat, getVatStartPage, postStartPageIneligibleVat)



  setup("bta-page-epaye", "BTA Page - EPAYE")
    .withRequests(getBtaPage, getStartBtaEpaye)

  setup("bta-page-sa", "BTA Page - SA")
    .withRequests(getBtaPage, getStartBtaSa)

  setup("bta-page-vat", "BTA Page - VAT")
    .withRequests(getBtaPage, getStartBtaVat)

  setup(
    "landing-page-eligible",
    "Landing Page - Eligible"
  ).withRequests(getLandingPage, getDetermineTaxId, getDetermineEligibilityPass)

  setup(
    "landing-page-ineligible-epaye",
    "Landing Page - Ineligible"
  ).withRequests(getLandingPage, getDetermineTaxId, getDetermineEligibilityFailEpaye)

  setup(
    "landing-page-ineligible-sa",
    "Landing Page - Ineligible"
  ).withRequests(getLandingPage, getDetermineTaxId, getDetermineEligibilityFailSa)

  setup(
    "landing-page-ineligible-vat",
    "Landing Page - Ineligible"
  ).withRequests(getLandingPage, getDetermineTaxId, getDetermineEligibilityFailVat)

  setup(
    "detached-url",
    "Detached URL"
  ).withRequests(getStart, getLandingPage, getDetermineTaxId, getDetermineEligibilityPass)

  setup("ineligible-page", "Ineligible Page") withRequests getIneligiblePage

  setup("your-bill-page", "Your Bill Page").withRequests(getYourBillPage, postYourBillPage)

  setup("why-unable-to-pay", "Why unable to pay Page").withRequests(getWhyUnablePage, postWhyUnablePage)

  setup("pay-in-6-months-yes", "Can you pay in 6 months Page").withRequests(getPayInSixPage, postPayInSixPageYes)

  setup(
    "can-pay-upfront-page-yes",
    "Can Pay Upfront Page - Yes"
  ).withRequests(getCanUpfrontPage, postCanUpfrontPageYes)

  setup(
    "can-pay-upfront-page-no",
    "Can Pay Upfront Page - No"
  ).withRequests(getCanUpfrontPage, postCanUpfrontPageNo, getRetrieveExtremeDates, getDetermineAffordability)

  setup(
    "how-much-upfront-page",
    "How Much to Pay Upfront Page"
  ).withRequests(getHowMuchUpfrontPage, postHowMuchUpfrontPage)

  setup(
    "upfront-summary-page",
    "Upfront Summary Page"
  ).withRequests(getUpfrontSummaryPage, getRetrieveExtremeDates, getDetermineAffordability)

  setup(
    "how-much-monthly-page",
    "How Much to Pay Monthly Page"
  ).withRequests(getHowMuchMonthlyPage, postHowMuchMonthlyPage)

  setup(
    "which-day-page",
    "Which Day Page"
  ).withRequests(getWhichDayPage, postWhichDayPage, getRetrieveStartDates, getDetermineQuotes)

  setup("how-many-months-page", "How Many Months Page")
    .withRequests(getHowManyMonthsPage, postHowManyMonthsPage)

  setup(
    "check-payment-plan-page",
    "Check Payment Plan Page"
  ).withRequests(getCheckPaymentPlanPage, postCheckPaymentPlanPage)

  setup(
    "about-your-bank-account-page-business",
    "About Your Bank Account Page - Business"
  ).withRequests(getAboutYourBankAccountPage, postAboutYourBankAccountPageBusiness)

  setup(
    "about-your-bank-account-page-personal",
    "About Your Bank Account Page - Personal"
  ).withRequests(getAboutYourBankAccountPage, postAboutYourBankAccountPagePersonal)

  setup(
    "setup-direct-debit-page-business",
    "Setup Direct Debit Page - Business"
  ).withRequests(getSetupDirectDebitPage, postSetupDirectDebitPageBusiness)

  setup(
    "setup-direct-debit-page-personal",
    "Setup Direct Debit Page - Personal"
  ).withRequests(getSetupDirectDebitPage, postSetupDirectDebitPagePersonal)

  setup(
    "direct-debit-summary-page",
    "Direct Debit Summary Page"
  ).withRequests(getDirectDebitSummaryPage, postDirectDebitSummaryPage)

  setup(
    "terms-and-conditions-page",
    "Terms and Conditions Page"
  ).withRequests(getTermsAndConditionsPage, postTermsAndConditionsPage)

  setup(
    "which-email-page",
    "Which Email Page"
  ).withRequests(getWhichEmailPage, postWhichEmailPage)

  setup(
    "verification-frontend",
    "Verification Frontend"
  ).withRequests(
    getEmailVerification,
    if (runLocal == true) { getVerificationFrontendLocal }
    else getVerificationFrontend,
    getTestOnlyPasscode,
    postVerificationFrontend
  )

  setup(
    "email-confirmation-page",
    "Email Confirmation Page"
  ).withRequests(getEmailCallback, getEmailConfirmationPage)

  setup("confirmation-page-epaye", "Confirmation Page - EPAYE")
    .withRequests(getSubmitArrangementEpaye, getConfirmationPage)

  setup("confirmation-page-vat", "Confirmation Page - VAT")
    .withRequests(getSubmitArrangementVat, getConfirmationPage)

  setup("confirmation-page-sa", "Confirmation Page - SA")
    .withRequests(getSubmitArrangementSa, getConfirmationPage)


  runSimulation()

}
