package screens

import android.icu.util.Calendar
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import com.atdroid.atyurin.futuremoney.R
import com.atdroid.atyurin.futuremoney.fragments.IncomesFragment
import com.kaspersky.kaspresso.testcases.core.testcontext.TestContext
import io.github.kakaocup.kakao.common.views.KView
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView
import org.hamcrest.CoreMatchers.`is`
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class IncomesScreen(testContext: TestContext<*>)  : BaseScreen(testContext){
    override val layoutId: Int = R.layout.fragment_totals
    override val viewClass: Class<*> = IncomesFragment::class.java

    private val fab = KButton { withId(R.id.fab) }

    private val type = KView { withId(R.id.sp_item_type) }

    private val beginDate = KTextView {withId(R.id.ll_item_begin_date)}
    private val endDate = KTextView {withId(R.id.ll_item_end_date)}

    private val periodType = KView { withId(R.id.sp_period_type_spinner) }

    private val period = KEditText { withId(R.id.et_period_value) }


    private val incomeName = KTextView {
        withId(R.id.tv_budget_item_name)
        isDisplayed()
    }
    private val incomeValue = KTextView {
        withId(R.id.tv_budget_item_value)
        isDisplayed()
    }
    private val incomeDate = KTextView {
        withId(R.id.tv_budget_item_date)
        isDisplayed()
    }



    private val inputName = KEditText { withId(R.id.et_item_name_value) }
    private val inputAmount = KEditText { withId(R.id.et_item_amount_value) }

    fun swipeLeft(){
        onView(withId(R.id.view_pager))
            .perform(ViewActions.swipeLeft())
    }

    private fun tab(name: String) = KTextView({
        isDescendantOfA {
            withId(R.id.view_pager_title_strip)
        }
        withText(name)
    })

    fun openTab(name: String) {
        tab(name).click()
    }

    fun openAddIncome(){
        fab.click()
    }

    fun fillName(name: String){
        inputName.replaceText(name)
    }

    fun fillAmount(amount: String){
        inputAmount.replaceText(amount)
    }

    fun selectType(text: String){
        type.click()
        onData(`is`(text)).perform(click())
    }

    fun fillPeriodic(beginDay: String, endDay: String, period: String, typePeriod: String ){
        selectBeginDayForPeriodic(beginDay)
        selectEndDayForPeriodic(endDay)
        selectPeriod(period)
        selectTypePeriod(typePeriod)
    }

    fun selectEndDayForPeriodic(day: String){
        endDate.click()

        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

        device.findObject(UiSelector().text(day)).click()

        device.findObject(UiSelector().text("OK")).click()
    }

    fun selectBeginDayForPeriodic(day: String){
        beginDate.click()

        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

        device.findObject(UiSelector().text(day)).click()

        device.findObject(UiSelector().text("OK")).click()
    }

    fun selectPeriod(period: String){
        this.period.replaceText(period)
    }

    fun selectTypePeriod(text: String){
        periodType.click()
        onData(`is`(text)).perform(click())
    }

    fun checkCard(name: String, amount: String, beginDay: String, endDay: String){
        incomeName.hasText(name)
        incomeValue.hasText(amount)

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1

        val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yy")
        val begin = LocalDate.of(year, month, beginDay.toInt()).format(dateFormatter)
        val end = LocalDate.of(year, month, endDay.toInt()).format(dateFormatter)
        val expectedText = "from $begin to $end"


        incomeDate.hasText(expectedText)
    }

    companion object {
        const val SCREEN_NAME = "Экран доходов"

        inline operator fun invoke(testContext: TestContext<*>, crossinline block: IncomesScreen.() -> Unit) {
            testContext.step(SCREEN_NAME) {
                IncomesScreen(testContext).apply {
                    block()
                }
            }
        }
    }

}