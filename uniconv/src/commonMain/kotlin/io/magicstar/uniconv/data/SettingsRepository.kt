package io.magicstar.uniconv.data

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val magnitudeKey: Preferences.Key<String>
    val fromUnitKey: Preferences.Key<String>
    val toUnitKey: Preferences.Key<String>

    suspend fun <T: Comparable<T>> getKey(key: Preferences.Key<T>): Flow<T?>
    suspend fun <T: Comparable<T>> setKey(key: Preferences.Key<T>, value: T)
}