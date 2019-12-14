package joajar.dockerpostgresql.controllers;

import joajar.dockerpostgresql.dto.CountryDto;
import joajar.dockerpostgresql.services.CountryDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class CountryDtoController {
    private CountryDtoService countryDtoService;

    @Autowired
    public CountryDtoController(CountryDtoService countryDtoService) {
        this.countryDtoService = countryDtoService;
    }

    @GetMapping(value = "/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CountryDto> getByCode(@PathVariable("code") String code) {
        return new ResponseEntity<>(countryDtoService.findCountryDto(code), HttpStatus.OK);
    }

}
