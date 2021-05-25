package com.university.itis.mapper;

import com.university.itis.dto.ImageDto;
import com.university.itis.model.Image;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImageMapper {

    @Value("${host}")
    private String host;

    public Image toImage(ImageDto imageDto) {
        return toImage(imageDto, new Image());
    }

    public Image toImage(ImageDto imageDto, Image image) {
        image.setId(imageDto.getId());
        image.setName(imageDto.getUrl());
        return image;
    }

    public ImageDto toDtoConvert(Image image) {
        ImageDto imageDto = new ImageDto();
        imageDto.setId(image.getId());
        imageDto.setUrl(host + "image/" + image.getName());
        return imageDto;
    }

    public List<ImageDto> toListDtoConvert(List<Image> imageList) {
        return imageList
                .stream()
                .map(this::toDtoConvert)
                .collect(Collectors.toList());
    }

    public List<Image> toListConvert(List<ImageDto> imageDtoList) {
        return imageDtoList
                .stream()
                .map(this::toImage)
                .collect(Collectors.toList());
    }
}
