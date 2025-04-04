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
package org.eclipse.sirius.components.diagrams.renderer;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

import org.eclipse.sirius.components.diagrams.LabelStyle;
import org.eclipse.sirius.components.diagrams.events.appearance.ILabelAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.LabelBoldAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.ResetLabelAppearanceChange;

/**
 * Service used to handle the customization of a label's appearance.
 *
 * @author nvannier
 */
public class LabelAppearanceHandler {

    public static final String BOLD = "BOLD";

    private Set<String> previousCustomizedStyleProperties;

    private Optional<LabelStyle> optionalPreviousLabelStyle;

    private List<ILabelAppearanceChange> appearanceChanges;

    /**
     * Instantiate a label appearance handler when rendering a label.
     *
     * @param appearanceChanges
     *         The list of label appearance changes to consider.
     * @param previousCustomizedStyleProperties
     *         The customized style properties from previous render.
     * @param previousLabelStyle
     *         The label style from previous render or null.
     */
    public LabelAppearanceHandler(List<ILabelAppearanceChange> appearanceChanges, Set<String> previousCustomizedStyleProperties, LabelStyle previousLabelStyle) {
        this.appearanceChanges = Objects.requireNonNull(appearanceChanges);
        this.previousCustomizedStyleProperties = Objects.requireNonNull(previousCustomizedStyleProperties);
        optionalPreviousLabelStyle = Optional.ofNullable(previousLabelStyle);
    }

    public LabelAppearanceProperty<Boolean> isBold(Supplier<Boolean> provider) {
        boolean boldReset = this.appearanceChanges.stream()
                .filter(ResetLabelAppearanceChange.class::isInstance)
                .map(ResetLabelAppearanceChange.class::cast)
                .anyMatch(reset -> Objects.equals(reset.propertyName(), BOLD));

        if (boldReset) {
            return new LabelAppearanceProperty<>(provider.get(), false);
        } else {
            Optional<LabelBoldAppearanceChange> optionalBoldChange = this.appearanceChanges.stream()
                    .filter(LabelBoldAppearanceChange.class::isInstance)
                    .map(LabelBoldAppearanceChange.class::cast)
                    .findFirst();

            LabelAppearanceProperty<Boolean> result;
            if (optionalBoldChange.isPresent()) {
                result = new LabelAppearanceProperty<>(optionalBoldChange.get().bold(), true);
            } else if (this.previousCustomizedStyleProperties.contains(BOLD) && this.optionalPreviousLabelStyle.isPresent()) {
                LabelStyle previousLabelStyle = this.optionalPreviousLabelStyle.get();
                result = new LabelAppearanceProperty<>(previousLabelStyle.isBold(), true);
            } else {
                result = new LabelAppearanceProperty<>(provider.get(), false);
            }
            return result;
        }
    }
}
