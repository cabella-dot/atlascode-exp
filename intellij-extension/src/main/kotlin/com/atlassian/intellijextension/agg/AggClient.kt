package com.atlassian.intellijextension.agg

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloResponse
import com.apollographql.apollo.api.Query
import kotlinx.coroutines.coroutineScope

class AggClient {
	val client: ApolloClient = ApolloClient.Builder().serverUrl("https://api.atlassian.com/graphql").build()
	val apiToken = "ATATT3xFfGF0C6a-pyyqOOAndIot6XKZQOCJNeyMC8TPWcMF-LV-omkeVSz40c8NP2EUtkA6zObPvbW6U6KYf1iCGyBB0uyj_P2HmSRwsgmiEFgJen7MM3Ith-eEgodmU5pqlT5roapEP7XOMDcTGkRoi36OpRq6nwtmwWktC5HxG-SpTY-sDD4=04A074D3"

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