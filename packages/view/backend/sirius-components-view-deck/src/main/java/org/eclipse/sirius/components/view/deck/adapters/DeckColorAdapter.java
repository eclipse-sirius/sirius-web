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
package org.eclipse.sirius.components.view.deck.adapters;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.deck.CardDescription;
import org.eclipse.sirius.components.view.deck.ConditionalDeckDescriptionStyle;
import org.eclipse.sirius.components.view.deck.ConditionalDeckElementDescriptionStyle;
import org.eclipse.sirius.components.view.deck.DeckDescription;
import org.eclipse.sirius.components.view.deck.DeckDescriptionStyle;
import org.eclipse.sirius.components.view.deck.DeckElementDescriptionStyle;
import org.eclipse.sirius.components.view.deck.DeckPackage;
import org.eclipse.sirius.components.view.deck.LaneDescription;
import org.eclipse.sirius.components.view.util.services.ColorPaletteService;

/**
 * Adapter allowing to set default colors after the creation of deck, lane or card style elements.
 *
 * @author fbarbin
 */
public class DeckColorAdapter extends EContentAdapter {

    private ColorPaletteService colorPaletteService;

    public DeckColorAdapter(View colorPalettesView) {
        this.colorPaletteService = new ColorPaletteService(colorPalettesView);
    }

    @Override
    public void notifyChanged(Notification notification) {
        super.notifyChanged(notification);
        if (Notification.SET == notification.getEventType() && notification.getNotifier() instanceof DeckDescription deckDescription && notification.getNewValue() instanceof DeckDescriptionStyle deckDescriptionStyle
                && DeckPackage.DECK_DESCRIPTION__STYLE == notification.getFeatureID(DeckDescriptionStyle.class)) {
            this.setDeckColors(deckDescriptionStyle, deckDescription);
        } else  if (Notification.ADD == notification.getEventType() && notification.getNotifier() instanceof DeckDescription deckDescription && notification.getNewValue() instanceof ConditionalDeckDescriptionStyle condtionalDeckDescriptionStyle
                && DeckPackage.DECK_DESCRIPTION__CONDITIONAL_STYLES == notification.getFeatureID(ConditionalDeckDescriptionStyle.class)) {
            this.setDeckColors(condtionalDeckDescriptionStyle, deckDescription);
        }
        if (Notification.SET == notification.getEventType() && notification.getNotifier() instanceof LaneDescription laneDescription && notification.getNewValue() instanceof DeckElementDescriptionStyle deckElementDescriptionStyle
                && DeckPackage.LANE_DESCRIPTION__STYLE == notification.getFeatureID(DeckElementDescriptionStyle.class)) {
            this.setLaneColors(deckElementDescriptionStyle, laneDescription);
        } else  if (Notification.ADD == notification.getEventType() && notification.getNotifier() instanceof LaneDescription laneDescription && notification.getNewValue() instanceof ConditionalDeckElementDescriptionStyle condtionalDeckElementDescriptionStyle
                && DeckPackage.LANE_DESCRIPTION__CONDITIONAL_STYLES == notification.getFeatureID(ConditionalDeckDescriptionStyle.class)) {
            this.setLaneColors(condtionalDeckElementDescriptionStyle, laneDescription);
        }
        if (Notification.SET == notification.getEventType() && notification.getNotifier() instanceof CardDescription cardDescription && notification.getNewValue() instanceof DeckElementDescriptionStyle deckElementDescriptionStyle
                && DeckPackage.LANE_DESCRIPTION__STYLE == notification.getFeatureID(DeckElementDescriptionStyle.class)) {
            this.setCardColors(deckElementDescriptionStyle, cardDescription);
        } else  if (Notification.ADD == notification.getEventType() && notification.getNotifier() instanceof CardDescription cardDescription && notification.getNewValue() instanceof ConditionalDeckElementDescriptionStyle condtionalDeckElementDescriptionStyle
                && DeckPackage.LANE_DESCRIPTION__CONDITIONAL_STYLES == notification.getFeatureID(ConditionalDeckDescriptionStyle.class)) {
            this.setCardColors(condtionalDeckElementDescriptionStyle, cardDescription);
        }
    }

    private void setDeckColors(DeckDescriptionStyle style, Object object) {
        if (style != null && style.getBackgroundColor() == null) {
            style.setBackgroundColor(this.colorPaletteService.getColorFromPalette(object, "white"));
        }
    }

    private void setLaneColors(DeckElementDescriptionStyle style, Object object) {
        if (style != null && style.getBackgroundColor() == null) {
            style.setBackgroundColor(this.colorPaletteService.getColorFromPalette(object, "grey 200"));
        }
        if (style != null && style.getColor() == null) {
            style.setColor(this.colorPaletteService.getColorFromPalette(object, "theme.palette.text.primary"));
        }
    }

    private void setCardColors(DeckElementDescriptionStyle style, Object object) {
        if (style != null && style.getBackgroundColor() == null) {
            style.setBackgroundColor(this.colorPaletteService.getColorFromPalette(object, "white"));
        }
        if (style != null && style.getColor() == null) {
            style.setColor(this.colorPaletteService.getColorFromPalette(object, "theme.palette.text.primary"));
        }
    }
}
