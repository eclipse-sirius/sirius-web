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
import org.eclipse.sirius.components.diagrams.LineStyle;
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
import org.eclipse.sirius.components.diagrams.events.appearance.label.ResetLabelAppearanceChange;

/**
 * Service used to handle the customization of a label's appearance.
 *
 * @author nvannier
 */
public class LabelAppearanceHandler {

    public static final String BOLD = "BOLD";
    public static final String ITALIC = "ITALIC";
    public static final String UNDERLINE = "UNDERLINE";
    public static final String STRIKE_THROUGH = "STRIKE_THROUGH";
    public static final String COLOR = "COLOR";
    public static final String FONT_SIZE = "FONT_SIZE";
    public static final String BACKGROUND = "BACKGROUND";
    public static final String BORDER_COLOR = "BORDER_COLOR";
    public static final String BORDER_SIZE = "BORDER_SIZE";
    public static final String BORDER_RADIUS = "BORDER_RADIUS";
    public static final String BORDER_STYLE = "BORDER_STYLE";

    private final Set<String> previousCustomizedStyleProperties;

    private final Optional<LabelStyle> optionalPreviousLabelStyle;

    private final List<ILabelAppearanceChange> appearanceChanges;

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
        this.optionalPreviousLabelStyle = Optional.ofNullable(previousLabelStyle);
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

    public LabelAppearanceProperty<Boolean> isItalic(Supplier<Boolean> provider) {
        boolean italicReset = this.appearanceChanges.stream()
                .filter(ResetLabelAppearanceChange.class::isInstance)
                .map(ResetLabelAppearanceChange.class::cast)
                .anyMatch(reset -> Objects.equals(reset.propertyName(), ITALIC));

        if (italicReset) {
            return new LabelAppearanceProperty<>(provider.get(), false);
        } else {
            Optional<LabelItalicAppearanceChange> optionalItalicChange = this.appearanceChanges.stream()
                    .filter(LabelItalicAppearanceChange.class::isInstance)
                    .map(LabelItalicAppearanceChange.class::cast)
                    .findFirst();

            LabelAppearanceProperty<Boolean> result;
            if (optionalItalicChange.isPresent()) {
                result = new LabelAppearanceProperty<>(optionalItalicChange.get().italic(), true);
            } else if (this.previousCustomizedStyleProperties.contains(ITALIC) && this.optionalPreviousLabelStyle.isPresent()) {
                LabelStyle previousLabelStyle = this.optionalPreviousLabelStyle.get();
                result = new LabelAppearanceProperty<>(previousLabelStyle.isItalic(), true);
            } else {
                result = new LabelAppearanceProperty<>(provider.get(), false);
            }
            return result;
        }
    }

    public LabelAppearanceProperty<Boolean> isUnderline(Supplier<Boolean> provider) {
        boolean underlineReset = this.appearanceChanges.stream()
                .filter(ResetLabelAppearanceChange.class::isInstance)
                .map(ResetLabelAppearanceChange.class::cast)
                .anyMatch(reset -> Objects.equals(reset.propertyName(), UNDERLINE));

        if (underlineReset) {
            return new LabelAppearanceProperty<>(provider.get(), false);
        } else {
            Optional<LabelUnderlineAppearanceChange> optionalUnderlineChange = this.appearanceChanges.stream()
                    .filter(LabelUnderlineAppearanceChange.class::isInstance)
                    .map(LabelUnderlineAppearanceChange.class::cast)
                    .findFirst();

            LabelAppearanceProperty<Boolean> result;
            if (optionalUnderlineChange.isPresent()) {
                result = new LabelAppearanceProperty<>(optionalUnderlineChange.get().underline(), true);
            } else if (this.previousCustomizedStyleProperties.contains(UNDERLINE) && this.optionalPreviousLabelStyle.isPresent()) {
                LabelStyle previousLabelStyle = this.optionalPreviousLabelStyle.get();
                result = new LabelAppearanceProperty<>(previousLabelStyle.isUnderline(), true);
            } else {
                result = new LabelAppearanceProperty<>(provider.get(), false);
            }
            return result;
        }
    }

