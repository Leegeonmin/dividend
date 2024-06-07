package com.zerobase.dividend.scraper;

import com.zerobase.dividend.dto.ScrapDto;

public interface Scraper {
    ScrapDto scrapByTicker(String ticker);
    String scrapCompanyNameByTicker(String ticker);

}
