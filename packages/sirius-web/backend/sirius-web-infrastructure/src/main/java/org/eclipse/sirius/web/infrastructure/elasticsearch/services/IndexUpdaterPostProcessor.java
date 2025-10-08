/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.web.infrastructure.elasticsearch.services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.change.ChangeKind;
import org.eclipse.emf.ecore.change.FeatureChange;
import org.eclipse.emf.ecore.change.ListChange;
import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.IInputPostProcessor;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.ProjectSemanticData;
import org.eclipse.sirius.web.domain.boundedcontexts.projectsemanticdata.services.api.IProjectSemanticDataSearchService;
import org.eclipse.sirius.web.infrastructure.elasticsearch.services.api.IIndexDocumentCreationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import reactor.core.publisher.Sinks.Many;

/**
 * Watches for changes produced by the processing of inputs and updates the indices accordingly.
 *
 * @author gdaniel
 */
@Service
public class IndexUpdaterPostProcessor implements IInputPostProcessor {

    private final IProjectSemanticDataSearchService projectSemanticDataSearchService;

    private final IIdentityService identityService;

    private final IIndexDocumentCreationService indexDocumentCreationService;

    private final Optional<ElasticsearchClient> optionalElasticSearchClient;

    private final Logger logger = LoggerFactory.getLogger(IndexUpdaterPostProcessor.class);

    public IndexUpdaterPostProcessor(IProjectSemanticDataSearchService projectSemanticDataSearchService, IIdentityService identityService, IIndexDocumentCreationService indexDocumentCreationService, Optional<ElasticsearchClient> optionalElasticSearchClient) {
        this.projectSemanticDataSearchService = Objects.requireNonNull(projectSemanticDataSearchService);
        this.identityService = Objects.requireNonNull(identityService);
        this.indexDocumentCreationService = Objects.requireNonNull(indexDocumentCreationService);
        this.optionalElasticSearchClient = Objects.requireNonNull(optionalElasticSearchClient);
    }

    @Override
    public void postProcess(IEditingContext editingContext, IInput input, Many<ChangeDescription> changeDescriptionSink) {
        if (this.optionalElasticSearchClient.isPresent() && editingContext instanceof EditingContext siriusWebEditingContext) {
            org.eclipse.emf.ecore.change.ChangeDescription emfChangeDescription = siriusWebEditingContext.getInputId2change().get(input.id());
            if (emfChangeDescription != null) {
                Optional<String> optionalProjectId = new UUIDParser().parse(editingContext.getId())
                        .flatMap(semanticDataId -> this.projectSemanticDataSearchService.findBySemanticDataId(AggregateReference.to(semanticDataId)))
                        .map(ProjectSemanticData::getProject)
                        .map(AggregateReference::getId);
                if (optionalProjectId.isPresent()) {
                    List<Object> objectsToUpdate = new ArrayList<>();
                    List<Object> objectsToAdd = new ArrayList<>();
                    List<Object> objectsToRemove = new ArrayList<>();
                    for (Entry<EObject, EList<FeatureChange>> change : emfChangeDescription.getObjectChanges().entrySet()) {
                        EObject changedEObject = change.getKey();
                        objectsToUpdate.add(changedEObject);
                        for (FeatureChange featureChange : change.getValue()) {
                            if (featureChange.getFeature() instanceof EReference eReference && eReference.isContainment()) {
                                this.handleContainmentReferenceChange(changedEObject, eReference, featureChange, objectsToAdd, objectsToRemove);
                            }
                        }
                    }
                    String projectId = optionalProjectId.get();
                    this.removeObjectsFromIndex(projectId, objectsToRemove);
                    this.addObjectsToIndex(projectId, objectsToAdd);
                    this.updateObjectsFromIndex(projectId, objectsToUpdate);
                }
            }
        }
    }

