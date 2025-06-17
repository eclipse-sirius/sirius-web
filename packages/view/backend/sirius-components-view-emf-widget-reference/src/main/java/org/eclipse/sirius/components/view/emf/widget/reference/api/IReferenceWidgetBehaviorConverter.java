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
package org.eclipse.sirius.components.view.emf.widget.reference.api;

import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescription;
import org.eclipse.sirius.components.widget.reference.ReferenceWidgetDescription.Builder;

/**
 * Used to convert parts of the reference widget.
 *
 * @author sbegaudeau
 */
public interface IReferenceWidgetBehaviorConverter {
    void convert(Builder referenceWidgetDescriptionBuilder, ReferenceWidgetDescription viewReferenceWidgetDescription, AQLInterpreter interpreter);
}
