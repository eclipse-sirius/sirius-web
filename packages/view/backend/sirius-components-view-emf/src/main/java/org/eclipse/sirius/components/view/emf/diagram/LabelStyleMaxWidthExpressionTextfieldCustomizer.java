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
package org.eclipse.sirius.components.view.emf.diagram;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.forms.CompletionProposal;
import org.eclipse.sirius.components.forms.TextareaStyle;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.diagram.NodeLabelStyle;
import org.eclipse.sirius.components.view.emf.AQLTextfieldCustomizer;
import org.eclipse.sirius.components.view.emf.ITextfieldCustomizer;
import org.springframework.stereotype.Component;

/**
 * Provides specific help text for text fields which represent maxWidthExpression.
 *
 * @author frouene
 */
@Component
public class LabelStyleMaxWidthExpressionTextfieldCustomizer implements ITextfieldCustomizer {


    private final AQLTextfieldCustomizer aqlTextfieldCustomizer;

    public LabelStyleMaxWidthExpressionTextfieldCustomizer(AQLTextfieldCustomizer aqlTextfieldCustomizer) {
        this.aqlTextfieldCustomizer = Objects.requireNonNull(aqlTextfieldCustomizer);
    }

    @Override
    public boolean handles(EAttribute eAttribute, EObject eObject) {
        return eAttribute.getName().equals("maxWidthExpression") && eObject instanceof NodeLabelStyle;
    }

    @Override
    public Function<VariableManager, TextareaStyle> getStyleProvider() {
        return this.aqlTextfieldCustomizer.getStyleProvider();
    }

    @Override
    public Function<VariableManager, List<CompletionProposal>> getCompletionProposalsProvider() {
        return this.aqlTextfieldCustomizer.getCompletionProposalsProvider();
    }

    @Override
    public Function<VariableManager, String> getHelpTextProvider() {
        return variableManager -> "Applies only to labels with a WRAP or ELLIPSIS overflow strategy";
    }

}
