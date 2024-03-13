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
package org.eclipse.sirius.components.collaborative.editingcontext;

import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.editingcontext.api.IEditingContextChangeDescriptionListener;
import org.eclipse.sirius.components.core.api.IInput;
import org.springframework.stereotype.Service;

/**
 * Used to react to a change description.
 *
 * @author sbegaudeau
 */
@Service
public class EditingContextChangeDescriptionConsumer implements IEditingContextChangeDescriptionListener {
    @Override
    public void onChangeDescription(ChangeDescription changeDescription, Consumer<IInput> inputConsumer) {

    }
}
