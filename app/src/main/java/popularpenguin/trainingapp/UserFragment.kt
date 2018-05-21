package popularpenguin.trainingapp

import android.app.Dialog
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.rowParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import popularpenguin.trainingapp.adapter.UserAdapter
import popularpenguin.trainingapp.data.UserTechnique
import popularpenguin.trainingapp.data.database
import popularpenguin.trainingapp.data.TrainingContract.UserEntry

class UserFragment: Fragment() {

    lateinit var adapter: UserAdapter
    lateinit var techniques: ArrayList<UserTechnique>
    lateinit var rootView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.user_list_layout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rootView = view.rootView

        val parser = rowParser { id: Long, name: String, desc: String, note: String ->
            UserTechnique(id, name, desc, note)
        }

        doAsync {
            techniques = context!!.database.use {
                select("user",
                        "_id",
                        UserEntry.COLUMN_NAME,
                        UserEntry.COLUMN_DESCRIPTION,
                        UserEntry.COLUMN_NOTE)
                        .parseList(parser)
            } as ArrayList<UserTechnique>
            uiThread {
                adapter = UserAdapter(context!!, techniques)

                val listView: ListView = view.findViewById(R.id.user_list)
                listView.adapter = adapter
                listView.onItemClickListener = adapter
            }
        }

        val fab: ImageView = view.findViewById(R.id.imageFab)
        fab.setOnClickListener {
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.dialog_user)

            val nameView: EditText = dialog.findViewById(R.id.user_dialog_name)

            val descriptionView: EditText = dialog.findViewById(R.id.user_dialog_description)

            val noteView: EditText = dialog.findViewById(R.id.user_dialog_note)

            val dialogSave: Button = dialog.findViewById(R.id.user_dialog_save)

            dialogSave.setOnClickListener {
                val name = if (nameView.text.isEmpty()) { "Technique" }
                else { nameView.text.toString() }
                val description = descriptionView.text.toString()
                val note = noteView.text.toString()

                // Insert into db

                doAsync {
                    val id = context!!.database.use {
                        insert("user",
                                UserEntry.COLUMN_NAME to name,
                                UserEntry.COLUMN_DESCRIPTION to description,
                                UserEntry.COLUMN_NOTE to note)
                    }
                    uiThread {
                        val technique = UserTechnique(id, name, description, note)
                        techniques.add(technique)

                        adapter.notifyDataSetChanged()

                        Snackbar.make(rootView, R.string.user_item_added, Snackbar.LENGTH_SHORT)
                                .show()
                    }
                }

                dialog.dismiss()
            }

            val dialogDelete: Button = dialog.findViewById(R.id.user_dialog_delete)
            dialogDelete.visibility = View.GONE

            dialog.show()
        }
    }
}