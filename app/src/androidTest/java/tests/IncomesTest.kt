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

class IncomesTest: BaseTest(){

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)


    @Before
    fun setup() = run {
        IncomesScreen(this){
            openMenu()
            openIncomes()
        }
    }

//    3) Добавить один периодический доход (Income),
//    убедиться что он отобразился на нужном экране, в нужной вкладке
    @Test
    fun incomeScreen_AddPeriodicIncome_IncomeIsDisplayedInCorrectTab() = run{
        IncomesScreen(this){
            //arrange
            val name = "dividends"
            val amount = "5000"
            val message = "Done"
            val type = "Periodic"
            val beginDay = "7"
            val endDay = "23"
            val period = "2"
            val typePeriod = "Week"
            step("Открыть экран добавления счетов") {
                openAddIncome()
            }

            //act
            step("Заполнить поля") {
                fillName(name)
                fillAmount(amount)
                selectType(type)
                fillPeriodic(beginDay, endDay, period, typePeriod)
                confirm()
            }

            //assert
            step("Проверка тоста о создании") {
                var decorView: View? = null
                activityRule.scenario.onActivity { activity ->
                    decorView = activity.window.decorView
                }
                checkToast(message, decorView!!)
            }
            openTab("Periodic")
            step("Проверить отображение списка счетов") {
                checkCard(name, "%,d".format(amount.toInt()), beginDay, endDay)
            }
        }
    }


}