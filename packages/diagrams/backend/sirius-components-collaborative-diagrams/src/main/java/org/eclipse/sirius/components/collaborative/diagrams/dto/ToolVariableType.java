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
package org.eclipse.sirius.components.collaborative.diagrams.dto;

/**
 * Represent the Type of the Tool Variable.
 *
 * @author fbarbin
 */
public enum ToolVariableType {
    /**
     * The Value is a simple string.
     */
    STRING,
    /**
     * The value represents an Object ID.
     */
    OBJECT_ID,
    /**
     * The value represent an Array of Object IDs, separated by a ",".
     */
    OBJECT_ID_ARRAY
}
