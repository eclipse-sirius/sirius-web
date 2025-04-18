/*******************************************************************************
 * Copyright (c) 2025, 2025 Obeo.
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
package org.eclipse.sirius.components.view.emf.form.converters.widgets.api;

import java.util.Optional;

import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.view.form.WidgetDescription;

/**
 * Used to convert widget descriptions.
 *
 * @author sbegaudeau
 */
public interface IWidgetDescriptionConverter {
    boolean canConvert(WidgetDescription viewWidgetDescription);

    Optional<AbstractWidgetDescription> convert(WidgetDescription viewWidgetDescription, AQLInterpreter interpreter);
}
