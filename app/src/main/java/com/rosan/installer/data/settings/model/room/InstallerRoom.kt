package com.rosan.installer.data.settings.model.room

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.rosan.installer.build.Manufacturer
import com.rosan.installer.build.RsConfig
import com.rosan.installer.data.settings.model.room.dao.AppDao
import com.rosan.installer.data.settings.model.room.dao.ConfigDao
import com.rosan.installer.data.settings.model.room.entity.AppEntity
import com.rosan.installer.data.settings.model.room.entity.ConfigEntity
import com.rosan.installer.data.settings.model.room.entity.converter.AuthorizerConverter
import com.rosan.installer.data.settings.model.room.entity.converter.DexoptModeConverter
import com.rosan.installer.data.settings.model.room.entity.converter.InstallModeConverter
import com.rosan.installer.data.settings.model.room.entity.converter.PackageSourceConverter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

@Database(
    entities = [AppEntity::class, ConfigEntity::class],
    version = 7,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 3, to = 4),
        AutoMigration(from = 4, to = 5),
        AutoMigration(from = 5, to = 6),
        AutoMigration(from = 6, to = 7),
    ]
)
@TypeConverters(
    AuthorizerConverter::class,
    InstallModeConverter::class,
    DexoptModeConverter::class,
    PackageSourceConverter::class
)
abstract class InstallerRoom : RoomDatabase() {
    companion object : KoinComponent {
        fun createInstance(): InstallerRoom {
            return Room.databaseBuilder(
                get(),
                InstallerRoom::class.java,
                "installer.db",
            ).addCallback(object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    // 延迟获取已初始化的 Database 实例
                    val database = get<InstallerRoom>()
                    val defaultConfig = when (RsConfig.currentManufacturer) {
                        Manufacturer.XIAOMI -> ConfigEntity.XiaomiDefault
                        else -> ConfigEntity.default
                    }
                    CoroutineScope(Dispatchers.IO).launch {
                        database.configDao.insert(defaultConfig)
                    }
                }
            })
                .build()
        }
    }

    abstract val appDao: AppDao

    abstract val configDao: ConfigDao
}

