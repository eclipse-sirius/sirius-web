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
import java.util.Optional;

import org.eclipse.sirius.components.forms.description.TableWidgetDescription;
import org.eclipse.sirius.components.forms.elements.TableWidgetElementProps;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.tables.components.TableComponent;
import org.eclipse.sirius.components.tables.components.TableComponentProps;

/**
 * The component used to render the table widget.
 *
 * @author lfasani
 */
public class TableWidgetComponent implements IComponent {

    private final TableWidgetComponentProps props;

    public TableWidgetComponent(TableWidgetComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.getVariableManager();
        TableWidgetDescription tableWidgetDescription = this.props.getTableDescription();

        String label = tableWidgetDescription.getLabelProvider().apply(variableManager);

        VariableManager idVariableManager = variableManager.createChild();
        idVariableManager.put(FormComponent.TARGET_OBJECT_ID, tableWidgetDescription.getTargetObjectIdProvider().apply(variableManager));
        idVariableManager.put(FormComponent.CONTROL_DESCRIPTION_ID, tableWidgetDescription.getId());
        idVariableManager.put(FormComponent.WIDGET_LABEL, label);
        String id = tableWidgetDescription.getIdProvider().apply(idVariableManager);

        List<String> iconURL = tableWidgetDescription.getIconURLProvider().apply(variableManager);
        Boolean readOnly = tableWidgetDescription.getIsReadOnlyProvider().apply(variableManager);

        Element tableElement = new Element(TableComponent.class,
                new TableComponentProps(variableManager, tableWidgetDescription.getTableDescription(), Optional.empty()));

        List<Element> children = new ArrayList<>();
        children.add(tableElement);

        TableWidgetElementProps.Builder tableElementPropsBuilder = TableWidgetElementProps.newTableWidgetElementProps(id)
                .label(label)
                .children(children);

        if (iconURL != null) {
            tableElementPropsBuilder.iconURL(iconURL);
        }
        if (tableWidgetDescription.getHelpTextProvider() != null) {
            tableElementPropsBuilder.helpTextProvider(() -> tableWidgetDescription.getHelpTextProvider().apply(variableManager));
        }
        if (readOnly != null) {
            tableElementPropsBuilder.readOnly(readOnly);
        }

        TableWidgetElementProps tableWidgetElementProps = tableElementPropsBuilder.build();
        return new Element(TableWidgetElementProps.TYPE, tableWidgetElementProps);
    }

}
