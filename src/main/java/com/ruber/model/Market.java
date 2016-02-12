package com.ruber.model;

public class Market {
    private Boolean enabled;
    private Integer contactId;
    private Currency currency;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }


    public Integer getContactId() {
        return contactId;
    }

    public void setContactId(Integer contactId) {
        this.contactId = contactId;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Market(Boolean enabled, Integer contactId, Currency currency) {
        this.enabled = enabled;
        this.contactId = contactId;
        this.currency = currency;
    }

    public Market() {

    }
}
