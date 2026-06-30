package com.demo.hire_tracker.repository;

import com.demo.hire_tracker.entity.JobApplication;
import com.demo.hire_tracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JobApplicationRepository extends JpaRepository<JobApplication , Long> {

    List<JobApplication> findByUser(User user);

    Optional<JobApplication> findByIdAndUser(Long id, User user);



}
