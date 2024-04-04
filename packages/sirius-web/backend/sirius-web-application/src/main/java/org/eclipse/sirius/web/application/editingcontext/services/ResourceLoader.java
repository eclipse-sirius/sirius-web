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
package org.eclipse.sirius.web.application.editingcontext.services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.sirius.components.emf.migration.api.MigrationData;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.components.emf.migration.api.IMigrationParticipant;
import org.eclipse.sirius.components.emf.migration.MigrationService;
import org.eclipse.sirius.web.application.editingcontext.services.api.IResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Service;

/**
 * Used to load documents as resources.
 *
 * @author sbegaudeau
 */
@Service
public class ResourceLoader implements IResourceLoader {

    private final Logger logger = LoggerFactory.getLogger(ResourceLoader.class);

    private final List<IMigrationParticipant> migrationParticipants;

    public ResourceLoader(List<IMigrationParticipant> migrationParticipants) {
        this.migrationParticipants = migrationParticipants;
    }

    @Override
    public Optional<Resource> toResource(ResourceSet resourceSet, String id, String name, String content) {
        Optional<Resource> optionalResource = Optional.empty();

        var migrationExtendedMetaData = new MigrationService(this.migrationParticipants);

        HashMap<Object, Object> options = new HashMap<>();
        options.put(JsonResource.OPTION_EXTENDED_META_DATA, migrationExtendedMetaData);
        options.put(JsonResource.OPTION_JSON_RESSOURCE_PROCESSOR, migrationExtendedMetaData);

        var resource = new JSONResourceFactory().createResourceFromPath(id);

        try (var inputStream = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8))) {
            resourceSet.getResources().add(resource);
            resource.eAdapters().add(new ResourceMetadataAdapter(name));
            resource.load(inputStream, options);

            optionalResource = Optional.of(resource);
        } catch (IOException | IllegalArgumentException exception) {
            this.logger.warn("An error occured while loading document {}: {}.", id, exception.getMessage());
            resourceSet.getResources().remove(resource);
        }

        return optionalResource;
    }

    public Optional<MigrationData> getMigrationDataFromDocumentContent(String content) {
        JsonObject jsonObject = JsonParser.parseString(content).getAsJsonObject();
        return Optional.ofNullable(jsonObject.getAsJsonObject(MigrationData.JSON_OBJECT_ROOT))
                .map(migrationRootElement -> new Gson().fromJson(migrationRootElement, MigrationData.class)).stream().findFirst();
    }
}
