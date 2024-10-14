package com.johnny.gamesales.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.johnny.gamesales.DTO.SalesSummary;
import com.johnny.gamesales.entity.GameSale;
import com.johnny.gamesales.service.GameSaleService;

@RestController
@RequestMapping("/api")
public class GameSaleController {

	private static final Logger logger = LoggerFactory.getLogger(GameSaleController.class);

	@Autowired
	private GameSaleService gameSaleService;

	@PostMapping("/import")
	public ResponseEntity<String> importGameSales(@RequestParam("file") MultipartFile file) {
		try {
			logger.debug("filename: "+file.getName());
			if (file.isEmpty()) {
				logger.warn("The file is empty");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is empty");
			} 
			boolean isImportSuccess = gameSaleService.importGameSales(file.getInputStream());
			if (!isImportSuccess) {
				return ResponseEntity.ok("Import Failed.");
			}
			logger.info("Import successfully.");
			return ResponseEntity.ok("Import successfully.");
		} catch (IOException e) {
			logger.error("Error processing file: ", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing the file");
		}
	}

	@GetMapping("/getGameSales")
	public ResponseEntity<List<GameSale>> getGameSales(@RequestParam(required = false) Timestamp fromDate,
			@RequestParam(required = false) Timestamp toDate, @RequestParam(required = false) BigDecimal salePrice,
			@RequestParam(required = false) String comparison, // "less" or "greater"
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "100") int size) {

		Pageable pageable = PageRequest.of(page, size);
		List<GameSale> gameSales = null;

		if (fromDate != null && toDate != null) {
			if (!StringUtils.isEmpty(comparison) && salePrice != null) {
				if ("less".equalsIgnoreCase(comparison)) {
					gameSales = gameSaleService.getGameSalesByPriceLessThan(salePrice, pageable).getContent();
				} else if ("greater".equalsIgnoreCase(comparison)) {
					gameSales = gameSaleService.getGameSalesByPriceGreaterThan(salePrice, pageable).getContent();
				}
			} else {
				gameSales = gameSaleService.getGameSalesByDateRange(fromDate, toDate, pageable).getContent();
			}
		} else {
			gameSales = gameSaleService.getAllGameSales(pageable).getContent();
		}
		return ResponseEntity.ok(gameSales);
	}

	@GetMapping("/getTotalSales")
	public ResponseEntity<SalesSummary> getTotalSales(@RequestParam("fromDate") String fromDateStr,
			@RequestParam("toDate") String toDateStr,
			@RequestParam(value = "gameNo", required = false) Integer gameNo) {

		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
			LocalDateTime fromDate = LocalDateTime.parse(fromDateStr, formatter);
			LocalDateTime toDate = LocalDateTime.parse(toDateStr, formatter);

			SalesSummary summary = gameSaleService.getTotalSales(fromDate, toDate, gameNo);
			return ResponseEntity.ok(summary);
		} catch (Exception e) {
			logger.error("Error processing file: ", e);
			return ResponseEntity.badRequest().body(null);
		}
	}
}
