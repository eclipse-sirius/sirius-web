/*******************************************************************************
 * Copyright (c) 2025 CEA LIST.
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

package org.eclipse.sirius.web.application.views.representations.services;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.forms.renderer.IWidgetDescriptor;
import org.eclipse.sirius.components.tables.components.ICustomCellDescriptor;
import org.springframework.stereotype.Service;

/**
 * Descriptors used in RepresentationsEventProcessorFactory.
 *
 * @author Jerome Gout
 */
@Service
public class RepresentationsEventProcessorFactoryDescriptors {

    private final List<IWidgetDescriptor> widgetDescriptors;

    private final List<ICustomCellDescriptor> customCellDescriptors;

    public RepresentationsEventProcessorFactoryDescriptors(List<IWidgetDescriptor> widgetDescriptors, List<ICustomCellDescriptor> customCellDescriptors) {
        this.widgetDescriptors = Objects.requireNonNull(widgetDescriptors);
        this.customCellDescriptors = Objects.requireNonNull(customCellDescriptors);
    }

    public List<IWidgetDescriptor> getWidgetDescriptors() {
        return this.widgetDescriptors;
    }

    public List<ICustomCellDescriptor> getCustomCellDescriptors() {
        return this.customCellDescriptors;
    }
}
