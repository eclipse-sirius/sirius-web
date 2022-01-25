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
package org.eclipse.sirius.web.compat.services.diagrams;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.viewpoint.description.DocumentedElement;
import org.eclipse.sirius.web.compat.api.IAQLInterpreterFactory;
import org.eclipse.sirius.web.compat.api.ICanCreateDiagramPredicateFactory;
import org.eclipse.sirius.web.compat.api.IIdentifierProvider;
import org.eclipse.sirius.web.compat.diagrams.DiagramLabelProvider;
import org.eclipse.sirius.web.compat.services.diagrams.api.IDiagramDescriptionConverter;
import org.eclipse.sirius.web.diagrams.description.DiagramDescription;
import org.eclipse.sirius.web.diagrams.description.DiagramDescription.Builder;
import org.eclipse.sirius.web.interpreter.AQLInterpreter;
import org.eclipse.sirius.web.representations.VariableManager;
import org.springframework.stereotype.Service;

/**
 * This class is used to convert a Sirius DiagramDescription to an Sirius Web DiagramDescription.
 *
 * @author sbegaudeau
 */
@Service
public class DiagramDescriptionConverter implements IDiagramDescriptionConverter {

    private static final String FORCE_AUTO_LAYOUT = "FORCE_AUTO_LAYOUT"; //$NON-NLS-1$

    private final List<IDiagramDescriptionPopulator> diagramDescriptionPopulators;

    private final IAQLInterpreterFactory interpreterFactory;

    private final IIdentifierProvider identifierProvider;

    private final ICanCreateDiagramPredicateFactory canCreateDiagramPredicateFactory;

    public DiagramDescriptionConverter(List<IDiagramDescriptionPopulator> diagramDescriptionPopulators, IAQLInterpreterFactory interpreterFactory, IIdentifierProvider identifierProvider,
            ICanCreateDiagramPredicateFactory canCreateDiagramPredicateFactory) {
        this.diagramDescriptionPopulators = Objects.requireNonNull(diagramDescriptionPopulators);
        this.interpreterFactory = Objects.requireNonNull(interpreterFactory);
        this.identifierProvider = Objects.requireNonNull(identifierProvider);
        this.canCreateDiagramPredicateFactory = Objects.requireNonNull(canCreateDiagramPredicateFactory);
    }

    @Override
    public DiagramDescription convert(org.eclipse.sirius.diagram.description.DiagramDescription siriusDiagramDescription) {
        AQLInterpreter interpreter = this.interpreterFactory.create(siriusDiagramDescription);
        Function<VariableManager, String> labelProvider = new DiagramLabelProvider(interpreter, siriusDiagramDescription);
        Predicate<VariableManager> canCreatePredicate = this.canCreateDiagramPredicateFactory.getCanCreateDiagramPredicate(siriusDiagramDescription, interpreter);

        // @formatter:off
        Builder builder = DiagramDescription.newDiagramDescription(UUID.fromString(this.identifierProvider.getIdentifier(siriusDiagramDescription)))
                .canCreatePredicate(canCreatePredicate)
                .labelProvider(labelProvider)
                .autoLayout(this.isAutoLayoutMode(siriusDiagramDescription));
        // @formatter:on

        for (IDiagramDescriptionPopulator diagramDescriptionPopulator : this.diagramDescriptionPopulators) {
            diagramDescriptionPopulator.populate(builder, siriusDiagramDescription, interpreter);
        }

        return builder.build();
    }

    private boolean isAutoLayoutMode(org.eclipse.sirius.diagram.description.DiagramDescription diagramDescription) {
        boolean isAutoLayout = false;
        EObject current = diagramDescription;
        while (!isAutoLayout && current != null) {
            if (current instanceof DocumentedElement) {
                String doc = ((DocumentedElement) current).getDocumentation();
                isAutoLayout = doc != null && doc.contains(FORCE_AUTO_LAYOUT);
            }
            current = current.eContainer();
        }
        return isAutoLayout;
    }
}
