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

import org.eclipse.emf.common.util.URI;

/**
 * Used to store some test identifiers related to the migration projects.
 *
 * @author mcharfadi
 */
public final class MigrationIdentifiers {

    public static final UUID MIGRATION_NODE_DESCRIPTION_LABEL_EXPRESSION_STUDIO = UUID.fromString("a3b86086-23f5-41cb-97b9-5ac7234a98af");

    public static final UUID MIGRATION_NODE_STYLE_DESCRIPTION_COLOR_STUDIO = UUID.fromString("14df1eb9-0915-4a62-ba83-b26ce5e2cfe1");

    public static final UUID MIGRATION_NODE_DESCRIPTION_USER_RESIZABLE_STUDIO = UUID.fromString("a5441b64-83a5-4754-8794-57227bf8a322");

    public static final String MIGRATION_NODE_DESCRIPTION_LABEL_EXPRESSION_STUDIO_DIAGRAM = "NodeDescription#labelExpression migration";

    public static final URI MIGRATION_NODE_DESCRIPTION_LABEL_EXPRESSION_STUDIO_DIAGRAM_URI = URI.createURI("sirius:///1d8aac3e-5fe7-4787-b0fc-1f8eb491cd5e");

    public static final String MIGRATION_NODE_STYLE_DESCRIPTION_COLOR_STUDIO_DIAGRAM = "NodeStyleDescription#color migration";

    public static final String MIGRATION_NODE_DESCRIPTION_USER_RESIZABLE_STUDIO_DIAGRAM = "NodeDescription#userResizable migration";

    public static final UUID MIGRATION_STUDIO_DIAGRAM_HIERARCHY = UUID.fromString("35f1cd7b-e5bb-443d-95ef-bab372a92b0f");

    public static final UUID MIGRATION_STUDIO_DIAGRAM = UUID.fromString("9698833e-ffd4-435a-9aec-765622ce524e");

    public static final UUID MIGRATION_DIAGRAM_LABEL_STYLE_BORDER_SIZE_STUDIO = UUID.fromString("8ce6147e-1f5b-426f-b1be-dfeabd37a50a");

    public static final String MIGRATION_DIAGRAM_LABEL_STYLE_BORDER_SIZE_STUDIO_DIAGRAM = "DiagramLabelStyle#borderSize migration";

    public static final UUID MIGRATION_NODE_LABEL_STYLE_SHOW_ICON_STUDIO = UUID.fromString("8e4dc281-b458-4354-b2c8-a03b426b6966");

    public static final String MIGRATION_NODE_LABEL_STYLE_SHOW_ICON_STUDIO_DIAGRAM = "NodeLabelStyle#showIcon migration";

    public static final UUID MIGRATION_SELECTION_DIALOG_DESCRIPTION_SELECTION_CANDIDATES_EXPRESSION_STUDIO = UUID.fromString("19d73d38-3de2-4d03-a8f1-ce36c2ed36db");

    public static final String MIGRATION_SELECTION_DIALOG_DESCRIPTION_SELECTION_CANDIDATES_EXPRESSION_STUDIO_DIAGRAM = "SelectionDialogDescription#selectionCandidatesExpression migration";

    public static final UUID MIGRATION_DIAGRAM_STYLE_DESCRIPTION = UUID.fromString("14df1eb9-0915-4a62-ba83-b26ce5e2cfe1");

    public static final String MIGRATION_DIAGRAM_STYLE_DESCRIPTION_DIAGRAM = "NodeStyleDescription#color migration";

    private MigrationIdentifiers() {
        // Prevent instantiation
    }
}
