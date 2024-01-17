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
package org.eclipse.sirius.components.emf.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.eclipse.emf.common.util.URI;
import org.eclipse.sirius.components.core.api.labels.BorderStyle;
import org.eclipse.sirius.components.core.api.labels.StyledString;
import org.eclipse.sirius.components.core.api.labels.StyledStringFragment;
import org.eclipse.sirius.components.core.api.labels.StyledStringFragmentStyle;
import org.eclipse.sirius.components.core.api.labels.UnderLineStyle;
import org.eclipse.sirius.components.emf.services.api.IStyledStringConverter;
import org.springframework.stereotype.Service;

/**
 * Used to convert EMF styled strings to Sirius Components ones.
 *
 * @author mcharfadi
 */
@Service
public class StyledStringConverter implements IStyledStringConverter {
    @Override
    public StyledString convert(org.eclipse.emf.edit.provider.StyledString styledString) {
        List<StyledStringFragment> styledStringFragments = new ArrayList<>();
        StreamSupport.stream(styledString.spliterator(), false)
                .map(this::convertStyledStringFragment)
                .forEach(styledStringFragments::add);
        return new StyledString(styledStringFragments);
    }

    private StyledStringFragment convertStyledStringFragment(org.eclipse.emf.edit.provider.StyledString.Fragment styledStringFragment) {
        String backgroundColor = convertEmfColorToCss(styledStringFragment.getStyle().getBackgoundColor());
        String foregroundColor = convertEmfColorToCss(styledStringFragment.getStyle().getForegroundColor());
        String strikeoutColor = convertEmfColorToCss(styledStringFragment.getStyle().getStrikeoutColor());
        String underlineColor = convertEmfColorToCss(styledStringFragment.getStyle().getUnderlineColor());
        String borderColor = convertEmfColorToCss(styledStringFragment.getStyle().getBorderColor());
        String font = Optional.ofNullable(styledStringFragment.getStyle().getFont()).map(URI::toString).orElse("Arial");
        boolean strikeout = styledStringFragment.getStyle().isStrikedout();
        UnderLineStyle underLineStyle = convertEmfUnderLineToCss(styledStringFragment.getStyle().getUnderlineStyle());
        BorderStyle borderStyle = convertEmfUnderLineToCss(styledStringFragment.getStyle().getBorderStyle());

        StyledStringFragmentStyle styledStringFragmentStyle = StyledStringFragmentStyle.newStyledStringFragmentStyle()
                .font(font)
                .backgroundColor(backgroundColor)
                .foregroundColor(foregroundColor)
                .strikeoutColor(strikeoutColor)
                .underlineColor(underlineColor)
                .borderColor(borderColor)
                .strikedout(strikeout)
                .underlineStyle(underLineStyle)
                .borderStyle(borderStyle)
                .build();

        return new StyledStringFragment(styledStringFragment.getString(), styledStringFragmentStyle);
    }

    private String convertEmfColorToCss(URI uriEmfColor) {
        if (uriEmfColor != null && uriEmfColor.toString().startsWith("color://rgb")) {
            return uriEmfColor.toString()
                    .replace("color://rgb/", "rgb(")
                    .replace('/', ',')
                    .concat(")");
        }
        return "";
    }

    private UnderLineStyle convertEmfUnderLineToCss(org.eclipse.emf.edit.provider.StyledString.Style.UnderLineStyle emfUnderLineStyle) {
        if (emfUnderLineStyle == null) {
            return UnderLineStyle.NONE;
        }

        return switch (emfUnderLineStyle) {
            case SINGLE -> UnderLineStyle.SOLID;
            case SQUIGGLE -> UnderLineStyle.WAVY;
            case LINK -> UnderLineStyle.DASHED;
            case ERROR -> UnderLineStyle.DOTTED;
            case DOUBLE -> UnderLineStyle.DOUBLE;
            default -> UnderLineStyle.NONE;
        };
    }

    private BorderStyle convertEmfUnderLineToCss(org.eclipse.emf.edit.provider.StyledString.Style.BorderStyle emfBorderStyle) {
        if (emfBorderStyle == null) {
            return BorderStyle.NONE;
        }

        return switch (emfBorderStyle) {
            case SOLID -> BorderStyle.SOLID;
            case DASH -> BorderStyle.DASHED;
            case DOT -> BorderStyle.DOTTED;
            default -> BorderStyle.NONE;
        };
    }
}
