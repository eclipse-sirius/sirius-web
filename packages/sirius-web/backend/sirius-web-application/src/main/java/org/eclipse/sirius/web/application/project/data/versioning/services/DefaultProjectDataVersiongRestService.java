/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.dto.Identified;
import org.eclipse.sirius.web.application.object.services.api.IDefaultObjectRestService;
import org.eclipse.sirius.web.application.object.services.api.IEObjectRestDeserializer;
import org.eclipse.sirius.web.application.object.services.api.IEObjectRestSerializer;
import org.eclipse.sirius.web.application.project.data.versioning.dto.ChangeType;
import org.eclipse.sirius.web.application.project.data.versioning.dto.RestBranch;
import org.eclipse.sirius.web.application.project.data.versioning.dto.RestCommit;
import org.eclipse.sirius.web.application.project.data.versioning.dto.RestDataIdentity;
import org.eclipse.sirius.web.application.project.data.versioning.dto.RestDataVersion;
import org.eclipse.sirius.web.application.project.data.versioning.services.api.IDefaultProjectDataVersioningRestService;
import org.eclipse.sirius.web.application.project.services.api.IProjectEditingContextService;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link IDefaultProjectDataVersioningRestService}.
 *
 * @author arichard
 */
@Service
public class DefaultProjectDataVersiongRestService implements IDefaultProjectDataVersioningRestService {

    private static final OffsetDateTime DEFAULT_CREATED = Instant.EPOCH.atOffset(ZoneOffset.UTC);

    private static final String DEFAULT_COMMIT_DESCRIPTION = "The one and only commit for this project";

    private static final String DEFAULT_BRANCH_NAME = "defaultBranch";

    private final IDefaultObjectRestService defaultObjectRestService;

    private final IIdentityService identityService;

    private final IObjectSearchService objectSearchService;

    private final IProjectEditingContextService projectEditingContextService;

    private final IEditService editService;

    private final IEObjectRestSerializer eObjectRestSerializer;

    private final IEObjectRestDeserializer objectRestDeserializer;

    public DefaultProjectDataVersiongRestService(IDefaultObjectRestService defaultObjectRestService, IIdentityService identityService, IObjectSearchService objectSearchService, IProjectEditingContextService projectEditingContextService, IEditService editService, IEObjectRestSerializer eObjectRestSerializer, IEObjectRestDeserializer objectRestDeserializer) {
        this.defaultObjectRestService = Objects.requireNonNull(defaultObjectRestService);
        this.identityService = Objects.requireNonNull(identityService);
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.projectEditingContextService = Objects.requireNonNull(projectEditingContextService);
        this.editService = Objects.requireNonNull(editService);
        this.eObjectRestSerializer = Objects.requireNonNull(eObjectRestSerializer);
        this.objectRestDeserializer = Objects.requireNonNull(objectRestDeserializer);
    }

    /**
     * There is only one commit per project in the default implementation.
     * It represents the current state of the project, without taking care of data created/updated/deleted since the creation of the project.
     * It's creation date/time is the Epoch date/time.
     * It's a tradeoff as it does not strictly follow the SystemModelingAPI specification.
     */
    @Override
    public List<RestCommit> getCommits(IEditingContext editingContext) {
        return this.projectEditingContextService.getProjectId(editingContext.getId())
                .flatMap(new UUIDParser()::parse)
                .map(projectId -> new RestCommit(projectId, DEFAULT_CREATED, DEFAULT_COMMIT_DESCRIPTION, new Identified(projectId), List.of()))
                .map(List::of)
                .orElse(List.of());
    }

