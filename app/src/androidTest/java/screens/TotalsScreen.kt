package screens

import androidx.compose.ui.test.hasText
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.action.ViewActions.click
import com.atdroid.atyurin.futuremoney.R
import com.atdroid.atyurin.futuremoney.fragments.TotalsFragment
import com.kaspersky.kaspresso.testcases.core.testcontext.TestContext
import io.github.kakaocup.kakao.common.views.KView
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView
import org.hamcrest.CoreMatchers.`is`
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class TotalsScreen(testContext: TestContext<*>) : BaseScreen(testContext){
    override val layoutId: Int = R.layout.fragment_totals
    override val viewClass: Class<*> = TotalsFragment::class.java

    private val calculateButton = KButton{withId(R.id.action_btn_calc_totals)}
    private val calculateSpinner = KView{withId(R.id.sp_begin_date_type)}
    private val graph = KView{withId(R.id.graph_view_totals)}
    private val accountsTitle = KTextView{withId(R.id.tv_accounts_total_title)}
    private val accountsValue = KTextView{withId(R.id.tv_accounts_total_value)}
    private val incomesValue = KTextView{withId(R.id.tv_incomes_total_value)}
    private val incomesTitle = KTextView{withId(R.id.tv_incomes_total_title)}
    private val expensesTitle = KTextView{withId(R.id.tv_outcomes_total_title)}
    private val expensesValue = KTextView{withId(R.id.tv_outcomes_total_value)}
    private val totalValue = KTextView{withId(R.id.tv_total_value)}
    private val totalTitle = KTextView{withId(R.id.tv_total_title)}

    private val headerGraph = KTextView{withId(R.id.tv_totals_title)}
    private val blockCalculateType = KView{withId(R.id.ll_calculate_date)}
    private val blockSettlementDate = KView{withId(R.id.ll_begin_date_type)}

    fun calculate(){
        calculateButton.click()
    }

    fun selectCalculate(text: String){
        calculateSpinner.click()
        onData(`is`(text)).perform(click())
    }

    fun checkAllElements(calculateType: String, accounts: String, incomes: String, expenses: String){
        checkDefaultElements(calculateType)
        checkCalculateData(accounts, incomes, expenses)
    }

    fun checkDefaultElements(calculateType: String){
        val date = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.ENGLISH)
        val dateValue = date.format(formatter)

        blockCalculateType{
            isDisplayed()
            hasText(calculateType)
        }
        blockSettlementDate{
            isDisplayed()
            hasText(dateValue)
        }
    }

    fun checkCalculateData(accounts: String, incomes: String, expenses: String){
        headerGraph.hasText("Total:")
        graph.isDisplayed()

        accountsTitle.hasText("Accounts:")
        accountsValue.hasText("%,d".format(accounts.toInt()))

        incomesValue.hasText("%,d".format(incomes.toInt()))
        incomesTitle.hasText("Incomes:")

        expensesTitle.hasText("Expenses:")
        expensesValue.hasText("%,d".format(expenses.toInt()))

        totalTitle.hasText("Total amount:")
        val total =  accounts.toInt() + incomes.toInt() - expenses.toInt()
        totalValue.hasText("%,d".format(total))
    }

    companion object {
        const val SCREEN_NAME = "Экран подсчетов"

        inline operator fun invoke(testContext: TestContext<*>, crossinline block: TotalsScreen.() -> Unit) {
            testContext.step(SCREEN_NAME) {
                TotalsScreen(testContext).apply {
                    block()
                }
            }
        }
    }

}