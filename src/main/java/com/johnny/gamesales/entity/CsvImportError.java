package com.johnny.gamesales.entity;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "csv_import_errors")
public class CsvImportError {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer errorId;
    private Integer gameNo;   // Game number for the record that failed
    private String errorMessage;
    private Timestamp createdAt;

    @ManyToOne
    @JoinColumn(name = "import_id", nullable = false) // Foreign key to CsvImportLog
    private CsvImportLog importLog; // Associating the error with the import log

    public CsvImportError(CsvImportLog importLog, Integer gameNo, String errorMessage) {
        this.importLog = importLog;
        this.gameNo = gameNo;
        this.errorMessage = errorMessage;
    }

	public Integer getErrorId() {
		return errorId;
	}

	public void setErrorId(Integer errorId) {
		this.errorId = errorId;
	}

	public Integer getGameNo() {
		return gameNo;
	}

	public void setGameNo(Integer gameNo) {
		this.gameNo = gameNo;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public CsvImportLog getImportLog() {
		return importLog;
	}

	public void setImportLog(CsvImportLog importLog) {
		this.importLog = importLog;
	}
    
}
