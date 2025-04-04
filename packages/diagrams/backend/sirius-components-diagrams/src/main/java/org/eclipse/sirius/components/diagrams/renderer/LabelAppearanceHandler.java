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

    public static final LabelAppearanceHandler INSTANCE = new LabelAppearanceHandler();
    
    public static final String BOLD = "bold";

    public Boolean isBold(Supplier<Boolean> provider, List<ILabelAppearanceChange> changes, Optional<LabelStyle> optPreviousLabelStyle, Set<String> customizedStyleProperties) {
        boolean boldReset =
                changes.stream().filter(ResetLabelAppearanceChange.class::isInstance).map(ResetLabelAppearanceChange.class::cast).anyMatch(reset -> Objects.equals(reset.propertyName(), BOLD));

        if (!boldReset) {
            Optional<LabelBoldAppearanceChange> optBoldChange = changes.stream().filter(LabelBoldAppearanceChange.class::isInstance)
                    .map(LabelBoldAppearanceChange.class::cast).findAny();

            boolean result;
            if (optBoldChange.isPresent()) {
                customizedStyleProperties.add(BOLD);
                result = optBoldChange.get().bold();
            } else if (customizedStyleProperties.contains(BOLD) && optPreviousLabelStyle.isPresent()) {
                LabelStyle previousLabelStyle = optPreviousLabelStyle.get();
                result = previousLabelStyle.isBold();
            } else {
                customizedStyleProperties.remove(BOLD);
                result = provider.get();
            }
            return result;
        } else {
            customizedStyleProperties.remove(BOLD);
            return provider.get();
        }
    }

}
