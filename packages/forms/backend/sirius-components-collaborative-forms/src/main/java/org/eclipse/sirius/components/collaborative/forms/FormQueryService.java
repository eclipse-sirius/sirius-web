/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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

import org.eclipse.sirius.components.collaborative.forms.api.IFormQueryService;
import org.eclipse.sirius.components.forms.AbstractWidget;
import org.eclipse.sirius.components.forms.FlexboxContainer;
import org.eclipse.sirius.components.forms.Form;
import org.eclipse.sirius.components.forms.Group;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
        // @formatter:off
        Optional<AbstractWidget> optionalWidget = form.getPages().stream()
                .flatMap(page -> page.getGroups().stream())
                .flatMap(group -> this.getAllWidgets(group).stream())
                .filter(widget -> Objects.equals(widgetId, widget.getId()))
                .findFirst();
        // @formatter:on

        if (optionalWidget.isEmpty()) {
            this.logger.warn("The widget with the id {} has not been found", widgetId);
        }

        return optionalWidget;
    }

    private List<AbstractWidget> getAllWidgets(Group group) {
        List<AbstractWidget> widgets = new ArrayList<>();
        group.getToolbarActions().forEach(widgets::add);
        group.getWidgets().forEach(widget -> {
            if (widget instanceof FlexboxContainer) {
                widgets.addAll(this.getAllWidgets((FlexboxContainer) widget));
            } else {
                widgets.add(widget);
            }
        });
        return widgets;
    }

    private List<AbstractWidget> getAllWidgets(FlexboxContainer flexboxContainer) {
        List<AbstractWidget> widgets = new ArrayList<>();
        flexboxContainer.getChildren().forEach(widget -> {
            if (widget instanceof FlexboxContainer) {
                widgets.addAll(this.getAllWidgets((FlexboxContainer) widget));
            } else {
                widgets.add(widget);
            }
        });
        return widgets;
    }
}
