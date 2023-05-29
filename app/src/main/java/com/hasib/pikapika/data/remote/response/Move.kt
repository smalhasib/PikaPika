package com.hasib.pikapika.data.remote.response

data class Move(
    val move: MoveX,
    val version_group_details: List<VersionGroupDetail>
)