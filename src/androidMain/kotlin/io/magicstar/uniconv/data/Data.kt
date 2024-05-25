package io.magicstar.uniconv.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import io.magicstar.uniconv.unit.model.lengthUnits
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name ="config")
val magnitudeKey = stringPreferencesKey("magnitude")
val originKey = stringPreferencesKey("origin")
val targetKey = stringPreferencesKey("target")
suspend fun initConfig(context: Context, defaultMagnitude: String) {
    val firstRun = booleanPreferencesKey("firstRun")
    if (context.dataStore.data.map { it[firstRun] }.first() == null) {
        context.dataStore.edit { preferences ->
            preferences[magnitudeKey] = defaultMagnitude
            preferences[originKey] = lengthUnits[0].name
            preferences[targetKey] = lengthUnits[1].name
            preferences[firstRun] = true
        }
    }
}

suspend fun saveKey(context: Context, key: Preferences.Key<String>, value: String) {
    context.dataStore.edit { preferences ->
        preferences[key] = value
    }
}

suspend fun getKey(context: Context, key: Preferences.Key<String>): String {
    return context.dataStore.data.map {
        it[key]
    }.filterNotNull().first()
}