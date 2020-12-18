package com.example.madlevel5task2.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.madlevel5task2.R
import com.example.madlevel5task2.model.Game
import com.example.madlevel5task2.repository.GameRepository
import kotlinx.android.synthetic.main.fragment_games.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class GamesFragment : Fragment() {

    private val viewModel: GameViewModel by viewModels()

    private lateinit var gameRepository: GameRepository

    private var gamesList = arrayListOf<Game>()
    private var gamesAdapter = GameAdapter(gamesList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_games, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gameRepository = GameRepository(requireContext())

        observeAddGameResult()
        initViews()
        gamesAdapter.notifyDataSetChanged()
    }

    private fun observeAddGameResult() {
        viewModel.livedataGamesList.observe(viewLifecycleOwner, Observer{ games ->
            games?.let {
                gamesList.clear()
                gamesList.addAll(games)
                gamesAdapter.notifyDataSetChanged()
            }
            gamesAdapter.notifyDataSetChanged()
        })
        gamesAdapter.notifyDataSetChanged()
    }

    private fun initViews() {
        // Initialize the recycler view with a linear layout manager, adapter
        rvGames.layoutManager = LinearLayoutManager(context)
        rvGames.adapter = gamesAdapter
        gamesAdapter.notifyDataSetChanged()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.w("Delete", "Inside onOptionsItemSelected")
        return when (item.itemId) {
            R.id.action_delete -> {
                viewModel.deleteAllGames()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}