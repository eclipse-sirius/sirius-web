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
package org.eclipse.sirius.components.forms.components;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.forms.description.PageDescription;
import org.eclipse.sirius.components.forms.renderer.IWidgetDescriptor;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The properties of the page component.
 *
 * @author sbegaudeau
 */
public class PageComponentProps implements IProps {
    private VariableManager variableManager;

    private PageDescription pageDescription;

    private final List<IWidgetDescriptor> widgetDescriptors;

    public PageComponentProps(VariableManager variableManager, PageDescription pageDescription, List<IWidgetDescriptor> widgetDescriptors) {
        this.variableManager = Objects.requireNonNull(variableManager);
        this.pageDescription = Objects.requireNonNull(pageDescription);
        this.widgetDescriptors = Objects.requireNonNull(widgetDescriptors);
    }

    public VariableManager getVariableManager() {
        return this.variableManager;
    }

    public PageDescription getPageDescription() {
        return this.pageDescription;
    }

    public List<IWidgetDescriptor> getWidgetDescriptors() {
        return this.widgetDescriptors;
    }
}
