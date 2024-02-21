/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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
package org.eclipse.sirius.components.collaborative.forms;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;

import org.eclipse.sirius.components.collaborative.forms.api.IFormQueryService;
import org.eclipse.sirius.components.forms.AbstractWidget;
import org.eclipse.sirius.components.forms.FlexboxContainer;
import org.eclipse.sirius.components.forms.Form;
import org.eclipse.sirius.components.forms.Group;
import org.eclipse.sirius.components.forms.SplitButton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class used to manipulate forms.
 *
 * @author sbegaudeau
 * @author hmarchadour
 */
@Service
public class FormQueryService implements IFormQueryService {

    private final Logger logger = LoggerFactory.getLogger(FormQueryService.class);

    @Override
    public Optional<AbstractWidget> findWidget(Form form, String widgetId) {

        Optional<AbstractWidget> optionalWidget = form.getPages().stream()
                .flatMap(page -> page.getGroups().stream())
                .flatMap(group -> this.getAllWidgets(group).stream())
                .filter(widget -> Objects.equals(widgetId, widget.getId()))
                .findFirst()
                .or(() -> form.getPages().stream()
                        .flatMap(page -> page.getToolbarActions().stream())
                        .map(AbstractWidget.class::cast)
                        .filter(widget -> Objects.equals(widgetId, widget.getId()))
                        .findFirst());

        if (optionalWidget.isEmpty()) {
            this.logger.warn("The widget with the id {} has not been found", widgetId);
        }

        return optionalWidget;
    }

    private List<AbstractWidget> getAllWidgets(Group group) {
        List<AbstractWidget> widgets = new ArrayList<>(group.getToolbarActions());
        group.getWidgets().forEach(widget -> {
            widgets.add(widget);
            if (widget instanceof FlexboxContainer flexboxContainer) {
                widgets.addAll(this.getAllWidgets(flexboxContainer));
            } else if (widget instanceof SplitButton splitButton) {
                widgets.addAll(this.getAllWidgets(splitButton));
            }
        });
        return widgets;
    }

    private List<AbstractWidget> getAllWidgets(FlexboxContainer flexboxContainer) {
        List<AbstractWidget> widgets = new ArrayList<>();
        flexboxContainer.getChildren().forEach(widget -> {
            widgets.add(widget);
            if (widget instanceof FlexboxContainer subFlexboxContainer) {
                widgets.addAll(this.getAllWidgets(subFlexboxContainer));
            }
        });
        return widgets;
    }

    private List<AbstractWidget> getAllWidgets(SplitButton splitButton) {
        return splitButton.getActions().stream().map(AbstractWidget.class::cast).toList();
    }
}
