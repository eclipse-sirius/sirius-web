/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
 * @author ncouvert
 */
public final class PepperIdentifiers {

    public static final UUID PEPPER_EDITING_CONTEXT_ID = UUID.fromString("d98557ca-e6fa-4943-ac47-99135429ec34");

    public static final UUID PEPPER_TASK_DOCUMENT = UUID.fromString("d11614ec-04f7-40bf-9d4f-16162c7b55d7");

    public static final UUID ORGANIZATION_OBJECT = UUID.fromString("3eb378ca-6303-4db4-b758-55bddb328438");

    public static final UUID PROJECT_OBJECT = UUID.fromString("9bd5554c-b46b-48c5-a16b-9badbedfd088");

    public static final UUID WORKPACKAGE_OBJECT = UUID.fromString("ad4fed5b-f8ec-4a69-9ada-a369f17c557b");

    private PepperIdentifiers() {
        // Prevent instantiation
    }
}
