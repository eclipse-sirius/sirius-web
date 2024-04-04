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
 * Used to store some test identifiers related to the migration projects.
 *
 * @author mcharfadi
 */
public final class MigrationIdentifiers {

    public static final UUID MIGRATION_STUDIO = UUID.fromString("a3b86086-23f5-41cb-97b9-5ac7234a98af");

    public static final String MIGRATION_STUDIO_DIAGRAM = "NodeDescription#labelExpression migration";

    private MigrationIdentifiers() {
        // Prevent instantiation
    }
}
