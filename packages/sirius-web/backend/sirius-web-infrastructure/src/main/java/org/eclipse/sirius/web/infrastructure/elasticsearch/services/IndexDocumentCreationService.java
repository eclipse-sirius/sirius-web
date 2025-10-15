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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.components.emf.services.EObjectIDManager;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.emfjson.resource.JsonResourceImpl;
import org.eclipse.sirius.emfjson.utils.JsonHelper;
import org.eclipse.sirius.web.infrastructure.elasticsearch.services.api.IIndexDocumentCreationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Produces documents to index.
 *
 * @author gdaniel
 */
@Service
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class IndexDocumentCreationService implements IIndexDocumentCreationService {

    private final ObjectMapper objectMapper;

    private final Logger logger = LoggerFactory.getLogger(IndexDocumentCreationService.class);

    public IndexDocumentCreationService(ObjectMapper objectMapper) {
        this.objectMapper = Objects.requireNonNull(objectMapper);
    }

    @Override
    public Optional<byte[]> createDocument(Object object) {
        Optional<byte[]> result = Optional.empty();
        if (object instanceof EObject eObject) {
            Resource resource = eObject.eResource();
            if (resource instanceof JsonResource jsonResource) {
                Map<Object, Object> emfJsonOptions = Map.of(
                        JsonResource.OPTION_ID_MANAGER, new EObjectIDManager(),
                        JsonResource.OPTION_SCHEMA_LOCATION, true,
                        JsonResource.OPTION_CUSTOM_HELPER, new JsonHelper(jsonResource)
                );
                String json = JsonResourceImpl.toJson(eObject, emfJsonOptions);
                try {
                    ObjectNode jsonObject = (ObjectNode) this.objectMapper.readTree(json);
                    ObjectNode updatedJsonObject = this.handleJsonObject(jsonObject, eObject);
                    result = Optional.of(this.objectMapper.writeValueAsBytes(updatedJsonObject));
                } catch (JsonProcessingException exception) {
                    this.logger.warn("Cannot create document", exception);
                }
            }
        }
        return result;
    }

    private ObjectNode handleJsonObject(ObjectNode jsonObject, EObject eObject) {
        jsonObject.remove("json");
        jsonObject.remove("ns");
        ArrayNode contentArray = (ArrayNode) jsonObject.get("content");
        if (contentArray.size() != 1) {
            this.logger.warn("The content of the json object contains more than 1 element, only the first one will be indexed");
        }
        ObjectNode contentObject = (ObjectNode) contentArray.get(0);
        // Flatten the object and get rid of containment sub-objects: we just want to keep the id of the objects in the containment structure.
        contentObject.properties().forEach(entry -> {
            if (entry.getKey().equals("data")) {
                ObjectNode dataObject = (ObjectNode) entry.getValue();
                dataObject.properties().forEach(dataEntry -> {
                    this.handleDataEntry(dataEntry, eObject, jsonObject);
                });
            } else {
                jsonObject.set(entry.getKey(), entry.getValue());
            }

        });
        jsonObject.remove("content");
        return jsonObject;
    }

    private void handleDataEntry(Entry<String, JsonNode> dataEntry, EObject eObject, ObjectNode result) {
        EStructuralFeature feature = eObject.eClass().getEStructuralFeature(dataEntry.getKey());
        if (feature instanceof EReference eReference) {
            if (eReference.isContainment()) {
                JsonNode eReferenceElement = dataEntry.getValue();
                if (eReferenceElement instanceof ArrayNode eReferenceArray) {
                    ArrayNode referenceIdArray = this.objectMapper.createArrayNode();
                    for (JsonNode eReferenceArrayElement : eReferenceArray) {
                        if (eReferenceArrayElement instanceof ObjectNode eReferenceArrayObject) {
                            referenceIdArray.add(eReferenceArrayObject.get("id").asText());
                        }
                    }
                    result.set(dataEntry.getKey(), referenceIdArray);
                } else if (eReferenceElement instanceof ObjectNode eReferenceObject) {
                    result.put(dataEntry.getKey(), eReferenceObject.get("id").asText());
                } else {
                    this.logger.warn("Cannot process reference value" + eReferenceElement.getClass().getSimpleName());
                }
            } else {
                result.set(dataEntry.getKey(), dataEntry.getValue());
            }
        } else {
            result.set(dataEntry.getKey(), dataEntry.getValue());
        }
    }

}
