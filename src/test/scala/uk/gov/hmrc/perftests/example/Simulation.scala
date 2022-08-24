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

import uk.gov.hmrc.performance.simulation.PerformanceTestRunner
import uk.gov.hmrc.perftests.example.Requests._

class Simulation extends PerformanceTestRunner {

  setup("start-page", "Start Page") withRequests (getStartPage, postStartPage)

  setup("bta-page", "BTA Page") withRequests (getBtaPage, getStartBta)

  setup("landing-page", "Landing Page") withRequests (getLandingPage, getDetermineTaxId, getDetermineEligibility)

  setup ("your-bill-page", "Your Bill Page") withRequests(getYourBillPage, postYourBillPage)

  setup ("can-pay-upfront-page", "Can Pay Upfront Page") withRequests(getCanUpfrontPage, postCanUpfrontPage)

  setup ("how-much-upfront-page", "How Much to Pay Upfront Page") withRequests(getHowMuchUpfrontPage, postHowMuchUpfrontPage)

  setup("upfront-summary-page", "Upfront Summary Page") withRequests (getUpfrontSummaryPage, getRetrieveExtremeDates, getDetermineAffordability)

  setup ("how-much-monthly-page", "How Much to Pay Monthly Page") withRequests(getHowMuchMonthlyPage, postHowMuchMonthlyPage)

  setup ("which-day-page", "Which Day Page") withRequests(getWhichDayPage, postWhichDayPage, getRetrieveStartDates, getDetermineQuotes)

  setup ("how-many-months-page", "How Many Months Page") withRequests(getHowManyMonthsPage, postHowManyMonthsPage)

  setup ("check-payment-plan-page", "Check Payment Plan Page") withRequests(getCheckPaymentPlanPage, postCheckPaymentPlanPage)

  setup ("account-type-page", "Account Type Page") withRequests(getAccountTypePage, postAccountTypePage)

  setup ("setup-direct-debit-page", "Setup Direct Debit Page") withRequests(getSetupDirectDebitPage, postSetupDirectDebitPage)

  setup ("direct-debit-summary-page", "Direct Debit Summary Page") withRequests(getDirectDebitSummaryPage, postDirectDebitSummaryPage)

  setup ("terms-and-conditions-page", "Terms and Conditions Page") withRequests(getTermsAndConditionsPage, postTermsAndConditionsPage, getSubmitArrangement)

  setup ("confirmation-page", "Confirmation Page") withRequests getConfirmationPage

  runSimulation()
}
