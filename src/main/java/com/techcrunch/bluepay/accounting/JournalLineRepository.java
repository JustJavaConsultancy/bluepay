package com.techcrunch.bluepay.accounting;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JournalLineRepository extends JpaRepository<JournalLine, Long> {
}