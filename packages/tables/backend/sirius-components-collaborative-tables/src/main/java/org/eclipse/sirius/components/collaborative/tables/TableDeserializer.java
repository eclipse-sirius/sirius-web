/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
package org.eclipse.sirius.components.collaborative.tables;

import java.util.Optional;

import org.eclipse.sirius.components.collaborative.api.IRepresentationDeserializer;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.eclipse.sirius.components.tables.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.node.ObjectNode;

/**
 * Used to deserialize the table representation.
 *
 * @author arichard
 */
@Service
public class TableDeserializer implements IRepresentationDeserializer {

    private final Logger logger = LoggerFactory.getLogger(TableDeserializer.class);

    @Override
    public boolean canHandle(ObjectNode root) {
        return Optional.ofNullable(root.get("kind"))
                .map(JsonNode::asText)
                .filter(Table.KIND::equals)
                .isPresent();
    }

    @Override
    public Optional<IRepresentation> handle(JsonParser jsonParser, DeserializationContext context, ObjectNode root) {
        try {
            return Optional.of(context.readTreeAsValue(root, Table.class));
        } catch (JacksonException exception) {
            this.logger.atWarn()
                    .setMessage("Table deserialization failed")
                    .setCause(exception)
                    .log();
        }

        return Optional.empty();
    }

}
