package com.university.itis.dto.map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MapRegionDto {
    private LocationDto topLeft;
    private LocationDto bottomRight;
}
