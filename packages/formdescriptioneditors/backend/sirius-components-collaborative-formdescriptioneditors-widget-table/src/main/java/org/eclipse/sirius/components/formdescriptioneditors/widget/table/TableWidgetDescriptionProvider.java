/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.components.formdescriptioneditors.widget.table;

import java.util.Optional;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.sirius.components.formdescriptioneditors.IWidgetDescriptionProvider;
import org.eclipse.sirius.components.forms.elements.TableWidgetElementProps;
import org.eclipse.sirius.components.view.widget.tablewidget.TableWidgetPackage;
import org.springframework.stereotype.Service;

/**
 * The IWidgetDescriptionProvider for the table widget.
 *
 * @author frouene
 */
@Service
public class TableWidgetDescriptionProvider implements IWidgetDescriptionProvider {

    @Override
    public Optional<EClass> getWidgetDescriptionType(String widgetKind) {
        if (TableWidgetElementProps.TYPE.equals(widgetKind)) {
            return Optional.of(TableWidgetPackage.Literals.TABLE_WIDGET_DESCRIPTION);
        } else {
            return Optional.empty();
        }
    }

}
