package com.atlassian.intellijextension.toolWindow

import com.atlassian.intellijextension.activities.AtlascodePluginDisposable
import com.atlassian.intellijextension.factories.CustomSchemeHandlerFactory
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Disposer
import com.intellij.ui.jcef.JBCefBrowser
import com.intellij.ui.jcef.JBCefBrowserBase
import com.intellij.ui.jcef.JBCefJSQuery
import com.intellij.ui.jcef.executeJavaScript
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import org.cef.CefApp
import javax.swing.JComponent

class AtlascodeWindow(val project: Project) {
	private val webView: JBCefBrowser = JBCefBrowser.createBuilder().setOffScreenRendering(true).build().apply {
		registerAppSchemeHandler()
		Disposer.register(project.getService(AtlascodePluginDisposable::class.java), this)

		val myJSQueryOpenInBrowser = JBCefJSQuery.create((this as JBCefBrowserBase?)!!)

		myJSQueryOpenInBrowser.addHandler { msg: String? ->
			val parser = Json

			val json = parser.parseToJsonElement(msg!!).jsonObject

			val messageType = json["messageType"]

			val data = json["data"]

			if (messageType != null && messageType.equals("test")) {
				println("Atlas code")
			}

			null

		}

		loadURL("http://localhost:5173/index.html")
		this.cefBrowser.executeJavaScript("window.postMessage = function (message: any) {" +
				myJSQueryOpenInBrowser.inject("message") + "}", this.cefBrowser.url, 0)
	}

	val content: JComponent = webView.component

	private fun registerAppSchemeHandler() {
		CefApp.getInstance().registerSchemeHandlerFactory("http", "atlascode",
			CustomSchemeHandlerFactory())
	}
}