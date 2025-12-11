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
        return new ComposedAdapterFactory.Descriptor() {
            @Override
            public AdapterFactory createAdapterFactory() {
                return new DomainItemProviderAdapterFactory();
            }
        };
    }

    @Bean
    public ComposedAdapterFactory.Descriptor viewAdapterFactoryDescriptor() {
        return new ComposedAdapterFactory.Descriptor() {
            @Override
            public AdapterFactory createAdapterFactory() {
                return new ViewItemProviderAdapterFactory();
            }
        };
    }

    @Bean
    public ComposedAdapterFactory.Descriptor diagramAdapterFactoryDescriptor() {
        return new ComposedAdapterFactory.Descriptor() {
            @Override
            public AdapterFactory createAdapterFactory() {
                return new DiagramItemProviderAdapterFactory();
            }
        };
    }

    @Bean
    public ComposedAdapterFactory.Descriptor customnodesAdapterFactoryDescriptor() {
        return new ComposedAdapterFactory.Descriptor() {
            @Override
            public AdapterFactory createAdapterFactory() {
                return new CustomnodesItemProviderAdapterFactory();
            }
        };
    }

    @Bean
    public ComposedAdapterFactory.Descriptor referenceAdapterFactoryDescriptor() {
        return new ComposedAdapterFactory.Descriptor() {
            @Override
            public AdapterFactory createAdapterFactory() {
                return new ReferenceItemProviderAdapterFactory();
            }
        };
    }

    @Bean
    public ComposedAdapterFactory.Descriptor formAdapterFactoryDescriptor() {
        return new ComposedAdapterFactory.Descriptor() {
            @Override
            public AdapterFactory createAdapterFactory() {
                return new FormItemProviderAdapterFactory();
            }
        };
    }

    @Bean
    public ComposedAdapterFactory.Descriptor deckAdapterFactoryDescriptor() {
        return new ComposedAdapterFactory.Descriptor() {
            @Override
            public AdapterFactory createAdapterFactory() {
                return new DeckItemProviderAdapterFactory();
            }
        };
    }

    @Bean
    public ComposedAdapterFactory.Descriptor ganttAdapterFactoryDescriptor() {
        return new ComposedAdapterFactory.Descriptor() {
            @Override
            public AdapterFactory createAdapterFactory() {
                return new GanttItemProviderAdapterFactory();
            }
        };
    }

    @Bean
    public ComposedAdapterFactory.Descriptor treeAdapterFactoryDescriptor() {
        return new ComposedAdapterFactory.Descriptor() {
            @Override
            public AdapterFactory createAdapterFactory() {
                return new TreeItemProviderAdapterFactory();
            }
        };
    }

    @Bean
    public ComposedAdapterFactory.Descriptor tableAdapterFactoryDescriptor() {
        return new ComposedAdapterFactory.Descriptor() {
            @Override
            public AdapterFactory createAdapterFactory() {
                return new TableItemProviderAdapterFactory();
            }
        };
    }

    @Bean
    public ComposedAdapterFactory.Descriptor tableWidgetAdapterFactoryDescriptor() {
        return new ComposedAdapterFactory.Descriptor() {
            @Override
            public AdapterFactory createAdapterFactory() {
                return new TableWidgetItemProviderAdapterFactory();
            }
        };
    }

    @Bean
    public ComposedAdapterFactory.Descriptor customcellsWidgetAdapterFactoryDescriptor() {
        return new ComposedAdapterFactory.Descriptor() {
            @Override
            public AdapterFactory createAdapterFactory() {
                return new CustomcellsItemProviderAdapterFactory();
            }
        };
    }
}
