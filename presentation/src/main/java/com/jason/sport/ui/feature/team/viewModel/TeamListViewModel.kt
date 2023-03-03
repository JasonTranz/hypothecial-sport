package com.jason.sport.ui.feature.team.viewModel

import androidx.lifecycle.ViewModel
import com.jason.domain.usecase.team.GetTeamListUseCase
import javax.inject.Inject

class TeamListViewModel @Inject constructor(
    private val getTeamListUseCase: GetTeamListUseCase
): ViewModel() {
}