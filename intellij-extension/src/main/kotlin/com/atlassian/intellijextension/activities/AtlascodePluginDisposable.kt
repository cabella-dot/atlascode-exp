package com.atlassian.intellijextension.activities

import com.intellij.openapi.Disposable
import com.intellij.openapi.components.Service

@Service(Service.Level.APP, Service.Level.PROJECT)
class AtlascodePluginDisposable : Disposable {

	override fun dispose() {

	}
}