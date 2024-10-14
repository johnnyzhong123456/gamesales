package com.johnny.gamesales.DTO;

import java.math.BigDecimal;

public class TotalSalesDTO {

	 private BigDecimal totalSalesPrice;
	    private Long totalGamesSold;

	    public TotalSalesDTO(BigDecimal totalSalesPrice, Long totalGamesSold) {
	        this.totalSalesPrice = totalSalesPrice;
	        this.totalGamesSold = totalGamesSold;
	    }

	    // Getters and Setters
	    public BigDecimal getTotalSalesPrice() {
	        return totalSalesPrice;
	    }

	    public void setTotalSalesPrice(BigDecimal totalSalesPrice) {
	        this.totalSalesPrice = totalSalesPrice;
	    }

	    public Long getTotalGamesSold() {
	        return totalGamesSold;
	    }

	    public void setTotalGamesSold(Long totalGamesSold) {
	        this.totalGamesSold = totalGamesSold;
	    }
}
