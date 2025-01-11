package com.clinicManagement.ClinicManagement.repository;


import com.clinicManagement.ClinicManagement.model.SearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {
    List<SearchHistory> findByUserUserId(Long userId);
    List<SearchHistory> findBySearchQueryContaining(String query);
}
