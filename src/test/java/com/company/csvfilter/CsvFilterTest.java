package com.company.csvfilter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static java.nio.charset.StandardCharsets.UTF_8;

import static org.junit.Assert.assertEquals;

/**
 * Created by cristian.calugaru on 06/03/2017.
 */
public class CsvFilterTest {

    CsvFilter csvFilter;

    @Before
    public void setup() {
        csvFilter = new CsvFilter();
    }

    @Test
    public void testCsvFilter() throws IOException {

        String outputPath = "output.txt";

        csvFilter.filter(
                        Paths.get("src/test/resources/file1.csv").toUri(),
                        Paths.get("src/test/resources/file2.csv").toUri(),
                        Paths.get(outputPath).toUri());

        List<String> resultLines = Files.readAllLines(Paths.get(outputPath), UTF_8);

        assertEquals(9, resultLines.size());
        assertEquals(resultLines.get(0), "SOURCE,ID,TIMESTAMP,TYPE,VALUE,COMMISSION");
        assertEquals(resultLines.get(1), "ABC,923757023,2017-01-12T13:24:13,TYPEA,1200000.00,12000.00");
        assertEquals(resultLines.get(2), "XYZ,956808949,2017-01-12T13:28:56,TYPEB,450000.00,2000.00");
        assertEquals(resultLines.get(3), "LMN,748034958,2017-01-12T14:02:18,TYPEA,25500000.00,95000.00");
        assertEquals(resultLines.get(4), "DEF,693490394,2017-01-12T15:21:14,TYPEC,10000.00,500.00");
        assertEquals(resultLines.get(5), "STU,290359876,2017-01-12T16:10:42,TYPED,380000.00,4300.00");
        assertEquals(resultLines.get(6), "ABC,923757023,2017-01-12T16:11:09,TYPEC,2400000.00,24000.00");
        assertEquals(resultLines.get(7), "LMN,748034959,2017-01-12T16:28:16,TYPEA,500000.00,5000.00");
        assertEquals(resultLines.get(8), "XYZ,290359876,2017-01-12T16:31:24,TYPED,630000.00,800.00");

        Files.delete(Paths.get(outputPath));
    }

}
