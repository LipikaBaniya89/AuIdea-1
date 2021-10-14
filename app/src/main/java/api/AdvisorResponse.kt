package api

import android.os.Parcelable
import java.io.Serializable

class AdvisorResponse : ArrayList<AdvisorResponseItem>()

data class AdvisorResponseItem(
    val advisorId: Int,
    val advisorImage: String,
    val advisorName: String,
    val advisorSpecialty: ArrayList<Specialty>
)

data class Specialty(
    val advisorId:Int,
    val specialty:String
):Serializable

