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
import android.widget.TextView
import android.widget.Toast
import api.AllApi
import api.HomeProject
import api.ProjectResponse
import kotlinx.android.synthetic.main.activity_about.*
import kotlinx.android.synthetic.main.advisor_information.*
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.project_detail)


        //        Download Manager
        var request = DownloadManager.Request(
            Uri.parse("https://atmiyauni.ac.in/wp-content/uploads/2020/04/AU-Brochure-update-March-2020.pdf"))
            .setTitle("Senior Project Report")
            .setDescription("Senior Project Report Downloading")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
            .setAllowedOverMetered(true)

        var dm = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        mydownloadid = dm.enqueue(request)

        var br = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                var id = p1?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                if (id == mydownloadid) {
                    Toast.makeText(
                        applicationContext,
                        "Download Completed",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
        registerReceiver(br, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))


        //Api and Recycler
        var title = intent.getStringExtra("TITLE")
        var name = intent.getStringExtra("GRP")
        var semes = intent.getStringExtra("SEM")
        var type = intent.getStringExtra("TYPE")


        val retrofit: Retrofit =
            Retrofit.Builder().baseUrl("https://auidea.azurewebsites.net/").addConverterFactory(
                GsonConverterFactory.create()
            ).build()

        val Api: AllApi = retrofit.create(AllApi::class.java)
        val projectNumber = intent.getStringExtra("p_number")



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
        savedProj.setOnClickListener{
            val intent2 = Intent()
            intent2.putExtra("SAVED", project)
            Log.d("CHECK",project[0].toString())
            setResult(Activity.RESULT_OK,intent2)
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