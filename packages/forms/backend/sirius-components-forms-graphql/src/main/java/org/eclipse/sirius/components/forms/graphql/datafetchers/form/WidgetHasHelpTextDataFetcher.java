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
package org.eclipse.sirius.components.forms.graphql.datafetchers.form;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.forms.AbstractWidget;
import org.eclipse.sirius.components.forms.renderer.IWidgetDescriptor;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;

import graphql.schema.DataFetchingEnvironment;
import graphql.schema.FieldCoordinates;

/**
 * The data fetcher used to populate widget's hasHelpText field.
 *
 * @author pcdavid
 */
@QueryDataFetcher(type = "Widget", field = "hasHelpText")
public class WidgetHasHelpTextDataFetcher  implements IDataFetcherWithFieldCoordinates<Boolean> {
    
    private static final String HAS_HELP_TEXT_FIELD = "hasHelpText";

    private static final List<String> CORE_WIDGET_TYPES = List.of(
            "Button",
            "ChartWidget",
            "Checkbox",
            "FlexboxContainer",
            "Image",
            "LabelWidget",
            "Link",
            "List",
            "MultiSelect",
            "Radio",
            "RichText",
            "Select",
            "SplitButton",
            "Textarea",
            "Textfield",
            "ToolbarAction",
            "TreeWidget"
    );

    private final List<FieldCoordinates> allFieldCoordinates;

    public WidgetHasHelpTextDataFetcher(List<IWidgetDescriptor> customWidgetDescriptors) {
        this.allFieldCoordinates = new ArrayList<>();
        CORE_WIDGET_TYPES.stream()
            .map(widgetType -> FieldCoordinates.coordinates(widgetType, HAS_HELP_TEXT_FIELD))
            .forEach(this.allFieldCoordinates::add);
        customWidgetDescriptors.stream()
            .flatMap(descriptor -> descriptor.getWidgetTypes().stream())
            .map(widgetType -> FieldCoordinates.coordinates(widgetType, HAS_HELP_TEXT_FIELD))
            .forEach(this.allFieldCoordinates::add);
    }

    @Override
    public List<FieldCoordinates> getFieldCoordinates() {
        return this.allFieldCoordinates;
    }

    @Override
    public Boolean get(DataFetchingEnvironment environment) throws Exception {
        AbstractWidget item = environment.getSource();
        return item.getHelpTextProvider() != null;
    }

}
