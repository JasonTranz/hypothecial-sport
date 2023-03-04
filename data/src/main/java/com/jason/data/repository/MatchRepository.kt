package com.jason.data.repository

import com.jason.data.mapper.toMatchCollectionList
import com.jason.data.mapper.toMatch
import com.jason.data.service.MatchService
import com.jason.domain.core.AppResult
import com.jason.domain.entity.MatchCollectionList
import com.jason.domain.entity.Match
import com.jason.domain.repository.IMatchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@OptIn(FlowPreview::class)
class MatchRepository @Inject constructor(
    private val matchService: MatchService
): IMatchRepository {
    override fun getMatchList(): Flow<AppResult<MatchCollectionList>> {
        return flow {
            matchService.getMatchList()
                .flowOn(Dispatchers.IO)
                .flatMapMerge {
                    flow {
                        if (it.matches != null) {
                            emit(it.toMatchCollectionList())
                        } else {
                            emit(null)
                        }
                    }
                }
                .catch {
                    emit(AppResult.Failure(Exception(it.message)))
                }
                .collect { response ->
                    if (response != null) {
                        emit(AppResult.Success(response))
                    } else {
                        emit(AppResult.Failure(Exception("null data")))
                    }
                }
        }
    }

    override fun getTeamMatchList(id: String): Flow<AppResult<MatchCollectionList>> {
        return flow {
            matchService.getTeamMatchList(id)
                .flowOn(Dispatchers.IO)
                .flatMapMerge {
                    flow {
                        if (it.matches != null) {
                            emit(it.toMatchCollectionList())
                        } else {
                            emit(null)
                        }
                    }
                }
                .catch {
                    emit(AppResult.Failure(Exception(it.message)))
                }
                .collect { response ->
                    if (response != null) {
                        emit(AppResult.Success(response))
                    } else {
                        emit(AppResult.Failure(Exception("null data")))
                    }
                }
        }
    }

}