    /**
     * The default implementation does not allow several commits per project. An amend commit is then executed.
     */
    @Override
    public RestCommit createCommit(IEditingContext editingContext, Optional<UUID> branchId, List<RestDataVersion> changes) {
        var objectsToDelete = new HashSet<>();
        var emptyNewObjects = new HashMap<EObject, Map<String, Object>>();
        var objectsToUpdate = new HashMap<EObject, Map<String, Object>>();
        var optionalProjectId = this.projectEditingContextService.getProjectId(editingContext.getId()).flatMap(new UUIDParser()::parse);
        if (optionalProjectId.isPresent()) {
            for (RestDataVersion dataVersion : changes) {
                var payload = dataVersion.payload();
                var identity = dataVersion.identity();

                if (payload == null && identity != null && identity.id() != null) {
                    this.objectSearchService.getObject(editingContext, identity.id().toString()).ifPresent(objectsToDelete::add);
                } else if (payload != null && identity == null) {
                    var rawType = payload.get("@type");
                    if (rawType instanceof String type) {
                        this.createEmptyEObject(editingContext, type).ifPresent(newEObject -> {
                            emptyNewObjects.put(newEObject, payload);
                        });
                    }
                } else if (payload != null && identity != null && identity.id() != null) {
                    this.objectSearchService.getObject(editingContext, identity.id().toString())
                        .filter(EObject.class::isInstance)
                        .map(EObject.class::cast)
                        .ifPresent(existingObject -> {
                            objectsToUpdate.put(existingObject, payload);
                        });
                }
            }
            objectsToDelete.forEach(this.editService::delete);

            var newObjectsAttached = new HashSet<EObject>();
            emptyNewObjects.forEach((newEObject, payload) -> this.objectRestDeserializer.fromMap(editingContext, payload, newEObject, emptyNewObjects, newObjectsAttached));
            objectsToUpdate.forEach((existingObject, payload) -> this.objectRestDeserializer.fromMap(editingContext, payload, existingObject, emptyNewObjects, newObjectsAttached));

            emptyNewObjects.forEach((newObject, payload) -> {
                if (!newObjectsAttached.contains(newObject)) {
                    // object with no container, so add it as root object to the first sirius document found
                    this.getResourceSet(editingContext).map(ResourceSet::getResources).stream()
                        .flatMap(Collection::stream)
                        .filter(resource -> resource.getURI().scheme().equals(IEMFEditingContext.RESOURCE_SCHEME))
                        .findFirst()
                        .ifPresent(resource -> {
                            resource.getContents().add(newObject);
                        });
                }
            });
            return new RestCommit(optionalProjectId.get(), DEFAULT_CREATED, DEFAULT_COMMIT_DESCRIPTION, new Identified(optionalProjectId.get()), List.of());
        }
        return null;
    }

    @Override
    public RestCommit getCommitById(IEditingContext editingContext, UUID commitId) {
        RestCommit commit = null;
        var optionalProjectId = this.projectEditingContextService.getProjectId(editingContext.getId())
                .flatMap(new UUIDParser()::parse);
        if (commitId != null && optionalProjectId.isPresent() && commitId.toString().equals(optionalProjectId.get().toString())) {
            var projectId = optionalProjectId.get();
            commit = new RestCommit(commitId, DEFAULT_CREATED, DEFAULT_COMMIT_DESCRIPTION, new Identified(projectId), List.of());
        }
        return commit;
    }

    /**
     * The default implementation retrieves all elements containing in the project, without taking care of data created/updated/deleted since the creation of the project.
     * It is not able to distinguish CREATED, UPDATED or DELETED elements.
     * Furthermore, the DataVersion.id attribute should be randomly generated but constant for an unlimited period.
     * We decided to generate this Id from its commit Id and element Id, to be able to compute it for tests purpose.
     * These are tradeoffs as it does not strictly follow the SystemModelingAPI specification.
     */
    @Override
    public List<RestDataVersion> getCommitChange(IEditingContext editingContext, UUID commitId, List<ChangeType> changeTypes) {
        List<RestDataVersion> dataVersions = new ArrayList<>();
        var changeTypesAllowed = changeTypes == null || changeTypes.isEmpty();
        var optionalProjectId = this.projectEditingContextService.getProjectId(editingContext.getId())
                .flatMap(new UUIDParser()::parse);

        if (commitId != null && optionalProjectId.isPresent() && commitId.toString().equals(optionalProjectId.get().toString()) && changeTypesAllowed) {
            var elements = this.defaultObjectRestService.getElements(editingContext).stream()
                    .filter(EObject.class::isInstance)
                    .map(EObject.class::cast)
                    .toList();
            for (var element : elements) {
                var elementId = this.identityService.getId(element);
                var changeId = UUID.nameUUIDFromBytes((commitId + elementId).getBytes());
                var payload = this.eObjectRestSerializer.toMap(element);
                var dataVersion = new RestDataVersion(changeId, new RestDataIdentity(UUID.fromString(elementId)), payload);
                dataVersions.add(dataVersion);
            }
        }
        return dataVersions;
    }

