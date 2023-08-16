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
 * Alignment possibility for alignItems properties.
 *
 * @author frouene
 */
public enum FlexboxAlignItems {
    stretch("stretch"),
    normal("normal"),
    flexStart("flex-start"),
    flexEnd("flex-end"),
    center("center"),
    start("start"),
    end("end"),
    selfStart("self-start"),
    selfEnd("self-end");

    private final String alignItems;

    FlexboxAlignItems(String alignItems) {
        this.alignItems = Objects.requireNonNull(alignItems);
    }

    @Override
    public String toString() {
        return this.alignItems;
    }
}
