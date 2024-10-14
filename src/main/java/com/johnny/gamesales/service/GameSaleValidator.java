package com.johnny.gamesales.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.johnny.gamesales.entity.GameSale;

public class GameSaleValidator {
	
	public List<String> validateGameSales(List<GameSale> gameSales) {
		
	   List<String> validationErrors = new ArrayList<String>();
		
	   for (GameSale gameSale: gameSales) {
		// Validate game_no
           if (gameSale.getGameNo() < 1 || gameSale.getGameNo() > 100) {
               validationErrors.add("Invalid game_no for game: " + gameSale.getGameName());
           }
           // Validate game_name length
           if (gameSale.getGameName().length() > 20) {
               validationErrors.add("Game name exceeds 20 characters for game: " + gameSale.getGameName());
           }
           // Validate game_code length
           if (gameSale.getGameCode().length() > 5) {
               validationErrors.add("Game code exceeds 5 characters for game: " + gameSale.getGameName());
           }
           // Validate type
           if (gameSale.getType() < 1 || gameSale.getType() > 2) {
               validationErrors.add("Invalid type for game: " + gameSale.getGameName());
           }
           // Validate cost_price
           if (gameSale.getCostPrice().compareTo(new BigDecimal(100)) > 0) {
               validationErrors.add("Cost price exceeds 100 for game: " + gameSale.getGameName());
           }
           // Validate date_of_sale
           if (gameSale.getDateOfSale() == null) {
               validationErrors.add("Date of sale is required for game: " + gameSale.getGameName());
           }
	   }
	   
	   return validationErrors;
	}

}
