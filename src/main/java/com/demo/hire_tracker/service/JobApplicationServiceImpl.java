package com.demo.hire_tracker.service;


import com.demo.hire_tracker.entity.JobApplication;
import com.demo.hire_tracker.entity.User;
import com.demo.hire_tracker.repository.JobApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobApplicationServiceImpl implements JobApplicationService{

    @Autowired
    private JobApplicationRepository repo;


    @Override
    public JobApplication addJobAppliton(JobApplication jobApplication , User user) {
        jobApplication.setUser(user);
        return repo.save(jobApplication);
    }


    @Override
    public List<JobApplication> showApplication(User user) {
        return repo.findByUser(user);
    }

    @Override
    public void deleteData(Long id , User user) {
        JobApplication exisiting = repo.findByIdAndUser(id,user)
                        .orElseThrow(()-> new RuntimeException("apllication nhi mila"));
        repo.delete(exisiting);

    }

    @Override
    public JobApplication updateData(Long id, JobApplication updateData , User user) {
        JobApplication old = repo.findByIdAndUser(id, user).orElseThrow(()->new RuntimeException("Wong key enter" + id));

        old.setCompanyName(updateData.getCompanyName());
        old.setJobTitle(updateData.getJobTitle());
        old.setStatus(updateData.getStatus());
        old.setAppliedDate(updateData.getAppliedDate());
        old.setJobLink(updateData.getJobLink());
        old.setNotes(updateData.getNotes());

        return repo.save(old);
    }


}
