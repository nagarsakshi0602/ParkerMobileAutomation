package com.testvagrant.parker.tests;

import com.testvagrant.parker.setup.TestSessionManager;
import org.testng.annotations.Test;

public class LaunchParkerTest {
    TestSessionManager testSessionManager;
    @Test
    public void launchApplication()
    {
        testSessionManager = new TestSessionManager();
        testSessionManager.initializeMobileDriver("android");

    }
}
