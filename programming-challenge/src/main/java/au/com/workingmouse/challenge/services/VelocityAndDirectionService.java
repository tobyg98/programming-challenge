package au.com.workingmouse.challenge.services;

import au.com.workingmouse.challenge.models.VelocityAndDirectionData;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.math.BigDecimal;

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


    public static String summarise(List<VelocityAndDirectionData> velocityAndDirectionDataset) {
        Integer totalLines = velocityAndDirectionDataset.size();
        var dataSums = datasetSums(velocityAndDirectionDataset);
        
        //integer variables are converted to doubles so that they can be averaged
        double recordDouble = dataSums.getRecord();
        double dcsModelDouble = dataSums.getDcsModel();
        double dcsSerialDouble = dataSums.getDcsSerial();
        
        		
        var summaryBuilder = new StringBuilder() ;

        // Transform dataset to be listed in columns rather than rows

        summaryBuilder.append("<head></head>")
                .append("<body>")
                .append("<h2>Summary</h2>")
                .append("<br />")
                .append("<strong>Total Lines:</strong>" + totalLines.toString())
                .append("<p>");  // Add p element to HTML for displaying averages

        summaryBuilder.append("<strong>Record Average: </strong>" + Double.toString(recordDouble/totalLines) + "<br>");
        summaryBuilder.append("<strong>DCS Model Average: </strong>" + Double.toString(dcsModelDouble/totalLines) +"<br>");
        summaryBuilder.append("<strong>DCS Serial Average: </strong>" + Double.toString(dcsSerialDouble/totalLines) + "<br>");
        
        summaryBuilder.append("<strong>DCS ABS Speed Average: </strong>" 
        		+ Double.toString(dataSums.getDcsAbsspdAvg()/totalLines) + "<br>");
        summaryBuilder.append("<strong>DCS Direction Average: </strong>" 
				+ Double.toString(dataSums.getDcsDirectionAvg()/totalLines) + "<br>");
        summaryBuilder.append("<strong>DCS North Current Average: </strong>" 
				+ Double.toString(dataSums.getDcsNorthCurAvg()/totalLines) + "<br>");
        summaryBuilder.append("<strong>DCS East Current Average: </strong>" 
				+ Double.toString(dataSums.getDcsEastCurAvg()/totalLines) + "<br>");
        summaryBuilder.append("<strong>DCS Heading Average: </strong>" 
				+ Double.toString(dataSums.getDcsHeadingAvg()/totalLines) + "<br>");
        summaryBuilder.append("<strong>DCS Tilt X Average: </strong>" 
				+ Double.toString(dataSums.getDcsTiltXAvg()/totalLines) + "<br>");
        summaryBuilder.append("<strong>DCS Tilt Y Average: </strong>" 
				+ Double.toString(dataSums.getDcsTiltYAvg()/totalLines) + "<br>");
        summaryBuilder.append("<strong>DCS SP Std Average: </strong>" 
				+ Double.toString(dataSums.getDcsSpStdAvg()/totalLines) + "<br>");
        summaryBuilder.append("<strong>DCS Sig Strength Average: </strong>" 
				+ Double.toString(dataSums.getDcsSigStrengthAvg()/totalLines) + "<br>");
        summaryBuilder.append("<strong>DCS Ping Cnt Average: </strong>" 
				+ Double.toString(dataSums.getDcsPingCntAvg()/totalLines) + "<br>");
        summaryBuilder.append("<strong>DCS Abs Tilt Average: </strong>" 
				+ Double.toString(dataSums.getDcsAbsTiltAvg()/totalLines) + "<br>");
        summaryBuilder.append("<strong>DCS Max Tilt Average: </strong>" 
				+ Double.toString(dataSums.getDscMaxTiltAvg()/totalLines) + "<br>");
        summaryBuilder.append("<strong>DCS Std Tilt Average: </strong>" 
				+ BigDecimal.valueOf(dataSums.getDcsStdTiltAvg()/totalLines) + "<br>");

        summaryBuilder.append("</p>")
                .append("</body>");

        return summaryBuilder.toString();
    }
    
    /**
     * Sums together the individual columns of each "VelocityAndDirectionData" object 
     * for use in determining the average of each column
     * 
     * @param velocityAndDirectionDataset
     * @return VelocityAndDirectionData object with values equal to the sum of all input objects
     */
    static VelocityAndDirectionData datasetSums(List<VelocityAndDirectionData> velocityAndDirectionDataset) {
        Integer totalLines = velocityAndDirectionDataset.size();
    	var dataSums = new VelocityAndDirectionData(
                null, 0, 0, 0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        
        
        for (int i = 0; i < totalLines; i++) {
        	dataSums.setRecord(dataSums.getRecord() + 
        			velocityAndDirectionDataset.get(i).getRecord());
        	dataSums.setDcsModel(dataSums.getDcsModel() + 
        			velocityAndDirectionDataset.get(i).getDcsModel());
        	dataSums.setDcsSerial(dataSums.getDcsSerial() + 
        			velocityAndDirectionDataset.get(i).getDcsSerial());
        	dataSums.setDcsAbsspdAvg(dataSums.getDcsAbsspdAvg() + 
        			velocityAndDirectionDataset.get(i).getDcsAbsspdAvg());
        	dataSums.setDcsDirectionAvg(dataSums.getDcsDirectionAvg() + 
        			velocityAndDirectionDataset.get(i).getDcsDirectionAvg());
        	dataSums.setDcsNorthCurAvg(dataSums.getDcsNorthCurAvg() + 
        			velocityAndDirectionDataset.get(i).getDcsNorthCurAvg());
        	dataSums.setDcsEastCurAvg(dataSums.getDcsEastCurAvg() + 
        			velocityAndDirectionDataset.get(i).getDcsEastCurAvg());
        	dataSums.setDcsHeadingAvg(dataSums.getDcsHeadingAvg() + 
        			velocityAndDirectionDataset.get(i).getDcsHeadingAvg());
        	dataSums.setDcsTiltXAvg(dataSums.getDcsTiltXAvg() + 
        			velocityAndDirectionDataset.get(i).getDcsTiltXAvg());
        	dataSums.setDcsTiltYAvg(dataSums.getDcsTiltYAvg() + 
        			velocityAndDirectionDataset.get(i).getDcsTiltYAvg());
        	dataSums.setDcsSpStdAvg(dataSums.getDcsSpStdAvg() + 
        			velocityAndDirectionDataset.get(i).getDcsSpStdAvg());
        	dataSums.setDcsSigStrengthAvg(dataSums.getDcsSigStrengthAvg() + 
        			velocityAndDirectionDataset.get(i).getDcsSigStrengthAvg());
        	dataSums.setDcsPingCntAvg(dataSums.getDcsPingCntAvg() + 
        			velocityAndDirectionDataset.get(i).getDcsPingCntAvg());
        	dataSums.setDcsAbsTiltAvg(dataSums.getDcsAbsTiltAvg() + 
        			velocityAndDirectionDataset.get(i).getDcsAbsTiltAvg());
        	dataSums.setDscMaxTiltAvg(dataSums.getDscMaxTiltAvg() + 
        			velocityAndDirectionDataset.get(i).getDscMaxTiltAvg());
        	dataSums.setDcsStdTiltAvg(dataSums.getDcsStdTiltAvg() + 
        			velocityAndDirectionDataset.get(i).getDcsStdTiltAvg());
        }
        
        return dataSums;
    }
}
