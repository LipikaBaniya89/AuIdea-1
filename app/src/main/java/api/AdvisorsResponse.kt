package api

class AdvisorsResponse : ArrayList<AdvisorsResponseItem>()

data class AdvisorsResponseItem(
    val advisorId: Int,
    val advisorImage: String,
    val advisorName: String,
    val projNo: Int,
    val projSemester: String,
    val projTeam: String,
    val projTitle: String,
    val projType: Int
)