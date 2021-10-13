package api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AllApi {

    @GET("api/recommendation/projectdetail")
    fun getProject(@Query("projectNo") p_number:String):Call<ProjectResponse>

    @GET("api/Recommendation/GetRecommendProject?studentId=6135102")
    fun getRecommender():Call<RecommendedResponse>

    @GET("api/recommendation/recommendationall")
    fun getRecommendation():Call<Recommendation>

    @GET("api/recommendation/search")
    fun getHomeProject():Call<HomeProject>

    @GET("api/recommendation/advisordetail")
    fun getAdvisorProject():Call<AdvisorProjectResponse>

    @GET("api/recommendation/advisorspecialty")
    fun getAdvisorSpecialty():Call<AdvisorSpecialtyResponse>

}