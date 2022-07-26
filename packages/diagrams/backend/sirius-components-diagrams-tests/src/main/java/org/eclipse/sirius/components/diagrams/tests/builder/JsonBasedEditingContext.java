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
package org.eclipse.sirius.components.diagrams.tests.builder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Creates a basic editing context from json.
 *
 * @author gcoutable
 */
public class JsonBasedEditingContext implements IEditingContext {

    private final Logger logger = LoggerFactory.getLogger(JsonBasedEditingContext.class);

    private final UUID id;

    private Element root;

    public JsonBasedEditingContext(String json) {
        this.id = UUID.randomUUID();
        try {
            this.root = new ObjectMapper().readValue(json, Element.class);
        } catch (JsonProcessingException exception) {
            this.logger.warn(exception.getMessage(), exception);
        }
    }

    public JsonBasedEditingContext(Path path) {
        this.id = UUID.randomUUID();
        try {
            String content = Files.readString(path);
            this.root = new ObjectMapper().readValue(content, Element.class);
        } catch (IOException exception) {
            this.logger.warn(exception.getMessage(), exception);
        }
    }

    @Override
    public String getId() {
        return this.id.toString();
    }

    public Element getContent() {
        return this.root;
    }
}
