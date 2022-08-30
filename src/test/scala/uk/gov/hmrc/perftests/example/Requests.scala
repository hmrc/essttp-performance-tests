/*
 * Copyright 2022 HM Revenue & Customs
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

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder
import uk.gov.hmrc.performance.conf.ServicesConfiguration

object Requests extends ServicesConfiguration {

  val baseUrl: String = baseUrlFor("essttp-frontend")
  val route: String   = "/set-up-a-payment-plan"

  val getStartPage: HttpRequestBuilder =
    http("Get Start Page")
      .get(s"$baseUrl$route/test-only/start-journey")
      .check(status.is(200))
      .check(css("input[name=csrfToken]", "value").saveAs("csrfToken"))

  val postStartPageBusiness: HttpRequestBuilder =
    http("Post Start Page - Business")
      .post(s"$baseUrl$route/test-only/start-journey": String)
      .formParam("csrfToken", s"$${csrfToken}")
      .formParam("signInAs", "Organisation")
      .formParam("enrolments[]", "Epaye")
      .formParam("debtTotalAmount", "3000")
      .formParam("taxReference","")
      .formParam("origin", "Origins.Epaye.Bta")
      .check(status.is(303))
      .check(header("Location").is(s"$route/test-only/bta-page").saveAs("btaPage"))

  val postStartPageIndividual: HttpRequestBuilder =
    http("Post Start Page - Individual")
      .post(s"$baseUrl$route/test-only/start-journey": String)
      .formParam("csrfToken", s"$${csrfToken}")
      .formParam("signInAs", "Individual")
      .formParam("enrolments[]", "Epaye")
      .formParam("debtTotalAmount", "3000")
      .formParam("taxReference","")
      .formParam("origin", "Origins.Epaye.GovUk")
      .check(status.is(303))
      .check(header("Location").is("https://github.com/hmrc/essttp-frontend#emulate-start-journey-from-gov-uk"))

  val postStartPageIneligible: HttpRequestBuilder =
    http("Post Start Page - Ineligible")
      .post(s"$baseUrl$route/test-only/start-journey": String)
      .formParam("csrfToken", s"$${csrfToken}")
      .formParam("signInAs", "Organisation")
      .formParam("enrolments[]", "Epaye")
      .formParam("debtTotalAmount", "3000")
      .formParam("taxReference","")
      .formParam("eligibilityErrors[]","IsMoreThanMaxDebtAllowance")
      .formParam("eligibilityErrors[]","ExistingTtp")
      .formParam("origin", "Origins.Epaye.Bta")
      .check(status.is(303))
      .check(header("Location").is(s"$route/test-only/bta-page").saveAs("btaPage"))

  val getBtaPage: HttpRequestBuilder =
    http("Get BTA Page")
      .get(s"$baseUrl$${btaPage}")
      .check(status.is(200))

  val getStartBta: HttpRequestBuilder =
    http("Get Start BTA")
      .get(s"$baseUrl$route/test-only/start-journey-epaye-bta": String)
      .check(status.is(303))
      .check(header("Location").saveAs("LandingPage"))

  val getGithubPage: HttpRequestBuilder =
    http("Get Github Page")
      .get("https://github.com/hmrc/essttp-frontend")
      .check(status.is(200))

  val getStart: HttpRequestBuilder =
    http("Get Start")
      .get(s"$baseUrl$route/govuk/epaye/start": String)
      .check(status.is(303))
      .check(header("Location").is(s"$route/determine-taxId"))

  val getLandingPage: HttpRequestBuilder =
    http("Get Landing Page")
      .get(s"$${LandingPage}")
      .check(status.is(200))

  val getDetermineTaxId: HttpRequestBuilder =
    http("Get Determine Tax Id")
      .get(s"$baseUrl$route/determine-taxId")
      .check(status.is(303))
      .check(header("Location").is(s"$route/determine-eligibility").saveAs("DetermineEligibility"))

  val getDetermineEligibilityPass: HttpRequestBuilder =
    http("Get Determine Eligibility - Pass")
      .get(s"$baseUrl$${DetermineEligibility}")
      .check(status.is(303))
      .check(header("Location").is(s"$route/your-bill").saveAs("YourBillPage"))

  val getDetermineEligibilityFail: HttpRequestBuilder =
    http("Get Determine Eligibility - Fail")
      .get(s"$baseUrl$${DetermineEligibility}")
      .check(status.is(303))
      .check(header("Location").is(s"$route/not-eligible").saveAs("IneligiblePage"))

  val getIneligiblePage: HttpRequestBuilder =
    http("Get Ineligible Page")
      .get(s"$baseUrl$${IneligiblePage}")
      .check(status.is(200))

  val getYourBillPage: HttpRequestBuilder =
    http("Get Your Bill Page")
      .get(s"$baseUrl$${YourBillPage}")
      .check(status.is(200))
      .check(css("input[name=csrfToken]", "value").saveAs("csrfToken"))

  val postYourBillPage: HttpRequestBuilder =
    http("Post Your Bill Page")
      .post(s"$baseUrl$${YourBillPage}": String)
      .formParam("csrfToken", s"$${csrfToken}")
      .check(status.is(303))
      .check(header("Location").is(s"$route/can-you-make-an-upfront-payment").saveAs("CanUpfrontPage"))

  val getCanUpfrontPage: HttpRequestBuilder =
    http("Get Can You Pay Upfront Page")
      .get(s"$baseUrl$${CanUpfrontPage}")
      .check(status.is(200))
      .check(css("input[name=csrfToken]", "value").saveAs("csrfToken"))

  val postCanUpfrontPageYes: HttpRequestBuilder =
    http("Post Can You Pay Upfront Page - Yes")
      .post(s"$baseUrl$${CanUpfrontPage}": String)
      .formParam("csrfToken", s"$${csrfToken}")
      .formParam("CanYouMakeAnUpFrontPayment", "Yes")
      .check(status.is(303))
      .check(header("Location").is(s"$route/how-much-can-you-pay-upfront").saveAs("HowMuchUpfrontPage"))

  val postCanUpfrontPageNo: HttpRequestBuilder =
    http("Post Can You Pay Upfront Page - No")
      .post(s"$baseUrl$${CanUpfrontPage}": String)
      .formParam("csrfToken", s"$${csrfToken}")
      .formParam("CanYouMakeAnUpFrontPayment", "No")
      .check(status.is(303))
      .check(header("Location").is(s"$route/retrieve-extreme-dates"))

  val getHowMuchUpfrontPage: HttpRequestBuilder =
    http("Get How Much Can You Pay Upfront Page")
      .get(s"$baseUrl$${HowMuchUpfrontPage}")
      .check(status.is(200))
      .check(css("input[name=csrfToken]", "value").saveAs("csrfToken"))

  val postHowMuchUpfrontPage: HttpRequestBuilder =
    http("Post How Much Can You Pay Upfront Page")
      .post(s"$baseUrl$${HowMuchUpfrontPage}": String)
      .formParam("csrfToken", s"$${csrfToken}")
      .formParam("UpfrontPaymentAmount", "1000")
      .check(status.is(303))
      .check(header("Location").is(s"$route/upfront-payment-summary").saveAs("UpfrontSummaryPage"))

  val getUpfrontSummaryPage: HttpRequestBuilder =
    http("Get Upfront Summary Page")
      .get(s"$baseUrl$${UpfrontSummaryPage}")
      .check(status.is(200))

  val getRetrieveExtremeDates: HttpRequestBuilder =
    http("Get Retrieve Extreme Dates")
      .get(s"$baseUrl$route/retrieve-extreme-dates")
      .check(status.is(303))
      .check(header("Location").is(s"$route/determine-affordability").saveAs("DetermineAffordability"))

  val getDetermineAffordability: HttpRequestBuilder =
    http("Get Determine Affordability")
      .get(s"$baseUrl$${DetermineAffordability}")
      .check(status.is(303))
      .check(header("Location").is(s"$route/how-much-can-you-pay-each-month").saveAs("HowMuchMonthlyPage"))

  val getHowMuchMonthlyPage: HttpRequestBuilder =
    http("Get How Much Can You Pay Monthly Page")
      .get(s"$baseUrl$${HowMuchMonthlyPage}")
      .check(status.is(200))
      .check(css("input[name=csrfToken]", "value").saveAs("csrfToken"))

  val postHowMuchMonthlyPage: HttpRequestBuilder =
    http("Post How Much Can You Pay Monthly Page")
      .post(s"$baseUrl$${HowMuchMonthlyPage}": String)
      .formParam("csrfToken", s"$${csrfToken}")
      .formParam("MonthlyPaymentAmount", "600")
      .check(status.is(303))
      .check(header("Location").is(s"$route/which-day-do-you-want-to-pay-each-month").saveAs("WhichDayPage"))

  val getWhichDayPage: HttpRequestBuilder =
    http("Get Which Day Page")
      .get(s"$baseUrl$${WhichDayPage}")
      .check(status.is(200))
      .check(css("input[name=csrfToken]", "value").saveAs("csrfToken"))

  val postWhichDayPage: HttpRequestBuilder =
    http("Post Which Day Page")
      .post(s"$baseUrl$${WhichDayPage}": String)
      .formParam("csrfToken", s"$${csrfToken}")
      .formParam("PaymentDay", "other")
      .formParam("DifferentDay", "19")
      .check(status.is(303))
      .check(header("Location").is(s"$route/retrieve-start-dates").saveAs("StartDates"))

  val getRetrieveStartDates: HttpRequestBuilder =
    http("Get Retrieve Start Dates")
      .get(s"$baseUrl$${StartDates}")
      .check(status.is(303))
      .check(header("Location").is(s"$route/determine-affordable-quotes").saveAs("DetermineQuotes"))

  val getDetermineQuotes: HttpRequestBuilder =
    http("Get Determine Quotes")
      .get(s"$baseUrl$${DetermineQuotes}")
      .check(status.is(303))
      .check(header("Location").is(s"$route/how-many-months-do-you-want-to-pay-over").saveAs("HowManyMonthsPage"))

  val getHowManyMonthsPage: HttpRequestBuilder =
    http("Get How Many Months Page")
      .get(s"$baseUrl$${HowManyMonthsPage}")
      .check(status.is(200))
      .check(css("input[name=csrfToken]", "value").saveAs("csrfToken"))

  val postHowManyMonthsPage: HttpRequestBuilder =
    http("Post How Many Months Page")
      .post(s"$baseUrl$${HowManyMonthsPage}": String)
      .formParam("csrfToken", s"$${csrfToken}")
      .formParam("Instalments", "4")
      .check(status.is(303))
      .check(header("Location").is(s"$route/check-your-payment-plan").saveAs("CheckPlanPage"))

  val getCheckPaymentPlanPage: HttpRequestBuilder =
    http("Get Check Payment Plan Page")
      .get(s"$baseUrl$${CheckPlanPage}")
      .check(status.is(200))
      .check(css("input[name=csrfToken]", "value").saveAs("csrfToken"))

  val postCheckPaymentPlanPage: HttpRequestBuilder =
    http("Post Check Payment Plan Page")
      .post(s"$baseUrl$${CheckPlanPage}": String)
      .formParam("csrfToken", s"$${csrfToken}")
      .check(status.is(303))
      .check(header("Location").is(s"$route/what-type-of-account-details-are-you-providing").saveAs("AccountTypePage"))

  val getAccountTypePage: HttpRequestBuilder =
    http("Get Account Type Page")
      .get(s"$baseUrl$${AccountTypePage}")
      .check(status.is(200))
      .check(css("input[name=csrfToken]", "value").saveAs("csrfToken"))

  val postAccountTypePageBusiness: HttpRequestBuilder =
    http("Post Account Type Page - Business")
      .post(s"$baseUrl$${AccountTypePage}": String)
      .formParam("csrfToken", s"$${csrfToken}")
      .formParam("typeOfAccount", "Business")
      .check(status.is(303))
      .check(header("Location").is(s"$route/set-up-direct-debit").saveAs("DirectDebitPage"))

  val postAccountTypePagePersonal: HttpRequestBuilder =
    http("Post Account Type Page - Personal")
      .post(s"$baseUrl$${AccountTypePage}": String)
      .formParam("csrfToken", s"$${csrfToken}")
      .formParam("typeOfAccount", "Personal")
      .check(status.is(303))
      .check(header("Location").is(s"$route/set-up-direct-debit").saveAs("DirectDebitPage"))

  val getSetupDirectDebitPage: HttpRequestBuilder =
    http("Get Setup Direct Debit Page")
      .get(s"$baseUrl$${DirectDebitPage}")
      .check(status.is(200))
      .check(css("input[name=csrfToken]", "value").saveAs("csrfToken"))

  val postSetupDirectDebitPageBusiness: HttpRequestBuilder =
    http("Post Setup Direct Debit Page - Business")
      .post(s"$baseUrl$${DirectDebitPage}": String)
      .formParam("csrfToken", s"$${csrfToken}")
      .formParam("name", "Lambent Illumination")
      .formParam("sortCode", "207102")
      .formParam("accountNumber", "86563611")
      .formParam("isSoleSignatory", "Yes")
      .check(status.is(303))
      .check(header("Location").is(s"$route/check-your-direct-debit-details").saveAs("CheckDirectDebitPage"))

  val postSetupDirectDebitPagePersonal: HttpRequestBuilder =
    http("Post Setup Direct Debit Page - Personal")
      .post(s"$baseUrl$${DirectDebitPage}": String)
      .formParam("csrfToken", s"$${csrfToken}")
      .formParam("name", "Teddy Dickson")
      .formParam("sortCode", "207102")
      .formParam("accountNumber", "44311655")
      .formParam("isSoleSignatory", "Yes")
      .check(status.is(303))
      .check(header("Location").is(s"$route/check-your-direct-debit-details").saveAs("CheckDirectDebitPage"))

  val getDirectDebitSummaryPage: HttpRequestBuilder =
    http("Get Direct Debit Summary Page")
      .get(s"$baseUrl$${CheckDirectDebitPage}")
      .check(status.is(200))
      .check(css("input[name=csrfToken]", "value").saveAs("csrfToken"))

  val postDirectDebitSummaryPage: HttpRequestBuilder =
    http("Post Direct Debit Summary Page")
      .post(s"$baseUrl$${CheckDirectDebitPage}": String)
      .formParam("csrfToken", s"$${csrfToken}")
      .check(status.is(303))
      .check(header("Location").is(s"$route/terms-and-conditions").saveAs("TermsPage"))

  val getTermsAndConditionsPage: HttpRequestBuilder =
    http("Get Terms and Conditions Page")
      .get(s"$baseUrl$${TermsPage}")
      .check(status.is(200))
      .check(css("input[name=csrfToken]", "value").saveAs("csrfToken"))

  val postTermsAndConditionsPage: HttpRequestBuilder =
    http("Post Terms and Conditions Page")
      .post(s"$baseUrl$${TermsPage}": String)
      .formParam("csrfToken", s"$${csrfToken}")
      .check(status.is(303))
      .check(header("Location").is(s"$route/submit-arrangement").saveAs("SubmitArrangement"))

  val getSubmitArrangement: HttpRequestBuilder =
    http("Get Submit Arrangement")
      .get(s"$baseUrl$${SubmitArrangement}")
      .check(status.is(303))
      .check(header("Location").is(s"$route/payment-plan-set-up").saveAs("ConfirmationPage"))

  val getConfirmationPage: HttpRequestBuilder =
    http("Get Confirmation Page")
      .get(s"$baseUrl$${ConfirmationPage}")
      .check(status.is(200))

}