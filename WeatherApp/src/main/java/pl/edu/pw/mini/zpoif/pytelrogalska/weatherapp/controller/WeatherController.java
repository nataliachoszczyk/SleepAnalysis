package pl.edu.pw.mini.zpoif.pytelrogalska.weatherapp.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import pl.edu.pw.mini.zpoif.pytelrogalska.weatherapp.service.WeatherService;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RequestMapping("/api/weather")
@RestController
public class WeatherController {

    private final WeatherService service;

    public WeatherController(WeatherService service) {
        this.service = service;
    }

//    @GetMapping("/{cityName}")
//    public String getWeather(@PathVariable String cityName) throws IOException {
//        MainObject object = service.generateObjectFromJson(cityName);
//        return service.getObjectWeatherInfo(object);
//    }


    @GetMapping("/{unit}/{cityName}")
    public String getWeatherWithUnits(@PathVariable String unit, @PathVariable String cityName,
                                      @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate startDate,
                                      @RequestParam @DateTimeFormat(pattern="HH:mm") LocalTime  startTime,
                                      @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate endDate,
                                      @RequestParam @DateTimeFormat(pattern="HH:mm") LocalTime endTime) throws IOException {


           LocalDateTime start = LocalDateTime.of(startDate,startTime);
           LocalDateTime end = LocalDateTime.of(endDate,endTime);
            return service.getObjectWeatherInfo(cityName, unit, start,end);

        }

}
