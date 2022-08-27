**About the Framework:**

Language: Java (JDK 1.8)
Automation tool: Selenium WebDriver (3.141.59), Maven
Framework: TestNG

**Description of Framework:**

1. I have used the Page Object Method design pattern to write the code
2. So inside that I have created one page class AmazonPage where I have stored all the locators using Pagefactory and also stored the functionality codes
3. After that I have creaeted one test class AmazonTest from where I am calling the page class method and verify the functionality
4. I have used the testng annotation inside the AmazonTest class to handle the before and after execution events
5. I have used pom.xml file for build control
6. I have used testng.xml runner file from where all the code is running
