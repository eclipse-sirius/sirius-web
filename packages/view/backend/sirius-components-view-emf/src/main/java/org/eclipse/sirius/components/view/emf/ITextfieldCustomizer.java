/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
package org.eclipse.sirius.components.view.emf;

import java.util.List;
import java.util.function.Function;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.sirius.components.forms.CompletionProposal;
import org.eclipse.sirius.components.forms.TextareaStyle;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Provides optional customizations to the style and behavior of specific text fields.
 *
 * @author pcdavid
 */
public interface ITextfieldCustomizer {
    boolean handles(EAttribute eAttribute);

    Function<VariableManager, TextareaStyle> getStyleProvider();

    Function<VariableManager, List<CompletionProposal>> getCompletionProposalsProvider();

    /**
     * Implementation which does nothing, used for mocks in unit tests.
     *
     * @author null
     */
    class NoOp implements ITextfieldCustomizer {

        @Override
        public boolean handles(EAttribute eAttribute) {
            return false;
        }

        @Override
        public Function<VariableManager, TextareaStyle> getStyleProvider() {
            return null;
        }

        @Override
        public Function<VariableManager, List<CompletionProposal>> getCompletionProposalsProvider() {
            return null;
        }

    }
}
