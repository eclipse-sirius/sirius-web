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
package org.eclipse.sirius.web.application.undo.services.recorder;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.sirius.components.diagrams.LabelStyle;
import org.eclipse.sirius.components.diagrams.events.appearance.IAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.label.ILabelAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.label.LabelBackgroundAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.label.LabelBoldAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.label.LabelBorderColorAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.label.LabelBorderRadiusAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.label.LabelBorderSizeAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.label.LabelBorderStyleAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.label.LabelColorAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.label.LabelFontSizeAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.label.LabelItalicAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.label.LabelStrikeThroughAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.label.LabelUnderlineAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.label.LabelVisibilityAppearanceChange;
import org.eclipse.sirius.components.diagrams.events.appearance.label.ResetLabelAppearanceChange;
import org.eclipse.sirius.components.diagrams.renderer.LabelAppearanceHandler;
import org.eclipse.sirius.web.application.undo.services.api.ILabelAppearanceChangeProvider;
import org.springframework.stereotype.Service;

/**
 * Used to provide new appearance changes from previous Label and previous Customized StyleProperties.
 *
 * @author mcharfadi
 */
@Service
public class LabelAppearanceChangeProvider implements ILabelAppearanceChangeProvider {

    @Override
    public List<IAppearanceChange> getAppearanceChanges(ILabelAppearanceChange change, Set<String> previousCustomizedStyleProperties, LabelStyle previousLabelStyle) {
        List<IAppearanceChange> appearanceChanges = new ArrayList<>();
        if (change instanceof LabelBoldAppearanceChange) {
            if (previousCustomizedStyleProperties.contains(LabelAppearanceHandler.BOLD)) {
                appearanceChanges.add(new LabelBoldAppearanceChange(change.labelId(), previousLabelStyle.isBold()));
            } else {
                appearanceChanges.add(new ResetLabelAppearanceChange(change.labelId(), LabelAppearanceHandler.BOLD));
            }
        }
        if (change instanceof LabelItalicAppearanceChange) {
            if (previousCustomizedStyleProperties.contains(LabelAppearanceHandler.ITALIC)) {
                appearanceChanges.add(new LabelItalicAppearanceChange(change.labelId(), previousLabelStyle.isItalic()));
            } else {
                appearanceChanges.add(new ResetLabelAppearanceChange(change.labelId(), LabelAppearanceHandler.ITALIC));
            }
        }
        if (change instanceof LabelUnderlineAppearanceChange) {
            if (previousCustomizedStyleProperties.contains(LabelAppearanceHandler.UNDERLINE)) {
                appearanceChanges.add(new LabelUnderlineAppearanceChange(change.labelId(), previousLabelStyle.isUnderline()));
            } else {
                appearanceChanges.add(new ResetLabelAppearanceChange(change.labelId(), LabelAppearanceHandler.UNDERLINE));
            }
        }
        if (change instanceof LabelStrikeThroughAppearanceChange) {
            if (previousCustomizedStyleProperties.contains(LabelAppearanceHandler.STRIKE_THROUGH)) {
                appearanceChanges.add(new LabelStrikeThroughAppearanceChange(change.labelId(), previousLabelStyle.isStrikeThrough()));
            } else {
                appearanceChanges.add(new ResetLabelAppearanceChange(change.labelId(), LabelAppearanceHandler.STRIKE_THROUGH));
            }
        }
        if (change instanceof LabelColorAppearanceChange) {
            if (previousCustomizedStyleProperties.contains(LabelAppearanceHandler.COLOR)) {
                appearanceChanges.add(new LabelColorAppearanceChange(change.labelId(), previousLabelStyle.getColor()));
            } else {
                appearanceChanges.add(new ResetLabelAppearanceChange(change.labelId(), LabelAppearanceHandler.COLOR));
            }
        }
        if (change instanceof LabelFontSizeAppearanceChange) {
            if (previousCustomizedStyleProperties.contains(LabelAppearanceHandler.FONT_SIZE)) {
                appearanceChanges.add(new LabelFontSizeAppearanceChange(change.labelId(), previousLabelStyle.getFontSize()));
            } else {
                appearanceChanges.add(new ResetLabelAppearanceChange(change.labelId(), LabelAppearanceHandler.FONT_SIZE));
            }
        }
        if (change instanceof LabelBackgroundAppearanceChange) {
            if (previousCustomizedStyleProperties.contains(LabelAppearanceHandler.BACKGROUND)) {
                appearanceChanges.add(new LabelBackgroundAppearanceChange(change.labelId(), previousLabelStyle.getBackground()));
            } else {
                appearanceChanges.add(new ResetLabelAppearanceChange(change.labelId(), LabelAppearanceHandler.BACKGROUND));
            }
        }
        if (change instanceof LabelBorderColorAppearanceChange) {
            if (previousCustomizedStyleProperties.contains(LabelAppearanceHandler.BORDER_COLOR)) {
                appearanceChanges.add(new LabelBorderColorAppearanceChange(change.labelId(), previousLabelStyle.getBorderColor()));
            } else {
                appearanceChanges.add(new ResetLabelAppearanceChange(change.labelId(), LabelAppearanceHandler.BORDER_COLOR));
            }
        }
        if (change instanceof LabelBorderSizeAppearanceChange) {
            if (previousCustomizedStyleProperties.contains(LabelAppearanceHandler.BORDER_SIZE)) {
                appearanceChanges.add(new LabelBorderSizeAppearanceChange(change.labelId(), previousLabelStyle.getBorderSize()));
            } else {
                appearanceChanges.add(new ResetLabelAppearanceChange(change.labelId(), LabelAppearanceHandler.BORDER_SIZE));
            }
        }
        if (change instanceof LabelBorderRadiusAppearanceChange) {
            if (previousCustomizedStyleProperties.contains(LabelAppearanceHandler.BORDER_RADIUS)) {
                appearanceChanges.add(new LabelBorderRadiusAppearanceChange(change.labelId(), previousLabelStyle.getBorderRadius()));
            } else {
                appearanceChanges.add(new ResetLabelAppearanceChange(change.labelId(), LabelAppearanceHandler.BORDER_RADIUS));
            }
        }
        if (change instanceof LabelBorderStyleAppearanceChange) {
            if (previousCustomizedStyleProperties.contains(LabelAppearanceHandler.BORDER_STYLE)) {
                appearanceChanges.add(new LabelBorderStyleAppearanceChange(change.labelId(), previousLabelStyle.getBorderStyle()));
            } else {
                appearanceChanges.add(new ResetLabelAppearanceChange(change.labelId(), LabelAppearanceHandler.BORDER_STYLE));
            }
        }
        if (change instanceof LabelVisibilityAppearanceChange) {
            if (previousCustomizedStyleProperties.contains(LabelAppearanceHandler.VISIBILITY)) {
                appearanceChanges.add(new LabelVisibilityAppearanceChange(change.labelId(), previousLabelStyle.getVisibility()));
            } else {
                appearanceChanges.add(new ResetLabelAppearanceChange(change.labelId(), LabelAppearanceHandler.VISIBILITY));
            }
        }
        if (change instanceof ResetLabelAppearanceChange) {
            appearanceChanges.addAll(this.getResetLabelAppearanceChange(change.labelId(), previousCustomizedStyleProperties, previousLabelStyle));
        }
        return appearanceChanges;
    }

