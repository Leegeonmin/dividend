package com.zerobase.dividend.scraper;

import com.zerobase.dividend.dto.scrapDto;

public interface Scraper {
    public scrapDto scrapByTicker(String ticker);
    public String scrapCompanyNameByTicker(String ticker);

}
