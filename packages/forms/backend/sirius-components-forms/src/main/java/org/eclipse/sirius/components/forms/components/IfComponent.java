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

import org.eclipse.sirius.components.forms.description.IfDescription;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Used to conditionally render another element.
 *
 * @author sbegaudeau
 */
public class IfComponent implements IComponent {

    private IfComponentProps props;

    public IfComponent(IfComponentProps props) {
        this.props = props;
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.getVariableManager();
        IfDescription ifDescription = this.props.getIfDescription();
        Boolean result = ifDescription.getPredicate().apply(variableManager);
        if (result.booleanValue()) {
            WidgetComponentProps widgetComponentProps = new WidgetComponentProps(variableManager, ifDescription.getWidgetDescription(), this.props.getWidgetDescriptors());
            return new Element(WidgetComponent.class, widgetComponentProps);
        }
        return null;
    }

}
