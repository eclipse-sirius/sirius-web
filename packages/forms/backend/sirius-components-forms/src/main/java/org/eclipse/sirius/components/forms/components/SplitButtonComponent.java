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
package org.eclipse.sirius.components.forms.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.forms.description.ButtonDescription;
import org.eclipse.sirius.components.forms.description.SplitButtonDescription;
import org.eclipse.sirius.components.forms.elements.SplitButtonElementProps;
import org.eclipse.sirius.components.forms.validation.DiagnosticComponent;
import org.eclipse.sirius.components.forms.validation.DiagnosticComponentProps;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The component used to create the splitButton widget.
 *
 * @author mcharfadi
 */
public class SplitButtonComponent implements IComponent {

    private final SplitButtonComponentProps props;

    public SplitButtonComponent(SplitButtonComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.getVariableManager();
        SplitButtonDescription splitButtonDescription = this.props.getSplitButtonDescription();

        String label = splitButtonDescription.getLabelProvider().apply(variableManager);
        List<String> iconURL = splitButtonDescription.getIconURLProvider().apply(variableManager);
        
        VariableManager idVariableManager = variableManager.createChild();
        idVariableManager.put(FormComponent.TARGET_OBJECT_ID, splitButtonDescription.getTargetObjectIdProvider().apply(variableManager));
        idVariableManager.put(FormComponent.CONTROL_DESCRIPTION_ID, splitButtonDescription.getId());
        idVariableManager.put(FormComponent.WIDGET_LABEL, label);
        String id = splitButtonDescription.getIdProvider().apply(idVariableManager);
        Boolean readOnly = splitButtonDescription.getIsReadOnlyProvider().apply(variableManager);

        List<Element> children = new ArrayList<>(List.of(new Element(DiagnosticComponent.class, new DiagnosticComponentProps(splitButtonDescription, variableManager))));

        VariableManager childrenVariableManager = idVariableManager.createChild();
        childrenVariableManager.put(FormComponent.PARENT_ELEMENT_ID, id);
        for (ButtonDescription buttonDescription : splitButtonDescription.getActions()) {
            children.add(new Element(ButtonComponent.class, new ButtonComponentProps(childrenVariableManager, buttonDescription)));
        }

        SplitButtonElementProps.Builder splitButtonElementPropsBuilder = SplitButtonElementProps.newSplitButtonElementProps(id)
                .label(label)
                .children(children);

        if (splitButtonDescription.getHelpTextProvider() != null) {
            splitButtonElementPropsBuilder.helpTextProvider(() -> splitButtonDescription.getHelpTextProvider().apply(variableManager));
        }

        if (iconURL != null) {
            splitButtonElementPropsBuilder.iconURL(iconURL);
        }

        if (readOnly != null) {
            splitButtonElementPropsBuilder.readOnly(readOnly);
        }

        SplitButtonElementProps splitButtonElementProps = splitButtonElementPropsBuilder.build();

        return new Element(SplitButtonElementProps.TYPE, splitButtonElementProps);
    }

}
