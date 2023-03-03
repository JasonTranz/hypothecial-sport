package com.jason.domain.usecase.match

import com.jason.domain.core.AppResult
import com.jason.domain.entity.Match
import com.jason.domain.repository.IMatchRepository
import com.jason.domain.usecase.IBaseUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTeamMatchListUseCase @Inject constructor(
    private val repository: IMatchRepository
) : IBaseUseCase<String, AppResult<List<Match>>> {
    override suspend fun invoke(input: String): Flow<AppResult<List<Match>>> {
        return repository.getTeamMatchList(input)
    }
}