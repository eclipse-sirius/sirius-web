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

import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.sirius.components.domain.provider.DomainItemProviderAdapterFactory;
import org.eclipse.sirius.components.view.deck.provider.DeckItemProviderAdapterFactory;
import org.eclipse.sirius.components.view.diagram.customnodes.provider.CustomnodesItemProviderAdapterFactory;
import org.eclipse.sirius.components.view.diagram.provider.DiagramItemProviderAdapterFactory;
import org.eclipse.sirius.components.view.form.provider.FormItemProviderAdapterFactory;
import org.eclipse.sirius.components.view.gantt.provider.GanttItemProviderAdapterFactory;
import org.eclipse.sirius.components.view.provider.ViewItemProviderAdapterFactory;
import org.eclipse.sirius.components.view.table.customcells.provider.CustomcellsItemProviderAdapterFactory;
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
    public ComposedAdapterFactory.Descriptor domainAdapterFactoryDescriptor() {
        return DomainItemProviderAdapterFactory::new;
    }

    @Bean
    public ComposedAdapterFactory.Descriptor viewAdapterFactoryDescriptor() {
        return ViewItemProviderAdapterFactory::new;
    }

    @Bean
    public ComposedAdapterFactory.Descriptor diagramAdapterFactoryDescriptor() {
        return DiagramItemProviderAdapterFactory::new;
    }

    @Bean
    public ComposedAdapterFactory.Descriptor customnodesAdapterFactoryDescriptor() {
        return CustomnodesItemProviderAdapterFactory::new;
    }

    @Bean
    public ComposedAdapterFactory.Descriptor referenceAdapterFactoryDescriptor() {
        return ReferenceItemProviderAdapterFactory::new;
    }

    @Bean
    public ComposedAdapterFactory.Descriptor formAdapterFactoryDescriptor() {
        return FormItemProviderAdapterFactory::new;
    }

    @Bean
    public ComposedAdapterFactory.Descriptor deckAdapterFactoryDescriptor() {
        return DeckItemProviderAdapterFactory::new;
    }

    @Bean
    public ComposedAdapterFactory.Descriptor ganttAdapterFactoryDescriptor() {
        return GanttItemProviderAdapterFactory::new;
    }

    @Bean
    public ComposedAdapterFactory.Descriptor treeAdapterFactoryDescriptor() {
        return TreeItemProviderAdapterFactory::new;
    }

    @Bean
    public ComposedAdapterFactory.Descriptor tableAdapterFactoryDescriptor() {
        return TableItemProviderAdapterFactory::new;
    }

    @Bean
    public ComposedAdapterFactory.Descriptor tableWidgetAdapterFactoryDescriptor() {
        return TableWidgetItemProviderAdapterFactory::new;
    }

    @Bean
    public ComposedAdapterFactory.Descriptor customcellsWidgetAdapterFactoryDescriptor() {
        return CustomcellsItemProviderAdapterFactory::new;
    }
}
