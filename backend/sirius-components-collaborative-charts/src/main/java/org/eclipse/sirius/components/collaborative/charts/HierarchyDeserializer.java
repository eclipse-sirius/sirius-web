/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.collaborative.charts;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.eclipse.sirius.components.charts.hierarchy.Hierarchy;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.IRepresentationDeserializer;
import org.eclipse.sirius.components.core.api.IKindParser;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Used to deserialize the hierarchy representation.
 *
 * @author sbegaudeau
 */
@Service
public class HierarchyDeserializer implements IRepresentationDeserializer {

    private final Logger logger = LoggerFactory.getLogger(HierarchyDeserializer.class);

    private final IKindParser kindParser;

    public HierarchyDeserializer(IKindParser kindParser) {
        this.kindParser = Objects.requireNonNull(kindParser);
    }

    @Override
    public boolean canHandle(ObjectNode root) {
        // @formatter:off
        return Optional.ofNullable(root.get("kind")) //$NON-NLS-1$
                .map(JsonNode::asText)
                .filter(this::isHierarchyRepresentation)
                .isPresent();
        // @formatter:on
    }

    private boolean isHierarchyRepresentation(String kind) {
        String type = this.kindParser.getParameterValues(kind).get("type").get(0); //$NON-NLS-1$
        return type.equals("ForceDirectedTree") || type.equals("TreeMap") || type.equals("ZoomableCirclePacking"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    @Override
    public Optional<IRepresentation> handle(ObjectMapper mapper, ObjectNode root) {
        try {
            return Optional.of(mapper.readValue(root.toString(), Hierarchy.class));
        } catch (JsonProcessingException exception) {
            this.logger.warn(exception.getMessage(), exception);
        }

        return Optional.empty();
    }

}
