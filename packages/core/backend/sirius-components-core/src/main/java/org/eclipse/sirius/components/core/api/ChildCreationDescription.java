/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
package org.eclipse.sirius.components.core.api;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

/**
 * The description of the child object which can be created for a specific object.
 *
 * @author sbegaudeau
 */
public class ChildCreationDescription {

    private final String id;

    private final String label;

    private final List<String> iconURL;

    public ChildCreationDescription(String id, String label, List<String> iconURL) {
        this.id = Objects.requireNonNull(id);
        this.label = Objects.requireNonNull(label);
        this.iconURL = Objects.requireNonNull(iconURL);
    }

    public String getId() {
        return this.id;
    }

    public String getLabel() {
        return this.label;
    }

    public List<String> getIconURL() {
        return this.iconURL;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label);
    }
}
