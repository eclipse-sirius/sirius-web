/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.components.view.emf.diagram.tools;

import org.eclipse.sirius.components.collaborative.dto.KeyBinding;
import org.eclipse.sirius.components.view.emf.diagram.tools.api.IKeyBindingFactory;
import org.springframework.stereotype.Service;

/**
 * Used to create key bindings.
 *
 * @author gdaniel
 */
@Service
public class KeyBindingFactory implements IKeyBindingFactory {

    @Override
    public KeyBinding createKeyBinding(org.eclipse.sirius.components.view.KeyBinding viewKeyBinding) {
        return KeyBinding.newKeyBinding()
                .isCtrl(viewKeyBinding.isCtrl())
                .isMeta(viewKeyBinding.isMeta())
                .isAlt(viewKeyBinding.isAlt())
                .key(viewKeyBinding.getKey())
                .build();
    }
}
