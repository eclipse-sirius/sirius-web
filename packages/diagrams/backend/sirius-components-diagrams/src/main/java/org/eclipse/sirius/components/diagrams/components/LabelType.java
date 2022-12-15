/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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
package org.eclipse.sirius.components.diagrams.components;

/**
 * Enum for types of label.
 *
 * @author fbarbin
 */
public enum LabelType {

    OUTSIDE("label:outside"),
    INSIDE_CENTER("label:inside-center"),
    OUTSIDE_CENTER("label:outside-center"),
    EDGE_BEGIN("label:edge-begin"),
    EDGE_CENTER("label:edge-center"),
    EDGE_END("label:edge-end");

    private final String value;

    LabelType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
