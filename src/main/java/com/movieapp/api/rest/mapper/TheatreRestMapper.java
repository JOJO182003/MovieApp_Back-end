package com.movieapp.api.rest.mapper;

import com.movieapp.domain.model.Theatre;
import com.movieapp.api.rest.dto.request.CreateTheatreRequest;
import com.movieapp.api.rest.dto.response.TheatreResponse;

public class TheatreRestMapper {

    public static TheatreResponse toResponse(Theatre theatre) {
        return new TheatreResponse(
                theatre.getId(),
                theatre.getName(),
                theatre.getCity(),
                theatre.getAddress()
        );
    }

    public static Theatre fromRequest(CreateTheatreRequest req) {
        return new Theatre(
                0,
                req.getName(),
                req.getCity(),
                req.getAddress()
        );
    }
}
