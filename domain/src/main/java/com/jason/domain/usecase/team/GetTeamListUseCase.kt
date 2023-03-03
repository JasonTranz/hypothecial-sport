package com.jason.domain.usecase.team

import com.jason.domain.core.AppResult
import com.jason.domain.entity.Team
import com.jason.domain.repository.ITeamRepository
import com.jason.domain.usecase.IBaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTeamListUseCase @Inject constructor(
    private val repository: ITeamRepository
) :IBaseUseCase<Unit, AppResult<List<Team>>>{
    override suspend fun invoke(input: Unit): Flow<AppResult<List<Team>>> {
        return repository.getTeamList()
    }
}