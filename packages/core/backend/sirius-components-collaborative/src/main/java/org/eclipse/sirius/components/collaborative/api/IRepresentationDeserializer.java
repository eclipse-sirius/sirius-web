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
package org.eclipse.sirius.components.collaborative.api;

import java.util.Optional;

import org.eclipse.sirius.components.representations.IRepresentation;

import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.node.ObjectNode;

/**
 * Used to deserialize a representation with Jackson.
 *
 * @author sbegaudeau
 */
public interface IRepresentationDeserializer {

    boolean canHandle(ObjectNode root);

    Optional<IRepresentation> handle(JsonParser jsonParser, DeserializationContext context, ObjectNode root);

}