/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.compatibility.api.IAQLInterpreterFactory;
import org.eclipse.sirius.components.compatibility.api.ICanCreateDiagramPredicateFactory;
import org.eclipse.sirius.components.compatibility.api.IIdOdesignElementsProvider;
import org.eclipse.sirius.components.compatibility.diagrams.DiagramLabelProvider;
import org.eclipse.sirius.components.compatibility.services.diagrams.api.IDiagramDescriptionConverter;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription.Builder;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.viewpoint.description.DocumentedElement;
import org.springframework.stereotype.Service;

/**
 * This class is used to convert a Sirius DiagramDescription to an Sirius Web DiagramDescription.
 *
 * @author sbegaudeau
 */
@Service
public class DiagramDescriptionConverter implements IDiagramDescriptionConverter {

    private static final String FORCE_AUTO_LAYOUT = "FORCE_AUTO_LAYOUT";

    private final List<IDiagramDescriptionPopulator> diagramDescriptionPopulators;

    private final IAQLInterpreterFactory interpreterFactory;

    private final ICanCreateDiagramPredicateFactory canCreateDiagramPredicateFactory;

    private final IIdOdesignElementsProvider idOdesignElementsProvider;

    public DiagramDescriptionConverter(IIdOdesignElementsProvider idDiagramProvider, List<IDiagramDescriptionPopulator> diagramDescriptionPopulators, IAQLInterpreterFactory interpreterFactory,
            ICanCreateDiagramPredicateFactory canCreateDiagramPredicateFactory) {
        this.diagramDescriptionPopulators = Objects.requireNonNull(diagramDescriptionPopulators);
        this.interpreterFactory = Objects.requireNonNull(interpreterFactory);
        this.canCreateDiagramPredicateFactory = Objects.requireNonNull(canCreateDiagramPredicateFactory);
        this.idOdesignElementsProvider = Objects.requireNonNull(idDiagramProvider);
    }

    @Override
    public DiagramDescription convert(org.eclipse.sirius.diagram.description.DiagramDescription siriusDiagramDescription) {
        AQLInterpreter interpreter = this.interpreterFactory.create(siriusDiagramDescription);
        Function<VariableManager, String> labelProvider = new DiagramLabelProvider(interpreter, siriusDiagramDescription);
        Predicate<VariableManager> canCreatePredicate = this.canCreateDiagramPredicateFactory.getCanCreateDiagramPredicate(siriusDiagramDescription, interpreter);

        // @formatter:off
        Builder builder = DiagramDescription.newDiagramDescription(this.idOdesignElementsProvider.getIdDiagramDescription(siriusDiagramDescription))
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
