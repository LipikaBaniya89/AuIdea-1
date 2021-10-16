package api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AllApi {
    @GET("api/recommendation/projectdetail")
    fun getProject(@Query("projectNo") p_number:String):Call<ProjectResponse>

    @GET("api/recommendation/home")
    fun getRecommendation(@Query("studentId") student_id:String):Call<Recommendation>

    //@GET("api/recommendation/recommendationall")
    //fun getRecommendation():Call<Recommendation>

    @GET("api/recommendation/search")
    fun getHomeProject():Call<HomeProject>

    @GET("api/recommendation/advisordetail")
    fun getAdvisorProject(@Query("advisorId") a_number: String):Call<AdvisorProjectResponse>

    @GET("api/recommendation/advisorspecialty")
    fun getAdvisor():Call<AdvisorResponse>

}