    @Override
    public List<IAppearanceChange> getResetLabelAppearanceChange(String labelId, Set<String> previousCustomizedStyleProperties, LabelStyle previousLabelStyle) {
        List<IAppearanceChange> appearanceChanges = new ArrayList<>();
        previousCustomizedStyleProperties.forEach(customizedStyleProperty -> {
            switch (customizedStyleProperty) {
                case LabelAppearanceHandler.BOLD -> appearanceChanges.add(new LabelBoldAppearanceChange(labelId, previousLabelStyle.isBold()));
                case LabelAppearanceHandler.ITALIC -> appearanceChanges.add(new LabelItalicAppearanceChange(labelId, previousLabelStyle.isItalic()));
                case LabelAppearanceHandler.UNDERLINE -> appearanceChanges.add(new LabelUnderlineAppearanceChange(labelId, previousLabelStyle.isUnderline()));
                case LabelAppearanceHandler.STRIKE_THROUGH -> appearanceChanges.add(new LabelStrikeThroughAppearanceChange(labelId, previousLabelStyle.isStrikeThrough()));
                case LabelAppearanceHandler.COLOR -> appearanceChanges.add(new LabelColorAppearanceChange(labelId, previousLabelStyle.getColor()));
                case LabelAppearanceHandler.FONT_SIZE -> appearanceChanges.add(new LabelFontSizeAppearanceChange(labelId, previousLabelStyle.getFontSize()));
                case LabelAppearanceHandler.BACKGROUND -> appearanceChanges.add(new LabelBackgroundAppearanceChange(labelId, previousLabelStyle.getBackground()));
                case LabelAppearanceHandler.BORDER_COLOR -> appearanceChanges.add(new LabelBorderColorAppearanceChange(labelId, previousLabelStyle.getBorderColor()));
                case LabelAppearanceHandler.BORDER_SIZE -> appearanceChanges.add(new LabelBorderSizeAppearanceChange(labelId, previousLabelStyle.getBorderSize()));
                case LabelAppearanceHandler.BORDER_RADIUS -> appearanceChanges.add(new LabelBorderRadiusAppearanceChange(labelId, previousLabelStyle.getBorderRadius()));
                case LabelAppearanceHandler.BORDER_STYLE -> appearanceChanges.add(new LabelBorderStyleAppearanceChange(labelId, previousLabelStyle.getBorderStyle()));
                case LabelAppearanceHandler.VISIBILITY -> appearanceChanges.add(new LabelVisibilityAppearanceChange(labelId, previousLabelStyle.getVisibility()));
                default -> {
                    //We do nothing, the style property is not supported
                }
            }
        });
        return appearanceChanges;
    }
}
