package de.dhbw.horb.programmieren.projekt.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.dhbw.horb.programmieren.projekt.algorithms.test.QuicksortTest;
import de.dhbw.horb.programmieren.projekt.view.test.ViewTest;
import de.dhbw.horb.programmieren.projekt.algorithms.test.MergesortTest;

@RunWith(Suite.class)
@SuiteClasses({ViewTest.class, QuicksortTest.class, MergesortTest.class})
public class AllTest {

}
