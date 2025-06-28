package com.vikul.lic.dao;

import com.vikul.lic.entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ILicRepo extends JpaRepository<Policy, Integer> {

    @Query(value = "SELECT * FROM policy WHERE policy_No = :policyNo", nativeQuery = true)
    Policy findByPolicyNo(@Param("policyNo") Integer policyNo);


}