    public LabelAppearanceProperty<Boolean> isStrikeThrough(Supplier<Boolean> provider) {
        boolean strikeThroughReset = this.appearanceChanges.stream()
                .filter(ResetLabelAppearanceChange.class::isInstance)
                .map(ResetLabelAppearanceChange.class::cast)
                .anyMatch(reset -> Objects.equals(reset.propertyName(), STRIKE_THROUGH));

        if (strikeThroughReset) {
            return new LabelAppearanceProperty<>(provider.get(), false);
        } else {
            Optional<LabelStrikeThroughAppearanceChange> optionalStrikeThroughChange = this.appearanceChanges.stream()
                    .filter(LabelStrikeThroughAppearanceChange.class::isInstance)
                    .map(LabelStrikeThroughAppearanceChange.class::cast)
                    .findFirst();

            LabelAppearanceProperty<Boolean> result;
            if (optionalStrikeThroughChange.isPresent()) {
                result = new LabelAppearanceProperty<>(optionalStrikeThroughChange.get().strikeThrough(), true);
            } else if (this.previousCustomizedStyleProperties.contains(STRIKE_THROUGH) && this.optionalPreviousLabelStyle.isPresent()) {
                LabelStyle previousLabelStyle = this.optionalPreviousLabelStyle.get();
                result = new LabelAppearanceProperty<>(previousLabelStyle.isStrikeThrough(), true);
            } else {
                result = new LabelAppearanceProperty<>(provider.get(), false);
            }
            return result;
        }
    }

    public LabelAppearanceProperty<String> getColor(Supplier<String> provider) {
        boolean colorReset = this.appearanceChanges.stream()
                .filter(ResetLabelAppearanceChange.class::isInstance)
                .map(ResetLabelAppearanceChange.class::cast)
                .anyMatch(reset -> Objects.equals(reset.propertyName(), COLOR));

        if (colorReset) {
            return new LabelAppearanceProperty<>(provider.get(), false);
        } else {
            Optional<LabelColorAppearanceChange> optionalColorChange = this.appearanceChanges.stream()
                    .filter(LabelColorAppearanceChange.class::isInstance)
                    .map(LabelColorAppearanceChange.class::cast)
                    .findFirst();

            LabelAppearanceProperty<String> result;
            if (optionalColorChange.isPresent()) {
                result = new LabelAppearanceProperty<>(optionalColorChange.get().color(), true);
            } else if (this.previousCustomizedStyleProperties.contains(COLOR) && this.optionalPreviousLabelStyle.isPresent()) {
                LabelStyle previousLabelStyle = this.optionalPreviousLabelStyle.get();
                result = new LabelAppearanceProperty<>(previousLabelStyle.getColor(), true);
            } else {
                result = new LabelAppearanceProperty<>(provider.get(), false);
            }
            return result;
        }
    }

    public LabelAppearanceProperty<Integer> getFontSize(Supplier<Integer> provider) {
        boolean fontSizeReset = this.appearanceChanges.stream()
                .filter(ResetLabelAppearanceChange.class::isInstance)
                .map(ResetLabelAppearanceChange.class::cast)
                .anyMatch(reset -> Objects.equals(reset.propertyName(), FONT_SIZE));

        if (fontSizeReset) {
            return new LabelAppearanceProperty<>(provider.get(), false);
        } else {
            Optional<LabelFontSizeAppearanceChange> optionalFontSizeChange = this.appearanceChanges.stream()
                    .filter(LabelFontSizeAppearanceChange.class::isInstance)
                    .map(LabelFontSizeAppearanceChange.class::cast)
                    .findFirst();

            LabelAppearanceProperty<Integer> result;
            if (optionalFontSizeChange.isPresent()) {
                result = new LabelAppearanceProperty<>(optionalFontSizeChange.get().fontSize(), true);
            } else if (this.previousCustomizedStyleProperties.contains(FONT_SIZE) && this.optionalPreviousLabelStyle.isPresent()) {
                LabelStyle previousLabelStyle = this.optionalPreviousLabelStyle.get();
                result = new LabelAppearanceProperty<>(previousLabelStyle.getFontSize(), true);
            } else {
                result = new LabelAppearanceProperty<>(provider.get(), false);
            }
            return result;
        }
    }

