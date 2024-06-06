package com.zerobase.dividend.scraper;

import com.zerobase.dividend.constants.Month;
import com.zerobase.dividend.dto.scrapDto;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class YahooFinanceScraperTest {

    private static final String URL = "https://finance.yahoo.com/quote/%s/history?frequency=1mo&period1=%d&period2=%d";
    private static  final String SUMMARY_URL = "https://finance.yahoo.com/quote/%s?p=%s";
    private static final long START_TIME = 86400;


    @Test
    void getDividendFromYahooFinance(){
        String ticker = "NKE";
        scrapDto dto = new scrapDto();
        try {
            long end = System.currentTimeMillis() / 1000;
            String url = String.format(URL, ticker, START_TIME, end);
            Connection connect = Jsoup.connect(url);
            Document document = connect.get();

            Elements parsingDivs = document.getElementsByAttributeValue("class", "table svelte-ewueuo");
            Element tableEle = parsingDivs.get(0);

            Element tbody = tableEle.children().get(1);
            List<scrapDto.Dividend> dividends = new ArrayList<>();
            tbody.children().stream().map(Element::text).filter(txt -> txt.endsWith("Dividend")).forEach(txt -> {
                String[] splits = txt.split(" ");
                int month = Month.strToNumber(splits[0]);
                int day = Integer.valueOf(splits[1].replace(",", ""));
                int year = Integer.valueOf(splits[2]);
                String dividend = splits[3];
                if (month < 0) {
                    throw new RuntimeException("Unexpected Month enum value -> " + splits[0]);
                }
                dividends.add(
                        new scrapDto.Dividend(LocalDateTime.of(year, month, day, 0, 0),dividend));

            });
            dto.setDividends(dividends);


        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("hi");
        System.out.println(dto);
        assertThat(dto).isNotNull();
    }
}