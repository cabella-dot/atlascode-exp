package com.atlassian.intellijextension.services

import com.atlassian.intellijextension.toolWindow.AtlascodeWindow
import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project

@Service(Service.Level.PROJECT)
class AtlascodeWindowService(project: Project) {
	val atlascodeWindow = AtlascodeWindow(project)
}