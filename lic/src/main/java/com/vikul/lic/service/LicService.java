package com.vikul.lic.service;

import com.vikul.lic.dao.ILicRepo;
import com.vikul.lic.dto.PolicyDto;
import com.vikul.lic.dto.PremMod;
import com.vikul.lic.entity.Policy;
import jakarta.transaction.Transactional;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.beans.Transient;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class LicService {

    @Autowired
    ILicRepo iLicRepo;

    public List<PolicyDto> addPolicies(MultipartFile file){
        List<PolicyDto> policyDtosList=new ArrayList<>();
        try(Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet=workbook.getSheetAt(0);
            for(int i=1;i<=sheet.getLastRowNum();i++){
                Row row=sheet.getRow(i);

                PolicyDto policyDto=new PolicyDto();

                policyDto.setPolicyNo((int) row.getCell(0).getNumericCellValue());
                policyDto.setName(row.getCell(1).getStringCellValue());
                Cell startDateCell = row.getCell(2);
                if (startDateCell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(startDateCell)) {
                    Date date = startDateCell.getDateCellValue();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    policyDto.setStartDate(sdf.format(date));
                } else if (startDateCell.getCellType() == CellType.STRING) {
                    policyDto.setStartDate(startDateCell.getStringCellValue());
                } else {
                    policyDto.setStartDate("");
                }
                //policyDto.setStartDate(row.getCell(2).getStringCellValue());
                policyDto.setMod(PremMod.valueOf(row.getCell(3).getStringCellValue()));
                //policyDto.setNextDue(row.getCell(4).getStringCellValue());
                policyDto.setAmount((float) row.getCell(4).getNumericCellValue());
                //policyDto.setDueTerms((int) row.getCell(6).getNumericCellValue());

                policyDtosList.add(policyDto);
            }
        }catch (IOException | IllegalArgumentException exception){
            throw new RuntimeException("Failed to Parse Excel File ::"+ exception.getMessage());
        }
        return policyDtosList;
    }


    Map<Integer, PolicyDto> policyDtoMap=new LinkedHashMap();
    public Map<Integer, PolicyDto> adddPolicies(MultipartFile file){
        //List<PolicyDto> policyDtosList=new ArrayList<>();
        try(Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet=workbook.getSheetAt(0);
            for(int i=1;i<=sheet.getLastRowNum();i++){
                Row row=sheet.getRow(i);

                PolicyDto policyDto=new PolicyDto();

                policyDto.setPolicyNo((int) row.getCell(0).getNumericCellValue());
                policyDto.setName(row.getCell(1).getStringCellValue());
                Cell startDateCell = row.getCell(2);
                if (startDateCell.getCellType() == CellType.NUMERIC && DateUtil.isCellDateFormatted(startDateCell)) {
                    Date date = startDateCell.getDateCellValue();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    policyDto.setStartDate(sdf.format(date));
                } else if (startDateCell.getCellType() == CellType.STRING) {
                    policyDto.setStartDate(startDateCell.getStringCellValue());
                } else {
                    policyDto.setStartDate("");
                }
                //policyDto.setStartDate(row.getCell(2).getStringCellValue());
                policyDto.setMod(PremMod.valueOf(row.getCell(3).getStringCellValue()));
                //policyDto.setNextDue(row.getCell(4).getStringCellValue());
                policyDto.setAmount((float) row.getCell(4).getNumericCellValue());
                //policyDto.setDueTerms((int) row.getCell(6).getNumericCellValue());

                //policyDtosList.add(policyDto);
                policyDtoMap.put(policyDto.getPolicyNo(), policyDto);
            }
        }catch (IOException | IllegalArgumentException exception){
            throw new RuntimeException("Failed to Parse Excel File ::"+ exception.getMessage());
        }
        //return policyDtosList;
        for(Map.Entry<Integer, PolicyDto> entry:policyDtoMap.entrySet()){
            System.out.println(entry.getKey()+" :: "+entry.getValue());
        }
        return policyDtoMap;
    }

    @Transactional
    public List<Policy> setPoliciesDetails(List<PolicyDto> policyDtos){
        List<Policy> policies=new ArrayList<>();
        for(PolicyDto policyDto:policyDtos){
            Policy policy=new Policy();
            policy.setPolicyNo(policyDto.getPolicyNo());
            policy.setAmount(policyDto.getAmount());
            policy.setName(policyDto.getName());
            policy.setMod(policyDto.getMod());
            String startDate=policyDto.getStartDate();
            DateTimeFormatter localDate=DateTimeFormatter.ofPattern("dd-MM-yyyy");
            policy.setStartDate(LocalDate.parse(startDate, localDate));
            policy.setNextDue(calculateNextDue(policyDto.getStartDate(), policyDto.getMod()));
            policies.add(policy);
        }

        return iLicRepo.saveAll(policies);


    }


    @Transactional
    public List<Policy> setPoliciesDetailss(Map<Integer, PolicyDto> policyDtosMap){
        List<Policy> policies=new ArrayList<>();
        for(Map.Entry<Integer, PolicyDto> entry:policyDtoMap.entrySet()){
            Policy policy=new Policy();
            policy.setPolicyNo(entry.getValue().getPolicyNo());
            policy.setAmount(entry.getValue().getAmount());
            policy.setName(entry.getValue().getName());
            policy.setMod(entry.getValue().getMod());
            String startDate=entry.getValue().getStartDate();
            DateTimeFormatter localDate=DateTimeFormatter.ofPattern("dd-MM-yyyy");
            policy.setStartDate(LocalDate.parse(startDate, localDate));
            policy.setNextDue(calculateNextDue(entry.getValue().getStartDate(), entry.getValue().getMod()));
            policies.add(policy);
        }

        return iLicRepo.saveAll(policies);


    }

    public LocalDate calculateNextDue(String startDate, PremMod mod){
        DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDateTime= LocalDate.parse(startDate, dateTimeFormatter);
        switch (mod){
            case Qly:
                return localDateTime=localDateTime.plusMonths(3);
                //break;
               // return localDateTime;
            case Hly:
                return localDateTime = localDateTime.plusMonths(6);
                //break;
                // return localDateTime;
            case Yly:
                return localDateTime = localDateTime.plusYears(1);
                //break;
                // return localDateTime;
            case Mly:
                return localDateTime=localDateTime.plusMonths(1);
            default:
                throw new IllegalArgumentException("Invalid premium mode: " + mod);
        }

    }

}
