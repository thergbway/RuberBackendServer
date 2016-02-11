package com.ruber.model;

public class Market {
    private Boolean enabled;
    private Integer mainAlbumId;
    private Integer contactId;
    private Currency currency;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getMainAlbumId() {
        return mainAlbumId;
    }

    public void setMainAlbumId(Integer mainAlbumId) {
        this.mainAlbumId = mainAlbumId;
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

    public Market(Boolean enabled, Integer mainAlbumId, Integer contactId, Currency currency) {

        this.enabled = enabled;
        this.mainAlbumId = mainAlbumId;
        this.contactId = contactId;
        this.currency = currency;
    }

    public Market() {

    }
}
