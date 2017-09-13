package popularpenguin.trainingapp.data

import android.content.Context

class Technique(ctx: Context, nameId: Int, descriptionId: Int) {
    var name = ctx.getString(nameId)
    var fullDescription = ctx.getString(descriptionId)
    var description = fullDescription
    var note = ""

    // if the description is too long for the list view, shorten it
    init {
        if (description.length > 110) {
            description = description.trim().substring(0..117) + "..."
        }
    }
}

class UserTechnique(var id: Long, var name: String,
                    var description: String, var note: String)