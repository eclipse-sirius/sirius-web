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
package org.eclipse.sirius.web.sample.configuration;

import java.util.List;
import java.util.UUID;

import org.eclipse.sirius.web.services.api.document.IStereotypeRegistry;
import org.eclipse.sirius.web.services.api.document.IStereotypeRegistryConfigurer;
import org.eclipse.sirius.web.services.api.document.Stereotype;
import org.eclipse.sirius.components.domain.Domain;
import org.eclipse.sirius.components.domain.DomainFactory;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.web.sample.papaya.domain.PapayaDomainProvider;
import org.eclipse.sirius.web.sample.papaya.view.PapayaViewProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import io.micrometer.core.instrument.MeterRegistry;

/**
 * Configuration used to register new stereotypes.
 *
 * @author sbegaudeau
 */
@Configuration
public class StereotypeRegistryConfigurer implements IStereotypeRegistryConfigurer {

    public static final UUID EMPTY_ID = UUID.nameUUIDFromBytes("empty".getBytes());

    public static final String EMPTY_LABEL = "Others...";

    public static final UUID EMPTY_VIEW_ID = UUID.nameUUIDFromBytes("empty_view".getBytes());

    public static final String EMPTY_VIEW_LABEL = "View";

    private static final UUID EMPTY_DOMAIN_ID = UUID.nameUUIDFromBytes("empty_domain".getBytes());

    private static final String EMPTY_DOMAIN_LABEL = "Domain";

    private static final UUID PAPAYA_DOMAIN_ID = UUID.nameUUIDFromBytes("papaya_domain".getBytes());

    private static final String PAPAYA_DOMAIN_LABEL = "Papaya Domain";

    private static final UUID PAPAYA_VIEW_ID = UUID.nameUUIDFromBytes("papaya_view".getBytes());

    private static final String PAPAYA_VIEW_LABEL = "Papaya View";

    private static final String TIMER_NAME = "siriusweb_stereotype_load";

    private final StereotypeBuilder stereotypeBuilder;

    private final SampleDomainNameProvider domainNameProvider;

    private final boolean studiosEnabled;

    public StereotypeRegistryConfigurer(MeterRegistry meterRegistry, @Value("${org.eclipse.sirius.web.features.studioDefinition:false}") boolean studiosEnabled) {
        this.stereotypeBuilder = new StereotypeBuilder(TIMER_NAME, meterRegistry);
        this.studiosEnabled = studiosEnabled;
        this.domainNameProvider = new SampleDomainNameProvider();
    }

    @Override
    public void addStereotypes(IStereotypeRegistry registry) {
        if (this.studiosEnabled) {
            registry.add(new Stereotype(EMPTY_DOMAIN_ID, EMPTY_DOMAIN_LABEL, this::getEmptyDomainContent));
            registry.add(new Stereotype(EMPTY_VIEW_ID, EMPTY_VIEW_LABEL, this::getEmptyViewContent));
            registry.add(new Stereotype(PAPAYA_DOMAIN_ID, PAPAYA_DOMAIN_LABEL, this::getPapayaDomainContent));
            registry.add(new Stereotype(PAPAYA_VIEW_ID, PAPAYA_VIEW_LABEL, this::getPapayaViewContent));
        }
        registry.add(new Stereotype(EMPTY_ID, EMPTY_LABEL, this::getEmptyContent));
    }

    private String getEmptyDomainContent() {
        Domain domain = DomainFactory.eINSTANCE.createDomain();
        domain.setName("SampleDomain");
        domain.setName(this.domainNameProvider.getSampleDomainName());
        return this.stereotypeBuilder.getStereotypeBody(List.of(domain));
    }

    private String getPapayaDomainContent() {
        return this.stereotypeBuilder.getStereotypeBody(new PapayaDomainProvider().getDomains());
    }

    private String getEmptyViewContent() {
        View newView = ViewFactory.eINSTANCE.createView();
        DiagramDescription diagramDescription = DiagramFactory.eINSTANCE.createDiagramDescription();
        diagramDescription.setName("New Diagram Description");
        newView.getDescriptions().add(diagramDescription);

        return this.stereotypeBuilder.getStereotypeBody(List.of(newView));
    }

    private String getPapayaViewContent() {
        return this.stereotypeBuilder.getStereotypeBody(List.of(new PapayaViewProvider().getView()));
    }

    private String getEmptyContent() {
        return this.stereotypeBuilder.getStereotypeBody(List.of());
    }

}
