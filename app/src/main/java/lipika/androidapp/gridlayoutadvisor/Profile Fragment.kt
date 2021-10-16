package lipika.androidapp.gridlayoutadvisor

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.advisor_grid.*
import kotlinx.android.synthetic.main.profile_popup.*
import kotlinx.android.synthetic.main.profile_popup.view.*
import java.lang.reflect.Array.getInt

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
           // val email = view.userEmail
            val image = view.userProfileImg
            val id = view.userID

            var id_string = signInAccount.email
            var id_int = (id_string.substring(1,8)).toInt()
            name.setText(signInAccount.displayName)
            //email.setText(signInAccount.email)
            Picasso.get().load(signInAccount.photoUrl).into(image)
            id.setText(id_int.toString())

            val shared=activity?.getPreferences(Context.MODE_PRIVATE)
            shared?.edit()?.putInt(LoginActivity.PASSKEY,id_int)?.apply()
            val stdID = shared?.getInt(LoginActivity.PASSKEY,-1)
            Log.d("STUDENT-ID",id_int.toString())
            Log.d("STUDENT-ID",stdID.toString())
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