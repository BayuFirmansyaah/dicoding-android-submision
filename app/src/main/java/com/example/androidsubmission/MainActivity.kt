package com.example.androidsubmission

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var rvLokomotif: RecyclerView
    private val list = ArrayList<Lokomotif>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "Daftar Lokomotif"

        rvLokomotif = findViewById(R.id.rv_lokomotif)
        rvLokomotif.setHasFixedSize(true)

        list.addAll(getListLokomotif())
        showRecycleView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.about_page -> {
                val intent = Intent(this, Biodata::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getListLokomotif(): ArrayList<Lokomotif>{
        val dataTitle = resources.getStringArray(R.array.daftar_lokomotif)
        val dataDescription = resources.getStringArray(R.array.deskripsi_lokomotif)
        val dataImage = resources.getStringArray(R.array.image_lokomotif)

        val listLokomotif = ArrayList<Lokomotif>()

        for(i in dataTitle.indices){
            val lokomotif =Lokomotif(name=dataTitle[i], description = dataDescription[i], image = dataImage[i])
            listLokomotif.add(lokomotif)
        }

        return listLokomotif;
    }

    private fun showRecycleView(){
        rvLokomotif.layoutManager = LinearLayoutManager(this)
        val listLokomotifAdapter = ListLokomotifAdapter(list)
        rvLokomotif.adapter = listLokomotifAdapter

        listLokomotifAdapter.setOnItemClickCallback(object : ListLokomotifAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Lokomotif?) {
                data?.let {
                    lokomotifDetail(it)
                }
            }
        })
    }

    private fun lokomotifDetail(lokomotif: Lokomotif){
        val intent = Intent(this@MainActivity, LokomotifDetail::class.java)
        intent.putExtra(LokomotifDetail.DETAIL_LOKOMOTIF, lokomotif)
        startActivity(intent)
    }
}