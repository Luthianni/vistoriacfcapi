package br.gov.ce.detran.vistoriacfcapi.web.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import br.gov.ce.detran.vistoriacfcapi.entity.Profile;
import br.gov.ce.detran.vistoriacfcapi.web.dto.ProfileCreateDto;
import br.gov.ce.detran.vistoriacfcapi.web.dto.ProfileResponseDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProfileMapper {
    
    public static Profile toProfile(ProfileCreateDto dto) {
        return new ModelMapper().map(dto, Profile.class);
    }

    public static ProfileResponseDto toDto(Profile profile) {
        return new ModelMapper().map(profile, ProfileResponseDto.class);
    }

    public static List<ProfileResponseDto> toListDto(List<Profile> profiles) {
        return profiles.stream().map(profile -> toDto(profile)).collect(Collectors.toList());
    }
}
