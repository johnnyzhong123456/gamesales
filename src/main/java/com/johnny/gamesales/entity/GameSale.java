package com.johnny.gamesales.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

@Entity
@Table(name = "game_sales", // Specify your table name here
indexes = {
        @Index(name = "idx_sale_date", columnList = "dateOfSale"),
        @Index(name = "idx_sale_price", columnList = "salePrice"),
        @Index(name = "idx_date_game", columnList = "dateOfSale, gameNo")
    })
public class GameSale implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Integer gameNo;

	@Column(length = 20, nullable = false)
	private String gameName;

	@Column(length = 5, nullable = false)
	private String gameCode;

	@Column(nullable = true)
	private Integer type;

	@Column(nullable = true)
	private BigDecimal costPrice;

	@Column(nullable = false)
	private BigDecimal tax;

	@Column(nullable = true)
	private BigDecimal salePrice;

	@Column(nullable = false)
	private Timestamp dateOfSale;

	public GameSale() {
		super();
	}

	public GameSale(Integer gameNo, String gameName, String gameCode, Integer type, BigDecimal costPrice,
			BigDecimal tax, BigDecimal salePrice, Timestamp dateOfSale) {
		super();
		this.gameNo = gameNo;
		this.gameName = gameName;
		this.gameCode = gameCode;
		this.type = type;
		this.costPrice = costPrice;
		this.tax = tax;
		this.salePrice = salePrice;
		this.dateOfSale = dateOfSale;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getGameNo() {
		return gameNo;
	}

	public void setGameNo(Integer gameNo) {
		this.gameNo = gameNo;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public String getGameCode() {
		return gameCode;
	}

	public void setGameCode(String gameCode) {
		this.gameCode = gameCode;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public BigDecimal getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public Timestamp getDateOfSale() {
		return dateOfSale;
	}

	public void setDateOfSale(Timestamp dateOfSale) {
		this.dateOfSale = dateOfSale;
	}

	public BigDecimal getTax() {
		return tax;
	}

	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}

}
