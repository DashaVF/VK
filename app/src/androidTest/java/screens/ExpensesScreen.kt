package screens

import com.atdroid.atyurin.futuremoney.R
import com.atdroid.atyurin.futuremoney.fragments.OutcomesFragment
import com.kaspersky.kaspresso.testcases.core.testcontext.TestContext
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.text.KButton

class ExpensesScreen(testContext: TestContext<*>) : BaseScreen(testContext){
    override val layoutId: Int = R.layout.fragment_totals
    override val viewClass: Class<*> = OutcomesFragment::class.java

    private val inputName = KEditText { withId(R.id.et_item_name_value) }
    private val inputAmount = KEditText { withId(R.id.et_item_amount_value) }

    private val fab = KButton { withId(R.id.fab) }

    fun fillName(name: String){
        inputName.replaceText(name)
    }

    fun fillAmount(amount: String){
        inputAmount.replaceText(amount)
    }

    fun openAddExpenses(){
        fab.click()
    }

    companion object {
        const val SCREEN_NAME = "Экран расходов"

        inline operator fun invoke(testContext: TestContext<*>, crossinline block: ExpensesScreen.() -> Unit) {
            testContext.step(SCREEN_NAME) {
                ExpensesScreen(testContext).apply {
                    block()
                }
            }
        }
    }

}