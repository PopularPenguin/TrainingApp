package popularpenguin.trainingapp.adapter

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
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
import popularpenguin.trainingapp.data.TrainingContract.NotesEntry

class TechniqueAdapter(context: Context, techniques: List<UserTechnique>) :
        ArrayAdapter<UserTechnique>(context, 0, techniques), AdapterView.OnItemClickListener {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val itemView = convertView ?:
                LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)

        val technique = getItem(position)

        val image = itemView.findViewById(R.id.note_image) as ImageView
        if (technique.note.isEmpty()) {
            image.visibility = View.INVISIBLE
        }
        else {
            image.visibility = View.VISIBLE
        }

        val nameText = itemView.findViewById(R.id.name_text) as TextView
        nameText.text = technique.name

        val descriptionText = itemView.findViewById(R.id.description_text) as TextView
        descriptionText.text = technique.description

        return itemView
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        val technique = getItem(position)

        val dialog = Dialog(context)
        dialog.setContentView(R.layout.dialog_note)

        val nameView = dialog.findViewById(R.id.note_dialog_name) as TextView
        nameView.text = technique.name

        val descriptionView = dialog.findViewById(R.id.note_dialog_description) as TextView
        descriptionView.text = technique.description

        val noteView = dialog.findViewById(R.id.note_dialog_note) as EditText
        noteView.setText(technique.note)

        val dialogSave = dialog.findViewById(R.id.note_dialog_save) as Button
        dialogSave.setOnClickListener {
            val note = noteView.text.toString().trim()

            doAsync {
                context.database.use {
                    update(NotesEntry.TABLE_NAME, NotesEntry.COLUMN_NOTE to note)
                            .whereSimple("_id = ?", technique.id.toString())
                            .exec()
                }
                uiThread {
                    technique.note = note

                    notifyDataSetChanged()

                    Toast.makeText(context, R.string.note_save, Toast.LENGTH_SHORT).show()
                }
            }

            dialog.dismiss()
        }

        val dialogDelete = dialog.findViewById(R.id.note_dialog_delete) as Button
        dialogDelete.setOnClickListener {
            if (!technique.note.isEmpty()) {
                createDeleteDialog(context, technique)
            }

            dialog.dismiss()
        }

        dialog.show()
    }

    /** Create the confirmation alert dialog for when the user taps delete */
    private fun createDeleteDialog(ctx: Context, technique: UserTechnique) {
        AlertDialog.Builder(ctx)
                .setMessage(R.string.note_alert_message)
                .setPositiveButton(R.string.alert_positive,
                        DialogInterface.OnClickListener { dialog, which ->

                    doAsync {
                        technique.note = ""
                        context.database.use {
                            update(NotesEntry.TABLE_NAME,
                                    NotesEntry.COLUMN_NOTE to "")
                                    .whereSimple("_id = ?", technique.id.toString())
                                    .exec()
                        }
                        uiThread {
                            notifyDataSetChanged()

                            dialog.dismiss()

                            Toast.makeText(context, R.string.note_delete, Toast.LENGTH_SHORT).show()
                        }
                    }
                })
                .setNegativeButton(R.string.alert_negative,
                        DialogInterface.OnClickListener { dialog, which ->

                    dialog.dismiss()
                })
                .show()
    }
}