    @Override
    public RestDataVersion getCommitChangeById(IEditingContext editingContext, UUID commitId, UUID changeId) {
        RestDataVersion dataVersion = null;
        var optionalProjectId = this.projectEditingContextService.getProjectId(editingContext.getId())
                .flatMap(new UUIDParser()::parse);

        if (changeId != null && commitId != null && optionalProjectId.isPresent() && commitId.toString().equals(optionalProjectId.get().toString())) {
            var elements = this.defaultObjectRestService.getElements(editingContext).stream()
                    .filter(EObject.class::isInstance)
                    .map(EObject.class::cast)
                    .toList();
            for (var element : elements) {
                var elementId = this.identityService.getId(element);
                if (elementId != null) {
                    var computedChangeId = UUID.nameUUIDFromBytes((commitId + elementId).getBytes());
                    if (changeId.toString().equals(computedChangeId.toString())) {
                        var payload = this.eObjectRestSerializer.toMap(element);
                        dataVersion = new RestDataVersion(changeId, new RestDataIdentity(UUID.fromString(elementId)), payload);
                        break;
                    }
                }
            }
        }
        return dataVersion;
    }

    /**
     * There is only one branch per project in the default implementation.
     * It represents the current state of the project, without taking care of data created/updated/deleted since the creation of the project.
     * It's creation date/time is the Epoch date/time.
     * It's a tradeoff as it does not strictly follow the SystemModelingAPI specification.
     */
    @Override
    public List<RestBranch> getBranches(IEditingContext editingContext) {
        return this.projectEditingContextService.getProjectId(editingContext.getId())
                .flatMap(new UUIDParser()::parse)
                .map(projectId -> List.of(new RestBranch(projectId, DEFAULT_CREATED, new Identified(projectId), DEFAULT_BRANCH_NAME, new Identified(projectId), new Identified(projectId))))
                .orElse(List.of());
    }

    /**
     * The default implementation does not allow several branches per project.
     */
    @Override
    public RestBranch createBranch(IEditingContext editingContext, String branchName, UUID commitId) {
        return null;
    }

    @Override
    public RestBranch getBranchById(IEditingContext editingContext, UUID branchId) {
        RestBranch branch = null;
        var optionalProjectId = this.projectEditingContextService.getProjectId(editingContext.getId())
                .flatMap(new UUIDParser()::parse);
        if (optionalProjectId.isPresent() && branchId != null && branchId.toString().equals(optionalProjectId.get().toString())) {
            var projectId = optionalProjectId.get();
            branch = new RestBranch(projectId, DEFAULT_CREATED, new Identified(projectId), DEFAULT_BRANCH_NAME, new Identified(projectId), new Identified(projectId));
        }
        return branch;
    }

    /**
     * The default implementation does not allow to delete the only branch in the project.
     */
    @Override
    public RestBranch deleteBranch(IEditingContext editingContext, UUID branchId) {
        return null;
    }

    private Optional<ResourceSet> getResourceSet(IEditingContext editingContext) {
        return Optional.of(editingContext)
            .filter(IEMFEditingContext.class::isInstance)
            .map(IEMFEditingContext.class::cast)
            .map(IEMFEditingContext::getDomain)
            .map(AdapterFactoryEditingDomain::getResourceSet);
    }

    private Optional<EObject> createEmptyEObject(IEditingContext editingContext, String type) {
        return this.getResourceSet(editingContext)
            .map(ResourceSet::getPackageRegistry)
            .map(EPackage.Registry::values)
            .stream()
            .flatMap(Collection::stream)
            .filter(EPackage.class::isInstance)
            .map(EPackage.class::cast)
            .map(ePackage -> Optional.ofNullable(ePackage.getEClassifier(type))
                    .filter(EClass.class::isInstance)
                    .map(EClass.class::cast)
                    .filter(eClass -> !(eClass.isAbstract() || eClass.isInterface())))
            .flatMap(Optional::stream)
            .map(EcoreUtil::create)
            .findFirst();
    }
}
