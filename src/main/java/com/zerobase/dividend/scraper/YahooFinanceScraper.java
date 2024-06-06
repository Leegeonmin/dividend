package com.zerobase.dividend.scraper;

import com.zerobase.dividend.constants.Month;
import com.zerobase.dividend.dto.scrapDto;
import com.zerobase.dividend.error.CustomException;
import com.zerobase.dividend.error.ErrorCode;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class YahooFinanceScraper implements  Scraper{
    private static final String URL = "https://finance.yahoo.com/quote/%s/history?frequency=1mo&period1=%d&period2=%d";
    private static  final String SUMMARY_URL = "https://finance.yahoo.com/quote/%s?p=%s";
    private static final long START_TIME = 86400;
    @Override
    public scrapDto scrapByTicker(String ticker){
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
                    throw new CustomException(ErrorCode.MONTH_NOT_CORRECT);
                }
                dividends.add(
                        new scrapDto.Dividend(LocalDateTime.of(year, month, day, 0, 0),dividend));

            });
            dto.setDividends(dividends);


        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomException(ErrorCode.ALREADY_EXISTED);
        }
        return dto;
    }
    @Override
    public String scrapCompanyNameByTicker(String ticker){
        String url = String.format(SUMMARY_URL, ticker, ticker);

        try {
            Document document = Jsoup.connect(url).get();
            Element titleEle = document.getElementsByTag("h1").get(1);
            String title = titleEle.select("h1.svelte-3a2v0c").first().text().replaceAll(" \\(.*\\)", "");;
            return title;

        } catch (IOException e) {
            throw new CustomException(ErrorCode.INVALID_TICKER);
        }
    }
}
