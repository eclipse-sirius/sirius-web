/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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
package org.eclipse.sirius.components.emf.view;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.emf.view.diagram.DiagramDescriptionConverter;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.view.View;

/**
 * Converts a View into an equivalent list of {@link DiagramDescription}.
 *
 * @author pcdavid
 */
public class ViewConverter {

    private final IObjectService objectService;

    private final IEditService editService;

    private final List<IJavaServiceProvider> javaServiceProviders;

    public ViewConverter(IObjectService objectService, IEditService editService, List<IJavaServiceProvider> javaServiceProviders) {
        this.objectService = Objects.requireNonNull(objectService);
        this.editService = Objects.requireNonNull(editService);
        this.javaServiceProviders = Objects.requireNonNull(javaServiceProviders);
    }

    /**
     * Extract and convert the {@link IRepresentationDescription} from a {@link View} model. Currently only
     * {@link DiagramDescription}s are supported.
     */
    public List<IRepresentationDescription> convert(View view, List<EPackage> visibleEPackages) {
        List<IRepresentationDescription> result = List.of();
        AQLInterpreter interpreter = this.createInterpreter(view, visibleEPackages);
        try {
            // @formatter:off
            result = view.getDescriptions().stream()
                         .filter(org.eclipse.sirius.components.view.DiagramDescription.class::isInstance)
                         .map(org.eclipse.sirius.components.view.DiagramDescription.class::cast)
                         .map(viewDiagramDescription -> this.convertDiagramDescription(viewDiagramDescription, interpreter))
                         .collect(Collectors.toList());
            // @formatter:on
        } catch (NullPointerException e) {
            // Can easily happen if the View model is currently invalid/inconsistent, typically because it is
            // currently being created or edited.
        }
        return result;
    }

    private AQLInterpreter createInterpreter(View view, List<EPackage> visibleEPackages) {
        // @formatter:off
        List<Class<?>> serviceClasses = this.javaServiceProviders.stream()
                                            .flatMap(provider -> provider.getServiceClasses(view).stream())
                                            .collect(Collectors.toList());
        // @formatter:on
        return new AQLInterpreter(serviceClasses, visibleEPackages);
    }

    private DiagramDescription convertDiagramDescription(org.eclipse.sirius.components.view.DiagramDescription viewDiagramDescription, AQLInterpreter interpreter) {
        DiagramDescriptionConverter converter = new DiagramDescriptionConverter(this.objectService, this.editService);
        return converter.convert(viewDiagramDescription, interpreter);
    }

}
