# 🔐 SauceDemo Login Test Automation

This project contains automated **login test cases** for the [SauceDemo](https://www.saucedemo.com/) web application, built using **Selenium WebDriver**, **TestNG**, and **Java**. It focuses on verifying both valid and invalid login scenarios to ensure the login functionality works as expected.

---

## 🧰 Tech Stack

- **Language:** Java
- **Automation Tool:** Selenium WebDriver
- **Test Framework:** TestNG
- **Build Tool:** Maven
- **Browser:** Chrome (via ChromeDriver)
- **Website under test:** [https://www.saucedemo.com/](https://www.saucedemo.com/)

---

## ✅ Test Scenarios Covered

### 1. Positive Test Cases
- ✅ Login with valid username and password (`standard_user`)
- ✅ Verify successful login redirects to the inventory page

### 2. Negative Test Cases
- ❌ Login with invalid username
- ❌ Login with invalid password
- ❌ Login with both fields empty
- ❌ Login with locked-out user (`locked_out_user`)
- ❌ Login with special characters in the username/password fields

---

## 🗂️ Project Structure

saucedemo-login-tests/
├── pom.xml                           # Maven project file (dependencies, build info)
├── testng.xml                        # TestNG suite configuration
├── README.md                         # Project documentation
├── src/
│   ├── test/
│   │   ├── java/
│   │   │   ├── pages/                # Page Object classes (e.g., LoginPage.java)
│   │   │   ├── tests/                # Test classes (e.g., LoginTest.java)
│   │   │   └── utils/                # Base classes, drivers, config
│   └── resources/
│       └── testdata/                 # Optional test data files (e.g., CSV/JSON)
└── test-output/                      # TestNG default report folder (after test run)

## 🚀 How to Run the Tests

### 1. Clone the Repository
```bash
git clone https://github.com/your-username/saucedemo-login-tests.git
cd saucedemo-login-tests

### 2. Install Dependencies
Make sure you have Maven and Java installed, then run:
mvn clean install

### 3. Run the Tests
You can run all tests using:
mvn test

### 4. Run Using TestNG Suite (Optional)
If you're using a custom TestNG XML file:
mvn test -DsuiteXmlFile=testng.xml

5. View Test Reports
After execution, open the TestNG report:
test-output/index.html
Just open it in a browser to see the summary and results.


