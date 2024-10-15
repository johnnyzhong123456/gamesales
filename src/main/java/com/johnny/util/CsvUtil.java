package com.johnny.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CsvUtil {
	
	   private static final String[] GAME_NAMES = {"Game A", "Game B", "Game C", "Game D", "Game E"};
	   private static final Logger logger = LoggerFactory.getLogger(CsvUtil.class);

	    public static void generateCsv(String filePath, int numberOfRows) throws IOException {
	        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
	            writer.write("game_no,game_name,game_code,type,cost_price,sale_price,date_of_sale\n");
	            Random random = new Random();
	            for (int i = 1; i <= numberOfRows; i++) {
	                int gameNo = random.nextInt(100) + 1; // Random value between 1 and 100
	                String gameName = GAME_NAMES[random.nextInt(GAME_NAMES.length)];
	                String gameCode = "G" + String.format("%02d", random.nextInt(100)); // Random code
	                int type = random.nextInt(2) + 1; // Random value 1 or 2
	                BigDecimal costPrice = BigDecimal.valueOf(random.nextDouble() * 100)
                            .setScale(2, RoundingMode.HALF_UP); // Use RoundingMode instead
	                BigDecimal salePrice = costPrice.multiply(BigDecimal.valueOf(1.09))
                            .setScale(2, RoundingMode.HALF_UP); // Use RoundingMode instead
	                int day = random.nextInt(30) + 1; // Random date between 1 April and 30 April
	                Timestamp dateOfSale = Timestamp.valueOf(String.format("2024-04-%02d 12:00:00", day));
	                writer.write(String.format("%d,%s,%s,%d,%.2f,%.2f,%s\n",
	                        gameNo, gameName, gameCode, type, costPrice, salePrice, dateOfSale)); // Write to CSV
	            }
	            logger.info("CSV file generated successfully: " + filePath);
	        }
	    }
}