    public LabelAppearanceProperty<String> getBackground(Supplier<String> provider) {
        boolean backgroundReset = this.appearanceChanges.stream()
                .filter(ResetLabelAppearanceChange.class::isInstance)
                .map(ResetLabelAppearanceChange.class::cast)
                .anyMatch(reset -> Objects.equals(reset.propertyName(), BACKGROUND));

        if (backgroundReset) {
            return new LabelAppearanceProperty<>(provider.get(), false);
        } else {
            Optional<LabelBackgroundAppearanceChange> optionalBackgroundChange = this.appearanceChanges.stream()
                    .filter(LabelBackgroundAppearanceChange.class::isInstance)
                    .map(LabelBackgroundAppearanceChange.class::cast)
                    .findFirst();

            LabelAppearanceProperty<String> result;
            if (optionalBackgroundChange.isPresent()) {
                result = new LabelAppearanceProperty<>(optionalBackgroundChange.get().background(), true);
            } else if (this.previousCustomizedStyleProperties.contains(BACKGROUND) && this.optionalPreviousLabelStyle.isPresent()) {
                LabelStyle previousLabelStyle = this.optionalPreviousLabelStyle.get();
                result = new LabelAppearanceProperty<>(previousLabelStyle.getBackground(), true);
            } else {
                result = new LabelAppearanceProperty<>(provider.get(), false);
            }
            return result;
        }
    }

    public LabelAppearanceProperty<String> getBorderColor(Supplier<String> provider) {
        boolean borderColorReset = this.appearanceChanges.stream()
                .filter(ResetLabelAppearanceChange.class::isInstance)
                .map(ResetLabelAppearanceChange.class::cast)
                .anyMatch(reset -> Objects.equals(reset.propertyName(), BORDER_COLOR));

        if (borderColorReset) {
            return new LabelAppearanceProperty<>(provider.get(), false);
        } else {
            Optional<LabelBorderColorAppearanceChange> optionalBorderColorChange = this.appearanceChanges.stream()
                    .filter(LabelBorderColorAppearanceChange.class::isInstance)
                    .map(LabelBorderColorAppearanceChange.class::cast)
                    .findFirst();

            LabelAppearanceProperty<String> result;
            if (optionalBorderColorChange.isPresent()) {
                result = new LabelAppearanceProperty<>(optionalBorderColorChange.get().borderColor(), true);
            } else if (this.previousCustomizedStyleProperties.contains(BORDER_COLOR) && this.optionalPreviousLabelStyle.isPresent()) {
                LabelStyle previousLabelStyle = this.optionalPreviousLabelStyle.get();
                result = new LabelAppearanceProperty<>(previousLabelStyle.getBorderColor(), true);
            } else {
                result = new LabelAppearanceProperty<>(provider.get(), false);
            }
            return result;
        }
    }

