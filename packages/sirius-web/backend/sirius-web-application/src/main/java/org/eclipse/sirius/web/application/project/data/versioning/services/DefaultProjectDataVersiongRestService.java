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
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.dto.Identified;
import org.eclipse.sirius.web.application.editingcontext.services.api.IEditingContextApplicationService;
import org.eclipse.sirius.web.application.object.services.api.IDefaultObjectRestService;
import org.eclipse.sirius.web.application.project.data.versioning.dto.ChangeType;
import org.eclipse.sirius.web.application.project.data.versioning.dto.RestBranch;
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

    private static final String DEFAULT_COMMIT_DESCRIPTION = "The one and only commit for this project";

    private static final String DEFAULT_BRANCH_NAME = "defaultBranch";

    private static final String ID_PROPERTY = "@id";

    private final IDefaultObjectRestService defaultObjectRestService;

    private final IObjectService objectService;

    private final IEditService editService;

    private final IEditingContextApplicationService editingContextApplicationService;

    public DefaultProjectDataVersiongRestService(IDefaultObjectRestService defaultObjectRestService, IObjectService objectService, IEditService editService, IEditingContextApplicationService editingContextApplicationService) {
        this.defaultObjectRestService = Objects.requireNonNull(defaultObjectRestService);
        this.objectService = Objects.requireNonNull(objectService);
        this.editService = Objects.requireNonNull(editService);
        this.editingContextApplicationService = Objects.requireNonNull(editingContextApplicationService);
    }

    /**
     * There is only one commit per project in the default implementation.
     * It represents the current state of the project, without taking care of data created/updated/deleted since the creation of the project.
     * It's creation date/time is the Epoch date/time.
     * It's a tradeoff as it does not strictly follow the SystemModelingAPI specification.
     */
    @Override
    public List<RestCommit> getCommits(IEditingContext editingContext) {
        return this.editingContextApplicationService.getProjectId(editingContext.getId())
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
        var optionalProjectId = this.editingContextApplicationService.getProjectId(editingContext.getId())
                .flatMap(new UUIDParser()::parse);
        if (optionalProjectId.isPresent()) {
            for (RestDataVersion dataVersion : changes) {
                var payload = dataVersion.payload();
                var identity = dataVersion.identity();
                if (payload == null && identity != null && identity.id() != null) {
                    this.objectService.getObject(editingContext, identity.id().toString())
                        .ifPresent(object -> objectsToDelete.add(object));
                } else if (payload != null && identity == null) {
                    var rawType = payload.get("@type");
                    if (rawType instanceof String type) {
                        this.createEmptyEObject(editingContext, type).ifPresent(newEObject -> {
                            emptyNewObjects.put(newEObject, payload);
                        });
                    }
                } else if (payload != null && identity != null && identity.id() != null) {
                    this.objectService.getObject(editingContext, identity.id().toString())
                        .filter(EObject.class::isInstance)
                        .map(EObject.class::cast)
                        .ifPresent(existingObject -> {
                            objectsToUpdate.put(existingObject, payload);
                        });
                }
            }
            objectsToDelete.forEach(obj -> {
                this.editService.delete(obj);
            });
            var newObjectsAttached = new HashSet<EObject>();
            emptyNewObjects.forEach((newEObject, payload) -> {
                payload.entrySet().forEach(feature -> {
                    this.deserializePayload(editingContext, newEObject, feature, emptyNewObjects, newObjectsAttached);
                });
            });
            objectsToUpdate.forEach((existingObject, payload) -> {
                payload.entrySet().forEach(feature -> {
                    this.deserializePayload(editingContext, existingObject, feature, emptyNewObjects, newObjectsAttached);
                });
            });
            emptyNewObjects.forEach((newObject, payload) -> {
                if (!newObjectsAttached.contains(newObject)) {
                    // object with no container, so add it as root object to the first sirius document found
                    this.getResourceSet(editingContext).map(ResourceSet::getResources).stream()
                        .flatMap(Collection::stream)
                        .filter(r -> r.getURI().scheme().equals(IEMFEditingContext.RESOURCE_SCHEME))
                        .findFirst()
                        .ifPresent(r -> {
                            r.getContents().add(newObject);
                        });
                }
            });
            var commit = new RestCommit(optionalProjectId.get(), DEFAULT_CREATED, DEFAULT_COMMIT_DESCRIPTION, new Identified(optionalProjectId.get()), List.of());
            return commit;
        }
        return null;
    }

    @Override
    public RestCommit getCommitById(IEditingContext editingContext, UUID commitId) {
        RestCommit commit = null;
        var optionalProjectId = this.editingContextApplicationService.getProjectId(editingContext.getId())
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
        var optionalProjectId = this.editingContextApplicationService.getProjectId(editingContext.getId())
                .flatMap(new UUIDParser()::parse);

        if (commitId != null && optionalProjectId.isPresent() && commitId.toString().equals(optionalProjectId.get().toString()) && changeTypesAllowed) {
            var elements = this.defaultObjectRestService.getElements(editingContext).stream()
                    .filter(EObject.class::isInstance)
                    .map(EObject.class::cast)
                    .toList();
            for (var element : elements) {
                var elementId = this.objectService.getId(element);
                var changeId = UUID.nameUUIDFromBytes((commitId + elementId).getBytes());
                var payload = this.serializePayload(element);
                var dataVersion = new RestDataVersion(changeId, new RestDataIdentity(UUID.fromString(elementId)), payload);
                dataVersions.add(dataVersion);
            }
        }
        return dataVersions;
    }

    @Override
    public RestDataVersion getCommitChangeById(IEditingContext editingContext, UUID commitId, UUID changeId) {
        RestDataVersion dataVersion = null;
        var optionalProjectId = this.editingContextApplicationService.getProjectId(editingContext.getId())
                .flatMap(new UUIDParser()::parse);

        if (changeId != null && commitId != null && optionalProjectId.isPresent() && commitId.toString().equals(optionalProjectId.get().toString())) {
            var elements = this.defaultObjectRestService.getElements(editingContext).stream()
                    .filter(EObject.class::isInstance)
                    .map(EObject.class::cast)
                    .toList();
            for (var element : elements) {
                var elementId = this.objectService.getId(element);
                if (elementId != null) {
                    var computedChangeId = UUID.nameUUIDFromBytes((commitId + elementId).getBytes());
                    if (changeId.toString().equals(computedChangeId.toString())) {
                        var payload = this.serializePayload(element);
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
        return this.editingContextApplicationService.getProjectId(editingContext.getId())
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
        var optionalProjectId = this.editingContextApplicationService.getProjectId(editingContext.getId())
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
        Optional<EObject> newEObjectOpt = this.getResourceSet(editingContext)
            .map(ResourceSet::getPackageRegistry)
            .map(EPackage.Registry::values)
            .stream()
            .flatMap(Collection::stream)
            .filter(EPackage.class::isInstance)
            .map(EPackage.class::cast)
            .map(ePkg -> Optional.ofNullable(ePkg.getEClassifier(type))
                    .filter(EClass.class::isInstance)
                    .map(EClass.class::cast)
                    .filter(eClass -> !(eClass.isAbstract() || eClass.isInterface())))
            .flatMap(Optional::stream)
            .map(EcoreUtil::create)
            .findFirst();
        return newEObjectOpt;
    }

    private void deserializePayload(IEditingContext editingContext, EObject eObject, Entry<String, Object> feature, Map<EObject, Map<String, Object>> emptyNewObjects, Set<EObject> newObjectsAttached) {
        var eStructuralFeature = eObject.eClass().getEStructuralFeature(feature.getKey());
        if (eStructuralFeature instanceof EAttribute eAttribute && !eAttribute.isDerived() && !eAttribute.isMany()) {
            eObject.eSet(eStructuralFeature, EcoreUtil.createFromString(eAttribute.getEAttributeType(), feature.getValue().toString()));
        } else if (eStructuralFeature instanceof EAttribute eAttribute && !eAttribute.isDerived() && eAttribute.isMany()) {
            eObject.eSet(eStructuralFeature, EcoreUtil.createFromString(eAttribute.getEAttributeType(), feature.getValue().toString()));
        } else if (eStructuralFeature instanceof EReference eReference && !eReference.isDerived() && !eReference.isMany()) {
            var value = feature.getValue();
            if (value instanceof Map map) {
                var elementId = Optional.ofNullable(map.get(ID_PROPERTY)).filter(String.class::isInstance).map(String.class::cast).orElse("");
                var element = this.objectService.getObject(editingContext, elementId.toString());
                if (element.isPresent()) {
                    eObject.eSet(eStructuralFeature, element.get());
                } else {
                    emptyNewObjects.entrySet().stream()
                        .filter(newObjEntry -> {
                            Map<String, Object> features = newObjEntry.getValue();
                            var rawId = features.get(ID_PROPERTY);
                            return rawId instanceof String id && Objects.equals(id, elementId);
                        })
                        .findFirst()
                        .ifPresent(newObjEntry -> {
                            eObject.eSet(eStructuralFeature, newObjEntry.getKey());
                            if (eReference.isContainment()) {
                                newObjectsAttached.add(newObjEntry.getKey());
                            }
                        });
                }
            }
        } else if (eStructuralFeature instanceof EReference eReference && !eReference.isDerived() && eReference.isMany()) {
            var value = feature.getValue();
            if (value instanceof List<?> valueIdentities) {
                valueIdentities.stream()
                    .filter(Map.class::isInstance)
                    .map(Map.class::cast)
                    .map(m -> m.get(ID_PROPERTY))
                    .filter(String.class::isInstance)
                    .map(String.class::cast)
                    .forEach(elementId -> {
                        var element = this.objectService.getObject(editingContext, elementId);
                        if (element.isPresent()) {
                            var existingValues = eObject.eGet(eStructuralFeature);
                            if (existingValues instanceof List<?> existingValuesAsList) {
                                ((List<Object>) existingValuesAsList).add(element.get());
                            }
                        } else {
                            emptyNewObjects.entrySet().stream()
                                .filter(newObjEntry -> {
                                    var features = newObjEntry.getValue();
                                    var rawId = features.get(ID_PROPERTY);
                                    return rawId instanceof String id && Objects.equals(id, elementId);
                                })
                                .findFirst()
                                .ifPresent(newObjEntry -> {
                                    var existingValues = eObject.eGet(eStructuralFeature);
                                    if (existingValues instanceof List<?> existingValuesAsList) {
                                        ((List<Object>) existingValuesAsList).add(newObjEntry.getKey());
                                    }
                                    if (eReference.isContainment()) {
                                        newObjectsAttached.add(newObjEntry.getKey());
                                    }
                                });
                        }
                    });
            }
        }
    }

    private Map<String, Object> serializePayload(EObject element) {
        Map<String, Object> payload = new LinkedHashMap<>();
        var id = this.objectService.getId(element);
        if (id != null) {
            payload.put(ID_PROPERTY, id);
        }
        payload.put("@type", element.eClass().getName());
        element.eClass().getEAllStructuralFeatures().stream()
            .filter(eSF -> !eSF.isDerived())
            .forEach(eSF -> {
                var payloadFeature = this.serializePayloadFeature(element, eSF);
                if (payloadFeature != null) {
                    payload.put(payloadFeature.getKey(), payloadFeature.getValue());
                }
            });
        return payload;
    }

    private Entry<String, Object> serializePayloadFeature(EObject element, EStructuralFeature eSF) {
        Entry<String, Object> entry = null;
        var featureValue = element.eGet(eSF);
        if (featureValue != null && eSF instanceof EAttribute eAttribute && !eAttribute.isMany()) {
            var value = EcoreUtil.convertToString(eAttribute.getEAttributeType(), featureValue);
            if (value != null && !value.isEmpty()) {
                entry = new SimpleImmutableEntry<>(eSF.getName(), value);
            }
        } else if (featureValue != null && eSF instanceof EReference eReference && !eReference.isMany()) {
            var featureValueId = this.objectService.getId(featureValue);
            if (featureValueId != null) {
                entry = new SimpleImmutableEntry<>(eSF.getName(), new Identified(UUID.fromString(featureValueId)));
            }
        } else if (eSF.isMany() && featureValue instanceof List<?> featureListValue && !featureListValue.isEmpty()) {
            var identitiesList = new ArrayList<>();
            featureListValue.forEach(i -> {
                var featureValueId = this.objectService.getId(i);
                if (featureValueId != null) {
                    identitiesList.add(new Identified(UUID.fromString(featureValueId)));
                }
            });
            entry = new SimpleImmutableEntry<>(eSF.getName(), identitiesList);
        }
        return entry;
    }
}
