package tests

import android.view.View
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.atdroid.atyurin.futuremoney.activity.MainActivity
import com.kaspersky.kaspresso.kaspresso.Kaspresso
import com.kaspersky.kaspresso.params.FlakySafetyParams
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import screens.AccountsScreen
import java.io.File

class AccountsScreenTest() : TestCase(
    kaspressoBuilder = Kaspresso.Builder.simple(
        customize = {
            flakySafetyParams = FlakySafetyParams.custom(timeoutMs = 10_000, intervalMs = 250)
        }
    )
) {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)


    @Before
    fun setup() = run {
        val account = AccountsScreen()
        account.openMenu()
        account.openAccounts()
    }

    @After
    fun tearDown() {
        val context = androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().targetContext
        context.cacheDir?.deleteRecursively()

        context.databaseList().forEach { dbName ->
            context.deleteDatabase(dbName)
        }

    }


//    1) Добавить новый счёт (Account), позитивный кейс: добавить валидное значение,
//    убедиться, что счёт создался и отобразился в списке
    @Test
    fun accountScreen_AddAccount_AccountIsCreatedAndDisplayed() = run{
        //arrange
        val account = AccountsScreen()
        val name = "for rest"
        val amount = "25000"

        //act
        step("Открыть экран добавления счетов") {
            account.openAddAccount()
        }
        step("Заполнить поля") {
            account.fillName(name)
            account.fillAmount(amount)
            account.confirm()
        }

        //assert

        step("Проверка тоста о создании счета") {
            var decorView: View? = null
            activityRule.scenario.onActivity { activity ->
                decorView = activity.window.decorView
            }
            account.checkToast("Done", decorView!!)
        }
        step("Проверить отображение списка счетов") {
            account.checkCard(name, "%,d".format(amount.toInt()), 0)
        }
    }




//    2) Добавить новый счёт (Account), негативный кейс: добавить невалидное значение, убедиться, что счёт создать не получается (можно проверить разные варианты)
//    параметризация тип



}