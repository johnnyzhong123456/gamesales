package com.johnny.gamesales.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.johnny.gamesales.DTO.SalesSummary;
import com.johnny.gamesales.DTO.TotalSalesDTO;
import com.johnny.gamesales.entity.GameSale;
import com.johnny.gamesales.repository.GameSaleRepository;

@Service
public class GameSaleService {
	
	private static final Logger logger = LoggerFactory.getLogger(GameSaleService.class);
	
	@Autowired
	private GameSaleRepository gameSaleRepository;

	private static final int BATCH_SIZE = 3000;
	private static final int THREAD_COUNT = 50;

	@Autowired
	private DataSource dataSource;
	
	public boolean importGameSales(InputStream fileInputStream) {
		List<String> errors = new ArrayList<String>();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream))) {
			String line;
			List<GameSale> gameSalesBatch = new ArrayList<>();
			reader.readLine(); // Skip header
			ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
			List<Future<Void>> futures = new ArrayList<>();

			while ((line = reader.readLine()) != null) {
				try {
					String[] fields = line.split(",");
					GameSale gameSale = new GameSale();
					gameSale.setGameNo(Integer.parseInt(fields[0]));
					gameSale.setGameName(fields[1]);
					gameSale.setGameCode(fields[2]);
					gameSale.setType(Integer.parseInt(fields[3]));
					gameSale.setCostPrice(new BigDecimal(fields[4]));
					gameSale.setSalePrice(new BigDecimal(fields[5]));
					gameSale.setDateOfSale(Timestamp.valueOf(fields[6]));
					gameSale.setTax(new BigDecimal(9));

					gameSalesBatch.add(gameSale);

					if (gameSalesBatch.size() >= BATCH_SIZE) {
						List<GameSale> batchToInsert = new ArrayList<>(gameSalesBatch);
						futures.add(executor.submit(() -> {
							saveBatchToDatabase(batchToInsert);
							return null;
						}));
						gameSalesBatch.clear();
					}
				} catch (Exception e) {
					logger.error("Error processing line: " + line + " - " + e.getMessage());
					errors.add("Error processing record: " + String.join(", ", line) + " - " + e.getMessage());
				}
			}
			// Save any remaining records
			if (!gameSalesBatch.isEmpty()) {
				futures.add(executor.submit(() -> {
					saveBatchToDatabase(new ArrayList<>(gameSalesBatch));
					return null;
				}));
			}

			// gracefully shutdown
			executor.shutdown();
			try {
				//force to shutdown if waiting 1 hours
				if (!executor.awaitTermination(1, TimeUnit.HOURS)) {
					executor.shutdownNow();
				}
			} catch (InterruptedException e) {
				executor.shutdownNow();
				Thread.currentThread().interrupt();
			}
			
			if (errors.size() > 0) {
				return false;
			}
			
		} catch (IOException e) {
			logger.error("Error occurred: " + e.getMessage());
			errors.add("Error processing record: "  + e.getMessage());

		}
		return true;
	}

    // bulk insert & disable auto commit to improve performance
	private void saveBatchToDatabase(List<GameSale> batchToInsert) {
		System.out.println("Hello . johnny i am here common!");
		try (Connection connection = dataSource.getConnection()) {
			connection.setAutoCommit(false); // Disable auto-commit for better performance
			StringBuilder sqlBuilder = new StringBuilder(
					"INSERT INTO game_sales (game_no, game_name, game_code, type, cost_price, sale_price, date_of_sale, tax) VALUES ");
			for (int i = 0; i < batchToInsert.size(); i++) {
				sqlBuilder.append("(?, ?, ?, ?, ?, ?, ?, ?)");
				if (i < batchToInsert.size() - 1) {
					sqlBuilder.append(", ");
				}
			}
			try (PreparedStatement ps = connection.prepareStatement(sqlBuilder.toString())) {
				int index = 1;
				for (GameSale sale : batchToInsert) {
					ps.setInt(index++, sale.getGameNo());
					ps.setString(index++, sale.getGameName());
					ps.setString(index++, sale.getGameCode());
					ps.setInt(index++, sale.getType());
					ps.setBigDecimal(index++, sale.getCostPrice());
					ps.setBigDecimal(index++, sale.getSalePrice());
					ps.setTimestamp(index++, sale.getDateOfSale());
					ps.setBigDecimal(index++, sale.getTax());
				}
				ps.executeUpdate();
				connection.commit(); // Commit transaction
				logger.debug("Saved batch of size: " + batchToInsert.size());
				
			} catch (SQLException e) {
				connection.rollback(); // Rollback on error
				logger.error("roll back the transaction: " + e.getMessage());
			}
		} catch (Exception e) {
			logger.error("Error getting connection: " + e.getMessage());
		}
	}

	 // Method to get all game sales with caching
    @Cacheable(value = "gameSales", key = "'all-' + #pageable.pageNumber")
	public Page<GameSale> getAllGameSales(Pageable pageable) {
		return gameSaleRepository.findAll(pageable);
	}
    // Method to get game sales by date range with caching
    @Cacheable(value = "gameSalesByDate", key = "'dateRange-' + #fromDate + '-' + #toDate + '-' + #pageable.pageNumber")
    public Page<GameSale> getGameSalesByDateRange(Timestamp fromDate, Timestamp toDate, Pageable pageable) {
        return gameSaleRepository.findByDateOfSaleBetween(fromDate, toDate, pageable);
    }

    // Method to get game sales by price less than a specified amount with caching
    @Cacheable(value = "gameSalesByPrice", key = "'less-' + #price + '-' + #pageable.pageNumber")
    public Page<GameSale> getGameSalesByPriceLessThan(BigDecimal price, Pageable pageable) {
        return gameSaleRepository.findBySalePriceLessThan(price, pageable);
    }

    // Method to get game sales by price greater than a specified amount with caching
    @Cacheable(value = "gameSalesByPrice", key = "'greater-' + #price + '-' + #pageable.pageNumber")
    public Page<GameSale> getGameSalesByPriceGreaterThan(BigDecimal price, Pageable pageable) {
        return gameSaleRepository.findBySalePriceGreaterThan(price, pageable);
    }
    
    @Cacheable(value = "totalSalesCache", key = "#fromDate.toString() + '-' + #toDate.toString() + '-' + (#gameNo != null ? #gameNo : 'all')")
    public SalesSummary getTotalSales(LocalDateTime fromDate, LocalDateTime toDate, Integer gameNo) {
        TotalSalesDTO totalSalesData = gameSaleRepository.findTotalSales(fromDate, toDate);
        BigDecimal totalSales = totalSalesData.getTotalSalesPrice() != null ? totalSalesData.getTotalSalesPrice() : BigDecimal.ZERO;
        Long totalGamesSold = totalSalesData.getTotalGamesSold() != null ? totalSalesData.getTotalGamesSold() : 0L;
        BigDecimal totalSalesByGame = BigDecimal.ZERO;
        if (gameNo != null) {
            totalSalesByGame = gameSaleRepository.findTotalSalesByGame(fromDate, toDate, gameNo);
        }
        return new SalesSummary(totalGamesSold.intValue(), totalSales, gameNo != null ? gameNo.toString() : null, totalSalesByGame);
    }
    
    
    
 
    
}