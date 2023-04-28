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
package org.eclipse.sirius.web.sample.slider;

import java.util.Optional;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.sirius.components.formdescriptioneditors.IWidgetDescriptionProvider;
import org.eclipse.sirius.web.customwidgets.CustomwidgetsPackage;
import org.springframework.stereotype.Service;

import graphql.com.google.common.base.Objects;

/**
 * The IWidgetDescriptionProvider for the Slider widget.
 *
 * @author pcdavid
 */
@Service
public class SliderWidgetDescriptionProvider implements IWidgetDescriptionProvider {

    @Override
    public Optional<EClass> getWidgetDescriptionType(String widgetKind) {
        if (Objects.equal(widgetKind, SliderWidgetDescriptor.TYPE)) {
            return Optional.of(CustomwidgetsPackage.Literals.SLIDER_DESCRIPTION);
        } else {
            return Optional.empty();
        }
    }

}
