package com.demo.hire_tracker.controller;


import com.demo.hire_tracker.entity.JobApplication;
import com.demo.hire_tracker.entity.User;
import com.demo.hire_tracker.repository.UserRepository;
import com.demo.hire_tracker.service.JobApplicationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/applications")
@CrossOrigin(origins = "http://localhost:5173")
public class JobApplicationController {

    @Autowired
    private JobApplicationService jobApplicationService;

    @Autowired
    private UserRepository userRepository;

    private User getLoggedInUser(UserDetails userDetails) {
        return userRepository.findByEmail(userDetails.getUsername()).get();
    }


    @PostMapping
    public JobApplication saveData(@Valid @RequestBody JobApplication myEntry,
                                   @AuthenticationPrincipal UserDetails userDetails){
        User user = getLoggedInUser(userDetails);
        return jobApplicationService.addJobAppliton(myEntry , user);
    }

    @GetMapping
    public List<JobApplication> showdata(@AuthenticationPrincipal UserDetails userDetails){
        User user = getLoggedInUser(userDetails);
        return jobApplicationService.showApplication(user);
    }

    @DeleteMapping("/{id}")
    public void jobApplication(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails){
        User user = getLoggedInUser(userDetails);
        jobApplicationService.deleteData(id, user);
    }

    @PutMapping("/{id}")
    public boolean jobApplication(@PathVariable Long id,@Valid @RequestBody JobApplication myEntry , @AuthenticationPrincipal UserDetails userDetails){
        User user = getLoggedInUser(userDetails);
        jobApplicationService.updateData(id,myEntry,user);
    return true;
    }

}
