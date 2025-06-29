package com.vikul.lic.controller;

import com.fasterxml.jackson.core.util.BufferRecycler;
import com.vikul.lic.dao.ILicRepo;
import com.vikul.lic.dto.PolicyDto;
import com.vikul.lic.entity.Policy;
import com.vikul.lic.service.LicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/lic/v1")
public class LicController {

    @Autowired
    LicService licService;

    @Autowired
    ILicRepo repo;

    @PostMapping("/upload")
    public ResponseEntity<List<Policy>> uploadExcel(@RequestBody MultipartFile file){
        List<PolicyDto> policyDtoList= licService.addPolicies(file);
        List<Policy> policyList= licService.setPoliciesDetails(policyDtoList);

        return new ResponseEntity<>(policyList, HttpStatus.CREATED);
    }


    @PostMapping("/uploadd")
    public ResponseEntity<List<Policy>> uploadExcell(@RequestBody MultipartFile file){
        String path="c:/jagjeet.xml";
        /*List<PolicyDto> policyDtoList= licService.addPolicies(file);
        List<Policy> policyList= licService.setPoliciesDetails(policyDtoList);*/
        var res=licService.adddPolicies(file);
        var res1=licService.setPoliciesDetailss(res);
        return new ResponseEntity<>(res1, HttpStatus.CREATED);
    }

    @PostMapping("/add")
    public String addUser(@RequestBody PolicyDto policyDto){
        return "";
    }

    @GetMapping("/policyNo/{policyNo}")
    public ResponseEntity<Policy> fetchUser(@PathVariable Integer policyNo){
       Policy policy= repo.findByPolicyNo(policyNo);
       return new ResponseEntity<>(policy, HttpStatus.ACCEPTED);
    }

    @GetMapping("/security-check")
    public String securityStatus() {
        return "Security OK from System A";
    }


    @GetMapping("/test")
    public ResponseEntity<String> test(){
        return new ResponseEntity<>("Runnning!!!", HttpStatus.OK);
    }

    @GetMapping("/systemA")
    public String testSystemA(){
        return "change from System A";
    }





}
