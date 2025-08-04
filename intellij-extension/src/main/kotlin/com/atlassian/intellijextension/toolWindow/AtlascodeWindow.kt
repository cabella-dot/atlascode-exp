package com.atlassian.intellijextension.toolWindow

import com.atlassian.intellijextension.activities.AtlascodePluginDisposable
import com.atlassian.intellijextension.agg.AggClient
import com.atlassian.intellijextension.factories.CustomSchemeHandlerFactory
import com.google.gson.Gson
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Disposer
import com.intellij.ui.jcef.JBCefBrowser
import com.intellij.ui.jcef.JBCefBrowserBase
import com.intellij.ui.jcef.JBCefJSQuery
import com.intellij.ui.jcef.executeJavaScript
import io.atlassian.micros.agg.AssignedWorkItemsQuery
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.cef.CefApp
import org.cef.browser.CefBrowser
import org.cef.handler.CefLoadHandlerAdapter
import javax.swing.JComponent

class AtlascodeWindow(val project: Project) {
	private val aggClient = AggClient()
	private val webView: JBCefBrowser = JBCefBrowser.createBuilder().setOffScreenRendering(true).build().apply {
		registerAppSchemeHandler()
		Disposer.register(project.getService(AtlascodePluginDisposable::class.java), this)

		val myJSQueryOpenInBrowser = JBCefJSQuery.create((this as JBCefBrowserBase?)!!)

		myJSQueryOpenInBrowser.addHandler { msg: String? ->

			val parser = Json

			val json = parser.parseToJsonElement(msg!!).jsonObject

			val messageType = json["messageType"]?.jsonPrimitive?.content

			if (messageType.equals("request", ignoreCase = true)) {
				CoroutineScope(Dispatchers.IO).launch {
					val response = aggClient.executeQuery(query = AssignedWorkItemsQuery())
					val responseData = response.data
					val msgType: String = "work-item-response"

					sendToWebview(msgType, responseData)
				}
			}

			null

		}
		this.jbCefClient.addLoadHandler(object: CefLoadHandlerAdapter() {
			override fun onLoadingStateChange(
				browser: CefBrowser?,
				isLoading: Boolean,
				canGoBack: Boolean,
				canGoForward: Boolean
			) {
				if (!isLoading && browser != null) {
					val injectedJavaScript = """window.postMessageToIntellij = function(messageType, data) {
				const msg = JSON.stringify({messageType, data});
				${myJSQueryOpenInBrowser.inject("msg")}
			}""".trimIndent()

					browser.executeJavaScript(injectedJavaScript, browser.url, 0)
				}
			}
		}, this.cefBrowser)


		loadURL("http://localhost:5173/index.html")

	}

	val content: JComponent = webView.component

	private fun registerAppSchemeHandler() {
		CefApp.getInstance().registerSchemeHandlerFactory("http", "atlascode",
			CustomSchemeHandlerFactory())
	}

	private fun sendToWebview(messageType: String, data: Any?) {
		val jsonData = Gson().toJson(mapOf("messageType" to messageType, "data" to data))

		val jsCode = buildJavaScript(jsonData)

		try {
			this.webView.cefBrowser.executeJavaScript(jsCode, this.webView.cefBrowser.url, 0)
		} catch (e: Exception) {
			e.printStackTrace()
		}
	}


	private fun buildJavaScript(jsonData: String): String {
		return """window.postMessage($jsonData, "*");"""
	}
}