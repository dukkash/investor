package com.dukkash.investor.model;

import com.dukkash.investor.ui.model.EstimateModel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Entity(name = "ESTIMATE")
public class Estimate {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Integer id;

    @ManyToOne
    private Company company;

    @Column(name = "current_price")
    private BigDecimal currentPrice;

    @Column(name = "estimated_date")
    private Date estimatedDate;

    @Column(name = "estimated_by")
    private String estimatedBy;

    @Column
    private String description;

    @Column(name = "target_price")
    private BigDecimal targetPrice;

    @Column(name = "target_date")
    private Date targetDate;

    @Transient
    private List<CompanyNote> notes;

    public Estimate() {
    }

    public List<CompanyNote> getNotes() {
        return notes;
    }

    public void setNotes(List<CompanyNote> notes) {
        this.notes = notes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Date getEstimatedDate() {
        return estimatedDate;
    }

    public void setEstimatedDate(Date estimatedDate) {
        this.estimatedDate = estimatedDate;
    }

    public String getEstimatedBy() {
        return estimatedBy;
    }

    public void setEstimatedBy(String estimatedBy) {
        this.estimatedBy = estimatedBy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getTargetPrice() {
        return targetPrice;
    }

    public void setTargetPrice(BigDecimal targetPrice) {
        this.targetPrice = targetPrice;
    }

    public Date getTargetDate() {
        return targetDate;
    }

    public void setTargetDate(Date targetDate) {
        this.targetDate = targetDate;
    }

    public EstimateModel toEstimateModel() {
        EstimateModel model = new EstimateModel();
        model.setCompanyName(this.getCompany().getName());
        model.setCurrentPrice(this.currentPrice);
        model.setDescription(this.getDescription());
        model.setEstimatedBy(this.estimatedBy);
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        model.setEstimatedDate(dateFormat.format(this.estimatedDate));
        model.setTickerSymbol(this.getCompany().getTickerSymbol());
        model.setTargetDate(dateFormat.format(this.targetDate));
        model.setTargetPrice(this.targetPrice);
        model.setPrice(this.getCompany().getPrice().toString());
        model.setNotes(this.notes);

        return model;
    }
}
