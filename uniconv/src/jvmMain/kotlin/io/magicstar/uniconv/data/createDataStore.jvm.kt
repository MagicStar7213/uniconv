package io.magicstar.uniconv.data

import androidx.datastore.core.FileStorage
import androidx.datastore.preferences.core.PreferencesFileSerializer
import java.io.File

fun createDataStore() = createDataStore(
    storage= FileStorage(
        serializer = PreferencesFileSerializer,
        produceFile = { File("${System.getProperty("user.home")}/.uniconv/",dataStoreFilename) }
    )
)