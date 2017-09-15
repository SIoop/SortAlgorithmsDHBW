package de.dhbw.horb.programmieren.projekt.view.test;

import java.util.concurrent.TimeoutException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;

import de.dhbw.horb.programmieren.projekt.SortingApp;

@RunWith(JUnit4.class)
public class ViewTest {
	
	public SortingApp start;
	private FxRobot robot;
	private String inputString = "15,18,8,26,3,17,9,4,6,11,13,1,20";

	@Before
	public void setUp() throws Exception {
		FxToolkit.registerPrimaryStage();
		start = (SortingApp) FxToolkit.setupApplication(SortingApp.class);
		robot = new FxRobot();
		robot.sleep(1000);
	}	
	
	@Test
	public void test() {
		robot.clickOn("#rbtnFile");
		robot.clickOn("#btnBrowse");
		robot.sleep(1000);
		robot.closeCurrentWindow();
		robot.clickOn("#rbtnManual");
		robot.clickOn("#tfManual");
		robot.eraseText(20);
		robot.write(inputString);
		robot.sleep(1000);
		robot.clickOn("#tfThreads");
		robot.eraseText(2);
		robot.write("4");
		robot.clickOn("#moreOptionsPane");
		robot.sleep(1000);
		robot.clickOn("#chkConsoleOutput");
		robot.clickOn("#tfDelay");
		robot.eraseText(2);
		robot.write("0");
		robot.clickOn("#btnStart");
		robot.sleep(3000);
		robot.clickOn("#rbtnRandom");
		robot.clickOn("#tfAmount");
		robot.eraseText(10);
		robot.write("2000");
		robot.clickOn("#rbtnMergeSort");
		robot.clickOn("#tfDelay");
		robot.eraseText(3);
		robot.write("20");
		robot.clickOn("#chkAnimation");
		robot.clickOn("#btnStart");
		robot.sleep(10000);
	}
	
	@After
	public void clean() {
		try {
			FxToolkit.cleanupStages();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}
}
