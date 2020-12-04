package com.redhat.quarkus.prodapp;

import org.jboss.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.BadRequestException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

@ApplicationScoped
public class MockStorage {

    private static final Logger log = Logger.getLogger(MockStorage.class);

    private final Map<String, List<ExtensionForVersionRow>> storageMock = new TreeMap<>();
    public static String defaultVersion = "1.7"; // TODO: proper encapsulation

    @PostConstruct
    void setUp() {
        storageMock.put(defaultVersion, Arrays.asList(
                new ExtensionForVersionRow("quarkus-grpc", "gRPC extension", "1.7", SupportStatus.SUPPORTED, null, ""),
                new ExtensionForVersionRow("quarkus-tika", "Tika extension", null, SupportStatus.UNSUPPORTED, null, null),
                new ExtensionForVersionRow("quarkus-resteasy", "RestEasy server extension", null, SupportStatus.SUPPORTED, null, null)
        ));
        storageMock.put("1.3", Arrays.asList(
                new ExtensionForVersionRow("quarkus-tika", "Tika extension", null, SupportStatus.UNSUPPORTED, null, null),
                new ExtensionForVersionRow("quarkus-resteasy", "RestEasy server extension", null, SupportStatus.SUPPORTED, null, null)
        ));
    }

    public Collection<String> getVersions() {
        return storageMock.keySet();
    }

    public List<ExtensionForVersionRow> getAll(String version) {
        return storageMock.get(version);
    }

    public void update(String version, String extensionId, SupportStatus supportStatus, SupportType supportType, String notes) {
        log.debug("Updating");
        List<ExtensionForVersionRow> rowsForVersion = storageMock.get(version);
        Optional<ExtensionForVersionRow> maybeExtension = rowsForVersion.stream().filter(row -> row.getId().equals(extensionId)).findFirst();

        if (maybeExtension.isEmpty()) {
            throw new BadRequestException("Extension " + extensionId + " not found for product version " + version);
        } else {
            maybeExtension.get().setSupportStatus(supportStatus).setNotes(notes).setSupportType(supportType);
        }

    }

    public void addVersion(Version version) {
        List<ExtensionForVersionRow> value = cloneExtensions(storageMock.get(version.getBaseVersion()));
        storageMock.put(version.getId(), value);
        if (version.isNewest()) {
            defaultVersion = version.getId();
        }
    }

    private List<ExtensionForVersionRow> cloneExtensions(List<ExtensionForVersionRow> extensionForVersionRows) {
        List<ExtensionForVersionRow> clone = new ArrayList<>();

        for (ExtensionForVersionRow row : extensionForVersionRows) {
            clone.add(row.clone());
        }

        return clone;
    }
}
