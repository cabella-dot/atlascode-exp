package com.atlassian.intellijextension.agg

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloResponse
import com.apollographql.apollo.api.Query
import kotlinx.coroutines.coroutineScope

class AggClient {
	val client: ApolloClient = ApolloClient.Builder().serverUrl("https://api.atlassian.com/graphql").build()
	val apiToken = "<api-token>"

	suspend fun <D: Query.Data> executeQuery(query: Query<D>): ApolloResponse<D> {
		val response = coroutineScope {
			client.query(query)
				.addHttpHeader("Content-Type", "application/json")
				.addHttpHeader("Authorization", "Bearer ${apiToken}")
				.execute()
		}

		return response
	}
}
