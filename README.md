# PlatformAccountSettingUIAutomation
UI Automation for platform account setting page.
This automation repo verifies the following account setting page
1. Dashboard page (verifies greetings, user intiails logo, invitation and my application count)
2. User Information page (verifies error messages and update user details)
3. Password page (verifies error messages and change password)
4. Delete User page
5. Invitation page ( verifies accept and reject invitation)
6. My Application Page ( verifies My Application count)

# Config details
User can provide the target URL, Theme  and other details on PlatformAccountSettingUI/src/main/resources/com.qa.config
/config.properties config file.

# Test Execution
execute the command "mvn clean test" to trigger the configured tests on testng.xml. 
