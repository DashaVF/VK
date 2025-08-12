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
import screens.IncomesScreen

class IncomesTest: TestCase(
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
        val income = IncomesScreen()
        income.openMenu()
        income.openIncomes()
    }

    @After
    fun tearDown() {
        val context = androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().targetContext
        context.cacheDir?.deleteRecursively()

        context.databaseList().forEach { dbName ->
            context.deleteDatabase(dbName)
        }

    }

//    3) Добавить один периодический доход (Income),
//    убедиться что он отобразился на нужном экране, в нужной вкладке

    @Test
    fun incomeScreen_AddPeriodicIncome_IncomeIsDisplayedInCorrectTab() = run{
        //arrange
        val income = IncomesScreen()
        val name = "dividends"
        val amount = "5000"
        val message = "Done"
        val type = "Periodic"
        val beginDay = "7"
        val endDay = "23"
        val period = "2"
        val typePeriod = "Week"

        step("Открыть экран добавления счетов") {
            income.openAddIncomeScreen()
        }

        //act
        step("Заполнить поля") {
            income.fillName(name)
            income.fillAmount(amount)
            income.selectType(type)
            income.fillPeriodic(beginDay, endDay, period, typePeriod)
            income.confirm()
        }



        //assert
        step("Проверка тоста о создании") {
            var decorView: View? = null
            activityRule.scenario.onActivity { activity ->
                decorView = activity.window.decorView
            }
            income.checkToast(message, decorView!!)
        }

        income.openTab("Periodic")
        step("Проверить отображение списка счетов") {
            income.checkCard(name, "%,d".format(amount.toInt()), beginDay, endDay)
        }
    }


}