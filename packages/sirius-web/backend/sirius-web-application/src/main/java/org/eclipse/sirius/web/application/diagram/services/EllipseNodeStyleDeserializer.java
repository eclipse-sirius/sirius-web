/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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
package org.eclipse.sirius.web.application.diagram.services;

import org.eclipse.sirius.components.collaborative.diagrams.api.ICustomNodeStyleDeserializer;
import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.web.application.diagram.EllipseNodeStyle;
import org.springframework.stereotype.Service;

import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.node.ObjectNode;

/**
 *  Implementation of ICustomNodeStyleDeserializer to correctly deserialize EllipseNodeStyle.
 *
 * @author frouene
 */
@Service
public class EllipseNodeStyleDeserializer implements ICustomNodeStyleDeserializer {

    //private final ObjectMapper objectMapper;

    /*public EllipseNodeStyleDeserializer(ObjectMapper objectMapper) {
        //this.objectMapper = Objects.requireNonNull(objectMapper);
    }*/

    @Override
    public boolean canHandle(String type) {
        return type.equals("customnode:ellipse");
    }

    @Override
    public INodeStyle handle(ObjectNode root, JsonParser jsonParser, DeserializationContext context) throws JacksonException {
        return context.readTreeAsValue(root, EllipseNodeStyle.class);
    }
}
