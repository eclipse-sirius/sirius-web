/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.view.form.adapters;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.util.EContentAdapter;
import org.eclipse.sirius.components.view.form.ButtonDescription;
import org.eclipse.sirius.components.view.form.ButtonDescriptionStyle;
import org.eclipse.sirius.components.view.form.CheckboxDescription;
import org.eclipse.sirius.components.view.form.CheckboxDescriptionStyle;
import org.eclipse.sirius.components.view.form.FormElementFor;
import org.eclipse.sirius.components.view.form.FormElementIf;
import org.eclipse.sirius.components.view.form.FormPackage;
import org.eclipse.sirius.components.view.form.GroupDescription;
import org.eclipse.sirius.components.view.form.LabelDescription;
import org.eclipse.sirius.components.view.form.LabelDescriptionStyle;
import org.eclipse.sirius.components.view.form.LinkDescription;
import org.eclipse.sirius.components.view.form.LinkDescriptionStyle;
import org.eclipse.sirius.components.view.form.ListDescription;
import org.eclipse.sirius.components.view.form.ListDescriptionStyle;
import org.eclipse.sirius.components.view.form.MultiSelectDescription;
import org.eclipse.sirius.components.view.form.MultiSelectDescriptionStyle;
import org.eclipse.sirius.components.view.form.RadioDescription;
import org.eclipse.sirius.components.view.form.RadioDescriptionStyle;
import org.eclipse.sirius.components.view.form.SelectDescription;
import org.eclipse.sirius.components.view.form.SelectDescriptionStyle;
import org.eclipse.sirius.components.view.form.TextAreaDescription;
import org.eclipse.sirius.components.view.form.TextareaDescriptionStyle;
import org.eclipse.sirius.components.view.form.TextfieldDescription;
import org.eclipse.sirius.components.view.form.TextfieldDescriptionStyle;
import org.eclipse.sirius.components.view.form.WidgetDescription;
import org.eclipse.sirius.components.view.form.WidgetDescriptionStyle;
import org.eclipse.sirius.components.view.form.util.FormSwitch;
import org.eclipse.sirius.components.view.util.services.ColorPaletteService;

/**
 * Adapter allowing to set default colors after the creation of widget style elements.
 *
 * @author arichard
 */
public class FormColorAdapter extends EContentAdapter {


    private FormStyleSwitch formStyleSwitch;

    public FormColorAdapter() {
        this.formStyleSwitch = new FormStyleSwitch();
    }

    @Override
    public void notifyChanged(Notification notification) {
        super.notifyChanged(notification);
        if (Notification.ADD == notification.getEventType() && notification.getNotifier() instanceof GroupDescription && notification.getNewValue() instanceof WidgetDescription widget
                && FormPackage.GROUP_DESCRIPTION__CHILDREN == notification.getFeatureID(GroupDescription.class)) {
            WidgetDescriptionStyle style = this.getStyle(widget);
            if (style != null) {
                this.formStyleSwitch.doSwitch(style);
            }
        } else if (Notification.ADD == notification.getEventType() && notification.getNotifier() instanceof FormElementFor && notification.getNewValue() instanceof WidgetDescription widget
                && FormPackage.FORM_ELEMENT_FOR__CHILDREN == notification.getFeatureID(FormElementFor.class)) {
            WidgetDescriptionStyle style = this.getStyle(widget);
            if (style != null) {
                this.formStyleSwitch.doSwitch(style);
            }
        } else if (Notification.ADD == notification.getEventType() && notification.getNotifier() instanceof FormElementIf && notification.getNewValue() instanceof WidgetDescription widget
                && FormPackage.FORM_ELEMENT_IF__CHILDREN == notification.getFeatureID(FormElementIf.class)) {
            WidgetDescriptionStyle style = this.getStyle(widget);
            if (style != null) {
                this.formStyleSwitch.doSwitch(style);
            }
        } else if (Notification.SET == notification.getEventType() && notification.getNotifier() instanceof WidgetDescription && notification.getNewValue() instanceof WidgetDescriptionStyle style) {
            this.formStyleSwitch.doSwitch(style);
        } else if (Notification.ADD == notification.getEventType() && notification.getNotifier() instanceof WidgetDescription && notification.getNewValue() instanceof WidgetDescriptionStyle style) {
            this.formStyleSwitch.doSwitch(style);
        }
    }

