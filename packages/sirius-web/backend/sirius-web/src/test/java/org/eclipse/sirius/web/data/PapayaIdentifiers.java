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
 * Used to store some test identifiers related to the Papaya projects.
 *
 * @author sbegaudeau
 */
public final class PapayaIdentifiers {

    public static final UUID PAPAYA_EDITING_CONTEXT_ID = UUID.fromString("cc89c500-c27e-4968-9c67-15cf767c6ef0");

    public static final UUID PROJECT_OBJECT = UUID.fromString("aa0b7b22-ade2-4148-9ee2-c5972bd72ab7");

    public static final UUID SIRIUS_WEB_PLANNING_PROJECT_OBJECT = UUID.fromString("1135f77e-0d16-4b10-8a4b-c492ac80221f");

    public static final UUID FIRST_TASK_OBJECT = UUID.fromString("31395bb3-1e09-42a5-b450-034955c45ac2");

    public static final UUID FIRST_ITERATION_OBJECT = UUID.fromString("8a8e1113-b7ce-4549-a80d-91349879e3d8");

    public static final UUID SECOND_ITERATION_OBJECT = UUID.fromString("87a8edac-9b4e-40a4-9a5c-672602bf6792");

    public static final UUID SIRIUS_COMPONENTS_REPRESENTATIONS_OBJECT = UUID.fromString("48f65c15-6655-41c8-8771-b15411b69137");

    public static final UUID SIRIUS_WEB_DOMAIN_OBJECT = UUID.fromString("fad0f4c9-e668-44f3-8deb-aef0edb6ddff");

    public static final UUID SIRIUS_WEB_DOMAIN_PACKAGE = UUID.fromString("569d3f9b-2a43-4254-b609-511258251d96");

    public static final UUID PAPAYA_PACKAGE_TABLE_REPRESENTATION = UUID.fromString("dd0080f8-430d-441f-99a4-f46c7d9b28ef");

    private PapayaIdentifiers() {
        // Prevent instantiation
    }
}
