package popularpenguin.trainingapp.adapter

import android.app.Dialog
import android.content.Context
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import org.jetbrains.anko.db.update
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import popularpenguin.trainingapp.R
import popularpenguin.trainingapp.data.UserTechnique
import popularpenguin.trainingapp.data.database
import popularpenguin.trainingapp.data.TrainingContract.UserEntry

class UserAdapter(context: Context, var techniques: ArrayList<UserTechnique>):
        ArrayAdapter<UserTechnique>(context, 0, techniques),
        AdapterView.OnItemClickListener {

    lateinit var rootView: View

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val itemView = convertView ?:
                LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)

        val technique = getItem(position)

        val image: ImageView = itemView.findViewById(R.id.note_image)
        if (technique.note.isEmpty()) {
            image.visibility = View.INVISIBLE
        }
        else {
            image.visibility = View.VISIBLE
        }

        val nameText: TextView = itemView.findViewById(R.id.name_text)
        nameText.text = technique.name

        val descriptionText: TextView = itemView.findViewById(R.id.description_text)
        descriptionText.text = technique.description

        rootView = itemView.rootView

        return itemView
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        val technique = getItem(position)

        val dialog = Dialog(context)
        dialog.setContentView(R.layout.dialog_user)

        val nameView: EditText = dialog.findViewById(R.id.user_dialog_name)
        nameView.setText(technique.fullName)

        val descriptionView: EditText = dialog.findViewById(R.id.user_dialog_description)
        descriptionView.setText(technique.fullDescription)

        val noteView: EditText = dialog.findViewById(R.id.user_dialog_note)
        noteView.setText(technique.note)

        val dialogSave: Button = dialog.findViewById(R.id.user_dialog_save)
        dialogSave.setOnClickListener {
            val name = if (nameView.text.isEmpty()) { "Technique" }
                else { nameView.text.toString() }
            val description = descriptionView.text.toString()
            val note = noteView.text.toString()

            doAsync {
                context.database.use {
                    update("user",
                            UserEntry.COLUMN_NAME to name,
                            UserEntry.COLUMN_DESCRIPTION to description,
                            UserEntry.COLUMN_NOTE to note)
                            .whereSimple("_id = ?", technique.id.toString())
                            .exec()
                }
                uiThread {
                    technique.name = name
                    technique.description = description
                    technique.note = note

                    techniques[position] = technique

                    notifyDataSetChanged()

                    Snackbar.make(view!!, R.string.user_item_updated, Snackbar.LENGTH_SHORT)
                            .show()
                }
            }

            dialog.dismiss()
        }

        val dialogDelete: Button = dialog.findViewById(R.id.user_dialog_delete)
        dialogDelete.setOnClickListener {
            createDeleteDialog(context, technique)

            dialog.dismiss()
        }

        dialog.show()
    }

    /** Create the confirmation alert dialog for when the user taps delete */
    private fun createDeleteDialog(ctx: Context, technique: UserTechnique) {
        AlertDialog.Builder(ctx)
                .setMessage(R.string.user_alert_message)
                .setPositiveButton(R.string.alert_positive, { dialog, which ->
                            doAsync {
                                context.database.use {
                                    delete("user", "_id = ${technique.id}", null)
                                }
                                uiThread {
                                    techniques.remove(technique)
                                    notifyDataSetChanged()

                                    dialog.dismiss()

                                    Snackbar.make(rootView,
                                            R.string.user_item_deleted,
                                            Snackbar.LENGTH_SHORT)
                                            .show()
                                }
                            }
                        })
                .setNegativeButton(R.string.alert_negative, { dialog, which -> dialog.dismiss() })
                .show()
    }
}