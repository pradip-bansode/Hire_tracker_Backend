package com.demo.hire_tracker.service;

import com.demo.hire_tracker.entity.JobApplication;
import com.demo.hire_tracker.entity.User;

import java.util.List;

public interface JobApplicationService {

    JobApplication addJobAppliton(JobApplication jobApplication, User user);

    List<JobApplication> showApplication(User user);

    void deleteData(Long id , User user);

    JobApplication updateData(Long id, JobApplication jobApplication , User user);


}
