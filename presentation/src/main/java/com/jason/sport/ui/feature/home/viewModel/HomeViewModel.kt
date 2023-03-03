package com.jason.sport.ui.feature.home.viewModel

import androidx.lifecycle.ViewModel
import com.jason.domain.usecase.match.GetMatchListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getMatchListUseCase: GetMatchListUseCase
): ViewModel() {


}