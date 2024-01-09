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

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.sirius.components.domain.DomainPackage;
import org.eclipse.sirius.components.domain.provider.DomainItemProviderAdapterFactory;
import org.eclipse.sirius.components.emf.configuration.ChildExtenderProvider;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.diagram.provider.DiagramItemProviderAdapterFactory;
import org.eclipse.sirius.components.view.form.FormPackage;
import org.eclipse.sirius.components.view.form.provider.FormItemProviderAdapterFactory;
import org.eclipse.sirius.components.view.provider.ViewItemProviderAdapterFactory;
import org.eclipse.sirius.web.customnodes.CustomnodesPackage;
import org.eclipse.sirius.web.customnodes.provider.CustomnodesItemProviderAdapterFactory;
import org.eclipse.sirius.web.customwidgets.CustomwidgetsPackage;
import org.eclipse.sirius.web.customwidgets.provider.CustomwidgetsItemProviderAdapterFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration of the EMF support for Sirius Web.
 *
 * @author sbegaudeau
 */
@Configuration
public class SampleEMFConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "org.eclipse.sirius.web.features", name = "studioDefinition")
    public EPackage domainEPackage() {
        return DomainPackage.eINSTANCE;
    }

    @Bean
    @ConditionalOnProperty(prefix = "org.eclipse.sirius.web.features", name = "studioDefinition")
    public AdapterFactory domainAdapterFactory() {
        return new DomainItemProviderAdapterFactory();
    }

    @Bean
    @ConditionalOnProperty(prefix = "org.eclipse.sirius.web.features", name = "studioDefinition")
    public EPackage viewEPackage() {
        return ViewPackage.eINSTANCE;
    }

    @Bean
    @ConditionalOnProperty(prefix = "org.eclipse.sirius.web.features", name = "studioDefinition")
    public AdapterFactory viewAdapterFactory() {
        return new ViewItemProviderAdapterFactory();
    }

    @Bean
    @ConditionalOnProperty(prefix = "org.eclipse.sirius.web.features", name = "studioDefinition")
    public EPackage viewDiagramEPackage() {
        return DiagramPackage.eINSTANCE;
    }

    @Bean
    @ConditionalOnProperty(prefix = "org.eclipse.sirius.web.features", name = "studioDefinition")
    public AdapterFactory diagramAdapterFactory() {
        return new DiagramItemProviderAdapterFactory();
    }

    @Bean
    @ConditionalOnProperty(prefix = "org.eclipse.sirius.web.features", name = "studioDefinition")
    public ChildExtenderProvider diagramChildExtenderProvider() {
        return new ChildExtenderProvider(ViewPackage.eNS_URI, DiagramItemProviderAdapterFactory.ViewChildCreationExtender::new);
    }

    @Bean
    @ConditionalOnProperty(prefix = "org.eclipse.sirius.web.features", name = "studioDefinition")
    public EPackage viewFormEPackage() {
        return FormPackage.eINSTANCE;
    }

    @Bean
    @ConditionalOnProperty(prefix = "org.eclipse.sirius.web.features", name = "studioDefinition")
    public AdapterFactory formAdapterFactory() {
        return new FormItemProviderAdapterFactory();
    }

    @Bean
    @ConditionalOnProperty(prefix = "org.eclipse.sirius.web.features", name = "studioDefinition")
    public ChildExtenderProvider formChildExtenderProvider() {
        return new ChildExtenderProvider(ViewPackage.eNS_URI, FormItemProviderAdapterFactory.ViewChildCreationExtender::new);
    }

    @Bean
    @ConditionalOnProperty(prefix = "org.eclipse.sirius.web.features", name = "studioDefinition")
    public EPackage customWidgetsEPackage() {
        return CustomwidgetsPackage.eINSTANCE;
    }

    @Bean
    @ConditionalOnProperty(prefix = "org.eclipse.sirius.web.features", name = "studioDefinition")
    public AdapterFactory customWidgetsAdapterFactory() {
        return new CustomwidgetsItemProviderAdapterFactory();
    }

    @Bean
    @ConditionalOnProperty(prefix = "org.eclipse.sirius.web.features", name = "studioDefinition")
    public ChildExtenderProvider sliderChildExtenderProvider() {
        return new ChildExtenderProvider(FormPackage.eNS_URI, CustomwidgetsItemProviderAdapterFactory.FormChildCreationExtender::new);
    }

    @Bean
    @ConditionalOnProperty(prefix = "org.eclipse.sirius.web.features", name = "studioDefinition")
    public EPackage customNodesEPackage() {
        return CustomnodesPackage.eINSTANCE;
    }

    @Bean
    @ConditionalOnProperty(prefix = "org.eclipse.sirius.web.features", name = "studioDefinition")
    public AdapterFactory customNodesAdapterFactory() {
        return new CustomnodesItemProviderAdapterFactory();
    }

    @Bean
    @ConditionalOnProperty(prefix = "org.eclipse.sirius.web.features", name = "studioDefinition")
    public ChildExtenderProvider customNodesChildExtenderProvider() {
        return new ChildExtenderProvider(DiagramPackage.eNS_URI, CustomnodesItemProviderAdapterFactory.DiagramChildCreationExtender::new);
    }
}
