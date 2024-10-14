package com.johnny.gamesales.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.johnny.gamesales.entity.CsvImportLog;

public interface CsvImportLogRepository extends JpaRepository<CsvImportLog, Integer>{

}
