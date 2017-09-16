package jkickerstats.interfaces;

import jkickerstats.types.Game;
import jkickerstats.types.Match;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static jkickerstats.interfaces.PageDownloader.downloadPage;
import static jkickerstats.interfaces.PageDownloader.downloadSeason;
import static jkickerstats.interfaces.PageParser.*;

@Component
public class ParsedMatchesRetrieverImpl implements ParsedMatchesRetriever {
    private static final Logger LOG = Logger.getLogger(ParsedMatchesRetrieverImpl.class.getName());
    private static final String SEASONS_URL = "http://www.kickern-hamburg.de/liga-tool/mannschaftswettbewerbe";

    public Stream<Integer> getSeasonIDs() {
        Document seasonsDoc = downloadPage(SEASONS_URL);
        return findSeasonIDs(seasonsDoc);
    }

    public Stream<Match> get(Integer seasonId) {
        return getLigaLinks(seasonId)
                .map(ParsedMatchesRetrieverImpl::getMatches)//
                .flatMap(Function.identity());
    }

    public Match downloadGamesFromMatch(Match match) {
        return new Match.MatchBuilder(match)//
                .withGames(getGames(match.getMatchLink()).collect(toList()))//
                .build();
    }

    static Stream<Game> getGames(String matchLink) {
        Document matchDoc = downloadPage(matchLink);
        try {
            return findGames(matchDoc);
        } catch (Exception e) {
            LOG.severe("processing match: " + matchLink);
            throw e;
        }
    }

    static Stream<String> getMatchLinks(String ligaLink) {
        Document ligaDoc = downloadPage(ligaLink);
        return findMatchLinks(ligaDoc);
    }

    static Stream<Match> getMatches(String ligaLink) {
        Document ligaDoc = downloadPage(ligaLink);
        try {
            return findMatches(ligaDoc);
        } catch (Exception e) {
            LOG.severe("processing liga: " + ligaLink);
            throw e;
        }
    }

    static Stream<String> getLigaLinks(Integer seasonId) {
        Document seasonDoc = downloadSeason(seasonId);
        return findLigaLinks(seasonDoc);
    }
}
