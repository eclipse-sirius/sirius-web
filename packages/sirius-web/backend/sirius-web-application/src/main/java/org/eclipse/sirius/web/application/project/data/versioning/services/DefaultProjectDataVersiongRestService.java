/*******************************************************************************
 * Copyright (c) 2024 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.eclipse.sirius.web.application.project.data.versioning.services;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.web.application.dto.Identified;
import org.eclipse.sirius.web.application.object.services.api.IDefaultObjectRestService;
import org.eclipse.sirius.web.application.project.data.versioning.dto.ChangeType;
import org.eclipse.sirius.web.application.project.data.versioning.dto.RestCommit;
import org.eclipse.sirius.web.application.project.data.versioning.dto.RestDataIdentity;
import org.eclipse.sirius.web.application.project.data.versioning.dto.RestDataVersion;
import org.eclipse.sirius.web.application.project.data.versioning.services.api.IDefaultProjectDataVersioningRestService;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link IDefaultProjectDataVersioningRestService}.
 *
 * @author arichard
 */
@Service
public class DefaultProjectDataVersiongRestService implements IDefaultProjectDataVersioningRestService {

    private static final OffsetDateTime DEFAULT_CREATED = Instant.EPOCH.atOffset(ZoneOffset.UTC);

    private static final String DEFAULT_DESCRIPTION = "The one and only commit for this project";

    private final IDefaultObjectRestService defaultObjectRestService;

    private final IObjectService objectService;

    public DefaultProjectDataVersiongRestService(IDefaultObjectRestService defaultObjectRestService, IObjectService objectService) {
        this.defaultObjectRestService = Objects.requireNonNull(defaultObjectRestService);
        this.objectService = Objects.requireNonNull(objectService);
    }

    /**
     * There is only one commit per project in the default implementation.
     * It represents the current state of the project, without taking care of data created/updated/deleted since the creation of the project.
     * It's creation date/time is the Epoch date/time.
     * It's a tradeoff as it does not strictly follow the SystemModelingAPI specification.
     */
    @Override
    public List<RestCommit> getCommits(IEditingContext editingContext) {
        var projectId = UUID.fromString(editingContext.getId());
        var commit = new RestCommit(projectId, DEFAULT_CREATED, DEFAULT_DESCRIPTION, new Identified(projectId), List.of());
        return List.of(commit);
    }

    /**
     * The default implementation does not allow several commits per project.
     */
    @Override
    public RestCommit createCommit(IEditingContext editingContext, Optional<UUID> branchId) {
        return null;
    }

    @Override
    public RestCommit getCommitById(IEditingContext editingContext, UUID commitId) {
        RestCommit commit = null;
        if (commitId != null && commitId.toString().equals(editingContext.getId())) {
            var projectId = UUID.fromString(editingContext.getId());
            commit = new RestCommit(commitId, DEFAULT_CREATED, DEFAULT_DESCRIPTION, new Identified(projectId), List.of());
        }
        return commit;
    }

    /**
     * The default implementation retrieves all elements containing in the project, without taking care of data created/updated/deleted since the creation of the project.
     * It is not able to distinguish CREATED, UPDATED or DELETED elements.
     * Furthermore, the DataVersion.id attribute should be randomly generated but constant for an unlimited period.
     * We decided to generate this Id from it's commit Id and element Id, to be able to compute it for tests purpose.
     * These are tradeoffs as it does not strictly follow the SystemModelingAPI specification.
     */
    @Override
    public List<RestDataVersion> getCommitChange(IEditingContext editingContext, UUID commitId, List<ChangeType> changeTypes) {
        List<RestDataVersion> dataVersions = new ArrayList<>();
        var changeTypesAllowed = changeTypes == null || changeTypes.isEmpty();
        if (commitId != null && commitId.toString().equals(editingContext.getId()) && changeTypesAllowed) {
            var elements = this.defaultObjectRestService.getElements(editingContext);
            for (var element : elements) {
                var elementId = this.objectService.getId(element);
                var changeId = UUID.nameUUIDFromBytes((commitId.toString() + elementId).getBytes());
                var dataVersion = new RestDataVersion(changeId, new RestDataIdentity(UUID.fromString(elementId)), element);
                dataVersions.add(dataVersion);
            }
        }
        return dataVersions;
    }

    @Override
    public RestDataVersion getCommitChangeById(IEditingContext editingContext, UUID commitId, UUID changeId) {
        RestDataVersion dataVersion = null;
        if (changeId != null && commitId != null && commitId.toString().equals(editingContext.getId())) {
            var elements = this.defaultObjectRestService.getElements(editingContext);
            for (var element : elements) {
                var elementId = this.objectService.getId(element);
                if (elementId != null) {
                    var computedChangeId = UUID.nameUUIDFromBytes((commitId.toString() + elementId).getBytes());
                    if (changeId.toString().equals(computedChangeId.toString())) {
                        dataVersion = new RestDataVersion(changeId, new RestDataIdentity(UUID.fromString(elementId)), element);
                        break;
                    }
                }
            }
        }
        return dataVersion;
    }

}
