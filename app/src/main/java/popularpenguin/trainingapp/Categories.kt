package popularpenguin.trainingapp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import org.jetbrains.anko.db.insert
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
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.list_layout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val catName = TrainingContract.CATEGORY_COMBATIVES
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
                        .whereSimple("${NotesEntry.COLUMN_CATEGORY} = ?", catName)
                        .parseList(parser)
            }
            uiThread {
                val adapter = TechniqueAdapter(context, list)

                val listView = view.findViewById(R.id.list) as ListView
                listView.adapter = adapter
                listView.onItemClickListener = adapter
            }
        }
    }
}

class CombosFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.list_layout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val catName = TrainingContract.CATEGORY_COMBOS
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
                        .whereSimple("${NotesEntry.COLUMN_CATEGORY} = ?", catName)
                        .parseList(parser)
            }
            uiThread {
                val adapter = TechniqueAdapter(context, list)

                val listView = view.findViewById(R.id.list) as ListView
                listView.adapter = adapter
                listView.onItemClickListener = adapter
            }
        }
    }
}

class DefensesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.list_layout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val catName = TrainingContract.CATEGORY_DEFENSES
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
                        .whereSimple("${NotesEntry.COLUMN_CATEGORY} = ?", catName)
                        .parseList(parser)
            }
            uiThread {
                val adapter = TechniqueAdapter(context, list)

                val listView = view.findViewById(R.id.list) as ListView
                listView.adapter = adapter
                listView.onItemClickListener = adapter
            }
        }
    }
}

class WeaponsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.list_layout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val catName = TrainingContract.CATEGORY_WEAPONS
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
                        .whereSimple("${NotesEntry.COLUMN_CATEGORY} = ?", catName)
                        .parseList(parser)
            }
            uiThread {
                val adapter = TechniqueAdapter(context, list)

                val listView = view.findViewById(R.id.list) as ListView
                listView.adapter = adapter
                listView.onItemClickListener = adapter
            }
        }
    }
}
