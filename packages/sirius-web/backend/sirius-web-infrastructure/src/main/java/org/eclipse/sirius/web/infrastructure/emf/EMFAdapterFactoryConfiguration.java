/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.web.infrastructure.emf;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.sirius.components.domain.provider.DomainItemProviderAdapterFactory;
import org.eclipse.sirius.components.view.deck.util.DeckAdapterFactory;
import org.eclipse.sirius.components.view.diagram.provider.DiagramItemProviderAdapterFactory;
import org.eclipse.sirius.components.view.form.provider.FormItemProviderAdapterFactory;
import org.eclipse.sirius.components.view.gantt.provider.GanttItemProviderAdapterFactory;
import org.eclipse.sirius.components.view.provider.ViewItemProviderAdapterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration of EMF specific concepts.
 *
 * @author sbegaudeau
 */
@Configuration
public class EMFAdapterFactoryConfiguration {

    @Bean
    public AdapterFactory domainAdapterFactory() {
        return new DomainItemProviderAdapterFactory();
    }

    @Bean
    public AdapterFactory viewAdapterFactory() {
        return new ViewItemProviderAdapterFactory();
    }

    @Bean
    public AdapterFactory diagramAdapterFactory() {
        return new DiagramItemProviderAdapterFactory();
    }

    @Bean
    public AdapterFactory formAdapterFactory() {
        return new FormItemProviderAdapterFactory();
    }

    @Bean
    public AdapterFactory deckAdapterFactory() {
        return new DeckAdapterFactory();
    }

    @Bean
    public AdapterFactory ganttAdapterFactory() {
        return new GanttItemProviderAdapterFactory();
    }
}
