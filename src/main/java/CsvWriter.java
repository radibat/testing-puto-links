import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CsvWriter {
    private static String fileName = "src/test/resources/socialLinks.csv";
    private static Logger log = LogManager.getLogger(CsvWriter.class);
    public static void writeCSV(List<String[]> csvData) throws IOException {

        try (CSVWriter writer = new CSVWriter(new FileWriter(fileName))) {
            writer.writeAll(csvData);
            log.info("Writer " + csvData.size() + " sections to file " + fileName);
        }
        catch (IOException e)
        {
            log.error(e.getMessage());
        }

    }

}
