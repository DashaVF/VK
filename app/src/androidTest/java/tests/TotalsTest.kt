package tests

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
import screens.BaseScreen
import screens.ExpensesScreen
import screens.IncomesScreen
import screens.TotalsScreen

class TotalsTest: BaseTest(){

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)


//    Добавить счёт, добавить расход, добавить приход, на главном экране произвести рассчёт
//    за всё время, убедиться в отображении всех элементов на экране
//    (все числовые ранее добавленные данные, их рассчёт, график, остальные UI элементы)

    @Test
    fun mainScreen_AddAccountIncomeExpense_VerifyAllElementsDisplayedAndCalculated() = run{
        //arrange
        var name = ""
        val account = "10000"
        val expense = "3500"
        val income = "5000"
        AccountsScreen(this){
            name = "новый счет"
            step("Перейти на страницу счетов"){
                openMenu()
                openAccounts()
            }
            step("Открыть экран добавления счетов") {
                openAddAccount()
            }
            step("Добавить счет") {
                fillName(name)
                fillAmount(account)
                confirm()
            }
        }
        ExpensesScreen(this){
            name = "на аптеку"
            step("Перейти на страницу расходов"){
                openMenu()
                openOutcomes()
            }
            step("Открыть экран добавления записи") {
                openAddExpenses()
            }
            step("Добавить расход"){
                fillName(name)
                fillAmount(expense)
                confirm()
            }
        }
        IncomesScreen(this){
            name = "дивиденды"
            step("Перейти на страницу начислений"){
                openMenu()
                openIncomes()
            }
            step("Открыть экран добавления записи") {
                openAddIncome()
            }
            step("Добавить приход"){
                fillName(name)
                fillAmount(income)
                confirm()
            }
        }


        //act
        val calculateType = "for all time"
        TotalsScreen(this){
            step("Перейти на страницу расчетов"){
                openMenu()
                openTotals()
            }
            step("Произвести расчет"){
                selectCalculate(calculateType)
                calculate()
            }
        }


        //assert
        TotalsScreen(this){
            step("Проверить отображение элементов и вычисления"){
                checkAllElements(calculateType, account, income, expense )
            }
        }
    }

}