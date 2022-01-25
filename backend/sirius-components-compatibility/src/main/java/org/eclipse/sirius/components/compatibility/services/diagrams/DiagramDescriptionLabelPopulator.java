/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
package org.eclipse.sirius.components.compatibility.services.diagrams;

import java.util.Objects;

import org.eclipse.sirius.components.compatibility.services.representations.IdentifiedElementLabelProvider;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription.Builder;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.springframework.stereotype.Service;

/**
 * Used to populate the label of the diagram description builder.
 *
 * @author sbegaudeau
 */
@Service
public class DiagramDescriptionLabelPopulator implements IDiagramDescriptionPopulator {

    private final IdentifiedElementLabelProvider identifiedElementLabelProvider;

    public DiagramDescriptionLabelPopulator(IdentifiedElementLabelProvider identifiedElementLabelProvider) {
        this.identifiedElementLabelProvider = Objects.requireNonNull(identifiedElementLabelProvider);
    }

    @Override
    public Builder populate(Builder builder, org.eclipse.sirius.diagram.description.DiagramDescription siriusDiagramDescription, AQLInterpreter interpreter) {
        return builder.label(this.identifiedElementLabelProvider.getLabel(siriusDiagramDescription));
    }
}
