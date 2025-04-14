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

import java.util.HashSet;
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

    public static final String BOLD = "bold";

    protected Set<String> customizedStyleProperties;

    protected Optional<LabelStyle> optPreviousLabelStyle;

    protected List<ILabelAppearanceChange> appearanceChanges;

    /**
     * Instantiate a label appearance handler when rendering a label.
     *
     * @param appearanceChanges
     *         list of label appearance changes to consider.
     * @param customizedStyleProperties
     *         customized style properties from previous render.
     * @param previousLabelStyle
     *         label style from previous render or null.
     */
    public LabelAppearanceHandler(List<ILabelAppearanceChange> appearanceChanges, Set<String> customizedStyleProperties, LabelStyle previousLabelStyle) {
        this.appearanceChanges = Objects.requireNonNull(appearanceChanges);
        this.customizedStyleProperties = new HashSet<>();
        this.customizedStyleProperties.addAll(Objects.requireNonNull(customizedStyleProperties));
        optPreviousLabelStyle = Optional.ofNullable(previousLabelStyle);
    }

    public Boolean isBold(Supplier<Boolean> provider) {
        boolean boldReset = this.appearanceChanges.stream()
                .filter(ResetLabelAppearanceChange.class::isInstance)
                .map(ResetLabelAppearanceChange.class::cast)
                .anyMatch(reset -> Objects.equals(reset.propertyName(), BOLD));

        if (boldReset) {
            this.customizedStyleProperties.remove(BOLD);
            return provider.get();
        } else {
            Optional<LabelBoldAppearanceChange> optBoldChange = this.appearanceChanges.stream()
                    .filter(LabelBoldAppearanceChange.class::isInstance)
                    .map(LabelBoldAppearanceChange.class::cast)
                    .findAny();

            boolean result;
            if (optBoldChange.isPresent()) {
                this.customizedStyleProperties.add(BOLD);
                result = optBoldChange.get().bold();
            } else if (this.customizedStyleProperties.contains(BOLD) && this.optPreviousLabelStyle.isPresent()) {
                LabelStyle previousLabelStyle = this.optPreviousLabelStyle.get();
                result = previousLabelStyle.isBold();
            } else {
                this.customizedStyleProperties.remove(BOLD);
                result = provider.get();
            }
            return result;
        }
    }

    public Set<String> getCustomizedStyleProperties() {
        return this.customizedStyleProperties;
    }
}
