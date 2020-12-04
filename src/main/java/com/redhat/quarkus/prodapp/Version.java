package com.redhat.quarkus.prodapp;

public class Version {
    private String id;
    private boolean newest;
    private String baseVersion;
    private String upstreamVersion;

    public Version() {
    }

    public Version(String id, boolean newest) {
        this.id = id;
        this.newest = newest;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isNewest() {
        return newest;
    }

    public void setNewest(boolean newest) {
        this.newest = newest;
    }

    public String getBaseVersion() {
        return baseVersion;
    }

    public void setBaseVersion(String baseVersion) {
        this.baseVersion = baseVersion;
    }

    public String isUpstreamVersion() {
        return upstreamVersion;
    }

    public void setUpstreamVersion(String upstreamVersion) {
        this.upstreamVersion = upstreamVersion;
    }
}
