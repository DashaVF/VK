package screens

import android.app.Activity
import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.atdroid.atyurin.futuremoney.R
import com.atdroid.atyurin.futuremoney.fragments.AccountsFragment
import com.kaspersky.kaspresso.testcases.core.testcontext.TestContext
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.list.KAdapterItem
import io.github.kakaocup.kakao.recycler.KRecyclerItem
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.not


class AccountsScreen(testContext: TestContext<*>) : BaseScreen(testContext){

    override val layoutId: Int = R.layout.fragment_account_items_list
    override val viewClass: Class<*> = AccountsFragment::class.java

    private val fab = KButton { withId(R.id.fab) }

    private val inputName = KEditText { withId(R.id.et_account_name_value) }
    private val inputAmount = KEditText { withId(R.id.et_account_amount_value) }


    private val accountName = KTextView { withId(R.id.tv_budget_item_name) }
    private val accountValue = KTextView { withId(R.id.tv_budget_item_value) }


    fun openAddAccount(){
        fab.click()
    }

    fun fillName(name: String) {
        inputName.replaceText(name)
    }

    fun fillAmount(amount: String) {
        inputAmount.replaceText(amount)
    }

    fun checkCard(name: String, amount: String){
        accountName.hasText(name)
        accountValue.hasText(amount)
    }

    companion object {
        const val SCREEN_NAME = "Экран счетов"

        inline operator fun invoke(testContext: TestContext<*>, crossinline block: AccountsScreen.() -> Unit) {
            testContext.step(SCREEN_NAME) {
                AccountsScreen(testContext).apply {
                    block()
                }
            }
        }
    }


}