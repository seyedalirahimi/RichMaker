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

        val predefinedIncomeCategories = listOf(
            "Allowance",
            "Salary",
            "Cash",
            "Bonus",
            "Other",
        )
        val predefinedExpenseCategories = listOf(
            "Food",
            "Pet",
            "Transport",
            "Culture",
            "Household",
            "Apparel",
            "Beauty",
            "Health",
            "Education",
            "Gift",
            "Other",
        )


        val predefinedTransactions = List(100) { // Generate 100 random transactions
            val randomAmount = Random.nextDouble(
                -2000.0, 5000.0
            )
            val randomCategory = if (randomAmount >= 0) {
                predefinedIncomeCategories.random()
            } else {
                predefinedExpenseCategories.random()
            }
            val randomTime = getRandomTime() // Get a random date and time
            Triple(randomCategory, randomAmount, randomTime) // Include random date
        }

        return Room.databaseBuilder(
            appContext, RichMakerDatabase::class.java, RichMakerDatabase.DATABASE_NAME
        ).addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                CoroutineScope(Dispatchers.IO).launch {

                    predefinedIncomeCategories.forEach { categoryName ->
                        db.execSQL("INSERT INTO categories (name, isIncome, isPredefined) VALUES ('$categoryName', TRUE, 1)")
                    }

                    predefinedExpenseCategories.forEach { categoryName ->
                        db.execSQL("INSERT INTO categories (name, isIncome, isPredefined) VALUES ('$categoryName', FALSE, 1)")
                    }

                    var index = 0
                    // Insert predefined transactions
                    predefinedTransactions.forEach { (categoryName, amount, time) ->
                        // Fetch the ID of the category to associate with the transaction
                        val cursor = db.query(
                            "SELECT id FROM categories WHERE name = ?", arrayOf(categoryName)
                        )
                        if (cursor.moveToFirst()) {
                            val categoryId = cursor.getInt(0)
                            db.execSQL(
                                """
                        INSERT INTO transactions (date, categoryId, amount, title, message) 
                        VALUES (
                            ${time}, 
                            $categoryId, 
                            $amount, 
                            'title #${index++}', 
                            NULL
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
}