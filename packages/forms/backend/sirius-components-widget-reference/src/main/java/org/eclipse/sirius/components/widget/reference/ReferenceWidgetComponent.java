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
package org.eclipse.sirius.components.widget.reference;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.sirius.components.forms.ClickEventKind;
import org.eclipse.sirius.components.forms.components.FormComponent;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.emf.form.ViewFormDescriptionConverter;

/**
 * The component to render a reference widget.
 *
 * @author pcdavid
 */
public class ReferenceWidgetComponent implements IComponent {

    public static final String ITEM_VARIABLE = "item";

    public static final String CLICK_EVENT_KIND_VARIABLE = "onClickEventKind";

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

        String iconURL = referenceDescription.getIconURLProvider().apply(variableManager);
        Boolean readOnly = referenceDescription.getIsReadOnlyProvider().apply(variableManager);
        String ownerId = referenceDescription.getOwnerIdProvider().apply(variableManager);

        List<?> rawValue = referenceDescription.getItemsProvider().apply(variableManager);
        List<?> rawOptions = referenceDescription.getOptionsProvider().apply(variableManager);
        Setting setting = referenceDescription.getSettingProvider().apply(variableManager);
        ReferenceWidgetStyle style = referenceDescription.getStyleProvider().apply(variableManager);

        List<ReferenceValue> items = rawValue.stream()
                .map(object -> {
                    VariableManager childVariables = variableManager.createChild();
                    childVariables.put(ReferenceWidgetComponent.ITEM_VARIABLE, object);
                    String itemId = referenceDescription.getItemIdProvider().apply(childVariables);
                    String itemLabel = referenceDescription.getItemLabelProvider().apply(childVariables);
                    String itemImageURL = referenceDescription.getItemImageURLProvider().apply(childVariables);
                    String itemKind = referenceDescription.getItemKindProvider().apply(childVariables);
                    Function<VariableManager, IStatus> clickHandlerProvider = referenceDescription.getItemClickHandlerProvider();
                    Function<VariableManager, IStatus> removeHandlerProvider = referenceDescription.getItemRemoveHandlerProvider();

                    var referenceValueBuilder = ReferenceValue.newReferenceValue(itemId)
                            .label(itemLabel)
                            .iconURL(itemImageURL)
                            .kind(itemKind);
                    if (clickHandlerProvider != null) {
                        Function<ClickEventKind, IStatus> clickHandler = (clickEventKind) -> {
                            VariableManager clickHandlerVariableManager = childVariables.createChild();
                            clickHandlerVariableManager.put(CLICK_EVENT_KIND_VARIABLE, clickEventKind.toString());
                            return clickHandlerProvider.apply(clickHandlerVariableManager);
                        };
                        referenceValueBuilder.clickHandler(clickHandler);
                    }
                    if (removeHandlerProvider != null) {
                        Supplier<IStatus> removeHandler = () -> {
                            return removeHandlerProvider.apply(childVariables);
                        };
                        referenceValueBuilder.removeHandler(removeHandler);
                    }
                    return referenceValueBuilder.build();
                })
                .toList();

        List<ReferenceValue> options = rawOptions.stream()
                .map(object -> {
                    VariableManager childVariables = variableManager.createChild();
                    childVariables.put(ReferenceWidgetComponent.ITEM_VARIABLE, object);
                    String itemId = referenceDescription.getItemIdProvider().apply(childVariables);
                    String itemLabel = referenceDescription.getItemLabelProvider().apply(childVariables);
                    String itemImageURL = referenceDescription.getItemImageURLProvider().apply(childVariables);
                    String itemKind = referenceDescription.getItemKindProvider().apply(childVariables);

                    return ReferenceValue.newReferenceValue(itemId)
                            .label(itemLabel)
                            .iconURL(itemImageURL)
                            .kind(itemKind)
                            .build();
                })
                .toList();

        var builder = ReferenceElementProps.newReferenceElementProps(id)
                .label(label)
                .iconURL(iconURL)
                .diagnostics(List.of())
                .values(items)
                .options(options)
                .setting(setting)
                .ownerId(ownerId)
                .clearHandler(() -> {
                    return referenceDescription.getClearHandlerProvider().apply(variableManager);
                });
        if (referenceDescription.getHelpTextProvider() != null) {
            builder.helpTextProvider(() -> referenceDescription.getHelpTextProvider().apply(variableManager));
        }
        if (referenceDescription.getSetHandlerProvider() != null) {
            Function<Object, IStatus> setHandler = object -> {
                VariableManager childVariables = variableManager.createChild();
                childVariables.put(ViewFormDescriptionConverter.NEW_VALUE, object);
                return referenceDescription.getSetHandlerProvider().apply(childVariables);
            };
            builder.setHandler(setHandler);
        }
        if (referenceDescription.getAddHandlerProvider() != null) {
            Function<List<?>, IStatus> addHandler = newValuesObjects -> {
                VariableManager childVariableManager = variableManager.createChild();
                childVariableManager.put(ViewFormDescriptionConverter.NEW_VALUE, newValuesObjects);
                return referenceDescription.getAddHandlerProvider().apply(childVariableManager);
            };
            builder.addHandler(addHandler);
        }

        if (readOnly != null) {
            builder.readOnly(readOnly);
        }
        if (style != null) {
            builder.style(style);
        }

        return new Element(ReferenceElementProps.TYPE, builder.build());
    }

}
