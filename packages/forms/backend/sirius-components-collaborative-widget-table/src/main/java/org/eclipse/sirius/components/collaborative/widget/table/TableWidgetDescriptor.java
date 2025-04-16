/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.components.collaborative.widget.table;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.forms.renderer.IWidgetDescriptor;
import org.eclipse.sirius.components.forms.validation.Diagnostic;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.tables.Table;
import org.eclipse.sirius.components.tables.components.ICustomCellDescriptor;
import org.eclipse.sirius.components.tables.elements.TableElementProps;
import org.eclipse.sirius.components.tables.renderer.TableComponentPropsValidator;
import org.eclipse.sirius.components.tables.renderer.TableElementFactory;
import org.eclipse.sirius.components.tables.renderer.TableInstancePropsValidator;
import org.eclipse.sirius.components.tables.renderer.TableRenderer;
import org.eclipse.sirius.components.widget.table.TableWidget;
import org.eclipse.sirius.components.widget.table.TableWidgetComponent;
import org.eclipse.sirius.components.widget.table.TableWidgetComponentProps;
import org.eclipse.sirius.components.widget.table.TableWidgetDescription;
import org.eclipse.sirius.components.widget.table.TableWidgetElementProps;
import org.springframework.stereotype.Component;

/**
 * Widget descriptor for the table widget.
 *
 * @author frouene
 */
@Component
public class TableWidgetDescriptor implements IWidgetDescriptor {

    private final List<ICustomCellDescriptor> customCellDescriptors;

    private final TableComponentPropsValidator tableComponentPropsValidator;

    private final TableInstancePropsValidator tableInstancePropsValidator;

    private final TableElementFactory tableElementFactory;

    public TableWidgetDescriptor(List<ICustomCellDescriptor> customCellDescriptors) {
        this.customCellDescriptors = Objects.requireNonNull(customCellDescriptors);
        this.tableComponentPropsValidator = new TableComponentPropsValidator(customCellDescriptors);
        this.tableInstancePropsValidator = new TableInstancePropsValidator(customCellDescriptors);
        this.tableElementFactory = new TableElementFactory(customCellDescriptors);
    }

    @Override
    public List<String> getWidgetTypes() {
        return List.of(TableElementProps.TYPE);
    }

    @Override
    public Optional<Boolean> validateComponentProps(Class<?> componentType, IProps props) {
        Optional<Boolean> result = Optional.empty();
        if (TableWidgetComponent.class.equals(componentType)) {
            result = Optional.of(props instanceof TableWidgetComponentProps);
        } else if (this.tableComponentPropsValidator.validateComponentProps(componentType, props)) {
            result = Optional.of(true);
        }
        return result;
    }

    @Override
    public Optional<Boolean> validateInstanceProps(String type, IProps props) {
        Optional<Boolean> result = Optional.empty();
        if (Objects.equals(type, TableWidgetElementProps.TYPE)) {
            result = Optional.of(props instanceof TableWidgetElementProps);
        } else if (this.tableInstancePropsValidator.validateInstanceProps(type, props)) {
            result = Optional.of(true);
        }
        return result;
    }

    @Override
    public Optional<Object> instanciate(String type, IProps elementProps, List<Object> children) {
        Object object = this.tableElementFactory.instantiateElement(type, elementProps, children);
        if (object == null) {
            Optional<Table> table = children.stream()
                    .filter(Table.class::isInstance)
                    .map(Table.class::cast)
                    .findFirst();

            List<Diagnostic> diagnostics = children.stream()
                    .filter(Diagnostic.class::isInstance)
                    .map(Diagnostic.class::cast)
                    .toList();
            if (Objects.equals(type, TableWidgetElementProps.TYPE) && elementProps instanceof TableWidgetElementProps props && table.isPresent()) {
                TableWidget.Builder tableBuilder = TableWidget.newTableWidget(props.getId())
                        .label(props.getLabel())
                        .iconURL(props.getIconURL())
                        .diagnostics(diagnostics)
                        .readOnly(props.isReadOnly())
                        .table(table.get());

                if (props.getHelpTextProvider() != null) {
                    tableBuilder.helpTextProvider(props.getHelpTextProvider());
                }
                object = tableBuilder.build();
            }

        }
        return Optional.ofNullable(object);
    }

    @Override
    public Optional<Element> createElement(VariableManager variableManager, AbstractWidgetDescription widgetDescription) {
        if (widgetDescription instanceof TableWidgetDescription tableWidgetDescription) {
            variableManager.put(TableRenderer.CUSTOM_CELL_DESCRIPTORS, this.customCellDescriptors);
            TableWidgetComponentProps tableWidgetComponentProps = new TableWidgetComponentProps(variableManager, tableWidgetDescription);
            return Optional.of(new Element(TableWidgetComponent.class, tableWidgetComponentProps));
        } else {
            return Optional.empty();
        }
    }
}
