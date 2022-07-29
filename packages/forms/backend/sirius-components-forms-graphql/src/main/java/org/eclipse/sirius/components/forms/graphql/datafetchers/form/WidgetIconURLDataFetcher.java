/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.sirius.components.annotations.spring.graphql.QueryDataFetcher;
import org.eclipse.sirius.components.forms.AbstractWidget;
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

    private static final String ICON_URL_FIELD = "iconURL"; //$NON-NLS-1$

    @Override
    public List<FieldCoordinates> getFieldCoordinates() {
        // @formatter:off
        var widgetTypes = List.of(
                "ChartWidget", //$NON-NLS-1$
                "Checkbox", //$NON-NLS-1$
                "FlexboxContainer", //$NON-NLS-1$
                "Link", //$NON-NLS-1$
                "List", //$NON-NLS-1$
                "MultiSelect", //$NON-NLS-1$
                "Radio", //$NON-NLS-1$
                "Select", //$NON-NLS-1$
                "Textarea", //$NON-NLS-1$
                "Textfield", //$NON-NLS-1$
                "TreeWidget" //$NON-NLS-1$
        );
        return widgetTypes.stream().map(widgetType -> FieldCoordinates.coordinates(widgetType, ICON_URL_FIELD)).collect(Collectors.toList());
        // @formatter:on
    }

    @Override
    public String get(DataFetchingEnvironment environment) throws Exception {
        AbstractWidget item = environment.getSource();
        return Optional.ofNullable(item.getIconURL()).map(url -> URLConstants.IMAGE_BASE_PATH + url).orElse(null);
    }

}
