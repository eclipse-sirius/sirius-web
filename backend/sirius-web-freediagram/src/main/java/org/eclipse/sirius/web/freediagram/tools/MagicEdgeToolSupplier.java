/***********************************************************************************************
 * Copyright (c) 2019, 2020 Obeo. All Rights Reserved.
 * This software and the attached documentation are the exclusive ownership
 * of its authors and was conceded to the profit of Obeo SARL.
 * This software and the attached documentation are protected under the rights
 * of intellectual ownership, including the section "Titre II  Droits des auteurs (Articles L121-1 L123-12)"
 * By installing this software, you acknowledge being aware of this rights and
 * accept them, and as a consequence you must:
 * - be in possession of a valid license of use conceded by Obeo only.
 * - agree that you have read, understood, and will comply with the license terms and conditions.
 * - agree not to do anything that could conflict with intellectual ownership owned by Obeo or its beneficiaries
 * or the authors of this software
 *
 * Should you not agree with these terms, you must stop to use this software and give it back to its legitimate owner.
 ***********************************************************************************************/
package org.eclipse.sirius.web.freediagram.tools;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import org.eclipse.sirius.web.diagrams.tools.CreateEdgeTool;
import org.eclipse.sirius.web.diagrams.tools.MagicCreateEdgeTool;
import org.eclipse.sirius.web.representations.Status;
import org.eclipse.sirius.web.representations.VariableManager;

/**
 * Magic Edge Tool supplier.
 *
 * @author hmarchadour
 */
public class MagicEdgeToolSupplier implements Supplier<MagicCreateEdgeTool> {

    private static final String MAGIC_EDGE_TOOL_IMAGE_PATH = "/icons/gif/magic_connection.gif"; //$NON-NLS-1$

    private static final String MAGIC_EDGE_LABEL = "Magic Edge"; //$NON-NLS-1$

    private static final String MAGIC_EDGE_ID = "MagicEdge"; //$NON-NLS-1$

    private final List<CreateEdgeTool> tools;

    public MagicEdgeToolSupplier(List<CreateEdgeTool> tools) {
        this.tools = Objects.requireNonNull(tools);
    }

    @Override
    public MagicCreateEdgeTool get() {
        // @formatter:off
        return MagicCreateEdgeTool.newMagicCreateEdgeTool(MAGIC_EDGE_ID)
                .label(MAGIC_EDGE_LABEL)
                .imageURL(MAGIC_EDGE_TOOL_IMAGE_PATH)
                .handler(this::handle)
                .createEdgeTools(this.tools)
                .build();
        // @formatter:on
    }

    private Status handle(VariableManager variableManager) {
        return Status.OK;
    }

}
