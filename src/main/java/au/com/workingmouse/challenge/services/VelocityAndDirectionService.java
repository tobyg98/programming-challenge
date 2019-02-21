package au.com.workingmouse.challenge.services;

import au.com.workingmouse.challenge.models.VelocityAndDirectionData;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

public class VelocityAndDirectionService {

    public static VelocityAndDirectionData parseLine(String line) {
        // NOTE: This CSV parsing is not fully inclusive
        String[] parts = line.split(",");

        VelocityAndDirectionData velocityAndDirectionData = new VelocityAndDirectionData();

        velocityAndDirectionData.setTimestamp(Timestamp.valueOf(parts[0]));
        velocityAndDirectionData.setRecord(Integer.parseInt(parts[1]));
        velocityAndDirectionData.setDcsModel(Integer.parseInt(parts[2]));
        velocityAndDirectionData.setDcsSerial(Integer.parseInt(parts[3]));
        velocityAndDirectionData.setDcsAbsspdAvg(Double.parseDouble(parts[4]));
        velocityAndDirectionData.setDcsDirectionAvg(Double.parseDouble(parts[5]));
        velocityAndDirectionData.setDcsNorthCurAvg(Double.parseDouble(parts[6]));
        velocityAndDirectionData.setDcsEastCurAvg(Double.parseDouble(parts[7]));
        velocityAndDirectionData.setDcsHeadingAvg(Double.parseDouble(parts[8]));
        velocityAndDirectionData.setDcsTiltXAvg(Double.parseDouble(parts[9]));
        velocityAndDirectionData.setDcsTiltYAvg(Double.parseDouble(parts[10]));
        velocityAndDirectionData.setDcsSpStdAvg(Double.parseDouble(parts[11]));
        velocityAndDirectionData.setDcsSigStrengthAvg(Double.parseDouble(parts[12]));
        velocityAndDirectionData.setDcsPingCntAvg(Double.parseDouble(parts[13]));
        velocityAndDirectionData.setDcsAbsTiltAvg(Double.parseDouble(parts[14]));
        velocityAndDirectionData.setDscMaxTiltAvg(Double.parseDouble(parts[15]));
        velocityAndDirectionData.setDcsStdTiltAvg(Double.parseDouble(parts[16]));

        return velocityAndDirectionData;
    }

    /**
     * Takes the given ordered list of VelocityAndDirectionData properties and initialises a new
     * VelocityAndDirectionData object.
     * @param line - row to parse
     * @return VelocityAndDirectionData
     */
    public static VelocityAndDirectionData parseLine(List<String> line) {

        if (line.size() != 17) {
            throw new IllegalArgumentException("VelocityAndDirectionData Objects require 17 input arguments.");
        }

        return new VelocityAndDirectionData(
                Timestamp.valueOf(line.get(0)),
                Integer.parseInt(line.get(1)),
                Integer.parseInt(line.get(2)),
                Integer.parseInt(line.get(3)),
                Double.parseDouble(line.get(4)),
                Double.parseDouble(line.get(5)),
                Double.parseDouble(line.get(6)),
                Double.parseDouble(line.get(7)),
                Double.parseDouble(line.get(8)),
                Double.parseDouble(line.get(9)),
                Double.parseDouble(line.get(10)),
                Double.parseDouble(line.get(11)),
                Double.parseDouble(line.get(12)),
                Double.parseDouble(line.get(13)),
                Double.parseDouble(line.get(14)),
                Double.parseDouble(line.get(15)),
                Double.parseDouble(line.get(16))
        );
    }

    public static List<VelocityAndDirectionData> parseLines(List<String> lines) {
        var parsedLines = new ArrayList<VelocityAndDirectionData>();

        int count = 0;
        for (String line : lines) {
            if (count++ == 0) {
                // Skip header
                continue;
            }
            parsedLines.add(VelocityAndDirectionService.parseLine(line));
        }

        return parsedLines;
    }

    /**
     * Parses the given List of rows (where each row defined by a list of the values of each of its properties)
     * and returns a List of VelocityAndDirectionData objects.
     * @param lines - rows to parse
     * @return VelocityAndDirectionData List
     */
    public static List<VelocityAndDirectionData> parseApiLines(List<List<String>> lines) {
        System.out.println(lines);
        var parsedLines = new ArrayList<VelocityAndDirectionData>();

        for (List<String> line : lines) {
            if (line.size() > 0) {
                parsedLines.add(VelocityAndDirectionService.parseLine(line));
            }
        }

        System.out.println(parsedLines);

        return parsedLines;
    }

