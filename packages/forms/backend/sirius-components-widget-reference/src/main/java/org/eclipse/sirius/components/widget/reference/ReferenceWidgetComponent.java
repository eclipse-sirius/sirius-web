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

import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The component to render a reference widget.
 *
 * @author pcdavid
 */
public class ReferenceWidgetComponent implements IComponent {
    public static final String ITEM_VARIABLE = "item";

    private final ReferenceWidgetComponentProps props;

    public ReferenceWidgetComponent(ReferenceWidgetComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.getVariableManager();
        ReferenceWidgetDescription referenceDescription = this.props.getReferenceWidgetDescription();

        String id = referenceDescription.getIdProvider().apply(variableManager);
        String label = referenceDescription.getLabelProvider().apply(variableManager);
        String iconURL = referenceDescription.getIconURLProvider().apply(variableManager);

        boolean isMany = referenceDescription.getIsManyValuedProvider().apply(variableManager);
        boolean isContainer = referenceDescription.getIsContainerProvider().apply(variableManager);
        List<?> rawValue = referenceDescription.getItemsProvider().apply(variableManager);
        Setting setting = referenceDescription.getSettingProvider().apply(variableManager);

        List<ReferenceValue> items = rawValue.stream()
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

        var builder = ReferenceElementProps.newMultiValuedReferenceElementProps(id)
                .label(label)
                .iconURL(iconURL)
                .diagnostics(List.of())
                .container(isContainer)
                .manyValued(isMany)
                .values(items)
                .setting(setting);
        if (referenceDescription.getHelpTextProvider() != null) {
            builder.helpTextProvider(() -> referenceDescription.getHelpTextProvider().apply(variableManager));
        }
        return new Element(ReferenceElementProps.TYPE, builder.build());
    }

}
