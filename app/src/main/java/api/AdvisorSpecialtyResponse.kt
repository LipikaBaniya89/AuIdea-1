package api

class AdvisorSpecialtyResponse : ArrayList<AdvisorSpecialtyResponseItem>()

data class AdvisorSpecialtyResponseItem(
    val advisorId: Int,
    val advisorImage: String,
    val advisorName: String,
    val advisorSpecialty: List<Any>
)