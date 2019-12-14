package joajar.dockerpostgresql.controllers;

import joajar.dockerpostgresql.dto.CountryDto;
import joajar.dockerpostgresql.exceptions.AllExceptionsHandler;
import joajar.dockerpostgresql.exceptions.InternalErrorException;
import joajar.dockerpostgresql.exceptions.InvalidCountryCodeException;
import joajar.dockerpostgresql.services.CountryDtoServiceImpl;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(MockitoJUnitRunner.class)
public class CountryDtoControllerTest {
    private MockMvc mockMvc;

    @Mock
    private CountryDtoServiceImpl countryDtoService;

    @InjectMocks
    private CountryDtoController countryDtoController;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(countryDtoController)
                .setControllerAdvice(new AllExceptionsHandler())
                .build();
    }

    @Test
    //Create Unit Test for the following test:
    //if database is down then the error message should be "INTERNAL_ERROR" and http response should be internal server error
    public void should_fail_while_downing_database() throws Exception {
        when(countryDtoService.findCountryDto("AAA")).thenThrow(InternalErrorException.class);

        mockMvc.perform(get("/{code}", "AAA").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("INTERNAL_ERROR"))
                .andExpect(status().isInternalServerError())
        ;

        verify(countryDtoService, times(1)).findCountryDto(anyString());
        verifyNoMoreInteractions(countryDtoService);
    }

    @Test
    //Create Unit Test for the following test:
    //if non existent code is called then return error message:"INVALID_COUNTRY_CODE" and http response should be internal server error
    public void should_fail_while_calling_nonexistent_code() throws Exception {
        when(countryDtoService.findCountryDto("AAA")).thenThrow(InvalidCountryCodeException.class);

        mockMvc.perform(get("/{code}", "AAA").accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("INVALID_COUNTRY_CODE"))
                .andExpect(status().isInternalServerError());

        verify(countryDtoService, times(1)).findCountryDto(anyString());
        verifyNoMoreInteractions(countryDtoService);
    }

    @Test
    public void should_get_data_by_country_code() throws Exception {
        final CountryDto arubaCountry = CountryDto.builder()
                .code("ABW")
                .name("Aruba")
                .continent("North America")
                .population(103000)
                .life_expectancy("78.4")
                .country_language("Papiamento")
                .build();

        when(countryDtoService.findCountryDto("ABW")).thenReturn(arubaCountry);

        mockMvc.perform(get("/{code}", "ABW").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.name").value(Matchers.is("Aruba")))
                .andExpect(jsonPath("$.continent").value(Matchers.is("North America")))
                .andExpect(jsonPath("$.population").value(Matchers.is(103000)))
                .andExpect(jsonPath("$.life_expectancy").value(Matchers.is("78.4")))
                .andExpect(jsonPath("$.country_language").value(Matchers.is("Papiamento")))
        ;

        verify(countryDtoService, times(1)).findCountryDto(anyString());
        verifyNoMoreInteractions(countryDtoService);
    }
}
