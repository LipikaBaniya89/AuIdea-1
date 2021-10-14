package lipika.androidapp.gridlayoutadvisor

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.advisor_information.*
import kotlinx.android.synthetic.main.project_detail.*

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.advisor_information)

        val name = intent.getStringExtra("name")
        val image = intent.getStringExtra("image")
        val advisor=intent.getIntExtra("a_number",0)
        val special=intent.getStringExtra("special")
        Log.d("1234",special.toString())

        val specialty_fragment = specialRecyclerFragment.newInstance(special.toString())
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerViewAdvisor, specialty_fragment).commit()
        specButton.setTextColor(resources.getColor(R.color.menuColor))
        projButton.setTextColor(resources.getColor(R.color.colorPrimaryLight))

        advisorNameInfo.text = name
        Picasso.get().load(image).into(imageAdvisor)

        advisorRadio.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.specButton) {

                specButton.setOnClickListener {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerViewAdvisor, specialty_fragment).commit()
                    specButton.setTextColor(resources.getColor(R.color.menuColor))
                    projButton.setTextColor(resources.getColor(R.color.colorPrimaryLight))
                }

            } else {

               val project_fragment = ProjectFragment.newInstance(advisor.toString())

                projButton.setOnClickListener {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerViewAdvisor, project_fragment).commit()
                    projButton.setTextColor(resources.getColor(R.color.menuColor))
                    specButton.setTextColor(resources.getColor(R.color.colorPrimaryLight))
                }
            }
        }
    }

}