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
package org.eclipse.sirius.web.compat.configuration;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.viewpoint.description.Group;
import org.eclipse.sirius.web.api.configuration.IRepresentationDescriptionRegistry;
import org.eclipse.sirius.web.api.configuration.IRepresentationDescriptionRegistryConfigurer;
import org.eclipse.sirius.web.compat.services.ExplorerTreeDescriptionProvider;
import org.eclipse.sirius.web.compat.services.ODesignRegistry;
import org.eclipse.sirius.web.compat.services.api.IODesignRegistry;
import org.eclipse.sirius.web.compat.services.api.ISiriusConfiguration;
import org.eclipse.sirius.web.compat.services.representations.ODesignReader;
import org.eclipse.sirius.web.compat.services.representations.SiriusRepresentationDescriptionProvider;
import org.eclipse.sirius.web.representations.IRepresentationDescription;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * Used to add the default representation descriptions to the registry such as the description of the model explorer and
 * the default form description and all the descriptions from the odesign files registered in the Sirius configurations.
 *
 * @author sbegaudeau
 */
@Configuration
public class SiriusRepresentationDescriptionRegistryConfigurer implements IRepresentationDescriptionRegistryConfigurer {

    private final List<ISiriusConfiguration> siriusConfigurations;

    private final ODesignReader oDesignReader;

    private final IODesignRegistry oDesignRegistry;

    private final SiriusRepresentationDescriptionProvider representationDescriptionProvider;

    private final ExplorerTreeDescriptionProvider treeDescriptionProvider;

    public SiriusRepresentationDescriptionRegistryConfigurer(List<ISiriusConfiguration> siriusConfigurations, ODesignReader oDesignReader, IODesignRegistry oDesignRegistry,
            SiriusRepresentationDescriptionProvider representationDescriptionProvider, ExplorerTreeDescriptionProvider treeDescriptionProvider) {
        this.siriusConfigurations = Objects.requireNonNull(siriusConfigurations);
        this.oDesignReader = Objects.requireNonNull(oDesignReader);
        this.oDesignRegistry = Objects.requireNonNull(oDesignRegistry);
        this.representationDescriptionProvider = Objects.requireNonNull(representationDescriptionProvider);
        this.treeDescriptionProvider = Objects.requireNonNull(treeDescriptionProvider);
    }

    @Override
    public void addRepresentationDescriptions(IRepresentationDescriptionRegistry registry) {
        registry.add(this.treeDescriptionProvider.getTreeDescription());

        // @formatter:off
        this.siriusConfigurations.stream()
            .map(ISiriusConfiguration::getODesignPaths)
            .flatMap(List::stream)
            .forEach(path -> this.registerODesign(registry, path));
        // @formatter:on
    }

    private void registerODesign(IRepresentationDescriptionRegistry registry, String odesignPath) {
        var optionalGroup = this.oDesignReader.read(new ClassPathResource(odesignPath));
        if (optionalGroup.isPresent()) {
            Group group = optionalGroup.get();
            if (this.oDesignRegistry instanceof ODesignRegistry) {
                ((ODesignRegistry) this.oDesignRegistry).add(group);
            }
            List<IRepresentationDescription> representationDescriptions = this.representationDescriptionProvider.getRepresentationDescriptions(group);
            representationDescriptions.forEach(registry::add);
        }
    }
}
