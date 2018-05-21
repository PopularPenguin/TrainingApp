package popularpenguin.trainingapp

import android.os.Bundle
import android.content.Context
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import org.jetbrains.anko.db.rowParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import popularpenguin.trainingapp.adapter.TechniqueAdapter
import popularpenguin.trainingapp.data.TrainingContract
import popularpenguin.trainingapp.data.database
import popularpenguin.trainingapp.data.TrainingContract.NotesEntry
import popularpenguin.trainingapp.data.UserTechnique

class CombativesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.list_layout, container, false)
        CategoryHelper(context!!, view).fetchFromDb(TrainingContract.CATEGORY_COMBATIVES)

        return view
    }
}

class CombosFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.list_layout, container, false)
        CategoryHelper(context!!, view).fetchFromDb(TrainingContract.CATEGORY_COMBOS)

        return view
    }
}

class DefensesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.list_layout, container, false)
        CategoryHelper(context!!, view).fetchFromDb(TrainingContract.CATEGORY_DEFENSES)

        return view
    }
}

class WeaponsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.list_layout, container, false)
        CategoryHelper(context!!, view).fetchFromDb(TrainingContract.CATEGORY_WEAPONS)

        return view
    }
}

class CategoryHelper(val context: Context, val view: View) {

    fun fetchFromDb(category: String) {
        val parser = rowParser { id: Long, name: String, desc: String, note: String ->
            UserTechnique(id, name, desc, note)
        }

        doAsync {
            val list = context.database.use {
                select(NotesEntry.TABLE_NAME,
                        NotesEntry._ID,
                        NotesEntry.COLUMN_NAME,
                        NotesEntry.COLUMN_DESCRIPTION,
                        NotesEntry.COLUMN_NOTE)
                        .whereSimple("${NotesEntry.COLUMN_CATEGORY} = ?", category)
                        .parseList(parser)
            }
            uiThread {
                val adapter = TechniqueAdapter(context, list)

                val listView: ListView = view.findViewById(R.id.list)
                listView.adapter = adapter
                listView.onItemClickListener = adapter
            }
        }
    }
}
