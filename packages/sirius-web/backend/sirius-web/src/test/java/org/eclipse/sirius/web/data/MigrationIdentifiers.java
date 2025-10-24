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

import org.eclipse.emf.common.util.URI;

/**
 * Used to store some test identifiers related to the migration projects.
 *
 * @author mcharfadi
 */
public final class MigrationIdentifiers {

    public static final UUID MIGRATION_NODE_DESCRIPTION_LABEL_EXPRESSION_STUDIO = UUID.fromString("89d67892-0cc9-4ca4-b30e-28688470c0d4");

    public static final UUID MIGRATION_NODE_STYLE_DESCRIPTION_COLOR_STUDIO = UUID.fromString("ab42d745-3bae-45ee-9839-12aff3d431cf");

    public static final UUID MIGRATION_NODE_DESCRIPTION_USER_RESIZABLE_STUDIO = UUID.fromString("e4a1dfda-81dd-481c-be93-63d96c6e7eb1");

    public static final String MIGRATION_NODE_DESCRIPTION_LABEL_EXPRESSION_STUDIO_DIAGRAM = "NodeDescription#labelExpression migration";

    public static final URI MIGRATION_NODE_DESCRIPTION_LABEL_EXPRESSION_STUDIO_DIAGRAM_URI = URI.createURI("sirius:///1d8aac3e-5fe7-4787-b0fc-1f8eb491cd5e");

    public static final String MIGRATION_NODE_STYLE_DESCRIPTION_COLOR_STUDIO_DIAGRAM = "NodeStyleDescription#color migration";

    public static final String MIGRATION_NODE_DESCRIPTION_USER_RESIZABLE_STUDIO_DIAGRAM = "NodeDescription#userResizable migration";

    public static final UUID MIGRATION_STUDIO_DIAGRAM_HIERARCHY = UUID.fromString("35f1cd7b-e5bb-443d-95ef-bab372a92b0f");

    public static final UUID MIGRATION_STUDIO_DIAGRAM = UUID.fromString("9698833e-ffd4-435a-9aec-765622ce524e");

    public static final UUID MIGRATION_DIAGRAM_LABEL_STYLE_BORDER_SIZE_STUDIO = UUID.fromString("65edc1f2-989c-4001-971b-29981179ebfa");

    public static final String MIGRATION_DIAGRAM_LABEL_STYLE_BORDER_SIZE_STUDIO_DIAGRAM = "DiagramLabelStyle#borderSize migration";

    public static final UUID MIGRATION_NODE_LABEL_STYLE_SHOW_ICON_STUDIO = UUID.fromString("6d89dded-c843-475f-91b4-e2c91b9a883a");

    public static final String MIGRATION_NODE_LABEL_STYLE_SHOW_ICON_STUDIO_DIAGRAM = "NodeLabelStyle#showIcon migration";

    public static final UUID MIGRATION_SELECTION_DIALOG_DESCRIPTION_SELECTION_CANDIDATES_EXPRESSION_STUDIO = UUID.fromString("06d828d9-c2c6-46d0-a9c4-7fabd588755b");

    public static final String MIGRATION_SELECTION_DIALOG_DESCRIPTION_SELECTION_CANDIDATES_EXPRESSION_STUDIO_DIAGRAM = "SelectionDialogDescription#selectionCandidatesExpression migration";

    public static final UUID MIGRATION_INSIDE_LABEL_STYLE_HEADER_SEPARATOR_DISPLAY_STUDIO = UUID.fromString("dc9643f8-b1ce-4c93-a176-379063d42b32");

    public static final String MIGRATION_INSIDE_LABEL_STYLE_HEADER_SEPARATOR_DISPLAY_STUDIO_DIAGRAM = "InsideLabelStyle#displayHeaderSeparator migration";

    public static final String MIGRATION_INSIDE_LABEL_STYLE_HEADER_SEPARATOR_DISPLAY_STUDIO_NODE_1 = "migration Node 1";

    public static final UUID MIGRATION_WIDGET_DESCRIPTION_STYLE_LAYOUT_PROPERTIES_MIGRATION_PARTICIPANT_STUDIO = UUID.fromString("e344d967-a639-4f6c-9c00-a466d51063c6");

    public static final String MIGRATION_WIDGET_DESCRIPTION_STYLE_LAYOUT_PROPERTIES_MIGRATION_PARTICIPANT_STUDIO_FORM = "Form View for WidgetDescriptionStyleLayoutPropertiesMigrationParticipant";

    public static final String MIGRATION_TREE_DESCRIPTION_ICON_URL_STUDIO = "340026b5-1363-4c93-8c2b-7f11188cca8b";

    public static final String MIGRATION_TREE_DESCRIPTION_ICON_URL_STUDIO_DOCUMENT_NAME = "TreeDescription#iconUrlDescription migration";

    public static final UUID MIGRATION_NODE_PALETTE_DELETE_FROM_DIAGRAM_TOOL_STUDIO = UUID.fromString("c81968f1-73c8-3973-bdae-7a2997132706");

    public static final String MIGRATION_NODE_PALETTE_DELETE_FROM_DIAGRAM_TOOL_STUDIO_DIAGRAM = "NodePalette#DeleteFromDiagram migration";

    public static final UUID MIGRATION_NODE_DESCRIPTION_LAYOUT_STRATEGY_STUDIO = UUID.fromString("402f51c9-96df-46f2-b39b-35ebd506ff31");

    public static final String MIGRATION_NODE_DESCRIPTION_LAYOUT_STRATEGY_STUDIO_DIAGRAM = "NodeDescription#layoutStrategy migration";
    public static final String MIGRATION_NODE_DESCRIPTION_LAYOUT_STRATEGY_ALREADY_MIGRATE_STUDIO_DIAGRAM = "NodeDescription#layoutStrategy already migrate";

    private MigrationIdentifiers() {
        // Prevent instantiation
    }
}
