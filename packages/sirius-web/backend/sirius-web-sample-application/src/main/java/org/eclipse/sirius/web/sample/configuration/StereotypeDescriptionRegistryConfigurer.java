/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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

import fr.obeo.dsl.designer.sample.flow.FlowFactory;

import java.util.List;
import java.util.UUID;

import org.eclipse.sirius.components.core.configuration.IStereotypeDescriptionRegistry;
import org.eclipse.sirius.components.core.configuration.IStereotypeDescriptionRegistryConfigurer;
import org.eclipse.sirius.components.core.configuration.StereotypeDescription;
import org.eclipse.sirius.components.domain.Domain;
import org.eclipse.sirius.components.domain.DomainFactory;
import org.eclipse.sirius.components.view.ColorPalette;
import org.eclipse.sirius.components.view.FixedColor;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.web.sample.papaya.domain.PapayaDomainProvider;
import org.eclipse.sirius.web.sample.papaya.view.PapayaViewProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import io.micrometer.core.instrument.MeterRegistry;

/**
 * Configuration used to register new stereotype descriptions.
 *
 * @author sbegaudeau
 */
@Configuration
public class StereotypeDescriptionRegistryConfigurer implements IStereotypeDescriptionRegistryConfigurer {

    public static final UUID EMPTY_ID = UUID.nameUUIDFromBytes("empty".getBytes());

    public static final String EMPTY_LABEL = "Others...";

    public static final UUID EMPTY_FLOW_ID = UUID.nameUUIDFromBytes("empty_flow".getBytes());

    public static final String EMPTY_FLOW_LABEL = "Flow";

    public static final UUID ROBOT_FLOW_ID = UUID.nameUUIDFromBytes("robot_flow".getBytes());

    public static final String ROBOT_FLOW_LABEL = "Robot Flow";

    public static final UUID BIG_GUY_FLOW_ID = UUID.nameUUIDFromBytes("big_guy_flow".getBytes());

    public static final String BIG_GUY_FLOW_LABEL = "Big Guy Flow (17k elements)";

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

    public StereotypeDescriptionRegistryConfigurer(MeterRegistry meterRegistry, @Value("${org.eclipse.sirius.web.features.studioDefinition:false}") boolean studiosEnabled) {
        this.stereotypeBuilder = new StereotypeBuilder(TIMER_NAME, meterRegistry);
        this.studiosEnabled = studiosEnabled;
        this.domainNameProvider = new SampleDomainNameProvider();
    }

    @Override
    public void addStereotypeDescriptions(IStereotypeDescriptionRegistry registry) {
        registry.add(new StereotypeDescription(EMPTY_FLOW_ID, EMPTY_FLOW_LABEL, this::getEmptyFlowContent));
        if (this.studiosEnabled) {
            registry.add(new StereotypeDescription(EMPTY_DOMAIN_ID, EMPTY_DOMAIN_LABEL, this::getEmptyDomainContent));
            registry.add(new StereotypeDescription(EMPTY_VIEW_ID, EMPTY_VIEW_LABEL, this::getEmptyViewContent));
            registry.add(new StereotypeDescription(PAPAYA_DOMAIN_ID, PAPAYA_DOMAIN_LABEL, this::getPapayaDomainContent));
            registry.add(new StereotypeDescription(PAPAYA_VIEW_ID, PAPAYA_VIEW_LABEL, this::getPapayaViewContent));
        }
        registry.add(new StereotypeDescription(ROBOT_FLOW_ID, ROBOT_FLOW_LABEL, this::getRobotFlowContent));
        registry.add(new StereotypeDescription(BIG_GUY_FLOW_ID, BIG_GUY_FLOW_LABEL, this::getBigGuyFlowContent));
        registry.add(new StereotypeDescription(EMPTY_ID, EMPTY_LABEL, "New", this::getEmptyContent));
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
        ColorPalette colorPalette = ViewFactory.eINSTANCE.createColorPalette();
        colorPalette.setName("Color Palette");
        FixedColor themePrimaryMain = ViewFactory.eINSTANCE.createFixedColor();
        themePrimaryMain.setName("theme_primary_main");
        themePrimaryMain.setValue("#BE1A78");
        colorPalette.getColors().add(themePrimaryMain);
        FixedColor themePrimaryDark = ViewFactory.eINSTANCE.createFixedColor();
        themePrimaryDark.setName("theme_primary_dark");
        themePrimaryDark.setValue("#851254");
        colorPalette.getColors().add(themePrimaryDark);
        FixedColor themePrimaryLight = ViewFactory.eINSTANCE.createFixedColor();
        themePrimaryLight.setName("theme_primary_light");
        themePrimaryLight.setValue("#CB4793");
        colorPalette.getColors().add(themePrimaryLight);
        FixedColor themeSecondaryMain = ViewFactory.eINSTANCE.createFixedColor();
        themeSecondaryMain.setName("theme_secondary_main");
        themeSecondaryMain.setValue("#261E58");
        colorPalette.getColors().add(themeSecondaryMain);
        FixedColor themeSecondaryDark = ViewFactory.eINSTANCE.createFixedColor();
        themeSecondaryDark.setName("theme_secondary_dark");
        themeSecondaryDark.setValue("#1A153D");
        colorPalette.getColors().add(themeSecondaryDark);
        FixedColor themeSecondaryLight = ViewFactory.eINSTANCE.createFixedColor();
        themeSecondaryLight.setName("theme_secondary_light");
        themeSecondaryLight.setValue("#514B79");
        colorPalette.getColors().add(themeSecondaryLight);
        newView.getColorPalettes().add(colorPalette);
        return this.stereotypeBuilder.getStereotypeBody(List.of(newView));
    }

    private String getPapayaViewContent() {
        return this.stereotypeBuilder.getStereotypeBody(List.of(new PapayaViewProvider().getView()));
    }

    private String getEmptyContent() {
        return this.stereotypeBuilder.getStereotypeBody(List.of());
    }

    private String getEmptyFlowContent() {
        return this.stereotypeBuilder.getStereotypeBody(List.of(FlowFactory.eINSTANCE.createSystem()));
    }

    private String getRobotFlowContent() {
        return this.stereotypeBuilder.getStereotypeBody(new ClassPathResource("robot.flow"));
    }

    private String getBigGuyFlowContent() {
        return this.stereotypeBuilder.getStereotypeBody(new ClassPathResource("Big_Guy.flow"));
    }
}
