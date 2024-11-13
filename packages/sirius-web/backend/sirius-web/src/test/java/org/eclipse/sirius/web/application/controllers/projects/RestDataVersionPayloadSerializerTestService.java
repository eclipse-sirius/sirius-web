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
package org.eclipse.sirius.web.application.controllers.projects;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.web.EObjectJsonSerializer;
import org.eclipse.sirius.web.application.project.data.versioning.services.api.IRestDataVersionPayloadSerializerService;
import org.springframework.stereotype.Service;

/**
 * Test implementation of {@link IRestDataVersionPayloadSerializerService}. Used indirectly by
 * {@link CommitRestControllerIntegrationTests}.
 *
 * @author arichard
 */
@Service
public class RestDataVersionPayloadSerializerTestService implements IRestDataVersionPayloadSerializerService {

    private EObjectJsonSerializer eObjectJsonSerializer;

    public RestDataVersionPayloadSerializerTestService() {
        this.eObjectJsonSerializer = new EObjectJsonSerializer();
    }

    @Override
    public boolean canHandle(Object object) {
        return object instanceof EObject;
    }

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        this.eObjectJsonSerializer.serialize((EObject) value, gen, serializers);
    }
}
