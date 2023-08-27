package com.demo.votingappwebpage.repository;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.votingappwebpage.model.Vote;

@Repository
public interface VotesRepository extends JpaRepository<Vote, Long> {

}