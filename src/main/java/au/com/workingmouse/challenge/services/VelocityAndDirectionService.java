package au.com.workingmouse.challenge.services;

import au.com.workingmouse.challenge.models.VelocityAndDirectionData;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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

    public static String summerise(List<VelocityAndDirectionData> velocityAndDirectionDataset) {
        Integer totalLines = velocityAndDirectionDataset.size();

        var summaryBuilder = new StringBuilder() ;

        summaryBuilder.append("<head></head>")
                .append("<body>")
                .append("<h2>Summary</h2>")
                .append("<br />")
                .append("<strong>Total Lines:</strong>" + totalLines.toString())
                .append("</body>");

        return summaryBuilder.toString();
    }
}
