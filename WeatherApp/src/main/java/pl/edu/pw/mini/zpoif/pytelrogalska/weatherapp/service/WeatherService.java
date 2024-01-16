package pl.edu.pw.mini.zpoif.pytelrogalska.weatherapp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pw.mini.zpoif.pytelrogalska.weatherapp.config.Config;
import pl.edu.pw.mini.zpoif.pytelrogalska.weatherapp.model.City;
import pl.edu.pw.mini.zpoif.pytelrogalska.weatherapp.model.List;
import pl.edu.pw.mini.zpoif.pytelrogalska.weatherapp.model.Main;
import pl.edu.pw.mini.zpoif.pytelrogalska.weatherapp.model.MainObject;
import pl.edu.pw.mini.zpoif.pytelrogalska.weatherapp.pollution.MainPollutionObject;
import pl.edu.pw.mini.zpoif.pytelrogalska.weatherapp.pollution.PollutionList;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

@Service
public class WeatherService {

    private final Config config;
    private String apiKey = "13662892eb587f841013ffacb523c53e";


    public WeatherService(Config config) {
        this.config = config;
    }

    @PostConstruct //najpierw wywołana metoda tylko generateMainWeatherClasses,
    // żeby potem móc działać na klasach i obiektach, potem generowanie PollutionClasses
    public void init() throws IOException {
       /* String cityName = "Warsaw";
        config.generateMainWeatherClasses(cityName,apikey);
        MainObject objectExample = generateObjectFromJson("Warsaw","metric");
        Double lat = objectExample.getCity().getCoord().getLat();
        Double lng = objectExample.getCity().getCoord().getLon();
        config.generatePollutionClasses(lat,lng,apikey);*/
    }

