/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
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

import java.util.List;
import java.util.Objects;

/**
 * The description of the child object which can be created for a specific object.
 *
 * @author sbegaudeau
 */
public record ChildCreationDescription(String id, String label, List<String> iconURL) {

    public ChildCreationDescription {
        Objects.requireNonNull(id);
        Objects.requireNonNull(label);
        Objects.requireNonNull(iconURL);
    }
}
