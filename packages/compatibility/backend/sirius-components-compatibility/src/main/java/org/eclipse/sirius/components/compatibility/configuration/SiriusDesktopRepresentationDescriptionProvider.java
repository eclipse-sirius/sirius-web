/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
package org.eclipse.sirius.components.compatibility.configuration;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.forms.services.api.IPropertiesDescriptionRegistry;
import org.eclipse.sirius.components.collaborative.forms.services.api.IPropertiesDescriptionRegistryConfigurer;
import org.eclipse.sirius.components.compatibility.services.api.ISiriusConfiguration;
import org.eclipse.sirius.components.compatibility.services.api.ISiriusDesktopRepresentationDescriptionConverter;
import org.eclipse.sirius.components.compatibility.services.representations.ODesignReader;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextRepresentationDescriptionProvider;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.forms.description.FormDescription;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.selection.description.SelectionDescription;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * Used to add the default representation descriptions to the registry such as the description of the model explorer and
 * the default form description and all the descriptions from the odesign files registered in the Sirius configurations.
 *
 * @author sbegaudeau
 * @author hmarchadour
 */
@Configuration
public class SiriusDesktopRepresentationDescriptionProvider implements IEditingContextRepresentationDescriptionProvider, IPropertiesDescriptionRegistryConfigurer {

    private final List<ISiriusConfiguration> siriusConfigurations;

    private final ODesignReader oDesignReader;

    private final ISiriusDesktopRepresentationDescriptionConverter representationDescriptionConverter;

    public SiriusDesktopRepresentationDescriptionProvider(List<ISiriusConfiguration> siriusConfigurations, ODesignReader oDesignReader, ISiriusDesktopRepresentationDescriptionConverter representationDescriptionConverter) {
        this.siriusConfigurations = Objects.requireNonNull(siriusConfigurations);
        this.oDesignReader = Objects.requireNonNull(oDesignReader);
        this.representationDescriptionConverter = Objects.requireNonNull(representationDescriptionConverter);
    }

    @Override
    public List<IRepresentationDescription> getRepresentationDescriptions(IEditingContext editingContext) {
        // We should probably not filter only diagram and selection representations but instead of
        // opening the floodgates, we will be conservative for now

        return this.siriusConfigurations.stream()
                .map(ISiriusConfiguration::getODesignPaths)
                .flatMap(List::stream)
                .map(this::getRepresentationDescriptions)
                .flatMap(List::stream)
                .filter(description -> description instanceof DiagramDescription || description instanceof SelectionDescription)
                .toList();
    }

    @Override
    public void addPropertiesDescriptions(IPropertiesDescriptionRegistry registry) {
        this.siriusConfigurations.stream()
                .map(ISiriusConfiguration::getODesignPaths)
                .flatMap(List::stream)
                .map(this::getRepresentationDescriptions)
                .flatMap(List::stream)
                .filter(FormDescription.class::isInstance)
                .map(FormDescription.class::cast)
                .map(FormDescription::getPageDescriptions)
                .flatMap(List::stream)
                .forEach(registry::add);
    }

    private List<IRepresentationDescription> getRepresentationDescriptions(String odesignPath) {
        return this.oDesignReader.read(new ClassPathResource(odesignPath))
                .map(this.representationDescriptionConverter::getRepresentationDescriptions)
                .orElse(List.of());
    }
}
