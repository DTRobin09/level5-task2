package com.example.madlevel5task2.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.madlevel5task2.R
import com.example.madlevel5task2.model.Game
import kotlinx.android.synthetic.main.fragment_add_game.*
import java.util.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AddGameFragment : Fragment() {

    private val viewModel: GameViewModel by viewModels()

    private var gamesList: List<Game> = arrayListOf<Game>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fabSave.setOnClickListener {
            saveGame()
        }

        observeGame()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            onBackPressed();
        }

        return super.onOptionsItemSelected(item)
    }

    private fun observeGame() {
        //fill the text fields with the current text and title from the viewmodel
        viewModel.livedataGamesList.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                games  ->
            games?.let {
                gamesList = games
            }

        })

        viewModel.error.observe(viewLifecycleOwner, androidx.lifecycle.Observer { message ->
            Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
        })

        viewModel.success.observe(viewLifecycleOwner, androidx.lifecycle.Observer { success ->
            //"pop" the backstack, this means we destroy this fragment and go back to the RemindersFragment
            findNavController().popBackStack()
        })
    }

    private fun saveGame() {
        val monthIndex = 1;
        val yearDifference = 1900;
        if (etYear.text.toString() == "" || etMonth.text.toString() == "" || etDay.text.toString() == "") {
            viewModel.error.value = "No valid date"
        } else {
            viewModel.addGame(etGameTitle.text.toString(), etPlatform.text.toString(), Date(etYear.text.toString().toInt() - yearDifference,etMonth.text.toString().toInt() - monthIndex,etDay.text.toString().toInt()))
        }
    }

    private fun onBackPressed() {
        findNavController().popBackStack()
    }

}