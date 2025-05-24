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

    protected Set<String> previousCustomizedStyleProperties;

    protected Optional<LabelStyle> optPreviousLabelStyle;

    protected List<ILabelAppearanceChange> appearanceChanges;

    /**
     * Instantiate a label appearance handler when rendering a label.
     *
     * @param appearanceChanges
     *         list of label appearance changes to consider.
     * @param previousCustomizedStyleProperties
     *         customized style properties from previous render.
     * @param previousLabelStyle
     *         label style from previous render or null.
     */
    public LabelAppearanceHandler(List<ILabelAppearanceChange> appearanceChanges, Set<String> previousCustomizedStyleProperties, LabelStyle previousLabelStyle) {
        this.appearanceChanges = Objects.requireNonNull(appearanceChanges);
        this.previousCustomizedStyleProperties = Objects.requireNonNull(previousCustomizedStyleProperties);
        optPreviousLabelStyle = Optional.ofNullable(previousLabelStyle);
    }

    public LabelAppearanceProperty<Boolean> isBold(Supplier<Boolean> provider) {
        boolean boldReset = this.appearanceChanges.stream()
                .filter(ResetLabelAppearanceChange.class::isInstance)
                .map(ResetLabelAppearanceChange.class::cast)
                .anyMatch(reset -> Objects.equals(reset.propertyName(), BOLD));

        if (boldReset) {
            return new LabelAppearanceProperty<Boolean>(provider.get(), false);
        } else {
            Optional<LabelBoldAppearanceChange> optBoldChange = this.appearanceChanges.stream()
                    .filter(LabelBoldAppearanceChange.class::isInstance)
                    .map(LabelBoldAppearanceChange.class::cast)
                    .findAny();

            LabelAppearanceProperty<Boolean> result;
            if (optBoldChange.isPresent()) {
                result = new LabelAppearanceProperty<>(optBoldChange.get().bold(), true);
            } else if (this.previousCustomizedStyleProperties.contains(BOLD) && this.optPreviousLabelStyle.isPresent()) {
                LabelStyle previousLabelStyle = this.optPreviousLabelStyle.get();
                result = new LabelAppearanceProperty<>(previousLabelStyle.isBold(), true);
            } else {
                result = new LabelAppearanceProperty<>(provider.get(), false);
            }
            return result;
        }
    }
}
