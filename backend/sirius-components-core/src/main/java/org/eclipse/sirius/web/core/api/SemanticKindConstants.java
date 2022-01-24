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
package org.eclipse.sirius.web.core.api;

/**
 * Constants related to the semantic kind.
 *
 * @author sbegaudeau
 */
public final class SemanticKindConstants {
    public static final String PREFIX = "siriusComponents://semantic"; //$NON-NLS-1$

    public static final String DOMAIN_ARGUMENT = "domain"; //$NON-NLS-1$

    public static final String ENTITY_ARGUMENT = "entity"; //$NON-NLS-1$

    private SemanticKindConstants() {
        // Prevent instantiation
    }
}
