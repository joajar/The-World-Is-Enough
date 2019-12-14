package joajar.dockerpostgresql.services;

import joajar.dockerpostgresql.domain.Country;
import joajar.dockerpostgresql.dto.CountryDto;
import joajar.dockerpostgresql.exceptions.InternalErrorException;
import joajar.dockerpostgresql.exceptions.InvalidCountryCodeException;
import joajar.dockerpostgresql.repository.TongueRepository;
import joajar.dockerpostgresql.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class CountryDtoServiceImpl implements CountryDtoService {
    private CountryRepository countryRepository;
    private TongueRepository tongueRepository;

    @Autowired
    public CountryDtoServiceImpl(CountryRepository countryRepository, TongueRepository tongueRepository) {
        this.countryRepository = countryRepository;
        this.tongueRepository = tongueRepository;
    }

    @Override
    public CountryDto findCountryDto(String code) throws InvalidCountryCodeException, InternalErrorException {

        Country country = countryRepository.findFirstCountryByCode(code)
                .orElseThrow(InvalidCountryCodeException::new);

        CountryDto countryDto = new CountryDto();

        try {
            countryDto.setCode(code);
            countryDto.setName(country.getName());
            countryDto.setContinent(country.getContinent());
            countryDto.setPopulation(country.getPopulation());

            if (country.getExpectancy() == null) {
                countryDto.setLife_expectancy("NO_LIFE_EXPECTANCY_SPECIFIED_IN_DB");
            } else {
                countryDto.setLife_expectancy(
                        "" + BigDecimal.valueOf(country.getExpectancy())
                                .setScale(1, RoundingMode.HALF_DOWN)
                );
            }

            if (!tongueRepository.findFirstByIdCodeOrderByPercentageDesc(code).isPresent()) {
                countryDto.setCountry_language("NO_LANGUAGE_SPECIFIED_IN_DB");
            } else {
                countryDto.setCountry_language(
                        tongueRepository.findFirstByIdCodeOrderByPercentageDesc(code)
                                .get()
                                .getId()
                                .getLanguage());
            }

        } catch (NullPointerException | InvalidDataAccessResourceUsageException  ex) {
            throw new InternalErrorException();
        }

        return countryDto;
    }
}
