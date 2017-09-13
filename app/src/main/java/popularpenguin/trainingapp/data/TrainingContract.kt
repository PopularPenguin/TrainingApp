package popularpenguin.trainingapp.data

import android.provider.BaseColumns

object TrainingContract {
    val CATEGORY_COMBATIVES = "combatives"
    val CATEGORY_COMBOS = "combos"
    val CATEGORY_DEFENSES = "defenses"
    val CATEGORY_WEAPONS = "weapons"

    object NotesEntry {
        val TABLE_NAME = "notes"

        val _ID = "_id"
        val COLUMN_CATEGORY = "category"
        val COLUMN_NAME = "name"
        val COLUMN_DESCRIPTION = "description"
        val COLUMN_NOTE = "note"
    }

    object UserEntry {
        val TABLE_NAME = "user"

        val _ID = "_id"
        val COLUMN_NAME = "name"
        val COLUMN_DESCRIPTION = "description"
        val COLUMN_NOTE = "note"
    }
}