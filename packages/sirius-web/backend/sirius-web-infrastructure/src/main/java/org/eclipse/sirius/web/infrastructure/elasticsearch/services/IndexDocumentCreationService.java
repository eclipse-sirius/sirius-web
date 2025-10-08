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

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Map;
import java.util.Map.Entry;
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
public class IndexDocumentCreationService implements IIndexDocumentCreationService {

    private final Logger logger = LoggerFactory.getLogger(IndexDocumentCreationService.class);


    @Override
    public Optional<JsonObject> createDocument(Object object) {
        Optional<JsonObject> result = Optional.empty();
        if (object instanceof EObject eObject) {
            Resource resource = eObject.eResource();
            if (resource instanceof JsonResource jsonResource) {
                Map<Object, Object> emfJsonOptions = Map.of(
                        JsonResource.OPTION_ID_MANAGER, new EObjectIDManager(),
                        JsonResource.OPTION_SCHEMA_LOCATION, true,
                        JsonResource.OPTION_CUSTOM_HELPER, new JsonHelper(jsonResource)
                );
                String json = JsonResourceImpl.toJson(eObject, emfJsonOptions);
                JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
                jsonObject.remove("json");
                jsonObject.remove("ns");
                JsonArray contentArray = jsonObject.getAsJsonArray("content");
                if (contentArray.size() != 1) {
                    this.logger.warn("The content of the json object contains more than 1 element, only the first one will be indexed");
                }
                JsonObject contentObject = contentArray.get(0).getAsJsonObject();
                // Flatten the object and get rid of containment sub-objects: we just want to keep the id of the objects in the containment structure.
                contentObject.entrySet().forEach(entry -> {
                    if (entry.getKey().equals("data")) {
                        JsonObject dataObject = entry.getValue().getAsJsonObject();
                        dataObject.entrySet().forEach(dataEntry -> {
                            this.handleDataEntry(dataEntry, eObject, jsonObject);
                        });
                    } else {
                        jsonObject.add(entry.getKey(), entry.getValue());
                    }

                });
                jsonObject.remove("content");
                result = Optional.of(jsonObject);
            }
        }
        return result;
    }

    @SuppressWarnings("checkstyle:MultipleStringLiterals")
    private void handleDataEntry(Entry<String, JsonElement> dataEntry, EObject eObject, JsonObject result) {
        EStructuralFeature feature = eObject.eClass().getEStructuralFeature(dataEntry.getKey());
        if (feature instanceof EReference eReference) {
            if (eReference.isContainment()) {
                JsonElement eReferenceElement = dataEntry.getValue();
                if (eReferenceElement instanceof JsonArray eReferenceArray) {
                    JsonArray referenceIdArray = new JsonArray();
                    for (JsonElement eReferenceArrayElement : eReferenceArray) {
                        if (eReferenceArrayElement instanceof JsonObject eReferenceArrayObject) {
                            JsonObject newEReferenceObject = new JsonObject();
                            newEReferenceObject.add("id", eReferenceArrayObject.get("id").getAsJsonPrimitive());
                            referenceIdArray.add(newEReferenceObject);
                        }
                    }
                    result.add(dataEntry.getKey(), referenceIdArray);
                } else if (eReferenceElement instanceof JsonObject eReferenceObject) {
                    JsonObject newEReferenceObject = new JsonObject();
                    newEReferenceObject.add("id", eReferenceObject.get("id").getAsJsonPrimitive());
                    result.add(dataEntry.getKey(), newEReferenceObject);
                } else {
                    this.logger.warn("Cannot process reference value" + eReferenceElement.getClass().getSimpleName());
                }
            } else {
                result.add(dataEntry.getKey(), dataEntry.getValue());
            }
        } else {
            result.add(dataEntry.getKey(), dataEntry.getValue());
        }
    }

}
