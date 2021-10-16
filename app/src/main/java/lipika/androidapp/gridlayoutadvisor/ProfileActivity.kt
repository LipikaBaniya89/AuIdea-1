package lipika.androidapp.gridlayoutadvisor

import android.content.Intent
import android.net.sip.SipManager.newInstance
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.profile_popup.*
import kotlinx.android.synthetic.main.profile_popup.view.*
import lipika.androidapp.gridlayoutadvisor.ProjectDesFragment.Companion.newInstance
import lipika.androidapp.gridlayoutadvisor.ProjectTeamFragment.Companion.newInstance
import lipika.androidapp.gridlayoutadvisor.RecommendFragment.Companion.newInstance
import org.w3c.dom.Text

class ProfileActivity: AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        //Log.i(HomeActivity.TAG, "Logout")
        auth.signOut()
        val logoutIntent = Intent(this, LoginActivity::class.java)
        logoutIntent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(logoutIntent)
        finish()


//        val signInAccount = GoogleSignIn.getLastSignedInAccount(applicationContext)
//        if (signInAccount != null){
//            val id = userID
//            var id_string = signInAccount.email
//            var id_int = (id_string.substring(1,8)).toInt()
//
//            id.setText(id_int.toString())
//            val projectNumber = intent.getStringExtra("p_number")
//        }
//
//        val idNo = intent.getStringExtra("p_number")
//
//        val uniqueId= ProjectDesFragment.newInstance(idNo.toString())

    }
}