    public LabelAppearanceProperty<Integer> getBorderSize(Supplier<Integer> provider) {
        boolean borderSizeReset = this.appearanceChanges.stream()
                .filter(ResetLabelAppearanceChange.class::isInstance)
                .map(ResetLabelAppearanceChange.class::cast)
                .anyMatch(reset -> Objects.equals(reset.propertyName(), BORDER_SIZE));

        if (borderSizeReset) {
            return new LabelAppearanceProperty<>(provider.get(), false);
        } else {
            Optional<LabelBorderSizeAppearanceChange> optionalBorderSizeChange = this.appearanceChanges.stream()
                    .filter(LabelBorderSizeAppearanceChange.class::isInstance)
                    .map(LabelBorderSizeAppearanceChange.class::cast)
                    .findFirst();

            LabelAppearanceProperty<Integer> result;
            if (optionalBorderSizeChange.isPresent()) {
                result = new LabelAppearanceProperty<>(optionalBorderSizeChange.get().borderSize(), true);
            } else if (this.previousCustomizedStyleProperties.contains(BORDER_SIZE) && this.optionalPreviousLabelStyle.isPresent()) {
                LabelStyle previousLabelStyle = this.optionalPreviousLabelStyle.get();
                result = new LabelAppearanceProperty<>(previousLabelStyle.getBorderSize(), true);
            } else {
                result = new LabelAppearanceProperty<>(provider.get(), false);
            }
            return result;
        }
    }

    public LabelAppearanceProperty<Integer> getBorderRadius(Supplier<Integer> provider) {
        boolean borderRadiusReset = this.appearanceChanges.stream()
                .filter(ResetLabelAppearanceChange.class::isInstance)
                .map(ResetLabelAppearanceChange.class::cast)
                .anyMatch(reset -> Objects.equals(reset.propertyName(), BORDER_RADIUS));

        if (borderRadiusReset) {
            return new LabelAppearanceProperty<>(provider.get(), false);
        } else {
            Optional<LabelBorderRadiusAppearanceChange> optionalBorderRadiusChange = this.appearanceChanges.stream()
                    .filter(LabelBorderRadiusAppearanceChange.class::isInstance)
                    .map(LabelBorderRadiusAppearanceChange.class::cast)
                    .findFirst();

            LabelAppearanceProperty<Integer> result;
            if (optionalBorderRadiusChange.isPresent()) {
                result = new LabelAppearanceProperty<>(optionalBorderRadiusChange.get().borderRadius(), true);
            } else if (this.previousCustomizedStyleProperties.contains(BORDER_RADIUS) && this.optionalPreviousLabelStyle.isPresent()) {
                LabelStyle previousLabelStyle = this.optionalPreviousLabelStyle.get();
                result = new LabelAppearanceProperty<>(previousLabelStyle.getBorderRadius(), true);
            } else {
                result = new LabelAppearanceProperty<>(provider.get(), false);
            }
            return result;
        }
    }

    public LabelAppearanceProperty<LineStyle> getBorderStyle(Supplier<LineStyle> provider) {
        boolean borderStyleReset = this.appearanceChanges.stream()
                .filter(ResetLabelAppearanceChange.class::isInstance)
                .map(ResetLabelAppearanceChange.class::cast)
                .anyMatch(reset -> Objects.equals(reset.propertyName(), BORDER_STYLE));

        if (borderStyleReset) {
            return new LabelAppearanceProperty<>(provider.get(), false);
        } else {
            Optional<LabelBorderStyleAppearanceChange> optionalBorderStyleChange = this.appearanceChanges.stream()
                    .filter(LabelBorderStyleAppearanceChange.class::isInstance)
                    .map(LabelBorderStyleAppearanceChange.class::cast)
                    .findFirst();

            LabelAppearanceProperty<LineStyle> result;
            if (optionalBorderStyleChange.isPresent()) {
                result = new LabelAppearanceProperty<>(optionalBorderStyleChange.get().borderStyle(), true);
            } else if (this.previousCustomizedStyleProperties.contains(BORDER_STYLE) && this.optionalPreviousLabelStyle.isPresent()) {
                LabelStyle previousLabelStyle = this.optionalPreviousLabelStyle.get();
                result = new LabelAppearanceProperty<>(previousLabelStyle.getBorderStyle(), true);
            } else {
                result = new LabelAppearanceProperty<>(provider.get(), false);
            }
            return result;
        }
    }
}