    private WidgetDescriptionStyle getStyle(WidgetDescription widget) {
        WidgetDescriptionStyle style = null;
        if (widget instanceof ButtonDescription styledWidget) {
            style = styledWidget.getStyle();
        } else if (widget instanceof CheckboxDescription styledWidget) {
            style = styledWidget.getStyle();
        } else if (widget instanceof LabelDescription styledWidget) {
            style = styledWidget.getStyle();
        } else if (widget instanceof LinkDescription styledWidget) {
            style = styledWidget.getStyle();
        } else if (widget instanceof ListDescription styledWidget) {
            style = styledWidget.getStyle();
        } else if (widget instanceof MultiSelectDescription styledWidget) {
            style = styledWidget.getStyle();
        } else if (widget instanceof RadioDescription styledWidget) {
            style = styledWidget.getStyle();
        } else if (widget instanceof SelectDescription styledWidget) {
            style = styledWidget.getStyle();
        } else if (widget instanceof TextAreaDescription styledWidget) {
            style = styledWidget.getStyle();
        } else if (widget instanceof TextfieldDescription styledWidget) {
            style = styledWidget.getStyle();
        }
        return style;
    }

    private class FormStyleSwitch extends FormSwitch<Void> {

        private ColorPaletteService colorPaletteService;

        public FormStyleSwitch() {
            this.colorPaletteService = new ColorPaletteService();
        }

        @Override
        public Void caseButtonDescriptionStyle(ButtonDescriptionStyle object) {
            if (object.getBackgroundColor() == null) {
                object.setBackgroundColor(this.colorPaletteService.getColorFromPalette(object, "theme.palette.primary.light"));
            }
            if (object.getForegroundColor() == null) {
                object.setForegroundColor(this.colorPaletteService.getColorFromPalette(object, "white"));
            }
            return null;
        }

        @Override
        public Void caseCheckboxDescriptionStyle(CheckboxDescriptionStyle object) {
            if (object.getColor() == null) {
                object.setColor(this.colorPaletteService.getColorFromPalette(object, "theme.palette.primary.main"));
            }
            return null;
        }

        @Override
        public Void caseLabelDescriptionStyle(LabelDescriptionStyle object) {
            if (object.getColor() == null) {
                object.setColor(this.colorPaletteService.getColorFromPalette(object, "theme.palette.text.primary"));
            }
            return null;
        }

        @Override
        public Void caseLinkDescriptionStyle(LinkDescriptionStyle object) {
            if (object.getColor() == null) {
                object.setColor(this.colorPaletteService.getColorFromPalette(object, "theme.palette.text.primary"));
            }
            return null;
        }

        @Override
        public Void caseListDescriptionStyle(ListDescriptionStyle object) {
            if (object.getColor() == null) {
                object.setColor(this.colorPaletteService.getColorFromPalette(object, "theme.palette.text.primary"));
            }
            return null;
        }

        @Override
        public Void caseMultiSelectDescriptionStyle(MultiSelectDescriptionStyle object) {
            if (object.getBackgroundColor() == null) {
                object.setBackgroundColor(this.colorPaletteService.getColorFromPalette(object, "transparent"));
            }
            if (object.getForegroundColor() == null) {
                object.setForegroundColor(this.colorPaletteService.getColorFromPalette(object, "theme.palette.text.primary"));
            }
            return null;
        }

        @Override
        public Void caseRadioDescriptionStyle(RadioDescriptionStyle object) {
            if (object.getColor() == null) {
                object.setColor(this.colorPaletteService.getColorFromPalette(object, "theme.palette.primary.main"));
            }
            return null;
        }

        @Override
        public Void caseSelectDescriptionStyle(SelectDescriptionStyle object) {
            if (object.getBackgroundColor() == null) {
                object.setBackgroundColor(this.colorPaletteService.getColorFromPalette(object, "transparent"));
            }
            if (object.getForegroundColor() == null) {
                object.setForegroundColor(this.colorPaletteService.getColorFromPalette(object, "theme.palette.text.primary"));
            }
            return null;
        }

        @Override
        public Void caseTextareaDescriptionStyle(TextareaDescriptionStyle object) {
            if (object.getBackgroundColor() == null) {
                object.setBackgroundColor(this.colorPaletteService.getColorFromPalette(object, "transparent"));
            }
            if (object.getForegroundColor() == null) {
                object.setForegroundColor(this.colorPaletteService.getColorFromPalette(object, "theme.palette.text.primary"));
            }
            return null;
        }

        @Override
        public Void caseTextfieldDescriptionStyle(TextfieldDescriptionStyle object) {
            if (object.getBackgroundColor() == null) {
                object.setBackgroundColor(this.colorPaletteService.getColorFromPalette(object, "transparent"));
            }
            if (object.getForegroundColor() == null) {
                object.setForegroundColor(this.colorPaletteService.getColorFromPalette(object, "theme.palette.text.primary"));
            }
            return null;
        }
    }
}
