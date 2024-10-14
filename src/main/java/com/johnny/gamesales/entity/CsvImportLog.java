package com.johnny.gamesales.entity;

import java.sql.Timestamp;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "csv_import_log")
public class CsvImportLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer importId;
    private Timestamp importDate;
    private String fileName;
    private Integer totalRecords;
    private Integer successfulImports;
    private Integer failedImports;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "importLog", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CsvImportError> errors; // List to hold related errors

    public enum Status {
        PENDING,
        IN_PROGRESS,
        COMPLETED,
        FAILED
    }

	public Integer getImportId() {
		return importId;
	}

	public void setImportId(Integer importId) {
		this.importId = importId;
	}

	public Timestamp getImportDate() {
		return importDate;
	}

	public void setImportDate(Timestamp importDate) {
		this.importDate = importDate;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Integer getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(Integer totalRecords) {
		this.totalRecords = totalRecords;
	}

	public Integer getSuccessfulImports() {
		return successfulImports;
	}

	public void setSuccessfulImports(Integer successfulImports) {
		this.successfulImports = successfulImports;
	}

	public Integer getFailedImports() {
		return failedImports;
	}

	public void setFailedImports(Integer failedImports) {
		this.failedImports = failedImports;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List<CsvImportError> getErrors() {
		return errors;
	}

	public void setErrors(List<CsvImportError> errors) {
		this.errors = errors;
	}

}
