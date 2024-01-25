/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
package org.eclipse.sirius.components.widget.reference;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import org.eclipse.sirius.components.forms.components.FormComponent;
import org.eclipse.sirius.components.forms.validation.DiagnosticComponent;
import org.eclipse.sirius.components.forms.validation.DiagnosticComponentProps;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.widget.reference.dto.MoveReferenceValueHandlerParameters;

/**
 * The component to render a reference widget.
 *
 * @author pcdavid
 */
public class ReferenceWidgetComponent implements IComponent {

    public static final String NEW_VALUE = "newValue";

    public static final String ITEM_VARIABLE = "item";

    public static final String MOVE_FROM_VARIABLE = "fromIndex";

    public static final String MOVE_TO_VARIABLE = "toIndex";

    private final ReferenceWidgetComponentProps props;

    public ReferenceWidgetComponent(ReferenceWidgetComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.getVariableManager();
        ReferenceWidgetDescription referenceDescription = this.props.getReferenceWidgetDescription();

        String label = referenceDescription.getLabelProvider().apply(variableManager);
        VariableManager idVariableManager = variableManager.createChild();
        idVariableManager.put(FormComponent.TARGET_OBJECT_ID, referenceDescription.getTargetObjectIdProvider().apply(variableManager));
        idVariableManager.put(FormComponent.CONTROL_DESCRIPTION_ID, referenceDescription.getId());
        idVariableManager.put(FormComponent.WIDGET_LABEL, label);
        String id = referenceDescription.getIdProvider().apply(idVariableManager);

        List<String> iconURL = referenceDescription.getIconURLProvider().apply(variableManager);
        Boolean readOnly = referenceDescription.getIsReadOnlyProvider().apply(variableManager);
        String ownerId = referenceDescription.getOwnerIdProvider().apply(variableManager);

        String ownerKind = referenceDescription.getOwnerKindProvider().apply(variableManager);
        String referenceKind = referenceDescription.getReferenceKindProvider().apply(variableManager);
        boolean isContainment = referenceDescription.getIsContainmentProvider().apply(variableManager);
        boolean isMany = referenceDescription.getIsManyProvider().apply(variableManager);
        ReferenceWidgetStyle style = referenceDescription.getStyleProvider().apply(variableManager);

        List<ReferenceValue> items = this.getItems(variableManager, referenceDescription);

        List<Element> children = List.of(new Element(DiagnosticComponent.class, new DiagnosticComponentProps(referenceDescription, variableManager)));

        var builder = ReferenceElementProps.newReferenceElementProps(id)
                .label(label)
                .iconURL(iconURL)
                .descriptionId(referenceDescription.getId())
                .values(items)
                .optionsProvider(() -> this.getOptions(variableManager, referenceDescription))
                .ownerKind(ownerKind)
                .referenceKind(referenceKind)
                .containment(isContainment)
                .many(isMany)
                .ownerId(ownerId)
                .children(children);
        if (referenceDescription.getHelpTextProvider() != null) {
            builder.helpTextProvider(() -> referenceDescription.getHelpTextProvider().apply(variableManager));
        }
        if (referenceDescription.getClearHandlerProvider() != null) {
            builder.clearHandler(() -> referenceDescription.getClearHandlerProvider().apply(variableManager));
        }
        if (referenceDescription.getSetHandlerProvider() != null) {
            Function<Object, IStatus> setHandler = object -> {
                VariableManager childVariables = variableManager.createChild();
                childVariables.put(NEW_VALUE, object);
                return referenceDescription.getSetHandlerProvider().apply(childVariables);
            };
            builder.setHandler(setHandler);
        }
        if (referenceDescription.getAddHandlerProvider() != null) {
            Function<List<?>, IStatus> addHandler = newValuesObjects -> {
                VariableManager childVariableManager = variableManager.createChild();
                childVariableManager.put(NEW_VALUE, newValuesObjects);
                return referenceDescription.getAddHandlerProvider().apply(childVariableManager);
            };
            builder.addHandler(addHandler);
        }
        if (referenceDescription.getMoveHandlerProvider() != null) {
            Function<MoveReferenceValueHandlerParameters, IStatus> moveHandler = input -> {
                VariableManager childVariableManager = variableManager.createChild();
                childVariableManager.put(ITEM_VARIABLE, input.value());
                childVariableManager.put(MOVE_FROM_VARIABLE, input.fromIndex());
                childVariableManager.put(MOVE_TO_VARIABLE, input.toIndex());
                return referenceDescription.getMoveHandlerProvider().apply(childVariableManager);
            };
            builder.moveHandler(moveHandler);
        }

        if (readOnly != null) {
            builder.readOnly(readOnly);
        }
        if (style != null) {
            builder.style(style);
        }

        return new Element(ReferenceElementProps.TYPE, builder.build());
    }

    private List<ReferenceValue> getItems(VariableManager variableManager, ReferenceWidgetDescription referenceDescription) {
        List<?> rawValue = referenceDescription.getItemsProvider().apply(variableManager);
        List<ReferenceValue> items = rawValue.stream()
                .map(object -> {
                    VariableManager childVariables = variableManager.createChild();
                    childVariables.put(ReferenceWidgetComponent.ITEM_VARIABLE, object);
                    String itemId = referenceDescription.getItemIdProvider().apply(childVariables);
                    String itemLabel = referenceDescription.getItemLabelProvider().apply(childVariables);
                    List<String> itemImageURL = referenceDescription.getItemIconURLProvider().apply(childVariables);
                    String itemKind = referenceDescription.getItemKindProvider().apply(childVariables);
                    Function<VariableManager, IStatus> removeHandlerProvider = referenceDescription.getItemRemoveHandlerProvider();

                    var referenceValueBuilder = ReferenceValue.newReferenceValue(itemId)
                            .label(itemLabel)
                            .iconURL(itemImageURL)
                            .kind(itemKind);
                    if (removeHandlerProvider != null) {
                        Supplier<IStatus> removeHandler = () -> {
                            return removeHandlerProvider.apply(childVariables);
                        };
                        referenceValueBuilder.removeHandler(removeHandler);
                    }
                    return referenceValueBuilder.build();
                })
                .toList();
        return items;
    }

    private List<ReferenceValue> getOptions(VariableManager variableManager, ReferenceWidgetDescription referenceDescription) {
        return referenceDescription.getOptionsProvider().apply(variableManager).stream().map(object -> {
            VariableManager childVariables = variableManager.createChild();
            childVariables.put(ReferenceWidgetComponent.ITEM_VARIABLE, object);
            String itemId = referenceDescription.getItemIdProvider().apply(childVariables);
            String itemLabel = referenceDescription.getItemLabelProvider().apply(childVariables);
            List<String> itemImageURL = referenceDescription.getItemIconURLProvider().apply(childVariables);
            String itemKind = referenceDescription.getItemKindProvider().apply(childVariables);
            return ReferenceValue.newReferenceValue(itemId)
                    .label(itemLabel)
                    .iconURL(itemImageURL)
                    .kind(itemKind)
                    .build();
        }).toList();
    }
}
