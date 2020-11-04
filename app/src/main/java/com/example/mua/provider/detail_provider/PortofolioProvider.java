package com.example.mua.provider.detail_provider;

public class PortofolioProvider {

    String id;
    String provider_id;
    String link;

    public PortofolioProvider(String id, String provider_id, String link) {
        this.id = id;
        this.provider_id = provider_id;
        this.link = link;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(String provider_id) {
        this.provider_id = provider_id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
