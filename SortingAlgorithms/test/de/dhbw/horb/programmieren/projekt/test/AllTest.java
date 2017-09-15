package de.dhbw.horb.programmieren.projekt.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.dhbw.horb.programmieren.projekt.algorithms.test.QuicksortTest;
import de.dhbw.horb.programmieren.projekt.algorithms.test.MergesortTest;
import de.dhwb.horb.programmieren.projekt.view.ViewTest;

@RunWith(Suite.class)
@SuiteClasses({ViewTest.class, QuicksortTest.class, MergesortTest.class})
public class AllTest {

}