    private void handleContainmentReferenceChange(EObject changedEObject, EReference changedEReference, FeatureChange featureChange, List<Object> objectsToAdd, List<Object> objectsToRemove) {
        if (featureChange.getListChanges().isEmpty()) {
            // The value can be null or an empty list if a reference has been set.
            if (featureChange.getValue() == null || (featureChange.getValue() instanceof List valueList && valueList.isEmpty())) {
                Object changedEReferenceValue = changedEObject.eGet(changedEReference);
                if (changedEReferenceValue instanceof List changedReferenceValueList) {
                    if (changedReferenceValueList.size() > 0) {
                        // An object was added, and since the change description contains a null or empty value, we know
                        // it has been added in first position.
                        objectsToAdd.add(changedReferenceValueList.get(0));
                    }
                } else {
                    objectsToAdd.add(changedEReferenceValue);
                }
            } else {
                objectsToRemove.add(featureChange.getValue());
            }
        }
        for (ListChange listChange : featureChange.getListChanges()) {
            if (listChange.getKind() == ChangeKind.REMOVE_LITERAL) {
                Object addedObject = ((List) changedEObject.eGet(changedEReference)).get(listChange.getIndex());
                objectsToAdd.add(addedObject);
            } else if (listChange.getKind() == ChangeKind.ADD_LITERAL) {
                for (EObject referenceValue : listChange.getReferenceValues()) {
                    objectsToRemove.add(referenceValue);
                    StreamSupport.stream(Spliterators.spliteratorUnknownSize(referenceValue.eAllContents(), Spliterator.ORDERED), false)
                        .forEach(objectsToRemove::add);
                }
            }
            // Discard MOVE_LITERAL, it doesn't impact the content of the index.
        }
    }

    private void removeObjectsFromIndex(String projectId, List<Object> objectsToRemove) {
        this.optionalElasticSearchClient.ifPresent(elasticSearchClient -> {
            for (Object objectToRemove : objectsToRemove) {
                try {
                    elasticSearchClient.delete(delete -> delete.index(ProjectIndexLifecycleManager.PROJECT_INDEX_NAME_PREFIX + projectId)
                            .id(this.identityService.getId(objectToRemove)));
                } catch (IOException exception) {
                    this.logger.warn("An error occurred while removing a document from the index", exception);
                }
            }
        });
    }

    private void addObjectsToIndex(String projectId, List<Object> objectsToAdd) {
        this.optionalElasticSearchClient.ifPresent(elasticSearchClient -> {
            for (Object objectToAdd : objectsToAdd) {
                Optional<JsonObject> optionalIndexDocument = this.indexDocumentCreationService.createDocument(objectToAdd);
                if (optionalIndexDocument.isPresent()) {
                    Gson gson = new Gson();
                    InputStream indexDocumentInputStream = new ByteArrayInputStream(gson.toJson(optionalIndexDocument.get()).getBytes());
                    try {
                        elasticSearchClient.index(index -> index.index(ProjectIndexLifecycleManager.PROJECT_INDEX_NAME_PREFIX + projectId)
                                .id(this.identityService.getId(objectToAdd))
                                .withJson(indexDocumentInputStream));
                    } catch (IOException exception) {
                        this.logger.warn("An error occurred while indexing a document", exception);
                    }
                }
            }
        });
    }

    private void updateObjectsFromIndex(String projectId, List<Object> objectsToUpdate) {
        this.optionalElasticSearchClient.ifPresent(elasticSearchClient -> {
            for (Object objectToUpdate : objectsToUpdate) {
                Optional<JsonObject> optionalIndexDocument = this.indexDocumentCreationService.createDocument(objectToUpdate);
                if (optionalIndexDocument.isPresent()) {
                    JsonObject indexUpdateDocument = new JsonObject();
                    // We have to wrap the document into a "doc" object to perform partial updates.
                    indexUpdateDocument.add("doc", optionalIndexDocument.get());
                    Gson gson = new Gson();
                    InputStream indexUpdateDocumentInputStream = new ByteArrayInputStream(gson.toJson(indexUpdateDocument).getBytes());
                    try {
                        elasticSearchClient.update(index -> index.index(ProjectIndexLifecycleManager.PROJECT_INDEX_NAME_PREFIX + projectId)
                                .id(this.identityService.getId(objectToUpdate))
                                .withJson(indexUpdateDocumentInputStream), Object.class);
                    } catch (IOException exception) {
                        this.logger.warn("An error occurred while updating a document from the index", exception);
                    }
                }
            }
        });
    }
}
