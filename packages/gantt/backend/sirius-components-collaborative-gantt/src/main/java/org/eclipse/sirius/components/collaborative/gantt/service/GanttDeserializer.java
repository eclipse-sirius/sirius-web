/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.collaborative.gantt.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.IRepresentationDeserializer;
import org.eclipse.sirius.components.gantt.Gantt;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Used to deserialize a gantt diagram.
 *
 * @author lfasani
 */
@Service
public class GanttDeserializer implements IRepresentationDeserializer {

    private final Logger logger = LoggerFactory.getLogger(GanttDeserializer.class);

    @Override
    public boolean canHandle(ObjectNode root) {
        return Optional.ofNullable(root.get("kind"))
            .map(JsonNode::asText)
            .filter(Gantt.KIND::equals)
            .isPresent();
    }

    @Override
    public Optional<IRepresentation> handle(ObjectMapper mapper, ObjectNode root) {
        try {
            return Optional.of(mapper.readValue(root.toString(), Gantt.class));
        } catch (JsonProcessingException exception) {
            this.logger.warn(exception.getMessage(), exception);
        }

        return Optional.empty();
    }

}
