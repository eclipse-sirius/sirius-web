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
package org.eclipse.sirius.components.view.emf;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.core.api.IURLParser;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.springframework.stereotype.Service;

/**
 * Used to test if a representation description has been created by the view converter.
 *
 * @author sbegaudeau
 */
@Service
public class ViewRepresentationDescriptionPredicate implements IViewRepresentationDescriptionPredicate {

    private final IURLParser urlParser;

    public ViewRepresentationDescriptionPredicate(IURLParser urlParser) {
        this.urlParser = Objects.requireNonNull(urlParser);
    }

    @Override
    public boolean test(IRepresentationDescription representationDescription) {
        if (representationDescription.getId().startsWith(IRepresentationDescriptionIdProvider.PREFIX)) {
            Map<String, List<String>> parameters = this.urlParser.getParameterValues(representationDescription.getId());
            List<String> values = Optional.ofNullable(parameters.get(IRepresentationDescriptionIdProvider.SOURCE_KIND)).orElse(List.of());
            return values.contains(IRepresentationDescriptionIdProvider.VIEW_SOURCE_KIND);
        }
        return false;
    }
}
