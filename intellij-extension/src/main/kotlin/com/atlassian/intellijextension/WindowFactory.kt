package com.atlassian.intellijextension

import com.atlassian.intellijextension.services.AtlascodeWindowService
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory

class WindowFactory: ToolWindowFactory {
	override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
		val atlascodeWindow = project.getService(AtlascodeWindowService::class.java).atlascodeWindow
		val component = toolWindow.component
		component.parent.add(atlascodeWindow.content)
	}
}