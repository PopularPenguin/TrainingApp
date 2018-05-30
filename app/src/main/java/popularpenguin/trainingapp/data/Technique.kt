package popularpenguin.trainingapp.data

import android.content.Context

class Technique(ctx: Context, nameId: Int, descriptionId: Int) {
    var fullName: String = ctx.getString(nameId)
    var name = fullName
    var fullDescription: String = ctx.getString(descriptionId)
    var description = fullDescription
    var note = ""

    // if the description is too long for the list view, shorten it
    init {
        if (name.length > 30) {
            name = name.trim().substring(0..27) + "..."
        }
        if (description.length > 110) {
            description = description.trim().substring(0..117) + "..."
        }
    }
}

class UserTechnique(var id: Long, var fullName: String,
                    var fullDescription: String, var note: String) {

    var name = fullName
    var description = fullDescription

    // if the description is too long for the list view, shorten it
    init {
        if (name.length > 30) {
            name = name.trim().substring(0..27) + "..."
        }
        if (description.length > 110) {
            description = description.trim().substring(0..117) + "..."
        }
    }
}