package com.mycompany

import org.apache.wicket.request.mapper.parameter.PageParameters
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.markup.html.WebPage

class HomePage : WebPage {
	private val serialVersionUID: Long = 1L

	constructor(parameters: PageParameters) : super(parameters) {
		add(Label("version", application.frameworkSettings.version))
	}
}
