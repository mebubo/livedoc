package org.hildan.livedoc.core.pojo.global;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

public class ApiChangelogsDoc {
    public final String livedocId = UUID.randomUUID().toString();

    private Set<ApiChangelogDoc> changelogs;

    public ApiChangelogsDoc() {
        this.changelogs = new LinkedHashSet<>();
    }

    public Set<ApiChangelogDoc> getChangelogs() {
        return changelogs;
    }

    public void setChangelogs(Set<ApiChangelogDoc> changelogs) {
        this.changelogs = changelogs;
    }

    public void addChangelog(ApiChangelogDoc apiChangelogDoc) {
        this.changelogs.add(apiChangelogDoc);
    }

}
