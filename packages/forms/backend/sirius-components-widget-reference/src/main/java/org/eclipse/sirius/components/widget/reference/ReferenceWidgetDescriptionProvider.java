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

import java.util.Optional;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.sirius.components.formdescriptioneditors.IWidgetDescriptionProvider;
import org.eclipse.sirius.components.widgets.reference.ReferencePackage;
import org.springframework.stereotype.Service;

import graphql.com.google.common.base.Objects;

/**
 * The IWidgetDescriptionProvider for the Reference widget.
 *
 * @author pcdavid
 */
@Service
public class ReferenceWidgetDescriptionProvider implements IWidgetDescriptionProvider {

    @Override
    public Optional<EClass> getWidgetDescriptionType(String widgetKind) {
        if (Objects.equal(widgetKind, ReferenceWidgetDescriptor.TYPE)) {
            return Optional.of(ReferencePackage.Literals.REFERENCE_WIDGET_DESCRIPTION);
        } else {
            return Optional.empty();
        }
    }

}
