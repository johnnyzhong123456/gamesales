package com.johnny.gamesales.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.johnny.gamesales.dto.SalesSummary;
import com.johnny.gamesales.entity.GameSale;

public interface GameSaleService {
	public boolean importGameSales(MultipartFile file);
	public Page<GameSale> getAllGameSales(Pageable pageable);
	public Page<GameSale> getGameSalesByDateRange(Timestamp fromDate, Timestamp toDate, Pageable pageable);
	public Page<GameSale> getGameSalesByPriceLessThan(BigDecimal price, Pageable pageable);
	public Page<GameSale> getGameSalesByPriceGreaterThan(BigDecimal price, Pageable pageable);
	public SalesSummary getTotalSales(LocalDateTime fromDate, LocalDateTime toDate, Integer gameNo);
}
