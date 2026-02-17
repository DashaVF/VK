# Тестовое задание VK  
**Специалист по автотестированию мобильных приложений**

## 📋 Обзор проекта
Автоматизированные тесты для мобильного приложения **FutureMoney** (Android).  
Тесты написаны на **Espresso** для **английской версии** приложения.

## 🔍 Мануальное тестирование
Результаты мануального тестирования доступны в [Google Sheets](https://docs.google.com/spreadsheets/d/1-TpA3x5wjNIBCMIudZI_6RuUbl8ahuJ0LSpbNoMwEHc/edit?gid=0#gid=0).

## ⚙️ Подготовка перед запуском
**Важно**: Перед запуском тестов переключите эмулятор на **английский язык** (тесты написаны для eng-версии приложения).

## 🚀 Запуск тестов

### Проблема с Android Studio
Android Studio перестала корректно работать, поэтому тесты запускаются через **Gradle-скрипты** и **ADB**.

### Полный запуск всех тестов

```bash
# 1. Очистка и сборка
./gradlew clean assembleDebug assembleAndroidTest
# и
./gradlew assembleDebug assembleDebugAndroidTest

# 2. Удаление старых APK
adb uninstall com.atdroid.atyurin.futuremoney
adb uninstall com.atdroid.atyurin.futuremoney.test

# 3. Установка новых APK
adb install app/build/outputs/apk/debug/app-debug.apk
adb install app/build/outputs/apk/androidTest/debug/app-debug-androidTest.apk

# 4. Запуск всех тестов
adb shell am instrument -w com.atdroid.atyurin.futuremoney.test/androidx.test.runner.AndroidJUnitRunner
```
### Запуск конкретного теста
```
adb shell am instrument -w -e class tests.AccountsTest#accountScreen_AddAccount_AccountIsCreatedAndDisplayed com.atdroid.atyurin.futuremoney.test/androidx.test.runner.AndroidJUnitRunner
```
### Запуск конкретного класса
```
adb shell am instrument -w -e class tests.IncomesTest com.atdroid.atyurin.futuremoney.test/androidx.test.runner.AndroidJUnitRunner
```
