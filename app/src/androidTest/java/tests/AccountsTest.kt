package tests

import android.view.View
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.atdroid.atyurin.futuremoney.activity.MainActivity
import junitparams.JUnitParamsRunner
import junitparams.Parameters
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import screens.AccountsScreen

@RunWith(JUnitParamsRunner::class)
class AccountsTest: BaseTest(){
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)


    @Before
    fun setup() = run {
        AccountsScreen(this){
            openMenu()
            openAccounts()
        }
    }



//    1) Добавить новый счёт (Account), позитивный кейс: добавить валидное значение,
//    убедиться, что счёт создался и отобразился в списке
    @Test
    fun accountScreen_AddAccount_AccountIsCreatedAndDisplayed() = run{

        AccountsScreen(this){
            //arrange
            val name = "for rest"
            val amount = "25000"
            val message = "Done"
            step("Открыть экран добавления счетов") {
                openAddAccount()
            }

            //act
            step("Заполнить поля") {
                fillName(name)
                fillAmount(amount)
                confirm()
            }

            //assert
            step("Проверка тоста о создании счета") {
                var decorView: View? = null
                activityRule.scenario.onActivity { activity ->
                    decorView = activity.window.decorView
                }
                checkToast(message, decorView!!)
            }
            step("Проверить отображение списка счетов") {
                checkCard(name, "%,d".format(amount.toInt()))
            }
        }

    }




//    2) Добавить новый счёт (Account), негативный кейс: добавить невалидное значение,
//    убедиться, что счёт создать не получается (можно проверить разные варианты)
    @Test
    @Parameters(method = "invalidData")
    fun accountScreen_AddAccountWithInvalidValue_AccountIsNotCreated(invalidName: String, invalidAmount: String, message: String) = run{
        AccountsScreen(this){
            //arrange
            step("Открыть экран добавления счетов") {
                openAddAccount()
            }

            //act
            step("Заполнить поля некорректными данными") {
                fillName(invalidName)
                fillAmount(invalidAmount)
                confirm()
            }

            //assert
            step("Проверка тоста на ошибку создании счета") {
                var decorView: View? = null
                activityRule.scenario.onActivity { activity ->
                    decorView = activity.window.decorView
                }
                checkToast(message, decorView!!)
            }
        }
    }

    fun invalidData() = listOf(
        arrayOf("", "100", "The name is not specified!"),
        arrayOf("Valid name", "", "The amount is not specified!"),
        arrayOf("Valid name", "qwerty", "The amount is not specified!"),
        arrayOf("Valid name", "qwe1r3t421y3", "The amount is not specified!"),
        arrayOf("Valid name", "*&*&^", "The amount is not specified!"),
        arrayOf("Valid name", "     ", "The amount is not specified!"),
        arrayOf("", "", "The name is not specified!")
    )

}