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

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.web.application.project.data.versioning.dto.RestDataVersion;
import org.eclipse.sirius.web.application.project.data.versioning.services.api.IRestDataVersionPayloadSerializerService;
import org.springframework.stereotype.Service;

/**
 * Specific JSON serializer for the payload attribute of {@link RestDataVersion}.
 *
 * @author arichard
 */
@Service
public class RestDataVersionPayloadSerializer extends JsonSerializer<Object> {

    private final List<IRestDataVersionPayloadSerializerService> restDataVersionPayloadSerializerServices;

    public RestDataVersionPayloadSerializer(List<IRestDataVersionPayloadSerializerService> restDataVersionPayloadSerializerServices) {
        this.restDataVersionPayloadSerializerServices = Objects.requireNonNull(restDataVersionPayloadSerializerServices);
    }

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        var optionalDelegate = this.restDataVersionPayloadSerializerServices.stream()
                .filter(delegate -> delegate.canHandle(value))
                .findFirst();
        if (optionalDelegate.isPresent()) {
            optionalDelegate.get().serialize(value, gen, serializers);
        } else {
            gen.writeNull();
        }
    }
}
