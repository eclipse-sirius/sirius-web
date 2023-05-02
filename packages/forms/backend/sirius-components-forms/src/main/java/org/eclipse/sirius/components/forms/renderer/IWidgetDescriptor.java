/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.forms.renderer;

import java.util.List;
import java.util.Optional;

import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * A descriptor for a custom widget type so that it can be rendered.
 *
 * @author pcdavid
 */
public interface IWidgetDescriptor {
    String getWidgetType();

    Class<? extends IComponent> getComponentClass();

    Class<? extends IProps> getInstancePropsClass();

    Class<? extends IProps> getComponentPropsClass();

    Optional<Object> instanciate(IProps elementProps, List<Object> children);

    Optional<Element> createElement(VariableManager variableManager, AbstractWidgetDescription widgetDescription);
}
