/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.components.collaborative.representations.migration;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Interface of IRepresentationMigrationParticipant.
 *
 * @author mcharfadi
 */
public interface IRepresentationMigrationParticipant  {
    String getVersion();

    String getKind();

    /**
     * Called before the serialisation of a representation,
     * this can be used to manipulate the Json that will be used for the serialisation.
     * You can modify the hashmap root to update values or attributes
     * root.put("newAttribute", newValue);
     * root.remove("oldAttribute");
     *
     * @param root
     *            the root of a Json node currently parsed.
     * @param currentAttribute
     *            the attributes currently parsed
     * @param currentValue
     *            the value currently parsed
     */
    default void replaceJsonNode(ObjectNode root, String currentAttribute, JsonNode currentValue) {

    }
}