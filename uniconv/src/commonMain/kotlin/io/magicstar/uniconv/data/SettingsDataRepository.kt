package io.magicstar.uniconv.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class SettingsDataRepository(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val dataStore: DataStore<Preferences>
): SettingsRepository {
    override val magnitudeKey = stringPreferencesKey("magnitude")
    override val unit1Key = stringPreferencesKey("unit1")
    override val unit2Key = stringPreferencesKey("unit2")

    override fun <T: Comparable<T>> getKey(key: Preferences.Key<T>): Flow<T?> = dataStore.data.map { it[key] }

    override suspend fun <T : Comparable<T>> setKey(key: Preferences.Key<T>, value: T) {
        withContext(dispatcher) {
            dataStore.edit { it[key] = value }
        }
    }

    override fun <T: Comparable<T>> containsKey(key: Preferences.Key<T>) = dataStore.data.map { it.contains(key) }
}