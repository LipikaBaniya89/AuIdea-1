package lipika.androidapp.gridlayoutadvisor

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DownloadActivity : AppCompatActivity() {

    var mydownloadid: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.project_detail)

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
                    Toast.makeText(this@DownloadActivity, "Download Completed", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
        registerReceiver(br, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
    }
}