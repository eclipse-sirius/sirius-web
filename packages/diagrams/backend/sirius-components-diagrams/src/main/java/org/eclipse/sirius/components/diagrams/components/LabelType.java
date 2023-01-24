/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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
    INSIDE_V_TOP_H_CENTER("label:inside-v_top-h_center"),
    INSIDE_V_TOP_H_LEFT("label:inside-v_top-h_left"),
    INSIDE_V_TOP_H_RIGHT("label:inside-v_top-h_right"),
    INSIDE_V_CENTER_H_CENTER("label:inside-v_center-h_center"),
    INSIDE_V_CENTER_H_LEFT("label:inside-v_center-h_left"),
    INSIDE_V_CENTER_H_RIGHT("label:inside-v_center-h_right"),
    INSIDE_V_BOTTOM_H_CENTER("label:inside-v_bottom-h_center"),
    INSIDE_V_BOTTOM_H_LEFT("label:inside-v_bottom-h_left"),
    INSIDE_V_BOTTOM_H_RIGHT("label:inside-v_bottom-h_right"),
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
