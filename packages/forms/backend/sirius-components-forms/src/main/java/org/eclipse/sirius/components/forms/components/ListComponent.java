/*******************************************************************************
 * Copyright (c) 2019, 2021, 2023 Obeo.
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
import java.util.function.Function;
import java.util.function.Supplier;

import org.eclipse.sirius.components.forms.ClickEventKind;
import org.eclipse.sirius.components.forms.ListItem;
import org.eclipse.sirius.components.forms.ListStyle;
import org.eclipse.sirius.components.forms.description.ListDescription;
import org.eclipse.sirius.components.forms.elements.ListElementProps;
import org.eclipse.sirius.components.forms.validation.DiagnosticComponent;
import org.eclipse.sirius.components.forms.validation.DiagnosticComponentProps;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The component used to create the list widget and its items.
 *
 * @author sbegaudeau
 */
public class ListComponent implements IComponent {

    public static final String CANDIDATE_VARIABLE = "candidate";

    public static final String CLICK_EVENT_KIND_VARIABLE = "onClickEventKind";

    private ListComponentProps props;

    public ListComponent(ListComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        VariableManager variableManager = this.props.getVariableManager();
        ListDescription listDescription = this.props.getListDescription();

        String id = listDescription.getIdProvider().apply(variableManager);
        String label = listDescription.getLabelProvider().apply(variableManager);
        String iconURL = listDescription.getIconURLProvider().apply(variableManager);
        List<?> itemCandidates = listDescription.getItemsProvider().apply(variableManager);
        ListStyle style = listDescription.getStyleProvider().apply(variableManager);

        List<Element> children = List.of(new Element(DiagnosticComponent.class, new DiagnosticComponentProps(listDescription, variableManager)));

        List<ListItem> items = new ArrayList<>(itemCandidates.size());
        for (Object itemCandidate : itemCandidates) {
            VariableManager itemVariableManager = variableManager.createChild();
            itemVariableManager.put(CANDIDATE_VARIABLE, itemCandidate);

            String itemId = listDescription.getItemIdProvider().apply(itemVariableManager);
            String itemLabel = listDescription.getItemLabelProvider().apply(itemVariableManager);
            String itemKind = listDescription.getItemKindProvider().apply(itemVariableManager);
            String itemImageURL = listDescription.getItemImageURLProvider().apply(itemVariableManager);
            boolean isItemDeletable = listDescription.getItemDeletableProvider().apply(itemVariableManager);
            Function<VariableManager, IStatus> clickHandlerProvider = listDescription.getItemClickHandlerProvider();
            Function<VariableManager, IStatus> deleteHandlerProvider = listDescription.getItemDeleteHandlerProvider();
            Supplier<IStatus> deleteHandler = () -> {
                return deleteHandlerProvider.apply(itemVariableManager);
            };
            Function<ClickEventKind, IStatus> clickHandler = (clickEventKind) -> {
                VariableManager clickHandlerVariableManager = itemVariableManager.createChild();
                clickHandlerVariableManager.put(CLICK_EVENT_KIND_VARIABLE, clickEventKind.toString());
                return clickHandlerProvider.apply(clickHandlerVariableManager);
            };

            // @formatter:off
            ListItem item = ListItem.newListItem(itemId)
                    .label(itemLabel)
                    .kind(itemKind)
                    .imageURL(itemImageURL)
                    .deletable(isItemDeletable)
                    .clickHandler(clickHandler)
                    .deleteHandler(deleteHandler)
                    .build();
            // @formatter:on

            items.add(item);
        }

        // @formatter:off
        ListElementProps.Builder listElementPropsBuilder = ListElementProps.newListElementProps(id)
                .label(label)
                .items(items)
                .children(children);
        // @formatter:on
        if (iconURL != null) {
            listElementPropsBuilder.iconURL(iconURL);
        }
        if (style != null) {
            listElementPropsBuilder.style(style);
        }
        if (listDescription.getHelpTextProvider() != null) {
            listElementPropsBuilder.helpTextProvider(() -> listDescription.getHelpTextProvider().apply(variableManager));
        }

        return new Element(ListElementProps.TYPE, listElementPropsBuilder.build());
    }

}
