package screens

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.atdroid.atyurin.futuremoney.R
import com.kaspersky.components.kautomator.component.text.UiButton
import com.kaspersky.components.kautomator.component.text.UiTextView
import com.kaspersky.kaspresso.kaspresso.Kaspresso
import com.kaspersky.kaspresso.params.FlakySafetyParams
import com.kaspersky.kaspresso.screens.KScreen
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import com.kaspersky.kaspresso.testcases.core.testcontext.TestContext
import com.kaspersky.kaspresso.testcases.models.info.StepInfo
import io.github.kakaocup.kakao.text.KButton


abstract class BaseScreen : KScreen<BaseScreen>(){

    private val menuBurger = KButton { withId(R.id.nav_view) }

    private val navTotals =  KButton  { withId(R.id.nav_totals) }
    private val navAccounts  =  KButton  { withId(R.id.nav_accounts) }
    private val navIncomes =  KButton  { withId(R.id.nav_incomes) }
    private val navOutcomes =  KButton  { withId(R.id.nav_outcomes) }
    private val navAbout =  KButton  { withId(R.id.nav_about) }

    private val confirm = KButton  { withId(R.id.action_btn_add_budget_item_confirm) }

    private val calc = KButton  { withId(R.id.action_btn_calc_totals) }

    private val drawerLayoutId = R.id.drawer_layout

    fun openMenu() {
        onView(withId(drawerLayoutId)).perform(DrawerActions.open())
    }


    fun confirm(){
        confirm.click()
    }

    fun calculate(){
        calc.click()
    }

    fun openTotals(){
        navTotals.click()
    }

    fun openAccounts(){
        navAccounts.click()
    }

    fun openIncomes(){
        navIncomes.click()
    }

    fun openOutcomes(){
        navOutcomes.click()
    }

    fun openAbout(){
        navAbout.click()
    }

}