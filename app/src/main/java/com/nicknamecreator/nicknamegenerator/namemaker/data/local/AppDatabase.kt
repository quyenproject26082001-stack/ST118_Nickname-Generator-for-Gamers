package com.nicknamecreator.nicknamegenerator.namemaker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nicknamecreator.nicknamegenerator.namemaker.data.local.dao.UserDao
import com.nicknamecreator.nicknamegenerator.namemaker.data.local.entity.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}