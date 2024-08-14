/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.components.view.emf.api;

import java.util.List;

import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.view.diagram.DialogDescription;

/**
 * Common interface for services dedicated to the {@link DialogDescription} conversion.
 *
 * @author fbarbin
 */
public interface IDialogDescriptionConverter {

    boolean canConvert(DialogDescription dialogDescription);

    List<IRepresentationDescription> convert(DialogDescription dialogDescription, AQLInterpreter interpreter);
}
