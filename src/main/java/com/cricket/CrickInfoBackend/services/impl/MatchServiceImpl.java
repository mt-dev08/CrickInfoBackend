package com.cricket.CrickInfoBackend.services.impl;

import com.cricket.CrickInfoBackend.entities.Match;
import com.cricket.CrickInfoBackend.respositories.MatchRepo;
import com.cricket.CrickInfoBackend.services.MatchService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class MatchServiceImpl implements MatchService {

    private MatchRepo matchRepo;

    public MatchServiceImpl(MatchRepo matchRepo) {
        this.matchRepo = matchRepo;
    }


    @Override
    public List<Match> getLiveMatches() {
        List<Match> matches = new ArrayList<>();
        try {
            String url = "https://www.cricbuzz.com/cricket-match/live-scores";
            Document document = Jsoup.connect(url).get();
            Elements liveScoreElements = document.select("div.cb-mtch-lst.cb-tms-itm");
            for (Element match : liveScoreElements) {
                HashMap<String, String> liveMatchInfo = new LinkedHashMap<>();
                String teamsHeading = match.select("h3.cb-lv-scr-mtch-hdr").select("a").text();
                String matchNumberVenue = match.select("span").text();
                Elements matchBatTeamInfo = match.select("div.cb-hmscg-bat-txt");
                String battingTeam = matchBatTeamInfo.select("div.cb-hmscg-tm-nm").text();
                String score = matchBatTeamInfo.select("div.cb-hmscg-tm-nm+div").text();
                Elements bowlTeamInfo = match.select("div.cb-hmscg-bwl-txt");
                String bowlTeam = bowlTeamInfo.select("div.cb-hmscg-tm-nm").text();
                String bowlTeamScore = bowlTeamInfo.select("div.cb-hmscg-tm-nm+div").text();
                String textLive = match.select("div.cb-text-live").text();
                String textComplete = match.select("div.cb-text-complete").text();
                //getting match link
                String matchLink = match.select("a.cb-lv-scrs-well.cb-lv-scrs-well-live").attr("href").toString();

                Match match1 = new Match();
                match1.setTeamHeading(teamsHeading);
                match1.setMatchNumberVenue(matchNumberVenue);
                match1.setBattingTeam(battingTeam);
                match1.setBattingTeamScore(score);
                match1.setBowlTeam(bowlTeam);
                match1.setBowlTeamScore(bowlTeamScore);
                match1.setLiveText(textLive);
                match1.setMatchLink(matchLink);
                match1.setTextComplete(textComplete);
                match1.setMatchStatus();


                matches.add(match1);

//                update the match in database


                updateMatch(match1);


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return matches;
    }

    private void updateMatch(Match match1) {

        Match match=this.matchRepo.findByTeamHeading(match1.getTeamHeading()).orElse(null);
        if(match==null){
            matchRepo.save(match1);
        }
        else{
            match1.setMacthId(match.getMacthId());
            matchRepo.save(match1);
        }
    }

    @Override
    public List<List<String>> getPointsTable() {

        List<List<String>> pointTable = new ArrayList<>();
        String tableURL = "https://www.cricbuzz.com/cricket-series/6732/icc-cricket-world-cup-2023/points-table";
        try {
            Document document = Jsoup.connect(tableURL).get();
            Elements table = document.select("table.cb-srs-pnts");
            Elements tableHeads = table.select("thead>tr>*");
            List<String> headers = new ArrayList<>();
            tableHeads.forEach(element -> {
                headers.add(element.text());
            });
            pointTable.add(headers);
            Elements bodyTrs = table.select("tbody>*");
            bodyTrs.forEach(tr -> {
                List<String> points = new ArrayList<>();
                if (tr.hasAttr("class")) {
                    Elements tds = tr.select("td");
                    String team = tds.get(0).select("div.cb-col-84").text();
                    points.add(team);
                    tds.forEach(td -> {
                        if (!td.hasClass("cb-srs-pnts-name")) {
                            points.add(td.text());
                        }
                    });
                    pointTable.add(points);
                }


            });

            System.out.println(pointTable);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pointTable;
    }

    @Override
    public List<Match> getAllMatches() {
        return this.matchRepo.findAll();
    }
}













//
//
//
//
//
//package com.cricket.CrickInfoBackend.services.impl;
//
//import com.cricket.CrickInfoBackend.entities.AllMatches;
//import com.cricket.CrickInfoBackend.entities.Match;
//import com.cricket.CrickInfoBackend.respositories.MatchRepo;
//import com.cricket.CrickInfoBackend.services.MatchService;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.util.*;
//
//@Service
//public class MatchServiceImpl implements MatchService {
//
//    private MatchRepo matchRepo;
//
//    public MatchServiceImpl(MatchRepo matchRepo) {
//        this.matchRepo = matchRepo;
//    }
//
//    @Override
//    public List<AllMatches> getAllMatches() {
//        return scrapeMatches("https://www.cricbuzz.com/cricket-match/live-scores/recent-matches", true);
//    }
//
//    @Override
//    public List<Match> getLiveMatches() {
//        return scrapeMatches("https://www.cricbuzz.com/cricket-match/live-scores", false);
//    }
//
//    private <T> List<T> scrapeMatches(String url, boolean isAllMatches) {
//        List<T> matches = new ArrayList<>();
//        try {
//            Document document = Jsoup.connect(url).get();
//            Elements liveScoreElements = document.select("div.cb-mtch-lst.cb-tms-itm");
//            for (Element match : liveScoreElements) {
//                T matchObj = createMatchFromElement(match, isAllMatches);
//                matches.add(matchObj);
//                updateMatch((Match) matchObj);
//            }
//        } catch (IOException e) {
//            e.printStackTrace(); // Consider logging or handling this exception more gracefully
//        }
//        return matches;
//    }
//
//    private <T> T createMatchFromElement(Element match, boolean isAllMatches) {
//        String teamsHeading = match.select("h3.cb-lv-scr-mtch-hdr").select("a").text();
//        String matchNumberVenue = match.select("span").text();
//        Elements matchBatTeamInfo = match.select("div.cb-hmscg-bat-txt");
//        String battingTeam = matchBatTeamInfo.select("div.cb-hmscg-tm-nm").text();
//        String score = matchBatTeamInfo.select("div.cb-hmscg-tm-nm+div").text();
//        Elements bowlTeamInfo = match.select("div.cb-hmscg-bwl-txt");
//        String bowlTeam = bowlTeamInfo.select("div.cb-hmscg-tm-nm").text();
//        String bowlTeamScore = bowlTeamInfo.select("div.cb-hmscg-tm-nm+div").text();
//        String textLive = match.select("div.cb-text-live").text();
//        String textComplete = match.select("div.cb-text-complete").text();
//        String matchLink = match.select("a.cb-lv-scrs-well.cb-lv-scrs-well-live").attr("href");
//
//        if (isAllMatches) {
//            AllMatches allMatches = new AllMatches();
//            allMatches.setTeamHeading(teamsHeading);
//            allMatches.setMatchNumberVenue(matchNumberVenue);
//            allMatches.setBattingTeam(battingTeam);
//            allMatches.setBattingTeamScore(score);
//            allMatches.setBowlTeam(bowlTeam);
//            allMatches.setBowlTeamScore(bowlTeamScore);
//            allMatches.setLiveText(textLive);
//            allMatches.setMatchLink(matchLink);
//            allMatches.setTextComplete(textComplete);
//            allMatches.setMatchStatus(); // Set this based on actual status
//            return (T) allMatches;
//        } else {
//            Match matchObj = new Match();
//            matchObj.setTeamHeading(teamsHeading);
//            matchObj.setMatchNumberVenue(matchNumberVenue);
//            matchObj.setBattingTeam(battingTeam);
//            matchObj.setBattingTeamScore(score);
//            matchObj.setBowlTeam(bowlTeam);
//            matchObj.setBowlTeamScore(bowlTeamScore);
//            matchObj.setLiveText(textLive);
//            matchObj.setMatchLink(matchLink);
//            matchObj.setTextComplete(textComplete);
//            matchObj.setMatchStatus(); // Set this based on actual status
//            return (T) matchObj;
//        }
//    }
//
//    private void updateMatch(Match match) {
//        Match existingMatch = matchRepo.findByTeamHeading(match.getTeamHeading()).orElse(null);
//        if (existingMatch == null) {
//            matchRepo.save(match);
//        } else {
//            match.setMacthId(existingMatch.getMacthId());
//            matchRepo.save(match);
//        }
//    }
//
//    @Override
//    public List<List<String>> getPointsTable() {
//        List<List<String>> pointTable = new ArrayList<>();
//        String tableURL = "https://www.cricbuzz.com/cricket-series/6732/icc-cricket-world-cup-2023/points-table";
//        try {
//            Document document = Jsoup.connect(tableURL).get();
//            Elements table = document.select("table.cb-srs-pnts");
//            Elements tableHeads = table.select("thead>tr>*");
//            List<String> headers = new ArrayList<>();
//            tableHeads.forEach(element -> headers.add(element.text()));
//            pointTable.add(headers);
//            Elements bodyTrs = table.select("tbody>*");
//            bodyTrs.forEach(tr -> {
//                if (tr.hasAttr("class")) {
//                    List<String> points = new ArrayList<>();
//                    Elements tds = tr.select("td");
//                    String team = tds.get(0).select("div.cb-col-84").text();
//                    points.add(team);
//                    tds.forEach(td -> {
//                        if (!td.hasClass("cb-srs-pnts-name")) {
//                            points.add(td.text());
//                        }
//                    });
//                    pointTable.add(points);
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace(); // Consider logging or handling this exception more gracefully
//        }
//        return pointTable;
//    }
//}
//
