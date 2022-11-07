package com.bysykkelapp;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.logging.Level;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;

/**
 * This class provides the functionality to consume.
 * Oslo city bysykkel API and display a list of stations
 * with station names, capacity, number of bikes available and number of docs available fields.
 */

public final class App {
    private App() {
    }

    /**
     * Runs the methods needed for application to work.
     * @param args The arguments of the program.
     */

    public static void main(String[] args) {

        Logger logger = Logger.getAnonymousLogger();
        ArrayList<Station> allStations = new ArrayList<>();
        String stationsInfoUrl = "https://gbfs.urbansharing.com/oslobysykkel.no/station_information.json";
        String stationAvailabilityInfoUrl = "https://gbfs.urbansharing.com/oslobysykkel.no/station_status.json";
        String myAppIdToken = "jovand-myapp";
        String responseWithStationsInfo = "";
        String responseWithStationsAvailabilityInfo = "";

        responseWithStationsInfo = getResponseString(logger, stationsInfoUrl, myAppIdToken, responseWithStationsInfo);

        responseWithStationsAvailabilityInfo = getResponseString(logger, stationAvailabilityInfoUrl, myAppIdToken, responseWithStationsAvailabilityInfo);


        if (responseWithStationsInfo.isEmpty() && responseWithStationsAvailabilityInfo.isEmpty()) {
            System.out.println("Response strings are empty.");
        } else {

            JSONArray stationsInfoArr = extractStationsData(responseWithStationsInfo);
            JSONArray stationsAvailabilityInfoArr = extractStationsData(responseWithStationsAvailabilityInfo);

            addStationsToArray(stationsInfoArr, allStations);
            addStationsAvailabilityData(stationsAvailabilityInfoArr, allStations);
        }


        prettyPrint(allStations);

    }

    /**
     * Fetches data from Oslo city bysykkel API by calling fetchDataFromApi function
     * handling the exceptions.
     * @param logger pointer to logger instance
     * @param stationsInfoUrl url to station info API
     * @param myAppIdToken identification string required by API
     * @param response initial empty string which will get the response
     * @return response data response from API.
     */

    private static String getResponseString(Logger logger, String stationsInfoUrl, String myAppIdToken, String response) {
        try {
            response = fetchDataFromApi(stationsInfoUrl, myAppIdToken);
        } catch (MalformedURLException | UnknownHostException e) {
            logger.log(Level.SEVERE, "an exception was thrown", e);
        } catch (IOException io) {
            logger.log(Level.SEVERE, "an exception was thrown", io);
        }
        return response;
    }

    /**
    * Fetches data from Oslo city bysykkel API.
    * @param urlAddress url address to the API
    * @param idToken identification string requested by API doc
    * @return informationString data response from API.
    * @throws MalformedURLException if wrong http address is used
    * @throws UnknownHostException if it cant access the server
    * @throws IOException if Scanner fail,
    */

    public static String fetchDataFromApi(String urlAddress, String idToken)
            throws MalformedURLException, UnknownHostException, IOException {
        StringBuilder informationString = new StringBuilder();
        URL url = new URL(urlAddress);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Client-Identifier", idToken);
        connection.connect();

        // Check if connect is made
        int responseCode = connection.getResponseCode();

        // 200 not OK
        if (responseCode != 200) {
            throw new RuntimeException("HttpResponseCode: " + responseCode);
        } else { // 200 OK
            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()) {
                informationString.append(scanner.nextLine());
            }
            scanner.close();
        }
        return informationString.toString();

    }

    /**
    * Extracts only stations array data from the API response string.
    * @param response API response in the form of String
    * @return JSONArray holding the data for every station.
    */

    public static JSONArray extractStationsData(String response) {
        JSONObject jsonResult = new JSONObject(response);
        JSONObject jsonResultData = jsonResult.getJSONObject("data");
        return jsonResultData.getJSONArray("stations");
    }

    /**
    * Goes through the array with station data and adds the Station objects to
    * the stations list.
    * @param jsonArray holding the data for every station.
    * @param stations the list holding the extracted station data from the API
    */

    public static void addStationsToArray(JSONArray jsonArray, ArrayList<Station> stations) {
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjects = jsonArray.optJSONObject(i);
                String stId = jsonObjects.optString("station_id");
                String name = jsonObjects.optString("name");
                String capacity = jsonObjects.optString("capacity");

                int id = Integer.parseInt(stId);
                int cap = Integer.parseInt(capacity);

                stations.add(new Station(id, name, cap));

            }
        }
    }
    /**
    * Goes through the array with station availability data and adds the number of bikes and docs available.
    * to the Station objects based on the matching id.
    * @param jsonArray holding the additional data for every station.
    * @param stations the list holding the extracted station data from the API.
    */

    public static void addStationsAvailabilityData(JSONArray jsonArray, ArrayList<Station> stations) {
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjects = jsonArray.optJSONObject(i);
                String stId = jsonObjects.optString("station_id");
                String numOfBikesAvailable = jsonObjects.optString("num_bikes_available");
                String numOfDocs = jsonObjects.optString("num_docks_available");

                int id = Integer.parseInt(stId);
                int numOfBikes = Integer.parseInt(numOfBikesAvailable);
                int numOfDocsInt = Integer.parseInt(numOfDocs);

                for (Station s : stations) {
                    if (s.getId() == id) {
                        s.setBikesAndDocs(numOfBikes, numOfDocsInt);
                        break;
                    }
                }

            }
        }
    }

    /**
    * Prints the formatted result list.
    * @param stations the list holding the extracted station data from the API
    */

    public static void prettyPrint(ArrayList<Station> stations){
        System.out.format("%10s%32s%15s%28s%27s", "STATION ID", "STATION NAME", "Capacity", "Number of bikes available", "Number of docs available");
        System.out.println();
        int count = 0;
        for (Station station : stations) {
            count++;
            System.out.println(station);
        }
        System.out.println("Total rows: " + count);
    }
}


