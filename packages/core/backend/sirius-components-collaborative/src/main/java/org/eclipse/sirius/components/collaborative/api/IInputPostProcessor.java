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
package org.eclipse.sirius.components.collaborative.api;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;

import reactor.core.publisher.Sinks.Many;

/**
 * API to provide actions after the input processing.
 *
 * @author frouene
 */
public interface IInputPostProcessor {

    void postProcess(IEditingContext editingContext, IInput input, Many<ChangeDescription> changeDescriptionSink);
}
