package com.practice.assignment.repo.hr;

import com.practice.assignment.entities.hr.Office;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfficeRepository extends JpaRepository<Office, Integer> {
    Office findByOfficeId(int officeId);
}
