package com.zerobase.dividend.scraper;

import com.zerobase.dividend.dto.ScrapDto;

public interface Scraper {
    public ScrapDto scrapByTicker(String ticker);
    public String scrapCompanyNameByTicker(String ticker);

}
