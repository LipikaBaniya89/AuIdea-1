package lipika.androidapp.gridlayoutadvisor

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_filter.*

class FilterActivity: AppCompatActivity() {
    var filter= arrayOf("-1","-1")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)

        val b = intent?.extras
        filter = b!!.getStringArray("DATA") as Array<String>

        savedFilter()

        val top_fragment=TopFragment()
        supportFragmentManager.beginTransaction().replace(R.id.topContainerView, top_fragment).commit()

        clear.setOnClickListener {
            reset()
        }

        done.setOnClickListener {
            filter()
        }
    }

    private fun savedFilter() {
        if (filter[0]!="-1") {
            if (filter[0]=="1") {
                seniorProject1.isChecked=true
            }

            if (filter[0]=="2") {
                seniorProject2.isChecked=true
            }

        }

        if (filter[1]!="-1") {
            if (filter[1]=="2016-2") {
                sem1.isChecked=true
            }

            if (filter[1]=="2017-1") {
                sem2.isChecked=true
            }

            if (filter[1]=="2017-2") {
                sem3.isChecked=true
            }

            if (filter[1]=="2018-1") {
                sem4.isChecked=true
            }

            if (filter[1]=="2018-2") {
                sem5.isChecked=true
            }

            if (filter[1]=="2019-1") {
                sem6.isChecked=true
            }

            if (filter[1]=="2019-2") {
                sem7.isChecked=true
            }

            if (filter[1]=="2020-1") {
                sem8.isChecked=true
            }

            if (filter[1]=="2020-2") {
                sem9.isChecked=true
            }

        }
    }

    private fun filter() {

        if (seniorProject1.isChecked) {
            filter[0]="1"
            Log.d("selected",seniorProject1.toString())
        }

        if (seniorProject2.isChecked) {
            filter[0]="2"

        }

        if (sem1.isChecked) {
            filter[1]=sem1.text.toString()
        }

        if (sem2.isChecked) {
            filter[1]=sem2.text.toString()
        }

        if (sem3.isChecked) {
            filter[1]=sem3.text.toString()
        }

        if (sem4.isChecked) {
            filter[1]=sem4.text.toString()
        }

        if (sem5.isChecked) {
            filter[1]=sem5.text.toString()
        }

        if (sem6.isChecked) {
            filter[1]=sem6.text.toString()
        }

        if (sem7.isChecked) {
            filter[1]=sem7.text.toString()
        }

        if (sem8.isChecked) {
            filter[1]=sem8.text.toString()
        }

        if (sem9.isChecked) {
            filter[1]=sem9.text.toString()
        }

        val passData= Intent()
        val b = Bundle()
        b.putStringArray("DATA", filter)
        passData.putExtras(b)
        setResult(110,passData)
        finish()
    }

    private fun reset() {
        seniorProject1.isChecked=false
        seniorProject2.isChecked=false
        sem1.isChecked=false
        sem2.isChecked=false
        sem3.isChecked=false
        sem4.isChecked=false
        sem5.isChecked=false
        sem6.isChecked=false
        sem7.isChecked=false
        sem8.isChecked=false
        sem9.isChecked=false

        filter[0]="-1"
        filter[1]="-1"

    }
}