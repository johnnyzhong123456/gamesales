package com.johnny.gamesales;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart; // For file upload
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.johnny.gamesales.controller.GameSaleController;
import com.johnny.gamesales.service.GameSaleService;
import com.johnny.util.CsvUtil;

@SpringBootTest
@AutoConfigureMockMvc
class GamesalesApplicationTests {

	private static final String CSV_FILE_NAME = "game_sales1.csv";

	@Autowired
	private MockMvc mockMvc;

	// Generate the csv
	@Test
	void genearteCSV() throws IOException {
		CsvUtil.generateCsv(CSV_FILE_NAME, 1000000);
	}

	@Mock
	private GameSaleService gameSaleService;

	@InjectMocks
	private GameSaleController gameSaleController;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testImportGameSales_Success() throws Exception {
//		// Arrange
		String csvContent = "id,game_no,game_name,game_code,type,cost_price,tax,sale_price,date_of_sale\n"
				+ "1,10,Game A,GAMEA,1,50.00,0.09,54.50,2023-01-01T00:00:00\n";
		// Mock the service method
//		doNothing().when(gameSaleService).importGameSales(any());
		// Act & Assert
		mockMvc.perform(
				multipart("/api/import").file("file", csvContent.getBytes()).contentType(MediaType.MULTIPART_FORM_DATA))
				.andExpect(status().isOk());
	}

	@Test
	void testImportGameSales_EmptyFile() throws Exception {
		// Act & Assert
		mockMvc.perform(multipart("/api/import").file("file", new byte[0]) // Empty file
				.contentType(MediaType.MULTIPART_FORM_DATA)).andExpect(status().isBadRequest())
				.andExpect(result -> result.getResponse().getContentAsString().equals("File is empty"));
	}

}
