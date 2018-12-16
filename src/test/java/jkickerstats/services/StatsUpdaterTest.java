package jkickerstats.services;

import jkickerstats.domain.Match;
import jkickerstats.interfaces.CsvCreator;
import org.assertj.core.api.Assertions;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static jkickerstats.domain.Match.createMatch;
import static jkickerstats.services.ParsedMatchesRetrieverImpl.getGames;
import static jkickerstats.services.StatsUpdater.getCurrentSeasonId;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class StatsUpdaterTest {
    @Autowired
    private StatsUpdater statsUpdater;

    @Autowired
    private CsvCreator csvCreator;

    @Autowired
    private ParsedMatchesRetriever retriever;

    @Ignore
    @Test
    public void createCSVFileWithAllGames() {
        List<Match> matches = statsUpdater.downloadAllMatches();
        List<String> gameStrings = csvCreator.createCsvRowList(matches);
        csvCreator.createCsvFile(gameStrings);
    }

    @Test
    @Ignore
    public void savesAllMatchesWithGames() {
        statsUpdater.getAllData();
    }

    @Ignore
    @Test
    public void getsSeasonIds() {
        Stream<Integer> seasonIDs = retriever.getSeasonIDs();
        assertThat(getCurrentSeasonId(seasonIDs)).isEqualTo((12));
    }

    @Ignore
    @Test
    public void updatesData() {
        statsUpdater.updateData();
    }

    @Ignore
    @Test
    public void getsLinks() {
        assertThat(ParsedMatchesRetrieverImpl.getLigaLinks(11)).isNotEmpty();
    }

    @Ignore
    @Test
    public void findsMatches() {
        ParsedMatchesRetrieverImpl.getLigaLinks(13)
                .forEach(ligaLink -> {
                    System.out.println("processing liga link: " + ligaLink);
                    ParsedMatchesRetrieverImpl.getMatchLinks(ligaLink)
                            .forEach(matchLink -> {
                                System.out.println("processing createMatch link: " + matchLink);
                                getGames(matchLink).forEach(System.out::println);
                            });
                });

    }

    @Ignore
    @Test
    public void findsAllMatches() {
        ParsedMatchesRetrieverImpl.getLigaLinks(16)
                .forEach(ligaLink -> ParsedMatchesRetrieverImpl.getMatches(ligaLink)
                        .forEach(match -> {
                            Match fullMatch = createMatch(match)
                                    .withGames(getGames(match.getMatchLink()).collect(toList()));
                            System.out.println(String.format("Datum:%s home:%s guest:%s spiele:%s",
                                    fullMatch.getMatchDate(),
                                    fullMatch.getHomeTeam(),
                                    fullMatch.getGuestTeam(),
                                    fullMatch.getGames().size()));
                        }));
    }
}