package io.magicstar.uniconv.data

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val magnitudeKey: Preferences.Key<String>
    val unit1Key: Preferences.Key<String>
    val unit2Key: Preferences.Key<String>
    val value1Key: Preferences.Key<String>
    val value2Key: Preferences.Key<String>

    fun <T: Comparable<T>> getKey(key: Preferences.Key<T>): Flow<T?>
    suspend fun <T: Comparable<T>> setKey(key: Preferences.Key<T>, value: T)
    fun <T: Comparable<T>> containsKey(key: Preferences.Key<T>): Flow<Boolean>
}