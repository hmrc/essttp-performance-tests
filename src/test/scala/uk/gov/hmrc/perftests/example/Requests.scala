/*
 * Copyright 2025 HM Revenue & Customs
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
import scala.util.Random

object Requests extends ServicesConfiguration {

  val baseUrl: String  = baseUrlFor("essttp-frontend")
  val emailUrl: String = baseUrlFor("email-verification")
  val route: String    = "/set-up-a-payment-plan"

  def vatId(): String = Random.between(100000000, 999999999).toString
  def nino(): String = "AB" + Random.between(100000, 999999).toString + "C"



  val getInitialPage: HttpRequestBuilder =
    http("Get Start Page")
      .get(s"$baseUrl$route/test-only/tax-regime")
      .check(status.is(200))
      .check(css("input[name=csrfToken]", "value").saveAs("csrfToken"))

  val postInitialPageEpaye: HttpRequestBuilder =
    http("Post Initial Page - EPAYE")
      .post(s"$baseUrl$route/test-only/tax-regime": String)
      .formParam("csrfToken", _("csrfToken").as[String])
      .formParam("taxRegime", _ => "Epaye")
      .check(status.is(303))
      .check(header(_ => "Location").is(s"$route/test-only/start-journey-epaye").saveAs("EpayeStartPage"))

  val postInitialPageSa: HttpRequestBuilder =
    http("Post Initial Page - SA")
      .post(s"$baseUrl$route/test-only/tax-regime": String)
      .formParam("csrfToken", _("csrfToken").as[String])
      .formParam("taxRegime", _ => "SA")
      .check(status.is(303))
      .check(header(_ => "Location").is(s"$route/test-only/start-journey-sa").saveAs("SaStartPage"))

  val postInitialPageVat: HttpRequestBuilder =
    http("Post Initial Page - VAT")
      .post(s"$baseUrl$route/test-only/tax-regime": String)
      .formParam("csrfToken", _("csrfToken").as[String])
      .formParam("taxRegime", _ => "VAT")
      .check(status.is(303))
      .check(header(_ => "Location").is(s"$route/test-only/start-journey-vat").saveAs("VatStartPage"))

  val postInitialPageSimp: HttpRequestBuilder =
    http("Post Initial Page - Simple Assessment")
      .post(s"$baseUrl$route/test-only/tax-regime": String)
      .formParam("csrfToken", _("csrfToken").as[String])
      .formParam("taxRegime", _ => "SIMP")
      .check(status.is(303))
      .check(header(_ => "Location").is(s"$route/test-only/start-journey-simp").saveAs("SimpStartPage"))

  val getEpayeStartPage: HttpRequestBuilder =
    http("Get EPAYE Start Page")
      .get(s"$baseUrl#{EpayeStartPage}")
      .check(status.is(200))

  val getSaStartPage: HttpRequestBuilder =
    http("Get SA Start Page")
      .get(s"$baseUrl#{SaStartPage}")
      .check(status.is(200))

  val getVatStartPage: HttpRequestBuilder =
    http("Get VAT Start Page")
      .get(s"$baseUrl#{VatStartPage}")
      .check(status.is(200))

  val getSimpStartPage: HttpRequestBuilder =
    http("Get Simple Assessment Start Page")
      .get(s"$baseUrl#{SimpStartPage}")
      .check(status.is(200))

  val postStartPageBusinessEpaye: HttpRequestBuilder =
    http("Post Start Page - Business")
      .post(s"$baseUrl$route/test-only/start-journey-epaye": String)
      .formParam("csrfToken", _("csrfToken").as[String])
      .formParam("credId", _ => "")
      .formParam("signInAs", _ => "Organisation")
      .formParam("confidenceLevel", _ => "50")
      .formParam("nino", _ => "AB123456C")
      .formParam("enrolments[]", _ => "Epaye")
      .formParam("payeDebtTotalAmount", _ => "10000")
      .formParam("interestAmount", _ => "0")
      .formParam("numberOfChargeTypeAssessments", _ => "1")
      .formParam("numberOfCustomerPostcodes", _ => "1")
      .formParam("payeTaxReference", _ => "")
      .formParam("mainTrans", _ => "2000")
      .formParam("subTrans", _ => "1000")
      .formParam("regimeDigitalCorrespondence", _ => "true")
      .formParam("emailAddressPresent", _ => "true")
      .formParam("isInterestBearingCharge", _ => "")
      .formParam("useChargeReference", _ => "")
      .formParam("ddInProgress", _ => "")
      .formParam("planMinLength", _ => "1")
      .formParam("planMaxLength", _ => "12")
      .formParam("origin", _ => "Origins.Epaye.Bta")
      .check(status.is(303))
      .check(header(_ => "Location").is(s"$route/test-only/bta-epaye-page").saveAs("btaPage"))

  val postStartPageIndividualEpaye: HttpRequestBuilder =
    http("Post Start Page - Individual")
      .post(s"$baseUrl$route/test-only/start-journey-epaye": String)
      .formParam("csrfToken", _("csrfToken").as[String])
      .formParam("credId", _ => "")
      .formParam("signInAs", _ => "Individual")
      .formParam("confidenceLevel", _ => "50")
      .formParam("nino", _ => "AB123456C")
      .formParam("enrolments[]", _ => "Epaye")
      .formParam("payeDebtTotalAmount", _ => "10000")
      .formParam("interestAmount", _ => "0")
      .formParam("numberOfChargeTypeAssessments", _ => "1")
      .formParam("numberOfCustomerPostcodes", _ => "1")
      .formParam("payeTaxReference", _ => "")
      .formParam("mainTrans", _ => "2000")
      .formParam("subTrans", _ => "1000")
      .formParam("regimeDigitalCorrespondence", _ => "true")
      .formParam("emailAddressPresent", _ => "true")
      .formParam("isInterestBearingCharge", _ => "")
      .formParam("useChargeReference", _ => "")
      .formParam("ddInProgress", _ => "")
      .formParam("planMinLength", _ => "1")
      .formParam("planMaxLength", _ => "12")
      .formParam("origin", _ => "Origins.Epaye.DetachedUrl")
      .check(status.is(303))
      .check(header(_ => "Location").is(s"$route/epaye/start").saveAs("start"))

  val postStartPageIneligibleEpaye: HttpRequestBuilder =
    http("Post Start Page - Ineligible")
      .post(s"$baseUrl$route/test-only/start-journey-epaye": String)
      .formParam("csrfToken", _("csrfToken").as[String])
      .formParam("credId", _ => "")
      .formParam("signInAs", _ => "Individual")
      .formParam("confidenceLevel", _ => "50")
      .formParam("nino", _ => "AB123456C")
      .formParam("enrolments[]", _ => "Epaye")
      .formParam("payeDebtTotalAmount", _ => "10000")
      .formParam("interestAmount", _ => "0")
      .formParam("numberOfChargeTypeAssessments", _ => "1")
      .formParam("numberOfCustomerPostcodes", _ => "1")
      .formParam("payeTaxReference", _ => "")
      .formParam("mainTrans", _ => "2000")
      .formParam("subTrans", _ => "1000")
      .formParam("regimeDigitalCorrespondence", _ => "true")
      .formParam("eligibilityErrors[]", _ => "IsMoreThanMaxDebtAllowance")
      .formParam("eligibilityErrors[]", _ => "ExistingTtp")
      .formParam("isInterestBearingCharge", _ => "")
      .formParam("useChargeReference", _ => "")
      .formParam("ddInProgress", _ => "")
      .formParam("planMinLength", _ => "1")
      .formParam("planMaxLength", _ => "12")
      .formParam("origin", _ => "Origins.Epaye.Bta")
      .check(status.is(303))
      .check(header(_ => "Location").is(s"$route/test-only/bta-epaye-page").saveAs("btaPage"))

  val postStartPageBusinessVat: HttpRequestBuilder =
    http("Post Start Page - Business")
      .post(s"$baseUrl$route/test-only/start-journey-vat": String)
      .formParam("csrfToken", _("csrfToken").as[String])
      .formParam("credId", _ => "")
      .formParam("signInAs", _ => "Organisation")
      .formParam("confidenceLevel", _ => "50")
      .formParam("nino", _ => "AB123456C")
      .formParam("enrolments[]", _ => "Vat")
      .formParam("vatDebtTotalAmount", _ => "10000")
      .formParam("interestAmount", _ => "0")
      .formParam("numberOfChargeTypeAssessments", _ => "1")
      .formParam("numberOfCustomerPostcodes", _ => "1")
      .formParam("vatTaxReference", _ => "")
      .formParam("mainTrans", _ => "4700")
      .formParam("subTrans", _ => "1174")
      .formParam("regimeDigitalCorrespondence", _ => "true")
      .formParam("emailAddressPresent", _ => "true")
      .formParam("isInterestBearingCharge", _ => "")
      .formParam("useChargeReference", _ => "")
      .formParam("chargeBeforeMaxAccountingDate", _ => "")
      .formParam("ddInProgress", _ => "")
      .formParam("planMinLength", _ => "1")
      .formParam("planMaxLength", _ => "12")
      .formParam("chargeBeforeMaxAccountingDate", _ => "")
      .formParam("origin", _ => "Origins.Vat.Bta")
      .check(status.is(303))
      .check(header(_ => "Location").is(s"$route/test-only/bta-vat-page").saveAs("btaPage"))

  val postStartPageIndividualVat: HttpRequestBuilder =
    http("Post Start Page - Individual")
      .post(s"$baseUrl$route/test-only/start-journey-vat": String)
      .formParam("csrfToken", _("csrfToken").as[String])
      .formParam("credId", _ => "")
      .formParam("signInAs", _ => "Individual")
      .formParam("confidenceLevel", _ => "50")
      .formParam("nino", _ => "AB123456C")
      .formParam("enrolments[]", _ => "Vat")
      .formParam("vatDebtTotalAmount", _ => "10000")
      .formParam("interestAmount", _ => "0")
      .formParam("numberOfChargeTypeAssessments", _ => "1")
      .formParam("numberOfCustomerPostcodes", _ => "1")
      .formParam("vatTaxReference", _ => vatId())
      .formParam("mainTrans", _ => "4700")
      .formParam("subTrans", _ => "1174")
      .formParam("regimeDigitalCorrespondence", _ => "true")
      .formParam("emailAddressPresent", _ => "true")
      .formParam("isInterestBearingCharge", _ => "")
      .formParam("useChargeReference", _ => "")
      .formParam("chargeBeforeMaxAccountingDate", _ => "")
      .formParam("ddInProgress", _ => "")
      .formParam("planMinLength", _ => "1")
      .formParam("planMaxLength", _ => "12")
      .formParam("origin", _ => "Origins.Vat.DetachedUrl")
      .check(status.is(303))
      .check(header(_ => "Location").is(s"$route/vat/start").saveAs("start"))

  val postStartPageIneligibleVat: HttpRequestBuilder =
    http("Post Start Page - Ineligible")
      .post(s"$baseUrl$route/test-only/start-journey-vat": String)
      .formParam("csrfToken", _("csrfToken").as[String])
      .formParam("credId", _ => "")
      .formParam("signInAs", _ => "Individual")
      .formParam("confidenceLevel", _ => "50")
      .formParam("nino", _ => "AB123456C")
      .formParam("enrolments[]", _ => "Vat")
      .formParam("vatDebtTotalAmount", _ => "10000")
      .formParam("interestAmount", _ => "0")
      .formParam("numberOfChargeTypeAssessments", _ => "1")
      .formParam("numberOfCustomerPostcodes", _ => "1")
      .formParam("vatTaxReference", _ => vatId())
      .formParam("mainTrans", _ => "4700")
      .formParam("subTrans", _ => "1174")
      .formParam("regimeDigitalCorrespondence", _ => "true")
      .formParam("emailAddressPresent", _ => "true")
      .formParam("eligibilityErrors[]", _ => "IsMoreThanMaxDebtAllowance")
      .formParam("eligibilityErrors[]", _ => "ExistingTtp")
      .formParam("isInterestBearingCharge", _ => "")
      .formParam("useChargeReference", _ => "")
      .formParam("chargeBeforeMaxAccountingDate", _ => "")
      .formParam("ddInProgress", _ => "")
      .formParam("planMinLength", _ => "1")
      .formParam("planMaxLength", _ => "12")
      .formParam("origin", _ => "Origins.Vat.Bta")
      .check(status.is(303))
      .check(header(_ => "Location").is(s"$route/test-only/bta-vat-page").saveAs("btaPage"))

  val postStartPageBusinessSa: HttpRequestBuilder =
    http("Post Start Page - Business")
      .post(s"$baseUrl$route/test-only/start-journey-sa": String)
      .formParam("csrfToken", _("csrfToken").as[String])
      .formParam("credId", _ => "")
      .formParam("signInAs", _ => "Organisation")
      .formParam("confidenceLevel", _ => "50")
      .formParam("nino", _ => "AB123456C")
      .formParam("enrolments[]", _ => "IrSa")
      .formParam("enrolments[]", _ => "MtdIt")
      .formParam("saDebtTotalAmount", _ => "10000")
      .formParam("interestAmount", _ => "0")
      .formParam("numberOfChargeTypeAssessments", _ => "1")
      .formParam("numberOfCustomerPostcodes", _ => "1")
      .formParam("saTaxReference", _ => "")
      .formParam("mainTrans", _ => "4910")
      .formParam("subTrans", _ => "1000")
      .formParam("regimeDigitalCorrespondence", _ => "true")
      .formParam("emailAddressPresent", _ => "true")
      .formParam("newTtpApi", _ => "true")
      .formParam("isInterestBearingCharge", _ => "")
      .formParam("useChargeReference", _ => "")
      .formParam("transitionToCDCS", _ => "")
      .formParam("chargeSource", _ => "CESA")
      .formParam("planMinLength", _ => "1")
      .formParam("planMaxLength", _ => "12")
      .formParam("origin", _ => "Origins.Sa.Bta")
      .check(status.is(303))
      .check(header(_ => "Location").is(s"$route/test-only/bta-sa-page").saveAs("btaPage"))

  val postStartPageIndividualSa: HttpRequestBuilder =
    http("Post Start Page - Individual")
      .post(s"$baseUrl$route/test-only/start-journey-sa": String)
      .formParam("csrfToken", _("csrfToken").as[String])
      .formParam("credId", _ => "")
      .formParam("signInAs", _ => "Individual")
      .formParam("confidenceLevel", _ => "50")
      .formParam("nino", _ => "AB123456C")
      .formParam("enrolments[]", _ => "IrSa")
      .formParam("enrolments[]", _ => "MtdIt")
      .formParam("saDebtTotalAmount", _ => "10000")
      .formParam("interestAmount", _ => "0")
      .formParam("numberOfChargeTypeAssessments", _ => "1")
      .formParam("numberOfCustomerPostcodes", _ => "1")
      .formParam("saTaxReference", _ => "")
      .formParam("mainTrans", _ => "4910")
      .formParam("subTrans", _ => "1000")
      .formParam("regimeDigitalCorrespondence", _ => "true")
      .formParam("emailAddressPresent", _ => "true")
      .formParam("newTtpApi", _ => "true")
      .formParam("isInterestBearingCharge", _ => "")
      .formParam("useChargeReference", _ => "")
      .formParam("transitionToCDCS", _ => "")
      .formParam("chargeSource", _ => "CESA")
      .formParam("planMinLength", _ => "1")
      .formParam("planMaxLength", _ => "12")
      .formParam("origin", _ => "Origins.Sa.DetachedUrl")
      .check(status.is(303))
      .check(header(_ => "Location").is(s"$route/sa/start").saveAs("start"))

  val postStartPageIneligibleSa: HttpRequestBuilder =
    http("Post Start Page - Ineligible")
      .post(s"$baseUrl$route/test-only/start-journey-sa": String)
      .formParam("csrfToken", _("csrfToken").as[String])
      .formParam("credId", _ => "")
      .formParam("signInAs", _ => "Individual")
      .formParam("confidenceLevel", _ => "50")
      .formParam("nino", _ => "AB123456C")
      .formParam("enrolments[]", _ => "IrSa")
      .formParam("enrolments[]", _ => "MtdIt")
      .formParam("saDebtTotalAmount", _ => "10000")
      .formParam("interestAmount", _ => "0")
      .formParam("numberOfChargeTypeAssessments", _ => "1")
      .formParam("numberOfCustomerPostcodes", _ => "1")
      .formParam("saTaxReference", _ => "")
      .formParam("mainTrans", _ => "4910")
      .formParam("subTrans", _ => "1000")
      .formParam("regimeDigitalCorrespondence", _ => "true")
      .formParam("emailAddressPresent", _ => "true")
      .formParam("eligibilityErrors[]", _ => "IsMoreThanMaxDebtAllowance")
      .formParam("eligibilityErrors[]", _ => "ExistingTtp")
      .formParam("newTtpApi", _ => "true")
      .formParam("isInterestBearingCharge", _ => "")
      .formParam("useChargeReference", _ => "")
      .formParam("transitionToCDCS", _ => "")
      .formParam("chargeSource", _ => "CESA")
      .formParam("planMinLength", _ => "1")
      .formParam("planMaxLength", _ => "12")
      .formParam("origin", _ => "Origins.Sa.Bta")
      .check(status.is(303))
      .check(header(_ => "Location").is(s"$route/test-only/bta-sa-page").saveAs("btaPage"))

  val postStartPageBusinessSimp: HttpRequestBuilder =
    http("Post Start Page - Business")
      .post(s"$baseUrl$route/test-only/start-journey-simp": String)
      .formParam("csrfToken", _("csrfToken").as[String])
      .formParam("credId", _ => "")
      .formParam("signInAs", _ => "Organisation")
      .formParam("confidenceLevel", _ => "50")
      .formParam("nino", _ => nino())
      .formParam("simpDebtTotalAmount", _ => "10000")
      .formParam("interestAmount", _ => "0")
      .formParam("numberOfChargeTypeAssessments", _ => "1")
      .formParam("numberOfCustomerPostcodes", _ => "1")
      .formParam("mainTrans", _ => "2000")
      .formParam("subTrans", _ => "1000")
      .formParam("regimeDigitalCorrespondence", _ => "true")
      .formParam("emailAddressPresent", _ => "true")
      .formParam("isInterestBearingCharge", _ => "")
      .formParam("useChargeReference", _ => "")
      .formParam("planMinLength", _ => "1")
      .formParam("planMaxLength", _ => "36")
      .formParam("origin", _ => "Origins.Simp.DetachedUrl")
      .check(status.is(303))
      .check(header(_ => "Location").is(s"$route/simp/start").saveAs("start"))

  val postStartPageIndividualSimp: HttpRequestBuilder =
    http("Post Start Page - Individual")
      .post(s"$baseUrl$route/test-only/start-journey-simp": String)
      .formParam("csrfToken", _("csrfToken").as[String])
      .formParam("credId", _ => "")
      .formParam("signInAs", _ => "Individual")
      .formParam("confidenceLevel", _ => "50")
      .formParam("nino", _ => nino())
      .formParam("simpDebtTotalAmount", _ => "10000")
      .formParam("interestAmount", _ => "0")
      .formParam("numberOfChargeTypeAssessments", _ => "1")
      .formParam("numberOfCustomerPostcodes", _ => "1")
      .formParam("mainTrans", _ => "2000")
      .formParam("subTrans", _ => "1000")
      .formParam("regimeDigitalCorrespondence", _ => "true")
      .formParam("emailAddressPresent", _ => "true")
      .formParam("isInterestBearingCharge", _ => "")
      .formParam("useChargeReference", _ => "")
      .formParam("planMinLength", _ => "1")
      .formParam("planMaxLength", _ => "36")
      .formParam("origin", _ => "Origins.Simp.DetachedUrl")
      .check(status.is(303))
      .check(header(_ => "Location").is(s"$route/simp/start").saveAs("start"))

  val postStartPageIneligibleSimp: HttpRequestBuilder =
    http("Post Start Page - Ineligible")
      .post(s"$baseUrl$route/test-only/start-journey-simp": String)
      .formParam("csrfToken", _("csrfToken").as[String])
      .formParam("credId", _ => "")
      .formParam("signInAs", _ => "Organisation")
      .formParam("confidenceLevel", _ => "50")
      .formParam("nino", _ => nino())
      .formParam("simpDebtTotalAmount", _ => "10000")
      .formParam("interestAmount", _ => "0")
      .formParam("numberOfChargeTypeAssessments", _ => "1")
      .formParam("numberOfCustomerPostcodes", _ => "1")
      .formParam("mainTrans", _ => "2000")
      .formParam("subTrans", _ => "1000")
      .formParam("regimeDigitalCorrespondence", _ => "true")
      .formParam("emailAddressPresent", _ => "true")
      .formParam("eligibilityErrors[]", _ => "IsMoreThanMaxDebtAllowance")
      .formParam("eligibilityErrors[]", _ => "ExistingTtp")
      .formParam("isInterestBearingCharge", _ => "")
      .formParam("useChargeReference", _ => "")
      .formParam("planMinLength", _ => "1")
      .formParam("planMaxLength", _ => "36")
      .formParam("origin", _ => "Origins.Simp.Pta")
      .check(status.is(303))
      .check(header(_ => "Location").is(s"$route/test-only/pta-simp").saveAs("ptaPage"))


  val getBtaPage: HttpRequestBuilder =
    http("Get BTA Page")
      .get(s"$baseUrl#{btaPage}")
      .check(status.is(200))

  val getPtaPage: HttpRequestBuilder =
    http("Get PTA Page")
      .get(s"$baseUrl#{ptaPage}")
      .check(status.is(200))

  val getStartBtaEpaye: HttpRequestBuilder =
    http("Get Start BTA")
      .get(s"$baseUrl$route/test-only/start-journey-epaye-bta": String)
      .check(status.is(303))
      .check(header(_ => "Location").saveAs("LandingPage"))

  val getStartBtaVat: HttpRequestBuilder =
    http("Get Start BTA")
      .get(s"$baseUrl$route/test-only/start-journey-vat-bta": String)
      .check(status.is(303))
      .check(header(_ => "Location").saveAs("LandingPage"))

  val getStartBtaSa: HttpRequestBuilder =
    http("Get Start BTA")
      .get(s"$baseUrl$route/test-only/start-journey-sa-bta": String)
      .check(status.is(303))
      .check(header(_ => "Location").saveAs("LandingPage"))

  val getStartPtaSimp: HttpRequestBuilder =
    http("Get Start PTA")
      .get(s"$baseUrl$route/test-only/start-journey-simp-pta": String)
      .check(status.is(303))
      .check(header(_ => "Location").saveAs("LandingPage"))

  val getStart: HttpRequestBuilder =
    http("Get Start")
      .get(s"$baseUrl#{start}": String)
      .check(status.is(303))
      .check(header(_ => "Location").saveAs("LandingPage"))

  val getLandingPage: HttpRequestBuilder =
    http("Get Landing Page")
      .get(s"#{LandingPage}")
      .check(status.is(200))

  val getDetermineTaxId: HttpRequestBuilder =
    http("Get Determine Tax Id")
      .get(s"$baseUrl$route/determine-taxId")
      .check(status.is(303))
      .check(header(_ => "Location").is(s"$route/determine-eligibility").saveAs("DetermineEligibility"))

  val getDetermineEligibilityPass: HttpRequestBuilder =
    http("Get Determine Eligibility - Pass")
      .get(s"$baseUrl#{DetermineEligibility}")
      .check(status.is(303))
      .check(header(_ => "Location").is(s"$route/your-bill").saveAs("YourBillPage"))

  val getDetermineEligibilityFailEpaye: HttpRequestBuilder =
    http("Get Determine Eligibility - Fail")
      .get(s"$baseUrl#{DetermineEligibility}")
      .check(status.is(303))
      .check(header(_ => "Location").is(s"$route/not-eligible-epaye").saveAs("IneligiblePage"))

  val getDetermineEligibilityFailVat: HttpRequestBuilder =
    http("Get Determine Eligibility - Fail")
      .get(s"$baseUrl#{DetermineEligibility}")
      .check(status.is(303))
      .check(header(_ => "Location").is(s"$route/not-eligible-vat").saveAs("IneligiblePage"))

  val getDetermineEligibilityFailSa: HttpRequestBuilder =
    http("Get Determine Eligibility - Fail")
      .get(s"$baseUrl#{DetermineEligibility}")
      .check(status.is(303))
      .check(header(_ => "Location").is(s"$route/not-eligible-sa").saveAs("IneligiblePage"))

  val getDetermineEligibilityFailSimp: HttpRequestBuilder =
    http("Get Determine Eligibility - Fail")
      .get(s"$baseUrl#{DetermineEligibility}")
      .check(status.is(303))
      .check(header(_ => "Location").is(s"$route/not-eligible-simple-assessment").saveAs("IneligiblePage"))

  val getIneligiblePage: HttpRequestBuilder =
    http("Get Ineligible Page")
      .get(s"$baseUrl#{IneligiblePage}")
      .check(status.is(200))

  val getYourBillPage: HttpRequestBuilder =
    http("Get Your Bill Page")
      .get(s"$baseUrl#{YourBillPage}")
      .check(status.is(200))
      .check(css("input[name=csrfToken]", "value").saveAs("csrfToken"))

  val postYourBillPage: HttpRequestBuilder =
    http("Post Your Bill Page")
      .post(s"$baseUrl#{YourBillPage}": String)
      .formParam("csrfToken", _("csrfToken").as[String])
      .check(status.is(303))

  val getWhyUnablePage: HttpRequestBuilder =
    http("Get Why Unable to Pay Page")
      .get(s"$baseUrl$route/why-are-you-unable-to-pay-in-full")
      .check(status.is(200))
      .check(css("input[name=csrfToken]", "value").saveAs("csrfToken"))

  val postWhyUnablePage: HttpRequestBuilder =
    http("Post Why Unable to Pay Page - None of above")
      .post(s"$baseUrl$route/why-are-you-unable-to-pay-in-full": String)
      .formParam("csrfToken", _("csrfToken").as[String])
      .formParam("WhyCannotPayInFull[]", _ => "other")
      .check(status.is(303))
      .check(header(_ => "Location").is(s"$route/can-you-make-an-upfront-payment").saveAs("CanYouMakeUpfrontPage"))

  val getCanUpfrontPage: HttpRequestBuilder =
    http("Get Can You Pay Upfront Page")
      .get(s"$baseUrl$route/can-you-make-an-upfront-payment")
      .check(status.is(200))
      .check(css("input[name=csrfToken]", "value").saveAs("csrfToken"))

  val postCanUpfrontPageYes: HttpRequestBuilder =
    http("Post Can You Pay Upfront Page - Yes")
      .post(s"$baseUrl$route/can-you-make-an-upfront-payment": String)
      .formParam("csrfToken", _("csrfToken").as[String])
      .formParam("CanYouMakeAnUpFrontPayment", _ => "Yes")
      .check(status.is(303))
      .check(header(_ => "Location").is(s"$route/how-much-can-you-pay-upfront").saveAs("HowMuchUpfrontPage"))

  val postCanUpfrontPageNo: HttpRequestBuilder =
    http("Post Can You Pay Upfront Page - No")
      .post(s"$baseUrl$route/can-you-make-an-upfront-payment": String)
      .formParam("csrfToken", _("csrfToken").as[String])
      .formParam("CanYouMakeAnUpFrontPayment", _ => "No")
      .check(status.is(303))
      .check(header(_ => "Location").is(s"$route/retrieve-extreme-dates"))

  val getHowMuchUpfrontPage: HttpRequestBuilder =
    http("Get How Much Can You Pay Upfront Page")
      .get(s"$baseUrl#{HowMuchUpfrontPage}")
      .check(status.is(200))
      .check(css("input[name=csrfToken]", "value").saveAs("csrfToken"))

  val postHowMuchUpfrontPage: HttpRequestBuilder =
    http("Post How Much Can You Pay Upfront Page")
      .post(s"$baseUrl#{HowMuchUpfrontPage}": String)
      .formParam("csrfToken", _("csrfToken").as[String])
      .formParam("UpfrontPaymentAmount", _ => "1")
      .check(status.is(303))
      .check(header(_ => "Location").is(s"$route/upfront-payment-summary").saveAs("UpfrontSummaryPage"))

  val getUpfrontSummaryPage: HttpRequestBuilder =
    http("Get Upfront Summary Page")
      .get(s"$baseUrl#{UpfrontSummaryPage}")
      .check(status.is(200))

  val getRetrieveExtremeDates: HttpRequestBuilder =
    http("Get Retrieve Extreme Dates")
      .get(s"$baseUrl$route/retrieve-extreme-dates")
      .check(status.is(303))
      .check(header(_ => "Location").is(s"$route/determine-affordability").saveAs("DetermineAffordability"))

  val getDetermineAffordability: HttpRequestBuilder =
    http("Get Determine Affordability")
      .get(s"$baseUrl#{DetermineAffordability}")
      .check(status.is(303))

  val getPayInSixPageSA: HttpRequestBuilder =
    http("Get Can you pay in 6 months Page - SA")
      .get(s"$baseUrl$route/paying-within-six-months")
      .queryParam("regime", _ => "sa")
      .check(status.is(200))
      .check(css("input[name=csrfToken]", "value").saveAs("csrfToken"))

  val getPayInSixPageVat: HttpRequestBuilder =
    http("Get Can you pay in 6 months Page - VAT")
      .get(s"$baseUrl$route/paying-within-six-months")
      .queryParam("regime", _ => "vat")
      .check(status.is(200))
      .check(css("input[name=csrfToken]", "value").saveAs("csrfToken"))

  val getPayInSixPageEpaye: HttpRequestBuilder =
    http("Get Can you pay in 6 months Page - EPAYE")
      .get(s"$baseUrl$route/paying-within-six-months")
      .queryParam("regime", _ => "epaye")
      .check(status.is(200))
      .check(css("input[name=csrfToken]", "value").saveAs("csrfToken"))

  val postPayInSixPageYes: HttpRequestBuilder =
    http("Post Can you pay in 6 months Page")
      .post(s"$baseUrl$route/paying-within-six-months")
      .formParam("csrfToken", _("csrfToken").as[String])
      .formParam("CanPayWithinSixMonths", _ => "Yes")
      .check(status.is(303))
      .check(header(_ => "Location").is(s"$route/how-much-can-you-pay-each-month").saveAs("HowMuchMonthlyPage"))


  val getHowMuchMonthlyPage: HttpRequestBuilder =
    http("Get How Much Can You Pay Monthly Page")
      .get(s"$baseUrl$route/how-much-can-you-pay-each-month")
      .check(status.is(200))
      .check(css("input[name=csrfToken]", "value").saveAs("csrfToken"))

  val postHowMuchMonthlyPage: HttpRequestBuilder =
    http("Post How Much Can You Pay Monthly Page")
      .post(s"$baseUrl$route/how-much-can-you-pay-each-month": String)
      .formParam("csrfToken", _("csrfToken").as[String])
      .formParam("MonthlyPaymentAmount", _ => "2000")
      .check(status.is(303))
      .check(header(_ => "Location").is(s"$route/which-day-do-you-want-to-pay-each-month").saveAs("WhichDayPage"))

  val getWhichDayPage: HttpRequestBuilder =
    http("Get Which Day Page")
      .get(s"$baseUrl#{WhichDayPage}")
      .check(status.is(200))
      .check(css("input[name=csrfToken]", "value").saveAs("csrfToken"))

  val postWhichDayPage: HttpRequestBuilder =
    http("Post Which Day Page")
      .post(s"$baseUrl#{WhichDayPage}": String)
      .formParam("csrfToken", _("csrfToken").as[String])
      .formParam("PaymentDay", _ => "other")
      .formParam("DifferentDay", _ => "19")
      .check(status.is(303))
      .check(header(_ => "Location").is(s"$route/retrieve-start-dates").saveAs("StartDates"))

  val getRetrieveStartDates: HttpRequestBuilder =
    http("Get Retrieve Start Dates")
      .get(s"$baseUrl#{StartDates}")
      .check(status.is(303))
      .check(header(_ => "Location").is(s"$route/determine-affordable-quotes").saveAs("DetermineQuotes"))

  val getDetermineQuotes: HttpRequestBuilder =
    http("Get Determine Quotes")
      .get(s"$baseUrl#{DetermineQuotes}")
      .check(status.is(303))
      .check(header(_ => "Location").is(s"$route/how-many-months-do-you-want-to-pay-over").saveAs("HowManyMonthsPage"))

  val getHowManyMonthsPage: HttpRequestBuilder =
    http("Get How Many Months Page")
      .get(s"$baseUrl#{HowManyMonthsPage}")
      .check(status.is(200))
      .check(css("input[name=csrfToken]", "value").saveAs("csrfToken"))

  val postHowManyMonthsPage: HttpRequestBuilder =
    http("Post How Many Months Page")
      .post(s"$baseUrl#{HowManyMonthsPage}": String)
      .formParam("csrfToken", _("csrfToken").as[String])
      .formParam("Instalments", _ => "5")
      .check(status.is(303))
      .check(header(_ => "Location").is(s"$route/check-your-payment-plan").saveAs("CheckPlanPage"))

  val getCheckPaymentPlanPage: HttpRequestBuilder =
    http("Get Check Payment Plan Page")
      .get(s"$baseUrl#{CheckPlanPage}")
      .check(status.is(200))
      .check(css("input[name=csrfToken]", "value").saveAs("csrfToken"))

  val postCheckPaymentPlanPage: HttpRequestBuilder =
    http("Post Check Payment Plan Page")
      .post(s"$baseUrl#{CheckPlanPage}": String)
      .formParam("csrfToken", _("csrfToken").as[String])
      .check(status.is(303))
      .check(header(_ => "Location").is(s"$route/check-you-can-set-up-a-direct-debit").saveAs("AboutYourBankAccountPage"))

  val getAboutYourBankAccountPage: HttpRequestBuilder =
    http("Get About Your Bank Account Page")
      .get(s"$baseUrl#{AboutYourBankAccountPage}")
      .check(status.is(200))
      .check(css("input[name=csrfToken]", "value").saveAs("csrfToken"))

  val postAboutYourBankAccountPageSoleSig: HttpRequestBuilder =
    http("Post About Your Bank Account Page - Sole Signatory")
      .post(s"$baseUrl#{AboutYourBankAccountPage}": String)
      .formParam("csrfToken", _("csrfToken").as[String])
      .formParam("isSoleSignatory", _ => "Yes")
      .check(status.is(303))
      .check(header(_ => "Location").is(s"$route/bank-account-type").saveAs("TypeOfBankAccountPage"))

  val postAboutYourBankAccountPageNotSoleSig: HttpRequestBuilder =
    http("Post About Your Bank Account Page - Not Sole Signatory")
      .post(s"$baseUrl#{AboutYourBankAccountPage}": String)
      .formParam("csrfToken", _("csrfToken").as[String])
      .formParam("isSoleSignatory", _ => "No")
      .check(status.is(303))
      .check(header(_ => "Location").is(s"$route/you-cannot-set-up-a-direct-debit-online").saveAs("CannotSetUpDirectDebitOnlinePage"))

  val getCannotSetUpDirectDebitOnlinePage: HttpRequestBuilder =
      http("Cannot Set Up Direct Debit Online Page")
        .get(s"$baseUrl#{CannotSetUpDirectDebitOnlinePage}")
        .check(status.is(200))

  val getTypeOfBankAccountPage: HttpRequestBuilder =
    http("Get Type Of Bank Account Page")
      .get(s"$baseUrl#{TypeOfBankAccountPage}")
      .check(status.is(200))
      .check(css("input[name=csrfToken]", "value").saveAs("csrfToken"))

  val postTypeOfBankAccountPageBusiness: HttpRequestBuilder =
      http("Post Type Of Bank Account Page - Business")
        .post(s"$baseUrl#{TypeOfBankAccountPage}": String)
        .formParam("csrfToken", _("csrfToken").as[String])
        .formParam("accountType", _ => "Business")
        .check(status.is(303))
        .check(header(_ => "Location").is(s"$route/bank-account-details").saveAs("DirectDebitPage"))

    val postTypeOfBankAccountPagePersonal: HttpRequestBuilder =
      http("Post Type Of Bank Account Page - Personal")
        .post(s"$baseUrl#{TypeOfBankAccountPage}": String)
        .formParam("csrfToken", _("csrfToken").as[String])
        .formParam("accountType", _ => "Personal")
        .check(status.is(303))
        .check(header(_ => "Location").is(s"$route/bank-account-details").saveAs("DirectDebitPage"))

  val getSetupDirectDebitPage: HttpRequestBuilder =
    http("Get Setup Direct Debit Page")
      .get(s"$baseUrl#{DirectDebitPage}")
      .check(status.is(200))
      .check(css("input[name=csrfToken]", "value").saveAs("csrfToken"))

  val postSetupDirectDebitPageBusiness: HttpRequestBuilder =
    http("Post Setup Direct Debit Page - Business")
      .post(s"$baseUrl#{DirectDebitPage}": String)
      .formParam("csrfToken", _("csrfToken").as[String])
      .formParam("name", _ => "Lambent Illumination")
      .formParam("sortCode", _ => "207102")
      .formParam("accountNumber", _ => "86563611")
      .check(status.is(303))
      .check(header(_ => "Location").is(s"$route/check-your-direct-debit-details").saveAs("CheckDirectDebitPage"))

  val postSetupDirectDebitPagePersonal: HttpRequestBuilder =
    http("Post Setup Direct Debit Page - Personal")
      .post(s"$baseUrl#{DirectDebitPage}": String)
      .formParam("csrfToken", _("csrfToken").as[String])
      .formParam("name", _ => "Teddy Dickson")
      .formParam("sortCode", _ => "207102")
      .formParam("accountNumber", _ => "44311655")
      .check(status.is(303))
      .check(header(_ => "Location").is(s"$route/check-your-direct-debit-details").saveAs("CheckDirectDebitPage"))

  val getDirectDebitSummaryPage: HttpRequestBuilder =
    http("Get Direct Debit Summary Page")
      .get(s"$baseUrl#{CheckDirectDebitPage}")
      .check(status.is(200))
      .check(css("input[name=csrfToken]", "value").saveAs("csrfToken"))

  val postDirectDebitSummaryPage: HttpRequestBuilder =
    http("Post Direct Debit Summary Page")
      .post(s"$baseUrl#{CheckDirectDebitPage}": String)
      .formParam("csrfToken", _("csrfToken").as[String])
      .check(status.is(303))
      .check(header(_ => "Location").is(s"$route/terms-and-conditions").saveAs("TermsPage"))

  val getTermsAndConditionsPage: HttpRequestBuilder =
    http("Get Terms and Conditions Page")
      .get(s"$baseUrl#{TermsPage}")
      .check(status.is(200))
      .check(css("input[name=csrfToken]", "value").saveAs("csrfToken"))

  val postTermsAndConditionsPage: HttpRequestBuilder =
    http("Post Terms and Conditions Page")
      .post(s"$baseUrl#{TermsPage}": String)
      .formParam("csrfToken", _("csrfToken").as[String])
      .check(status.is(303))
      .check(header(_ => "Location").is(s"$route/which-email-do-you-want-to-use").saveAs("WhichEmailPage"))

  val getWhichEmailPage: HttpRequestBuilder =
    http("Get Which Email Page")
      .get(s"$baseUrl#{WhichEmailPage}")
      .check(status.is(200))
      .check(css("input[name=csrfToken]", "value").saveAs("csrfToken"))

  val postWhichEmailPage: HttpRequestBuilder =
    http("Post Which Email Page")
      .post(s"$baseUrl#{WhichEmailPage}": String)
      .formParam("csrfToken", _("csrfToken").as[String])
      .formParam("selectAnEmailToUseRadio", _ => "new")
      .formParam("newEmailInput", _ => "helloexample@email.com")
      .check(status.is(303))
      .check(header(_ => "Location").is(s"$route/email-verification").saveAs("EmailVerification"))

  val getEmailVerification: HttpRequestBuilder =
    http("Get Email Verification")
      .get(s"$baseUrl#{EmailVerification}")
      .check(status.is(303))
      .check(header(_ => "Location").saveAs("VerificationFrontend"))

  val getVerificationFrontend: HttpRequestBuilder =
    http("Get Verification Frontend")
      .get(s"$emailUrl#{VerificationFrontend}")
      .check(status.is(200))

  val getVerificationFrontendLocal: HttpRequestBuilder =
    http("Get Verification Frontend")
      .get(s"#{VerificationFrontend}")
      .check(status.is(200))

  val getTestOnlyPasscode: HttpRequestBuilder =
    http("Get Test Only Passcode")
      .get(s"$baseUrl$route/test-only/email-verification-passcodes")
      .check(jsonPath("$..passcodes[-1:].passcode").find.saveAs("code"))
      .check(status.is(200))

  val postVerificationFrontend: HttpRequestBuilder =
    http("Post Verification Frontend")
      .post { session =>
        val url = session("VerificationFrontend").as[String]
        if (url.startsWith("/")) s"$emailUrl$url" else url
      }
      .formParam("csrfToken", _("csrfToken").as[String])
      .formParam("passcode", _("code").as[String])
      .check(status.is(303))

  val getEmailCallback: HttpRequestBuilder =
    http("Get Email Callback")
      .get(s"$baseUrl$route/email-callback")
      .check(status.is(303))
      .check(header(_ => "Location").is(s"$route/email-address-confirmed").saveAs("EmailConfirmationPage"))

  val getEmailConfirmationPage: HttpRequestBuilder =
    http("Get Email Confirmation Page")
      .get(s"$baseUrl#{EmailConfirmationPage}")
      .check(status.is(200))

  val getSubmitArrangementEpaye: HttpRequestBuilder =
    http("Get Submit Arrangement")
      .get(s"$baseUrl$route/submit-arrangement")
      .check(status.is(303))
      .check(header(_ => "Location").is(s"$route/epaye-payment-plan-set-up").saveAs("ConfirmationPage"))

  val getSubmitArrangementVat: HttpRequestBuilder =
    http("Get Submit Arrangement")
      .get(s"$baseUrl$route/submit-arrangement")
      .check(status.is(303))
      .check(header(_ => "Location").is(s"$route/vat-payment-plan-set-up").saveAs("ConfirmationPage"))

  val getSubmitArrangementSa: HttpRequestBuilder =
    http("Get Submit Arrangement")
      .get(s"$baseUrl$route/submit-arrangement")
      .check(status.is(303))
      .check(header(_ => "Location").is(s"$route/sa-payment-plan-set-up").saveAs("ConfirmationPage"))

  val getSubmitArrangementSimp: HttpRequestBuilder =
    http("Get Submit Arrangement")
      .get(s"$baseUrl$route/submit-arrangement")
      .check(status.is(303))
      .check(header(_ => "Location").is(s"$route/simple-assessment-payment-plan-set-up").saveAs("ConfirmationPage"))

  val getConfirmationPage: HttpRequestBuilder =
    http("Get Confirmation Page")
      .get(s"$baseUrl#{ConfirmationPage}")
      .check(status.is(200))

}
