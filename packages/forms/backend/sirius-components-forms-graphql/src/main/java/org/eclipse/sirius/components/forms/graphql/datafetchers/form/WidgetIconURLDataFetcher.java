/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
import java.util.Optional;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.forms.AbstractWidget;
import org.eclipse.sirius.components.forms.renderer.IWidgetDescriptor;
import org.eclipse.sirius.components.graphql.api.IDataFetcherWithFieldCoordinates;
import org.eclipse.sirius.components.graphql.api.URLConstants;

import graphql.schema.DataFetchingEnvironment;
import graphql.schema.FieldCoordinates;

/**
 * The data fetcher used to concatenate the server image URL to widget's icon path.
 *
 * @author pcdavid
 */
@QueryDataFetcher(type = "Widget", field = "iconURL")
public class WidgetIconURLDataFetcher implements IDataFetcherWithFieldCoordinates<String> {

    private static final String ICON_URL_FIELD = "iconURL";

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
            "Textarea",
            "Textfield",
            "ToolbarAction",
            "TreeWidget"
    );


    private final List<FieldCoordinates> allFieldCoordinates;

    public WidgetIconURLDataFetcher(List<IWidgetDescriptor> customWidgetDescriptors) {
        this.allFieldCoordinates = new ArrayList<>();
        CORE_WIDGET_TYPES.stream()
            .map(widgetType -> FieldCoordinates.coordinates(widgetType, ICON_URL_FIELD))
            .forEach(this.allFieldCoordinates::add);
        customWidgetDescriptors.stream()
            .flatMap(descriptor -> descriptor.getWidgetTypes().stream())
            .map(widgetType -> FieldCoordinates.coordinates(widgetType, ICON_URL_FIELD))
            .forEach(this.allFieldCoordinates::add);
    }

    @Override
    public List<FieldCoordinates> getFieldCoordinates() {
        return this.allFieldCoordinates;
    }

    @Override
    public String get(DataFetchingEnvironment environment) throws Exception {
        AbstractWidget item = environment.getSource();
        return Optional.ofNullable(item.getIconURL()).map(url -> URLConstants.IMAGE_BASE_PATH + url).orElse(null);
    }

}
