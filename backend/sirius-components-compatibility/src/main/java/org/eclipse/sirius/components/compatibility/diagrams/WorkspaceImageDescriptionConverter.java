/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.components.compatibility.diagrams;

import java.util.Objects;

import org.eclipse.sirius.components.diagrams.ImageNodeStyle;
import org.eclipse.sirius.components.diagrams.LineStyle;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.Result;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.diagram.description.style.ContainerStyleDescription;
import org.eclipse.sirius.diagram.description.style.StylePackage;
import org.eclipse.sirius.diagram.description.style.WorkspaceImageDescription;
import org.eclipse.sirius.viewpoint.description.EAttributeCustomization;

/**
 * Used to convert workspace image description into image node style.
 *
 * @author sbegaudeau
 */
public class WorkspaceImageDescriptionConverter {

    private static final String WORKSPACE_PATH = "workspacePath"; //$NON-NLS-1$

    private static final int DEFAULT_SCALING_FACTOR = 1;

    private final AQLInterpreter interpreter;

    private final VariableManager variableManager;

    private final WorkspaceImageDescription workspaceImageDescription;

    private final EAttributeCustomizationProvider eAttributeCustomizationProvider;

    public WorkspaceImageDescriptionConverter(AQLInterpreter interpreter, VariableManager variableManager, WorkspaceImageDescription workspaceImageDescription) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.variableManager = Objects.requireNonNull(variableManager);
        this.workspaceImageDescription = Objects.requireNonNull(workspaceImageDescription);
        this.eAttributeCustomizationProvider = new EAttributeCustomizationProvider(interpreter, variableManager);
    }

    public ImageNodeStyle convert() {
        // @formatter:off
        String workspacePath = this.eAttributeCustomizationProvider.getEAttributeCustomization(this.workspaceImageDescription, WORKSPACE_PATH)
                .map(EAttributeCustomization::getValue)
                .flatMap(expression -> this.interpreter.evaluateExpression(this.variableManager.getVariables(), expression).asString())
                .orElse(this.workspaceImageDescription.getWorkspacePath());
        // @formatter:on

        Result scalingFactorResult = this.interpreter.evaluateExpression(this.variableManager.getVariables(), this.workspaceImageDescription.getSizeComputationExpression());
        int scalingFactor = scalingFactorResult.asInt().orElse(DEFAULT_SCALING_FACTOR);

        ColorDescriptionConverter colorDescriptionConverter = new ColorDescriptionConverter(this.interpreter, this.variableManager.getVariables());
        String borderColor = colorDescriptionConverter.convert(this.workspaceImageDescription.getBorderColor());
        LineStyle borderStyle = new LineStyleConverter().getStyle(this.workspaceImageDescription.getBorderLineStyle());
        int borderRadius = this.getBorderRadius(this.workspaceImageDescription);
        Result result = this.interpreter.evaluateExpression(this.variableManager.getVariables(), this.workspaceImageDescription.getBorderSizeComputationExpression());
        int borderSize = result.asInt().getAsInt();

        // @formatter:off
        return ImageNodeStyle.newImageNodeStyle()
                .imageURL(workspacePath.substring(workspacePath.indexOf('/', 1)))
                .scalingFactor(scalingFactor)
                .borderColor(borderColor)
                .borderRadius(borderRadius)
                .borderSize(borderSize)
                .borderStyle(borderStyle)
                .build();
        // @formatter:on
    }

    private int getBorderRadius(ContainerStyleDescription containerStyleDescription) {
        int borderRadius = 0;
        if (containerStyleDescription.isRoundedCorner()) {
            if (containerStyleDescription.eIsSet(StylePackage.Literals.ROUNDED_CORNER_STYLE_DESCRIPTION__ARC_HEIGHT)) {
                borderRadius = Math.max(borderRadius, containerStyleDescription.getArcHeight());
            }
            if (containerStyleDescription.eIsSet(StylePackage.Literals.ROUNDED_CORNER_STYLE_DESCRIPTION__ARC_WIDTH)) {
                borderRadius = Math.max(borderRadius, containerStyleDescription.getArcWidth());
            }
        }
        return borderRadius;
    }

}
