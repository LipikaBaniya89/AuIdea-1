package lipika.androidapp.gridlayoutadvisor

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import api.AllApi
import api.HomeProject
import api.HomeProjectItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_about.*
import kotlinx.android.synthetic.main.activity_about.color_bar
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_recommend.*
import kotlinx.android.synthetic.main.activity_recommend.bottom_navigation_view
import kotlinx.android.synthetic.main.activity_recommend.saveRecyclerView
import kotlinx.android.synthetic.main.item_container_sp1.view.*
import kotlinx.android.synthetic.main.item_container_sp2.view.*
import kotlinx.android.synthetic.main.profile_popup.*
import kotlinx.android.synthetic.main.project_detail.*
import kotlinx.android.synthetic.main.project_detail.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val REQUEST_CODE=102
private const val REQUEST_CODE1=103
private const val REQUEST_CODE_SECONDACT=101
const val FILTER_CODE=110
private lateinit var auth: FirebaseAuth

class HomeActivity : AppCompatActivity() {
var saveStorage = mutableListOf<Array<String>>()

    private companion object{
        const val TAG = "LoginActivity"
    }

    private lateinit var listAdapter: ProjectAdapter

    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var auth: FirebaseAuth

    private var list:ArrayList<HomeProjectItem> = ArrayList<HomeProjectItem>()
    private var filterList:List<HomeProjectItem> = emptyList()
    private var saveFilter= arrayOf("-1","-1")

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode== FILTER_CODE) {
            val b = data?.extras
            saveFilter = b!!.getStringArray("DATA") as Array<String>
            filterList=list
            if ( saveFilter ?.get(0) ?:"-1" !="-1") filterList=filterList.filter { it.projectType ==  saveFilter ?.get(0)?.toInt() ?: -1 }
            if ( saveFilter ?.get(1) ?:"-1" !="-1") filterList=filterList.filter { it.semester ==  saveFilter ?.get(1)}
            listAdapter.setData(filterList)
        }

        if (requestCode == REQUEST_CODE_SECONDACT){
            if(data != null){
                data.getStringArrayExtra("SAVED")?.let{saveStorage.add(it)}
                Log.d("CHECK",saveStorage.toString())
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        auth = Firebase.auth

        val top_fragment = TopFragment()
        supportFragmentManager.beginTransaction().replace(R.id.topContainerView, top_fragment)
            .commit()

        saveRecyclerView.layoutManager = LinearLayoutManager(this)
        listAdapter = ProjectAdapter(emptyList())
        saveRecyclerView.adapter = listAdapter

        bottom_navigation_view.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        val retrofit: Retrofit =
            Retrofit.Builder().baseUrl("https://auidea.azurewebsites.net/").addConverterFactory(
                GsonConverterFactory.create()
            ).build()

        val Api: AllApi = retrofit.create(AllApi::class.java)

//        Get Project HomeScreen
        val getProjectRequest: Call<HomeProject> = Api.getHomeProject()
        getProjectRequest.enqueue(object : Callback<HomeProject> {

            override fun onResponse(call: Call<HomeProject>, response: Response<HomeProject>) {
                var projectResponse = response.body()
                if (projectResponse != null) {
                    listAdapter.setData(projectResponse)
                    list=projectResponse
                    Log.d("SPARK-API", projectResponse.toString())
                }
            }

            override fun onFailure(call: Call<HomeProject>, t: Throwable) {
                Log.d("SPARK-API", "Failed to Request!")
            }
        })

        //Fav intent
        val save: ImageView = findViewById(R.id.savedIcon)
        save.setOnClickListener {
            Log.d("MENU","Favourite")
            val intent3 = Intent(this, SaveActivity::class.java)
            intent3.putExtra("SAVEDLIST", ArrayList(saveStorage))
            startActivity(intent3)
        }

        //Filter button intent
        val filter: ImageView = findViewById(R.id.filterIcon)
        filter.setOnClickListener {
            val intent = Intent(this, FilterActivity::class.java)
            val b = Bundle()
            b.putStringArray("DATA", saveFilter)
            intent.putExtras(b)
            startActivityForResult(intent, FILTER_CODE)
        }

        //About button intent
        val button: ImageButton = findViewById(R.id.aboutButton)
        button.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId) {
            R.id.homeActivity -> {
                replaceFragment(HomeFragment())
                return@OnNavigationItemSelectedListener true
            }

            R.id.recommendActivity -> {
                replaceFragment(RecommendActivity())
                return@OnNavigationItemSelectedListener true
            }

            R.id.navAdvisor -> {
                replaceFragment(AdvisorFragment())
                return@OnNavigationItemSelectedListener true

            }
            R.id.navProfile -> {
                replaceFragment(ProfileFragment())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    private inner class View1Holder(itemView: View): RecyclerView.ViewHolder(itemView){
        var p_name: TextView = itemView.findViewById(R.id.nameSP1)
        var g_name: TextView = itemView.findViewById(R.id.grpSP1)
        var sem: TextView = itemView.findViewById(R.id.semSP1)
        var type: TextView = itemView.findViewById(R.id.SP1)

        lateinit var project: HomeProjectItem

        fun bind(project: HomeProjectItem){
            this.project = project
            p_name.text = (project.projectTitle)
            g_name.setText(project.groupName)
            sem.text = (project.semester)
            type.text = ("Senior Project " + project.projectType.toString())
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ProjectDetail::class.java)

                intent.putExtra("p_number", project.projectNo.toString())
                intent.putExtra("TITLE", project.projectTitle)
                intent.putExtra("GRP",project.groupName)
                intent.putExtra("SEM", project.semester)
                intent.putExtra("TYPE", project.projectType.toString())


                Log.d("SPARK-API","Working ${project.projectNo}")
                startActivityForResult(intent, REQUEST_CODE_SECONDACT)
            }
        }
    }

    private inner class ProjectAdapter(var projects: List<HomeProjectItem>):
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        var seniorProject1=1
        var seniorProject2=2

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val View1=layoutInflater.inflate(R.layout.item_container_sp1,parent,false)

            //For Text
            View1.SP1.setTextColor(if (viewType==seniorProject1) resources.getColor(R.color.SP1)
            else
                resources.getColor(R.color.SP2))

            //For color bar
            View1.color_bar.setBackgroundColor(if (viewType==seniorProject1) resources.getColor(R.color.SP1)
            else
                resources.getColor(R.color.SP2))

            return View1Holder(View1)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val project = projects[position]
            (holder as View1Holder).bind(project)
        }

        override fun getItemCount(): Int {
            return projects.size
        }

        override fun getItemViewType(position: Int): Int {
            val project = projects[position]
            return if(project.projectType == 1){
                seniorProject1
            }else{
                seniorProject2
            }
        }

        fun setData(project: List<HomeProjectItem>) {
            projects = project
            notifyDataSetChanged()
        }
    }
}