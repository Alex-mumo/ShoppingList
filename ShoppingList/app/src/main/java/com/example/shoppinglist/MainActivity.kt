package com.example.shoppinglist
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var name: EditText
    private lateinit var amount: EditText
    private lateinit var save: AppCompatButton
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        name = findViewById<EditText>(R.id.name)
        amount = findViewById<EditText>(R.id.amount)
        save = findViewById<AppCompatButton>(R.id.save)
        save.setOnClickListener{
            saveData()
        }
    }
    /*functions to save data to firebase*/
    private fun saveData() {
        val item = name.text.toString().trim()
        if (item.isEmpty()){
            name.error = "Please enter name"
            return
        }
        val money = amount.text.toString().trim()
        if (money.isEmpty()){
            amount.error = "Please enter amount"
            return
        }
        val reference = FirebaseDatabase.getInstance().getReference("info")
        val infoId = reference.push().key /*generating a key for every entry*/
        val info = infoId?.let { Info(it, item, money) }
        if (infoId != null) {
            reference.child(infoId).setValue(info).addOnCompleteListener{
                Toast.makeText(this, "Item Saved Successfully", Toast.LENGTH_LONG).show()
            }
        }
        else{
            Toast.makeText(this, "Item Not Saved Successfully", Toast.LENGTH_LONG).show()
        }
    }
    /*creating options menu*/
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.exit->{
                finish() //exits the app
                Toast.makeText(this,"Exiting", Toast.LENGTH_LONG).show()
                return true
            }
            R.id.theme ->{
                Toast.makeText(this, "Theme", Toast.LENGTH_LONG).show()
                return true
            }
            R.id.settings->{
                Toast.makeText(this, "Settings", Toast.LENGTH_LONG).show()
                return true
            }
            else -> {return super.onOptionsItemSelected(item)}
        }

    }
/*
    class CustomerAdapter(val userList: ArrayList<Info>) : RecyclerView.Adapter<CustomerAdapter.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerAdapter.ViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.activity_main, parent, false)
            return ViewHolder(v)
        }
        override fun onBindViewHolder(holder: CustomerAdapter.ViewHolder, position: Int) {
            holder.bindItems(userList[position])

        }
        override fun getItemCount(): Int {
            return userList.size

        }
        class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            fun bindItems(info: Info) {
                val name = itemView.findViewById<EditText>(R.id.name) as EditText
                val money = itemView.findViewById<EditText>(R.id.amount) as EditText
                //name.text =  info.name
                //money.text = info.money
            }

        }
    }*/

}