package com.example.nearbyeats_app.application.mapper

import com.example.nearbyeats_app.application.dto.ColleagueDto
import com.example.nearbyeats_app.domain.model.Colleague

object ColleagueMapper {
    fun toDto(colleague: Colleague): ColleagueDto = ColleagueDto(
        id = colleague.id,
        name = colleague.name,
        statusLabel = colleague.status.label,
        avatarInitial = colleague.avatarInitial
    )
}
