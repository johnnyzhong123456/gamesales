package com.johnny.gamesales.repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.johnny.gamesales.DTO.TotalSalesDTO;
import com.johnny.gamesales.entity.GameSale;

public interface GameSaleRepository extends JpaRepository<GameSale, Long> {

	// using the naming convention
	Page<GameSale> findByDateOfSaleBetween(Timestamp fromDate, Timestamp toDate, Pageable pageable);

	Page<GameSale> findBySalePriceLessThan(BigDecimal price, Pageable pageable);

	Page<GameSale> findBySalePriceGreaterThan(BigDecimal price, Pageable pageable);

	@Query("SELECT new com.johnny.gamesales.DTO.TotalSalesDTO(SUM(g.salePrice), COUNT(g.id)) "
	         + "FROM GameSale g "
	         + "WHERE g.dateOfSale BETWEEN :fromDate AND :toDate")
	    TotalSalesDTO findTotalSales(@Param("fromDate") LocalDateTime fromDate, @Param("toDate") LocalDateTime toDate);
	
	 @Query("SELECT SUM(g.salePrice) " +
	           "FROM GameSale g " +
	           "WHERE g.dateOfSale BETWEEN :fromDate AND :toDate AND g.gameNo = :gameNo")
	    BigDecimal findTotalSalesByGame(@Param("fromDate") LocalDateTime fromDate, 
	                                     @Param("toDate") LocalDateTime toDate, 
	                                     @Param("gameNo") Integer gameNo);
}
