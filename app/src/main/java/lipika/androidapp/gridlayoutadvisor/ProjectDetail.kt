package lipika.androidapp.gridlayoutadvisor

import android.app.Activity
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import api.AllApi
import api.HomeProject
import api.ProjectResponse
import kotlinx.android.synthetic.main.activity_about.*
import kotlinx.android.synthetic.main.advisor_information.*
import kotlinx.android.synthetic.main.fragment_project_detail.*
import kotlinx.android.synthetic.main.item_container_sp1.*
import kotlinx.android.synthetic.main.item_container_sp1.view.*
import kotlinx.android.synthetic.main.project_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProjectDetail : AppCompatActivity() {

    var mydownloadid: Long = 0
    var empty=true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.project_detail)

        //Api and Recycler
        var title = intent.getStringExtra("TITLE")
        var name = intent.getStringExtra("GRP")
        var semes = intent.getStringExtra("SEM")
        var type = intent.getStringExtra("TYPE")
        empty=intent.getBooleanExtra("EMPTY",true)


        val retrofit: Retrofit =
            Retrofit.Builder().baseUrl("https://auidea.azurewebsites.net/").addConverterFactory(
                GsonConverterFactory.create()
            ).build()

        val Api: AllApi = retrofit.create(AllApi::class.java)
        val projectNumber = intent.getStringExtra("p_number")

        val project_detail = ProjectDesFragment.newInstance(projectNumber.toString())
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, project_detail).commit()
            description.setTextColor(resources.getColor(R.color.menuColor))
            team.setTextColor(resources.getColor(R.color.colorPrimaryLight))


//        Get Project Detail
        val getProjectRequest: Call<ProjectResponse> = Api.getProject(projectNumber.toString())
        getProjectRequest.enqueue(object : Callback<ProjectResponse> {

            override fun onResponse(call: Call<ProjectResponse>, response: Response<ProjectResponse>) {
                var projectResponse = response.body()
                Log.d("SPARK-API", response.message())
                Log.d("SPARK-API", projectNumber.toString())


                if (projectResponse != null) {
                    spType.text = "Senior Project " + projectResponse[0].projectType.toString()
                    type = projectResponse[0].projectType.toString()
                    grpName.text = projectResponse[0].groupName
                    name = projectResponse[0].groupName
                    projName.text = projectResponse[0].projectTitle
                    title = projectResponse[0].projectTitle
                    Log.d("SPARK-API", projectResponse[0].projectType.toString())

                    // For Color bar
                    if (projectResponse[0].projectType.toString() == "1") {
                        color_bar_d.setBackgroundColor(resources.getColor(R.color.SP1))
                    } else {
                        color_bar_d.setBackgroundColor(resources.getColor(R.color.SP2))
                    }


                }
            }

            override fun onFailure(call: Call<ProjectResponse>, t: Throwable) {
                Log.d("SPARK-API", "Failed to Request!")
            }

        })

        val project = arrayOf(title,name,semes,type)
        savedProj.isVisible=empty

        savedProj.setOnClickListener{
            Toast.makeText(this,"Added to favourites",Toast.LENGTH_SHORT).show()
            val intent2 = Intent()
            intent2.putExtra("SAVED", project)
            Log.d("CHECK",project[0].toString())
            setResult(Activity.RESULT_OK,intent2)
            savedProj.setImageResource(R.drawable.ic_action_filled_heart)
        }

        radio.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.description) {
                val project_detail = ProjectDesFragment.newInstance(projectNumber.toString())
                description.setOnClickListener {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, project_detail).commit()
                    description.setTextColor(resources.getColor(R.color.menuColor))
                    team.setTextColor(resources.getColor(R.color.colorPrimaryLight))
                }

            } else {
                val project_team = ProjectTeamFragment.newInstance(projectNumber.toString())
                team.setOnClickListener {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView, project_team).commit()
                    team.setTextColor(resources.getColor(R.color.menuColor))
                    description.setTextColor(resources.getColor(R.color.colorPrimaryLight))
                }
            }
        }
    }
}
