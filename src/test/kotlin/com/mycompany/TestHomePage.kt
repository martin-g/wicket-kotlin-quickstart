package com.mycompany

import org.apache.wicket.util.tester.WicketTester
import org.junit.Test

/**
 * Simple test using the WicketTester
 */
class TestHomePage {

	val tester = WicketTester(WicketApplication())

	@Test
	fun homepageRendersSuccessfully() {
		//start and render the test page
		tester.startPage(HomePage::class.java)

		//assert rendered page class
		tester.assertRenderedPage(HomePage::class.java)
	}
}
