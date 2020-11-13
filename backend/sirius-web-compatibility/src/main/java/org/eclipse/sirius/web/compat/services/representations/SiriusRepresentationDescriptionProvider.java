/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.compat.services.representations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.sirius.properties.ViewExtensionDescription;
import org.eclipse.sirius.viewpoint.description.Group;
import org.eclipse.sirius.viewpoint.description.RepresentationDescription;
import org.eclipse.sirius.viewpoint.description.Viewpoint;
import org.eclipse.sirius.web.compat.services.diagrams.api.IDiagramDescriptionConverter;
import org.eclipse.sirius.web.compat.services.forms.api.IViewExtensionDescriptionConverter;
import org.eclipse.sirius.web.forms.description.FormDescription;
import org.eclipse.sirius.web.representations.IRepresentationDescription;
import org.springframework.stereotype.Service;

/**
 * Retrieves and converts the representation descriptions from the given odesign classpath resource.
 *
 * @author sbegaudeau
 */
@Service
public class SiriusRepresentationDescriptionProvider {

    private final IDiagramDescriptionConverter diagramDescriptionConverter;

    private final IViewExtensionDescriptionConverter viewExtensionDescriptionConverter;

    public SiriusRepresentationDescriptionProvider(IDiagramDescriptionConverter diagramDescriptionConverter, IViewExtensionDescriptionConverter viewExtensionDescriptionConverter) {
        this.diagramDescriptionConverter = Objects.requireNonNull(diagramDescriptionConverter);
        this.viewExtensionDescriptionConverter = Objects.requireNonNull(viewExtensionDescriptionConverter);
    }

    public List<IRepresentationDescription> getRepresentationDescriptions(Group group) {
        List<Viewpoint> viewpoints = group.getOwnedViewpoints();

        // @formatter:off
        List<RepresentationDescription> siriusRepresentationDescriptions = viewpoints.stream()
                .map(Viewpoint::getOwnedRepresentations)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        // @formatter:on

        List<IRepresentationDescription> representationDescriptions = new ArrayList<>();
        for (RepresentationDescription siriusRepresentationDescription : siriusRepresentationDescriptions) {
            if (siriusRepresentationDescription instanceof org.eclipse.sirius.diagram.description.DiagramDescription
                    && !(siriusRepresentationDescription instanceof org.eclipse.sirius.diagram.sequence.description.SequenceDiagramDescription)) {
                representationDescriptions.add(this.diagramDescriptionConverter.convert((org.eclipse.sirius.diagram.description.DiagramDescription) siriusRepresentationDescription));
            }
        }
        representationDescriptions.addAll(this.getFormDescriptions(group));
        return representationDescriptions;
    }

    private List<FormDescription> getFormDescriptions(Group group) {
        // @formatter:off
        List<ViewExtensionDescription> viewExtensionDescriptions = group.getExtensions().stream()
                .filter(ViewExtensionDescription.class::isInstance)
                .map(ViewExtensionDescription.class::cast)
                .collect(Collectors.toList());

        return viewExtensionDescriptions.stream()
                .map(viewExtensionDescription -> this.viewExtensionDescriptionConverter.convert(viewExtensionDescription))
                .collect(Collectors.toUnmodifiableList());
        // @formatter:on
    }

}
