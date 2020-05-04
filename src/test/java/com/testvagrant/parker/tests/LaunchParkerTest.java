package com.testvagrant.parker.tests;

import com.testvagrant.parker.setup.TestSessionManager;
import org.openqa.selenium.Platform;
import org.testng.annotations.Test;

public class LaunchParkerTest {
    TestSessionManager testSessionManager;
    @Test
    public void launchApplication()
    {
        testSessionManager = new TestSessionManager();
        testSessionManager.setDriver();
        testSessionManager.closeSession();
    }
}
