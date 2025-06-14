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

import java.util.Objects;

/**
 * Used to represent a domain in the GraphQL API.
 *
 * @author lfasani
 */
public record Domain(String id, String label) {
    public Domain {
        Objects.requireNonNull(id);
        Objects.requireNonNull(label);
    }
}
