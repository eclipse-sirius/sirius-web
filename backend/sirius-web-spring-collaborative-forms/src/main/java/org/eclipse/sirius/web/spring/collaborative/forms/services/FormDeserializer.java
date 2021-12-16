/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.spring.collaborative.forms.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.web.forms.Form;
import org.eclipse.sirius.web.representations.IRepresentation;
import org.eclipse.sirius.web.spring.collaborative.api.IRepresentationDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Used to deserialize a form.
 *
 * @author sbegaudeau
 */
@Service
public class FormDeserializer implements IRepresentationDeserializer {

    private final Logger logger = LoggerFactory.getLogger(FormDeserializer.class);

    @Override
    public Optional<Class<? extends IRepresentation>> getImplementationClass(String kind) {
        if (Objects.equals(Form.KIND, kind)) {
            return Optional.of(Form.class);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean canHandle(ObjectNode root) {
        // @formatter:off
        return Optional.ofNullable(root.get("kind")) //$NON-NLS-1$
                .map(JsonNode::asText)
                .filter(Form.KIND::equals)
                .isPresent();
        // @formatter:on
    }

    @Override
    public Optional<IRepresentation> handle(ObjectMapper mapper, ObjectNode root) {
        try {
            return Optional.of(mapper.readValue(root.toString(), Form.class));
        } catch (JsonProcessingException exception) {
            this.logger.warn(exception.getMessage(), exception);
        }

        return Optional.empty();
    }

}
