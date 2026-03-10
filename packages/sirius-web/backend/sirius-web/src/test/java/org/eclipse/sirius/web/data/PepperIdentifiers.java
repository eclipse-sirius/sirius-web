/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
 * Used to store some test identifiers related to the PEPPER projects.
 *
 * @author sbegaudeau
 */
public final class PepperIdentifiers {

    public static final UUID PEPPER_EDITING_CONTEXT_ID = UUID.fromString("c67f0c59-6e51-4aac-b771-e267c74a98ea");

    public static final UUID PEPPER_TASK_DOCUMENT = UUID.fromString("53857397-b950-4a4a-8830-cabb7e97d7c2");

    public static final UUID ORGANIZATION_OBJECT = UUID.fromString("c9632f2b-06f7-4f93-8a7a-184cff05029d");

    public static final UUID PROJECT_OBJECT = UUID.fromString("20fcd054-8c2c-45dd-95fe-ee66e20d1d1e");

    public static final UUID WORKPACKAGE_OBJECT = UUID.fromString("b0a16b2a-e08e-4d1a-b839-654820d47cc3");

    private PepperIdentifiers() {
        // Prevent instantiation
    }
}
