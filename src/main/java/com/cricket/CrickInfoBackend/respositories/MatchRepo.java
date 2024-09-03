package com.cricket.CrickInfoBackend.respositories;

import com.cricket.CrickInfoBackend.entities.Match;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MatchRepo extends JpaRepository<Match,Integer> {

    Optional<Match> findByTeamHeading(String teamHeading);
//    List<Match> findByStatus(String status);

}