    /**
     * Parses the body of the response returned from the API call made in the main method.
     * @param response - String representation of the API JSON response
     * @return List of Lists of row properties
     */
    public static List<List<String>> parseApiResponse(String response) {
        // Parse the relevant material from the body of the API response
        String responseRecords = ((response.split("records\": "))[1]).split(", \"limit\":")[0];
        String[] splitRecords = responseRecords.split("}");

        splitRecords[0] = splitRecords[0].replace("[{", "");
        splitRecords[splitRecords.length - 1] = splitRecords[splitRecords.length - 1].replace("]", "");

        for (int i = 1; i < splitRecords.length; i++) {
            splitRecords[i] = splitRecords[i].replace(",{", "");
        }

        // Pull the values from each record field (API response returns the records in the expected order for
        // VelocityAndDirectionData initialisation)
        String[][] recordDataNoNames = new String[splitRecords.length][];
        for (int i = 0; i < splitRecords.length; i++) {
            recordDataNoNames[i] = splitRecords[i].split("\":");
        }

        List<List<String>> parsedRows = new ArrayList<>();
        for (String[] splitRow : recordDataNoNames) {
            List<String> parsedRowProperties = new ArrayList<>();
            // Finish parsing record values
            for (int i = 0; i < splitRow.length; i++) {
                splitRow[i] = splitRow[i].split(",\"")[0];
                // Replace unwanted chars left in TimeStamp Strings
                splitRow[i] = splitRow[i].replace("\"", "");
                splitRow[i] = splitRow[i].replace("T", " ");
            }

            // Add only the relevant properties to the rows property list (ignore ID)
            for (int i = 2; i < splitRow.length; i++) {
                parsedRowProperties.add(splitRow[i]);
            }
            parsedRows.add(parsedRowProperties);
        }
        return parsedRows;
    }

    public static String summerise(List<VelocityAndDirectionData> velocityAndDirectionDataset) {
        Integer totalLines = velocityAndDirectionDataset.size();

        var summaryBuilder = new StringBuilder() ;

        // Transform dataset to be listed in columns rather than rows
        HashMap<String, List<Object>> datasetColumns = buildColumns(velocityAndDirectionDataset);
        // Compute the averages of the set of columns
        HashMap<String, Double> averages = getTableAverages(datasetColumns);

        summaryBuilder.append("<head></head>")
                .append("<body>")
                .append("<h2>Summary</h2>")
                .append("<br />")
                .append("<strong>Total Lines:</strong>" + totalLines.toString())
                .append("<p>");  // Add p element to HTML for displaying averages

        Set<String> averagedVariables = averages.keySet();

        for(String column : averagedVariables) {
            String averageLineDisplay = column + " average: " + averages.get(column).toString();
            summaryBuilder.append(averageLineDisplay)
                    .append("<br />");
        }

        summaryBuilder.append("</p>")
                .append("</body>");

        System.out.println(summaryBuilder.toString());

        return summaryBuilder.toString();
    }


    /**
     * Converts the given list of dataset table rows into a map of dataset table columns.
     * @require velocityAndDirectionDataset != null
     * @param velocityAndDirectionDataset - list of dataset rows
     * @return HashMap {String columnName : ObjectList columnValues}
     */
    public static HashMap<String, List<Object>> buildColumns(
            List<VelocityAndDirectionData> velocityAndDirectionDataset) {

        // Initialise a list to store the names of members of the VelocityAndDirectionData class,
        // i.e. get a list of the names of the columns in the dataset
        // Need an instance of a class to get its list of its members, so use one from the given dataset (any would do)
        Field[] fields = velocityAndDirectionDataset.size() > 0 ?
                velocityAndDirectionDataset.get(0).getClass().getDeclaredFields()
                : new Field[0];

        // Initialise Map of columns
        HashMap<String, List<Object>> columns = new HashMap<>();
        for (Field field : fields) {
            columns.put(field.getName(), new ArrayList<>());
        }

        // Iterate over the rows in the dataset
        for (VelocityAndDirectionData row : velocityAndDirectionDataset) {
            // Iterate over the columns in the row
            for (Field field : fields) {
                try {
                    // Capitalise the first letter of the column name
                    String capitalisedFieldName = field.getName().substring(0, 1).toUpperCase()
                            + field.getName().substring(1);
                    // Method (getter) to invoke
                    String getterName = "get" + capitalisedFieldName;
                    Method func = row.getClass().getMethod(getterName);
                    var result = func.invoke(row);  // Returns Timestamp, Integer or Double given the current members
                    // Add value to column value list
                    columns.get(field.getName()).add(result);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }
        return columns;
    }

    /***
     * Returns a HashMap which maps column names to their averages (aggregated across all rows in the given dataset).
     * @require velocityAndDirectionDataset != null
     * @param columns - A map of columns to average
     * @return HashMap {String columnName : Double columnAverageValue}
     */
    public static HashMap<String, Double> getTableAverages(HashMap<String, List<Object>> columns) {
        Set<String> columnNames = columns.keySet();
        HashMap<String, Double> averages = new HashMap<>();
        // Compute the average value for each column and store in the map of averages
        for (String column : columnNames) {
            // Ignore case where member is Timestamp
            if (column.equals("timestamp")) {
                continue;
            }
            List<Object> fieldValues = columns.get(column);
            Double average = 0.0;
            for (Object value : fieldValues) {
                if (value instanceof Integer) {
                    average += (Integer) value;
                } else if (value instanceof Double) {
                    average += (Double) value;
                }
            }
            average /= fieldValues.size();
            averages.put(column, average);
        }
        return averages;
    }
}
