/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
package org.eclipse.sirius.web.infrastructure.configuration.emf;

import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.sirius.components.domain.provider.DomainItemProviderAdapterFactory;
import org.eclipse.sirius.components.view.deck.provider.DeckItemProviderAdapterFactory;
import org.eclipse.sirius.components.view.diagram.customnodes.provider.CustomnodesItemProviderAdapterFactory;
import org.eclipse.sirius.components.view.diagram.provider.DiagramItemProviderAdapterFactory;
import org.eclipse.sirius.components.view.form.provider.FormItemProviderAdapterFactory;
import org.eclipse.sirius.components.view.gantt.provider.GanttItemProviderAdapterFactory;
import org.eclipse.sirius.components.view.provider.ViewItemProviderAdapterFactory;
import org.eclipse.sirius.components.view.table.provider.TableItemProviderAdapterFactory;
import org.eclipse.sirius.components.view.tree.provider.TreeItemProviderAdapterFactory;
import org.eclipse.sirius.components.view.widget.tablewidget.provider.TableWidgetItemProviderAdapterFactory;
import org.eclipse.sirius.components.widgets.reference.provider.ReferenceItemProviderAdapterFactory;
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
    public AdapterFactory customNodesAdapterFactory() {
        return new CustomnodesItemProviderAdapterFactory();
    }

    @Bean
    public AdapterFactory referenceWidgetAdapterFactory() {
        return new ReferenceItemProviderAdapterFactory();
    }

    @Bean
    public AdapterFactory formAdapterFactory() {
        return new FormItemProviderAdapterFactory();
    }

    @Bean
    public AdapterFactory deckAdapterFactory() {
        return new DeckItemProviderAdapterFactory();
    }

    @Bean
    public AdapterFactory ganttAdapterFactory() {
        return new GanttItemProviderAdapterFactory();
    }

    @Bean
    public AdapterFactory treeAdapterFactory() {
        return new TreeItemProviderAdapterFactory();
    }

    @Bean
    public AdapterFactory tableAdapterFactory() {
        return new TableItemProviderAdapterFactory();
    }

    @Bean
    public AdapterFactory tableWidgetAdapterFactory() {
        return new TableWidgetItemProviderAdapterFactory();
    }
}
