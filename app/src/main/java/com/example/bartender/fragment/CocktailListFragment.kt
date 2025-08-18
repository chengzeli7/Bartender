package com.example.bartender.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bartender.CocktailDetailActivity
import com.example.bartender.adapter.CocktailAdapter
import com.example.bartender.databinding.FragmentCocktailListBinding
import com.example.bartender.model.Cocktail
import com.example.bartender.repository.CocktailRepository

class CocktailListFragment : Fragment() {

    private var _binding: FragmentCocktailListBinding? = null
    private val binding get() = _binding!!

    private lateinit var cocktailAdapter: CocktailAdapter
    private lateinit var repository: CocktailRepository
    private var listType: Int = TYPE_ALL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            listType = it.getInt(ARG_LIST_TYPE, TYPE_ALL)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        repository = CocktailRepository.getInstance(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCocktailListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSearchView()
        loadCocktails()
    }

    // 添加onResume方法，确保每次Fragment可见时都刷新数据
    override fun onResume() {
        super.onResume()
        // 只有收藏列表需要每次显示时刷新，其他列表根据需求决定是否刷新
        if (listType == TYPE_FAVORITES) {
            loadCocktails()
        }
    }

    private fun setupRecyclerView() {
        cocktailAdapter = CocktailAdapter { cocktail ->
            navigateToCocktailDetail(cocktail)
        }

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = cocktailAdapter
        }
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { searchCocktails(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { searchCocktails(it) }
                return true
            }
        })
    }

    private fun loadCocktails() {
        val cocktails = when (listType) {
            TYPE_ALL -> repository.getAllCocktails()
            TYPE_POPULAR -> repository.getPopularCocktails()
            TYPE_FAVORITES -> repository.getFavoriteCocktails()
            else -> repository.getAllCocktails()
        }
        cocktailAdapter.submitList(cocktails)
    }

    private fun searchCocktails(query: String) {
        val filteredList = repository.searchCocktails(query)
        val result = when (listType) {
            TYPE_ALL -> filteredList
            TYPE_POPULAR -> filteredList.take(5)
            TYPE_FAVORITES -> filteredList.filter { it.isFavorite }
            else -> filteredList
        }
        cocktailAdapter.submitList(result)
    }

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            val favoriteStatusChanged = it.data?.getBooleanExtra(CocktailDetailActivity.FAVORITE_STATUS_CHANGED, false) ?: false
            if (favoriteStatusChanged) {
                loadCocktails()
            }
        }
    }

    private fun navigateToCocktailDetail(cocktail: Cocktail) {
        val intent = Intent(requireContext(), CocktailDetailActivity::class.java).apply {
            putExtra(CocktailDetailActivity.EXTRA_COCKTAIL_ID, cocktail.id)
        }
        startForResult.launch(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARG_LIST_TYPE = "list_type"
        const val TYPE_ALL = 0
        const val TYPE_POPULAR = 1
        const val TYPE_FAVORITES = 2

        fun newInstance(type: Int): CocktailListFragment {
            return CocktailListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_LIST_TYPE, type)
                }
            }
        }
    }
}