/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.components.collaborative.diagrams.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.eclipse.sirius.components.diagrams.INodeStyle;

/**
 * Use to correctly deserialize custom node style.
 *
 * @author frouene
 * @since v2025.10.0
 */
public interface ICustomNodeStyleDeserializer {

    boolean canHandle(String type);

    /**
     * This method should usually return {@code mapper.readValue(root, TheCustomNodeStyle.class)}.
     *
     * @param mapper the current ObjectMapper
     * @param root the current object representing a nodeStyle being deserialized
     * @return the node style after deserialization
     */
    INodeStyle handle(ObjectMapper mapper, String root) throws JsonProcessingException;
}
