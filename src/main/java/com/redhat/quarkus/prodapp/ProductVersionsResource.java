package com.redhat.quarkus.prodapp;

import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Path("/versions")
@Produces("application/json")
@Consumes("application/json")
public class ProductVersionsResource {

    private static final Logger log = Logger.getLogger(ProductVersionsResource.class);

    @Inject
    MockStorage dao;

    @GET
    @Path("/{version}/extensions")
    public List<ExtensionForVersionRow> getExtensionsForVersion(@PathParam String version) {
        version = toRealVersion(version);
        return dao.getAll(version);
    }

    @POST
    @Path("/{version}/extensions/{extensionId}")
    public void updateExtension(@PathParam String version, @PathParam String extensionId, ExtensionUpdate data) {
        log.debugf("saving %s", data);
        version = toRealVersion(version);
        dao.update(version, extensionId, data.getSupportStatus(), data.getSupportType(), data.getNotes());
    }

    @GET
    public Collection<Version> getVersions() {
        return dao.getVersions().stream().map(
                v -> new Version(v, MockStorage.defaultVersion.equals(v))
        ).collect(Collectors.toList());
    }

    @POST
    public void addVersion(Version version) {
        version.setBaseVersion(toRealVersion(version.getBaseVersion()));
        dao.addVersion(version);
    }

    private String toRealVersion(String version) {
        if ("newest".equals(version)) {
            version = MockStorage.defaultVersion;
        }
        return version;
    }
}
