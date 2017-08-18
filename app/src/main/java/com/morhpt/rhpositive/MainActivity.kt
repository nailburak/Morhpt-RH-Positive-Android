package com.morhpt.rhpositive

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    data class Cards(val cardTitle: String? = null, val cardDesc: String? = null, val cardImage: String? = null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val dbRef = FirebaseDatabase.getInstance().reference.child("cards")

        main_recycler_view.layoutManager = LinearLayoutManager(this)
        main_recycler_view.adapter = object : FirebaseRecyclerAdapter<Cards, CardsHolder>(
                Cards::class.java,
                R.layout.item_main_card,
                CardsHolder::class.java,
                dbRef
        ){
            override fun populateViewHolder(viewHolder: CardsHolder?, model: Cards?, position: Int) {
                viewHolder?.title?.text = model?.cardTitle
                viewHolder?.desc?.text = model?.cardDesc
                viewHolder?.setImage(model?.cardImage, applicationContext)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private class CardsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.item_main_title)
        val desc = itemView.findViewById<TextView>(R.id.item_main_desc)
        val image = itemView.findViewById<ImageView>(R.id.item_main_image)

        fun setImage(img: String?, context: Context){
            Picasso.with(context)
                    .load(img)
                    .into(image)
        }
    }
//        dbRef.push().setValue(Cards("Bill Gates",
//                "Gates III is a co-founder of Microsoft and is an American business magnate, investor, author and philanthropist.",
//                "https://www.project-syndicate.org/default/library/eb7a653970f377481252dbb4a16923f2.square.jpg"))
//        dbRef.push().setValue(Cards("Steve Jobs",
//                "Steve Jobs was an American entrepreneur, businessman, inventor, and industrial designer. Jobs was the chairman, and the chief executive officer, and a co-founder of Apple Inc",
//                "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b9/Steve_Jobs_Headshot_2010-CROP.jpg/1200px-Steve_Jobs_Headshot_2010-CROP.jpg"))
//        dbRef.push().setValue(Cards("Mark Zuckerberg",
//                "Mark Elliot Zuckerberg is an American computer programmer and Internet entrepreneur. He is a co-founder of Facebook.",
//                "https://res.cloudinary.com/crunchbase-production/image/upload/v1448830269/gzcifut4c2xah95x0ewd.jpg"))
}
