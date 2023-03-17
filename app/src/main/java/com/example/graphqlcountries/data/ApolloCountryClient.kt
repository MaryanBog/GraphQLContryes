package com.example.graphqlcountries.data

import com.apollographql.apollo3.ApolloClient
import com.example.CountriesQuery
import com.example.CountryQuery
import com.example.graphqlcountries.domain.CountryClient
import com.example.graphqlcountries.domain.DetailedCountry
import com.example.graphqlcountries.domain.SimpleCountry


class ApolloCountryClient(
    private val apolloClient: ApolloClient
): CountryClient {

    override suspend fun getCountries(): List<SimpleCountry> {
        return apolloClient
            .query(CountriesQuery())
            .execute()
            .data
            ?.countries
            ?.map { it.toSimpleCountry() }
            ?: emptyList()
    }

    override suspend fun getCountry(code: String): DetailedCountry? {
        return apolloClient
            .query(CountryQuery(code))
            .execute()
            .data
            ?.country
            ?.toDetailedCountry()
    }
}