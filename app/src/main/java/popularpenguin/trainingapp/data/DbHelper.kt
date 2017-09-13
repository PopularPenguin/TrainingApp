package popularpenguin.trainingapp.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*
import popularpenguin.trainingapp.R

import popularpenguin.trainingapp.data.TrainingContract.NotesEntry
import popularpenguin.trainingapp.data.TrainingContract.UserEntry

class DbHelper(private val ctx: Context) : ManagedSQLiteOpenHelper(ctx, "db", null, 1) {
    companion object {
        private var instance: DbHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): DbHelper = instance ?: DbHelper(ctx.applicationContext)

        // AUTOINCREMENT is bugged in Anko at the moment, use this instead
        val PRIMARY = SqlType.create("INTEGER PRIMARY KEY AUTOINCREMENT")
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(NotesEntry.TABLE_NAME, true,
                NotesEntry._ID to PRIMARY,
                NotesEntry.COLUMN_CATEGORY to TEXT + NOT_NULL,
                NotesEntry.COLUMN_NAME to TEXT + NOT_NULL,
                NotesEntry.COLUMN_DESCRIPTION to TEXT + NOT_NULL,
                NotesEntry.COLUMN_NOTE to TEXT)

        db.createTable(UserEntry.TABLE_NAME, true,
                UserEntry._ID to PRIMARY,
                UserEntry.COLUMN_NAME to TEXT,
                UserEntry.COLUMN_DESCRIPTION to TEXT,
                UserEntry.COLUMN_NOTE to TEXT)

        addData(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Insert new entries here and in addData()
    }

    fun addData(db: SQLiteDatabase) {
        val combativesList = listOf(
                Technique(ctx, R.string.c_jab, R.string.c_jab_description),
                Technique(ctx, R.string.c_cross, R.string.c_cross_description),
                Technique(ctx, R.string.c_hook, R.string.c_hook_description),
                Technique(ctx, R.string.c_uppercut, R.string.c_uppercut_description))

        val combosList = listOf(
                Technique(ctx, R.string.co_1, R.string.co_1_description),
                Technique(ctx, R.string.co_2, R.string.co_2_description),
                Technique(ctx, R.string.co_3, R.string.co_3_description),
                Technique(ctx, R.string.co_4, R.string.co_4_description),
                Technique(ctx, R.string.co_5, R.string.co_5_description),
                Technique(ctx, R.string.co_6, R.string.co_6_description),
                Technique(ctx, R.string.co_7, R.string.co_7_description),
                Technique(ctx, R.string.co_jab_low, R.string.co_jab_low_description),
                Technique(ctx, R.string.co_two_back, R.string.co_two_back_description),
                Technique(ctx, R.string.co_three_back, R.string.co_three_back_description))

        val defensesList = listOf(
                Technique(ctx, R.string.d_choke_front, R.string.d_choke_front_description),
                Technique(ctx, R.string.d_choke_side, R.string.d_choke_side_description),
                Technique(ctx, R.string.d_choke_back_static, R.string.d_choke_back_static_description))

        val weaponsList = listOf(
                Technique(ctx, R.string.w_downwards_knife_stab, R.string.w_downwards_knife_stab_description))

        /** Insert the data into the db */

        for (technique in combativesList) {
            db.insertOrThrow(NotesEntry.TABLE_NAME,
                    NotesEntry.COLUMN_CATEGORY to TrainingContract.CATEGORY_COMBATIVES,
                    NotesEntry.COLUMN_NAME to technique.name,
                    NotesEntry.COLUMN_DESCRIPTION to technique.fullDescription,
                    NotesEntry.COLUMN_NOTE to technique.note)
        }

        for (technique in combosList) {
            db.insertOrThrow(NotesEntry.TABLE_NAME,
                    NotesEntry.COLUMN_CATEGORY to TrainingContract.CATEGORY_COMBOS,
                    NotesEntry.COLUMN_NAME to technique.name,
                    NotesEntry.COLUMN_DESCRIPTION to technique.description,
                    NotesEntry.COLUMN_NOTE to technique.note)
        }

        for (technique in defensesList) {
            db.insertOrThrow(NotesEntry.TABLE_NAME,
                    NotesEntry.COLUMN_CATEGORY to TrainingContract.CATEGORY_DEFENSES,
                    NotesEntry.COLUMN_NAME to technique.name,
                    NotesEntry.COLUMN_DESCRIPTION to technique.description,
                    NotesEntry.COLUMN_NOTE to technique.note)
        }

        for (technique in weaponsList) {
            db.insertOrThrow(NotesEntry.TABLE_NAME,
                    NotesEntry.COLUMN_CATEGORY to TrainingContract.CATEGORY_WEAPONS,
                    NotesEntry.COLUMN_NAME to technique.name,
                    NotesEntry.COLUMN_DESCRIPTION to technique.description,
                    NotesEntry.COLUMN_NOTE to technique.note)
        }
    }
}

// Access property for Context
val Context.database: DbHelper
    get() = DbHelper.getInstance(applicationContext)