    public String readJsonString(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        String jsonString=null;

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();
            jsonString = response.toString();
        } else {
            System.out.println("HTTP request failed: " + responseCode);
        }
        return jsonString;
    }

    public URL getWeatherJsonFormApi(String cityName, String units) throws MalformedURLException {
        String apiUrl = String.format("https://api.openweathermap.org/data/2.5/forecast?q=%s&appid=%s&units=%s",
                cityName, apiKey, units);
        URL url = new URL(apiUrl);
        return url;
    }

    public URL getPollutionJsonFormApi(String lat, String lng) throws MalformedURLException {
        String apiUrl = String.format("http://api.openweathermap.org/data/2.5/air_pollution/forecast?lat=%s&lon=%s&appid=%s",
                lat,lng,apiKey);
        URL url = new URL(apiUrl);
        return url;
    }
    public MainObject generateObjectFromJson(String cityName, String units ) throws IOException {
        URL url = getWeatherJsonFormApi(cityName,units);
        String jsonString = readJsonString(url);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonString, MainObject.class);
    }

    public MainPollutionObject generatePollutionFromJSON(String lat, String lng) throws IOException {
        URL url = getPollutionJsonFormApi(lat,lng);
        String jsonString = readJsonString(url);
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonString, MainPollutionObject.class);
    }



    public String getObjectWeatherInfo(String cityName, String units, LocalDateTime startdate, LocalDateTime enddate) throws IOException {
        MainObject weatherObject = generateObjectFromJson(cityName,units);
        String lat = String.valueOf(weatherObject.getCity().getCoord().getLat());
        String lng = String.valueOf(weatherObject.getCity().getCoord().getLon());
        MainPollutionObject pollutionObject = generatePollutionFromJSON(lat,lng);

        java.util.List<List> lista = weatherObject.getList();
        java.util.List<PollutionList> listaPollution = pollutionObject.getList();


        java.util.List<LocalDateTime> listaDat = new ArrayList<>();
        StringBuilder infoPogodowe = new StringBuilder();

        String jednostkaT = "";
        if(!(units.equals("metric") || units.equals("standard") || units.equals("imperial"))){
            infoPogodowe.append("<i>"+"Uwaga : Podane jednostki temperatury są w nieprawidłowym formacie. Domyślne jednostki to: Kelvin, m/s."+"<br>"+ "Aby zobaczyć temperaturę w Celsjuszach użyj : metric, w Farenheitach użyj : imperial."+"<br>"+"<br>"+"</i>");
        }
        switch (units){
                case "metric":
                    jednostkaT=" C ";
                    break;
                case "imperial":
                    jednostkaT=" F ";
                    break;
                default: jednostkaT=" K ";
            }


        infoPogodowe.append("<i>"+"Informacje pogodowe w wybranym zakresie czasowym:"+"<br>"+"<br>"+"</i>");

        for(List list: lista){
            Integer wDate = list.getDt();
            Date wDateFixed = new Date(wDate *1000L);
            LocalDateTime wlocalDate = wDateFixed.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            listaDat.add(wlocalDate);
            if(wlocalDate.isAfter(startdate) && wlocalDate.isBefore(enddate)){

                String data = ""+wlocalDate;

                infoPogodowe.append("<b>"+"POGODA W DNIU:  "+  data.substring(0,10) +" O GODZINIE:  " + data.substring(11,16)+"</b>");
                infoPogodowe.append("<br>");
                Main main = list.getMain();
                infoPogodowe.append( "<b>"+"<i>"+"  Temperatura: "+"</b>"+"</i>"+"<br>" + " Rzeczywista: "+ main.getTemp() + " " + jednostkaT+"<br>");
                infoPogodowe.append( "<i>" +" Odczuwalna: " +"</i>"+ main.getFeelsLike()+" "+jednostkaT + "<br>");
                infoPogodowe.append( "<b>"+"  Zachmurzenie : "+"<br>"+"</b>"+list.getClouds().getAll() +"%"+"<br>");
                infoPogodowe.append("<b>"+" Widoczność: " +"<br>"+ "</b>" + list.getVisibility()+"m"+"<br>");
                infoPogodowe.append( "<b>"+" Wiatr  : " +"<br>"+"</b>"+"<i>"+ " Prędkość: " + "</i>"+ list.getWind().getSpeed());
                if(units.equals("imperial")){
                    infoPogodowe.append(" miles/h");
                }else{
                    infoPogodowe.append(" m/s");}
                infoPogodowe.append("<br>"+"<i>"+ "Kierunek: " +"</i>"+ list.getWind().getDeg()+ "<br>"+"<i>"+" Porywy: "+"</i>" + list.getWind().getGust());
                if(units.equals("imperial")){
                    infoPogodowe.append(" miles/h");
                }else{
                    infoPogodowe.append(" m/s");}
                infoPogodowe.append("<br>" + "<b>" + "Wilgotność: "+"</b>"+"<br>" + list.getMain().getHumidity()+"%");
                infoPogodowe.append("<b>"+"<br>"+"Jakość powietrza"+"</b>"+"<br>");
                for(PollutionList pl : listaPollution){
                    Integer pDate= pl.getDt();
                    Date pDateFixed = new Date(pDate *1000L);
                    LocalDateTime pDateLocal = pDateFixed.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

                    if(listaDat.contains(pDateLocal)){
                        infoPogodowe.append("<i>"+"Index jakości  : "+"</i>"+ + pl.getMain().getAqi());
                        Integer index = pl.getMain().getAqi();
                        switch (index){
                            case 1:
                                infoPogodowe.append(" (jakość dobra)");
                                break;
                            case 2:
                                infoPogodowe.append(" (jakość w porządku)");
                                break;
                            case 3:
                                infoPogodowe.append(" (jakość średnia)");
                                break;
                            case 4:
                                infoPogodowe.append(" (jakość słaba)");
                                break;
                            case 5:
                                infoPogodowe.append(" (jakość bardzo słaba)");
                        }
                        infoPogodowe.append("<br>"+"<i>"+"Stężenie tlenku węgla (CO) : "+"</i>" + pl.getComponents().getCo()+ "  miligrama na metr sześcienny");
                        infoPogodowe.append("<br>"+"<i>"+ "Stężenie tlenku azotu (NO): " +"</i>"+pl.getComponents().getNo() +" miligrama na metr sześcienny" );
                        infoPogodowe.append("<br>"+"<i>"+"Stężenie dwutlenku azotu (NO2): "+"</i>" + pl.getComponents().getNo2()+ " miligrama na metr sześcienny");
                        infoPogodowe.append("<br>"+"<i>"+"Stężenie ozonu (O3): "+"</i>" + pl.getComponents().getO3()+ " miligrama na metr sześcienny");
                        infoPogodowe.append("<br>"+"<i>"+"Stężenie dwutlenku siarki (SO2): "+"</i>" + pl.getComponents().getSo2()+ " miligrama na metr sześcienny");
                        infoPogodowe.append("<br>"+"<i>"+"Stężenie pyłków zawieszonych: "+"</i>" + pl.getComponents().getPm25()+ " miligrama na metr sześcienny");
                        infoPogodowe.append("<br>"+"<i>"+"Stężenie amoniaku (NH3): "+"</i>" + pl.getComponents().getNh3()+ " miligrama na metr sześcienny");
                        listaDat.remove(pDateLocal);
                    }
                }
                infoPogodowe.append( "<br>"+"<br>");

            }}








        return "<b>"+"SELECTED CITY :  " + cityName+"</b>" + "<br>"+ "<br>" +infoPogodowe.toString() + "<br>"+"<br>"+ "Informacje specjalistyczne:"+"<br>" ;
    }


}
