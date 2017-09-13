package de.dhbw.horb.programmieren.projekt.algorithms.test;

import java.util.concurrent.TimeoutException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;

import de.dhbw.horb.programmieren.projekt.controller.Controller;
import de.dhbw.horb.programmieren.projekt.SortingApp;

@RunWith(JUnit4.class)
public class SortAlgorithmTest {
	
	Controller controller;
	public SortingApp start;
	private FxRobot robot;

	@Before
	public void setUp() throws Exception {
		FxToolkit.registerPrimaryStage();
		start = (SortingApp) FxToolkit.setupApplication(SortingApp.class);
		robot = new FxRobot();
		robot.sleep(1000);
	}	
	
	@Test
	public void blaCyka() {
		robot.clickOn("#rbtnFile");
		robot.clickOn("#btnBrowse");
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
