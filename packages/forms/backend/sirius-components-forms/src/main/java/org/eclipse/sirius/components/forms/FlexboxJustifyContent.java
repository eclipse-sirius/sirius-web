/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.forms;

import java.util.Objects;

/**
 * Alignment possibility for justifyContent properties.
 *
 * @author frouene
 */
public enum FlexboxJustifyContent {
    stretch("stretch"),
    start("start"),
    center("center"),
    end("end"),
    flexStart("flex-start"),
    flexEnd("flex-end"),
    left("left"),
    right("right"),
    normal("normal"),
    spaceBetween("space-between"),
    spaceAround("space-around"),
    spaceEvenly("space-evenly");

    private final String justifyContent;

    FlexboxJustifyContent(String justifyContent) {
        this.justifyContent = Objects.requireNonNull(justifyContent);
    }

    @Override
    public String toString() {
        return this.justifyContent;
    }
}
