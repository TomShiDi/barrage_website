package barrage.demo.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author TomShiDi
 * @Since 2020/7/28
 * @Version 1.0
 */
public class QuotesInfo implements Serializable {
    private Integer quotesId;

    private String quotesContent;

    private Integer publisherId;

    private String author;

    private Date recordDate;

    public Integer getQuotesId() {
        return quotesId;
    }

    public void setQuotesId(Integer quotesId) {
        this.quotesId = quotesId;
    }

    public String getQuotesContent() {
        return quotesContent;
    }

    public void setQuotesContent(String quotesContent) {
        this.quotesContent = quotesContent;
    }

    public Integer getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Integer publisherId) {
        this.publisherId = publisherId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }
}
