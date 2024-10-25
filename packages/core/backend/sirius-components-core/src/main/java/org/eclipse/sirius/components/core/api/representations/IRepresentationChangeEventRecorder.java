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
package org.eclipse.sirius.components.core.api.representations;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;

import java.util.List;

/**
 * Interface used to declare change event recorder.
 *
 * @author mcharfadi
 */
public interface IRepresentationChangeEventRecorder {

    List<IRepresentationChangeEvent> getChanges(IEditingContext editingContext, IInput input);

    boolean canHandle(IEditingContext editingContext, IInput input);

}
