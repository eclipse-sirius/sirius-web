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
package org.eclipse.sirius.components.collaborative.api;

import java.util.Objects;

/**
 * An object containing representation description data.
 *
 * @author arichard
 */
public class RepresentationDescriptionMetadata {

    private final String id;

    private final String label;

    private final String defaultName;

    public RepresentationDescriptionMetadata(String id, String label, String defaultName) {
        this.id = Objects.requireNonNull(id);
        this.label = Objects.requireNonNull(label);
        this.defaultName = Objects.requireNonNull(defaultName);
    }

    public String getId() {
        return this.id;
    }

    public String getLabel() {
        return this.label;
    }

    public String getDefaultName() {
        return this.defaultName;
    }
}