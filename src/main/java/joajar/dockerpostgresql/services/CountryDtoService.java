package joajar.dockerpostgresql.services;

import joajar.dockerpostgresql.dto.CountryDto;
import joajar.dockerpostgresql.exceptions.InternalErrorException;
import joajar.dockerpostgresql.exceptions.InvalidCountryCodeException;

public interface CountryDtoService {
    CountryDto findCountryDto(String code) throws InvalidCountryCodeException, InternalErrorException;
}
