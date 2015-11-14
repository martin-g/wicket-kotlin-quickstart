package com.mycompany

import org.apache.wicket.markup.html.WebPage
import org.apache.wicket.protocol.http.WebApplication

/**
 * Application object for your web application.
 * If you want to run this application without deploying, run the Start class.
 *
 * @see com.mycompany.Start#main(String[])
 */
class WicketApplication : WebApplication() {
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	override fun getHomePage() : Class<out WebPage> {
		return  HomePage::class.java
	}

	/**
	 * @see org.apache.wicket.Application#init()
	 */
	override fun init(): Unit {
		super.init()

		// add your configuration here
	}
}
