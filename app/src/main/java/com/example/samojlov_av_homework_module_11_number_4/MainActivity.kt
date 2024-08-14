package com.example.samojlov_av_homework_module_11_number_4

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.samojlov_av_homework_module_11_number_4.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(), Removable {

    lateinit var userViewModel: UserViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var toolbarMain: androidx.appcompat.widget.Toolbar
    private lateinit var editTextNameET: EditText
    private lateinit var editTextAgeET: EditText
    private lateinit var saveLaunchBTN: Button
    private lateinit var listViewLW: ListView
    private var userList: MutableList<String> = mutableListOf()
    private var adapter: ArrayAdapter<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
//        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        init()
    }

    private fun init() {

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        toolbarMain = binding.toolbarMain
        setSupportActionBar(toolbarMain)
        title = getString(R.string.toolbar_title)
        toolbarMain.subtitle = getString(R.string.toolbar_subtitle)
        toolbarMain.setLogo(R.drawable.toolbar_icon)

        editTextNameET = binding.editTextNameET
        editTextAgeET = binding.editTextAgeET

        listViewLW = binding.listViewLW


        userViewModel.currentUserList.observe(this) {
            adapter = ArrayAdapter(this, R.layout.mytextview, it)
            listViewLW.adapter = adapter
            adapter!!.notifyDataSetChanged()
        }

        saveLaunchBTN = binding.saveLaunchBTN
        saveLaunchBTN.setOnClickListener {
            if (editTextNameET.text.isEmpty() || editTextAgeET.text.isEmpty()) return@setOnClickListener
            val user = User(editTextNameET.text.toString(), editTextAgeET.text.toString())
            userList.add(user.toString())
            userViewModel.currentUserList.value = (userList.also { userViewModel.listUser = it })
            adapter!!.notifyDataSetChanged()
            editTextNameET.text.clear()
            editTextAgeET.text.clear()
        }

        listViewLW.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val note = adapter!!.getItem(position)
                val dialog = MyDialog()
                val args = Bundle()
                args.putString("note", note.toString())
                dialog.arguments = args
                dialog.show(supportFragmentManager, "custom")
            }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    @SuppressLint("SetTextI18n", "ShowToast")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.exitMenuMain -> {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.toast_exit),
                    Toast.LENGTH_LONG
                ).show()
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun remove(note: String) {
        adapter?.remove(note)
        Snackbar.make(listViewLW, "Пользователь $note удален", Snackbar.LENGTH_LONG).show()
    }
}