package com.mycompany

import java.lang.management.ManagementFactory

import org.eclipse.jetty.jmx.MBeanContainer
import org.eclipse.jetty.server.HttpConfiguration
import org.eclipse.jetty.server.HttpConnectionFactory
import org.eclipse.jetty.server.SecureRequestCustomizer
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.server.ServerConnector
import org.eclipse.jetty.server.SslConnectionFactory
import org.eclipse.jetty.util.resource.Resource
import org.eclipse.jetty.util.ssl.SslContextFactory
import org.eclipse.jetty.webapp.WebAppContext

/**
 * Separate startup class for people that want to run the examples directly. Use parameter
 * -Dcom.sun.management.jmxremote to startup JMX (and e.g. connect with jconsole).
 */
fun main(args: Array<String>) {
	System.setProperty("wicket.configuration", "development")

	val server = Server()

	val http_config = HttpConfiguration()
	http_config.secureScheme = "https"
	http_config.securePort = 8443
	http_config.outputBufferSize = 32768

	val http = ServerConnector(server, HttpConnectionFactory(http_config))
	http.port = 8080
	http.idleTimeout = 1000 * 60 * 60

	server.addConnector(http)

	val keystore = Resource.newClassPathResource("/keystore")
	if (keystore != null && keystore.exists())
	{
		// if a keystore for a SSL certificate is available, start a SSL
		// connector on port 8443.
		// By default, the quickstart comes with a Apache Wicket Quickstart
		// Certificate that expires about half way september 2021. Do not
		// use this certificate anywhere important as the passwords are
		// available in the source.

		val sslContextFactory = SslContextFactory()
		sslContextFactory.setKeyStoreResource(keystore)
		sslContextFactory.setKeyStorePassword("wicket")
		sslContextFactory.setKeyManagerPassword("wicket")

		val https_config = HttpConfiguration(http_config)
		https_config.addCustomizer(SecureRequestCustomizer())

		val https = ServerConnector(server, SslConnectionFactory(
			sslContextFactory, "http/1.1"), HttpConnectionFactory(https_config))
		https.port = 8443
		https.idleTimeout = 500000

		server.addConnector(https)
		System.out.println("SSL access to the examples has been enabled on port 8443")
		System.out.println("You can access the application using SSL on https://localhost:8443")
		System.out.println()
	}

	val bb = WebAppContext()
	bb.server = server
	bb.contextPath = "/"
	bb.war = "src/main/webapp"

	// uncomment next line if you want to test with JSESSIONID encoded in the urls
	// ((AbstractSessionManager)
	// bb.getSessionHandler().getSessionManager()).setUsingCookies(false);

	server.handler = bb

	val mBeanServer = ManagementFactory.getPlatformMBeanServer()
	val mBeanContainer = MBeanContainer(mBeanServer)
	server.addEventListener(mBeanContainer)
	server.addBean(mBeanContainer)

	try {
		server.start()
		server.join()
	}
	catch (e: Exception) {
		e.printStackTrace()
		System.exit(100)
	}
}
