package lipika.androidapp.gridlayoutadvisor

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.advisor_grid.*
import kotlinx.android.synthetic.main.profile_popup.*
import kotlinx.android.synthetic.main.profile_popup.view.*

class ProfileFragment:Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.profile_popup,container, false)

        val signInAccount = GoogleSignIn.getLastSignedInAccount(context)
        if (signInAccount != null){

            val name =  view.userName
            val email = view.userEmail

            name.setText(signInAccount.displayName)
            email.setText(signInAccount.email)
        }

        view.logoutButton.setOnClickListener{
            Log.d("logout clicked", "Selected")
            val intent = Intent(activity, ProfileActivity::class.java)
            startActivity(intent)
        }
        return view
    }

    override fun onStart() {
        super.onStart()
    }
}