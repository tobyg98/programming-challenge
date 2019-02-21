package au.com.workingmouse.challenge;

import au.com.workingmouse.challenge.config.Configuration;
import au.com.workingmouse.challenge.models.VelocityAndDirectionData;
import au.com.workingmouse.challenge.services.HttpsClient;
import au.com.workingmouse.challenge.services.VelocityAndDirectionService;
import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class);
    protected static final String OUTPUT_FILENAME = "index.html";
    // Change argument to 'limit' to set the number of rows returned by the query
    private static final String API_URL = "https://data.qld.gov.au/api/action/datastore_search" +
            "?resource_id=0ca6f77c-4088-4d77-8c88-beae2b57ce14&limit=10000";

    public static void main(String[] args) throws IOException {
        try {
            Configuration.load();

            var lines = loadAndParseFile();
            String html = VelocityAndDirectionService.summerise(lines);

            writeFile(html);

        } catch (Exception e) {
            LOGGER.error("Failed to run application", e);
        }
    }

    protected static List<VelocityAndDirectionData> loadAndParseFile() throws IOException {
        // FileService fileService = new FileService();
        // List<String> lines = fileService.readLines(Configuration.getImportFile());

        try {
            Stream<String> queryResults = HttpsClient.request(API_URL).lines();
            List<String> queryList = queryResults.collect(Collectors.toList());

            String response = queryList.get(0);
            List<List<String>> apiLines = VelocityAndDirectionService.parseApiResponse(response);

            return VelocityAndDirectionService.parseApiLines(apiLines);
        } catch (Exception e) {
            System.out.println("LOAD AND PARSE FAILED WITH EXCEPTION: " + e);
            e.printStackTrace();
            return null;
        }
    }

    protected static void writeFile(String html) throws IOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(OUTPUT_FILENAME))) {
            bufferedWriter.write(html);
        }
    }
}