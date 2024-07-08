package com.rmf.testandroidmvvm.presentation.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rmf.testandroidmvvm.R
import com.rmf.testandroidmvvm.presentation.home.adapter.JokeAdapter
import com.rmf.testandroidmvvm.util.Resource

class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModels<MainViewModel>()
    private lateinit var jokeAdapter: JokeAdapter
    private lateinit var loadingDialog: AlertDialog
    private lateinit var errorDialog: AlertDialog


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val searchEditText = findViewById<EditText>(R.id.searchEditText)
        val searchButton = findViewById<Button>(R.id.searchButton)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)
        jokeAdapter = JokeAdapter(emptyList())
        recyclerView.adapter = jokeAdapter


        setupLoadingDialog()
        setupErrorDialog()

        searchButton.setOnClickListener {
            val query = searchEditText.text.toString()
            mainViewModel.searchJokes(query)
        }

        mainViewModel.jokes.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    if (resource.isLoading)
                        loadingDialog.show()
                    else
                        loadingDialog.dismiss()
                }

                is Resource.Success -> {
                    loadingDialog.dismiss()
                    jokeAdapter.setJokes(resource.data ?: emptyList())
                }

                is Resource.Error -> {
                    loadingDialog.dismiss()
                    showErrorDialog(resource.message ?: "Unknown error")
                }
            }
        }

    }

    private fun setupLoadingDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setView(R.layout.dialog_loading)
        builder.setCancelable(false)
        loadingDialog = builder.create()
    }

    private fun setupErrorDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
        errorDialog = builder.create()
    }

    private fun showErrorDialog(message: String) {
        errorDialog.setMessage(message)
        errorDialog.show()
    }

}