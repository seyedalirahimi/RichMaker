package com.ali.richmaker.data.local.database.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ali.richmaker.data.local.database.RichMakerDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Singleton
import kotlin.random.Random

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {


    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): RichMakerDatabase {

        val predefinedCategories = listOf(
            "Salary", "Food", "Transportation", "Entertainment", "Healthcare", "Education", "Other"
        )


        val predefinedTransactions = List(100) { // Generate 100 random transactions
            val randomCategory = predefinedCategories.random() // Pick a random category
            val randomAmount = Random.nextDouble(
                -5000.0,
                5000.0
            ) // Generate a random amount between -5000 and 5000
            val isIncome = randomAmount > 0 // Treat positive amounts as income
            val randomTime = getRandomTime() // Get a random date and time
            Quadruple(randomCategory, randomAmount, isIncome, randomTime) // Include random date
        }

        return Room.databaseBuilder(
            appContext, RichMakerDatabase::class.java, RichMakerDatabase.DATABASE_NAME
        ).addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                CoroutineScope(Dispatchers.IO).launch {

                    // Insert predefined categories
                    predefinedCategories.forEach { categoryName ->
                        db.execSQL("INSERT INTO categories (name, isPredefined) VALUES ('$categoryName', 1)")
                    }

                    // Insert predefined transactions
                    predefinedTransactions.forEach { (categoryName, amount, isIncome, time) ->
                        // Fetch the ID of the category to associate with the transaction
                        val cursor = db.query(
                            "SELECT id FROM categories WHERE name = ?", arrayOf(categoryName)
                        )
                        if (cursor.moveToFirst()) {
                            val categoryId = cursor.getInt(0)
                            db.execSQL(
                                """
                        INSERT INTO transactions (date, categoryId, amount, title, message, isIncome) 
                        VALUES (
                            ${time}, 
                            $categoryId, 
                            $amount, 
                            '${categoryName} Transaction', 
                            NULL, 
                            ${if (isIncome) 1 else 0}
                        )
                    """.trimIndent()
                            )
                        }
                        cursor.close()
                    }
                }
            }
        }).build()
    }


    private fun getRandomTime(): Long {
        val now = System.currentTimeMillis() // Current time in milliseconds
        val twoMonthsAgo = now - (60L * 24 * 60 * 60 * 1000) // Approx. 60 days in milliseconds
        return Random.nextLong(twoMonthsAgo, now) // Random time in range
    }

    data class Quadruple<T1, T2, T3, T4>(
        val first: T1,
        val second: T2,
        val third: T3,
        val fourth: T4
    )
}