package com.johnny.gamesales.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.johnny.gamesales.entity.CsvImportError;

public interface CsvImportErrorRepository extends JpaRepository<CsvImportError, Integer>{

}
