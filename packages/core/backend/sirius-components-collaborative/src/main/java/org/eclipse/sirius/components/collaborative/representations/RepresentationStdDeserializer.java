/*******************************************************************************
 * Copyright (c) 2019, 2026 Obeo.
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
package org.eclipse.sirius.components.collaborative.representations;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.IRepresentationDeserializer;
import org.eclipse.sirius.components.representations.IRepresentation;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.deser.std.StdDeserializer;
import tools.jackson.databind.node.ObjectNode;

/**
 * Custom deserializer to customize the ObjectMapper.
 *
 * @author gcoutable
 */
public class RepresentationStdDeserializer extends StdDeserializer<IRepresentation> implements Serializable {

    private final List<IRepresentationDeserializer> representationDeserializers;

    public RepresentationStdDeserializer(List<IRepresentationDeserializer> representationDeserializers) {
        this(IRepresentation.class, representationDeserializers);
    }

    public RepresentationStdDeserializer(Class<?> valueClass, List<IRepresentationDeserializer> representationDeserializers) {
        super(valueClass);
        this.representationDeserializers = Objects.requireNonNull(representationDeserializers);
    }

    @Override
    public IRepresentation deserialize(JsonParser jsonParser, DeserializationContext context) throws JacksonException {
        ObjectNode root = (ObjectNode) context.readTree(jsonParser);

        return this.representationDeserializers.stream()
                .filter(representationDeserializer -> representationDeserializer.canHandle(root))
                .findFirst()
                .flatMap(representationDeserializer -> representationDeserializer.handle(jsonParser, context, root))
                .orElse(null);
    }
}