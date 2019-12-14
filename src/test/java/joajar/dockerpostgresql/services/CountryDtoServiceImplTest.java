package joajar.dockerpostgresql.services;

import joajar.dockerpostgresql.domain.Country;
import joajar.dockerpostgresql.domain.Tongue;
import joajar.dockerpostgresql.domain.TongueId;
import joajar.dockerpostgresql.dto.CountryDto;
import joajar.dockerpostgresql.exceptions.InvalidCountryCodeException;
import joajar.dockerpostgresql.repository.CountryRepository;
import joajar.dockerpostgresql.repository.TongueRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(MockitoJUnitRunner.class)
public class CountryDtoServiceImplTest {
    @Mock
    private CountryRepository countryRepository;

    @Mock
    private TongueRepository tongueRepository;

    @InjectMocks
    private CountryDtoServiceImpl countryDtoService;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        countryDtoService = new CountryDtoServiceImpl(countryRepository, tongueRepository);
    }

    @Test(expected = InvalidCountryCodeException.class)
    public void should_fail_while_calling_nonexistent_code() {

        final CountryDto countryDto;

        when(countryRepository.findFirstCountryByCode("AAA")).thenThrow(InvalidCountryCodeException.class);

        countryDto = countryDtoService.findCountryDto("AAA");

        assertNull(countryDto);
        verify(countryRepository, times(1)).findFirstCountryByCode(anyString());
        verifyNoMoreInteractions(countryRepository);
        verifyNoInteractions(tongueRepository);
    }

    @Test
    public void should_get_data_by_country_code() {

        final Country arubaCountry = Country.builder().code("ABW").name("Aruba").continent("North America")
                .region("Caribbean").surface_area(193.).indep_year(2020).population(103000).expectancy(78.400002)
                .gnp(828.).gnp_old(793.).local_name("Aruba").government_form("Nonmetropolitan Territory of The Netherlands")
                .head_of_state("Beatrix").capital(129).code2("AW").build();

        final CountryDto arubaCountryDto = CountryDto.builder().code("ABW").name("Aruba").continent("North America")
                .population(103000).life_expectancy("78.4").country_language("Papiamento").build();

        final Tongue arubaTonguePapiamento = Tongue.builder().id(TongueId.builder().code("ABW").language("Papiamento").build())
                .official(false).percentage(76.699997).build();

        final CountryDto countryDto;

        when(countryRepository.findFirstCountryByCode("ABW")).thenReturn(Optional.ofNullable(arubaCountry));
        when(tongueRepository.findFirstByIdCodeOrderByPercentageDesc("ABW")).thenReturn(Optional.ofNullable(arubaTonguePapiamento));

        countryDto = countryDtoService.findCountryDto("ABW");

        assertNotNull(countryDto);
        assertEquals(arubaCountryDto, countryDto);
        verify(countryRepository, times(1)).findFirstCountryByCode(anyString());
        verifyNoMoreInteractions(countryRepository);
        verify(tongueRepository, times(2)).findFirstByIdCodeOrderByPercentageDesc(anyString());
        verifyNoMoreInteractions(tongueRepository);
    }

    @Test
    public void should_get_data_with_null_expectancy() {

        final Country arubaCountry = Country.builder().code("ABW").name("Aruba").continent("North America")
                .region("Caribbean").surface_area(193.).indep_year(2020).population(103000).expectancy(null)
                .gnp(828.).gnp_old(793.).local_name("Aruba").government_form("Nonmetropolitan Territory of The Netherlands")
                .head_of_state("Beatrix").capital(129).code2("AW").build();

        final Tongue arubaTonguePapiamento = Tongue.builder().id(TongueId.builder().code("ABW").language("Papiamento").build())
                .official(false).percentage(76.699997).build();

        final CountryDto countryDto;

        when(countryRepository.findFirstCountryByCode("ABW")).thenReturn(Optional.ofNullable(arubaCountry));
        when(tongueRepository.findFirstByIdCodeOrderByPercentageDesc("ABW")).thenReturn(Optional.ofNullable(arubaTonguePapiamento));

        countryDto = countryDtoService.findCountryDto("ABW");

        assertNotNull(countryDto);
        assertEquals("ABW", countryDto.getCode());
        assertEquals("Aruba", countryDto.getName());
        assertEquals("North America", countryDto.getContinent());
        assertEquals(103000, (int) countryDto.getPopulation());
        assertEquals("NO_LIFE_EXPECTANCY_SPECIFIED_IN_DB", countryDto.getLife_expectancy());
        assertEquals("Papiamento", countryDto.getCountry_language());
        verify(countryRepository, times(1)).findFirstCountryByCode(anyString());
        verifyNoMoreInteractions(countryRepository);
        verify(tongueRepository, times(2)).findFirstByIdCodeOrderByPercentageDesc(anyString());
        verifyNoMoreInteractions(tongueRepository);
    }

    @Test
    public void should_get_data_with_nonexistent_language_data() {

        final Country arubaCountry = Country.builder().code("ABW").name("Aruba").continent("North America")
                .region("Caribbean").surface_area(193.).indep_year(2020).population(103000).expectancy(78.400002)
                .gnp(828.).gnp_old(793.).local_name("Aruba").government_form("Nonmetropolitan Territory of The Netherlands")
                .head_of_state("Beatrix").capital(129).code2("AW").build();

        final CountryDto countryDto;

        when(countryRepository.findFirstCountryByCode("ABW")).thenReturn(Optional.ofNullable(arubaCountry));
        when(tongueRepository.findFirstByIdCodeOrderByPercentageDesc("ABW")).thenReturn(Optional.empty());

        countryDto = countryDtoService.findCountryDto("ABW");

        assertNotNull(countryDto);
        assertEquals("ABW", countryDto.getCode());
        assertEquals("Aruba", countryDto.getName());
        assertEquals("North America", countryDto.getContinent());
        assertEquals(103000, (int) countryDto.getPopulation());
        assertEquals("78.4", countryDto.getLife_expectancy());
        assertEquals("NO_LANGUAGE_SPECIFIED_IN_DB", countryDto.getCountry_language());
        verify(countryRepository, times(1)).findFirstCountryByCode(anyString());
        verifyNoMoreInteractions(countryRepository);
        verify(tongueRepository, times(1)).findFirstByIdCodeOrderByPercentageDesc(anyString());
        verifyNoMoreInteractions(tongueRepository);
    }
}
