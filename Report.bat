COPY "allure-results" "allure-2.17.2\bin\allure-results"

cd allure-2.17.2\bin

ECHO Starting Allure Report.

START allure serve allure-results

