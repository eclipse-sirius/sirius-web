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
package org.eclipse.sirius.web.data;

import java.util.UUID;

/**
 * Used to store some test identifiers related to the Papaya Sample project.
 *
 * @author sbegaudeau
 */
public final class PapayaSampleIdentifiers {

    public static final UUID PAPAYA_PROJECT = UUID.fromString("c3d7df85-e0bd-472c-aec1-c05cc88276e4");

    public static final UUID ROOT_OBJECT = UUID.fromString("aa0b7b22-ade2-4148-9ee2-c5972bd72ab7");

    public static final UUID FIRST_TASK_OBJECT = UUID.fromString("31395bb3-1e09-42a5-b450-034955c45ac2");

    public static final UUID SIRIUS_COMPONENTS_REPRESENTATIONS_OBJECT = UUID.fromString("48f65c15-6655-41c8-8771-b15411b69137");

    private PapayaSampleIdentifiers() {
        // Prevent instantiation
    }
}
