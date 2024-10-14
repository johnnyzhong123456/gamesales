package com.johnny.gamesales.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class SalesSummary implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int totalGamesSold;
	private BigDecimal totalSales;
	private String gameNo;
	private BigDecimal totalSalesByGame;

	public SalesSummary(int totalGamesSold, BigDecimal totalSales, String gameNo, BigDecimal totalSalesByGame) {
		this.totalGamesSold = totalGamesSold;
		this.totalSales = totalSales;
		this.gameNo = gameNo;
		this.totalSalesByGame = totalSalesByGame;
	}

	public int getTotalGamesSold() {
		return totalGamesSold;
	}

	public void setTotalGamesSold(int totalGamesSold) {
		this.totalGamesSold = totalGamesSold;
	}

	public BigDecimal getTotalSales() {
		return totalSales;
	}

	public void setTotalSales(BigDecimal totalSales) {
		this.totalSales = totalSales;
	}

	public String getGameNo() {
		return gameNo;
	}

	public void setGameNo(String gameNo) {
		this.gameNo = gameNo;
	}

	public BigDecimal getTotalSalesByGame() {
		return totalSalesByGame;
	}

	public void setTotalSalesByGame(BigDecimal totalSalesByGame) {
		this.totalSalesByGame = totalSalesByGame;
	}

}
