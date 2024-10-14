package com.johnny.gamesales.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class TotalSalesDTO implements Serializable{

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
