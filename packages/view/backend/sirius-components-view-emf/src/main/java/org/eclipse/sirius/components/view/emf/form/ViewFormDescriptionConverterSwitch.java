/*******************************************************************************
 * Copyright (c) 2022, 2025 Obeo.
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
package org.eclipse.sirius.components.view.emf.form;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.forms.description.AbstractControlDescription;
import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.view.emf.form.converters.BarChartDescriptionConverter;
import org.eclipse.sirius.components.view.emf.form.converters.ButtonDescriptionConverter;
import org.eclipse.sirius.components.view.emf.form.converters.CheckboxDescriptionConverter;
import org.eclipse.sirius.components.view.emf.form.converters.DateTimeDescriptionConverter;
import org.eclipse.sirius.components.view.emf.form.converters.FlexboxContainerDescriptionConverter;
import org.eclipse.sirius.components.view.emf.form.converters.FormElementForDescriptionConverter;
import org.eclipse.sirius.components.view.emf.form.converters.FormElementIfDescriptionConverter;
import org.eclipse.sirius.components.view.emf.form.converters.ImageDescriptionConverter;
import org.eclipse.sirius.components.view.emf.form.converters.LabelDescriptionConverter;
import org.eclipse.sirius.components.view.emf.form.converters.LinkDescriptionConverter;
import org.eclipse.sirius.components.view.emf.form.converters.ListDescriptionConverter;
import org.eclipse.sirius.components.view.emf.form.converters.MultiSelectDescriptionConverter;
import org.eclipse.sirius.components.view.emf.form.converters.PieChartDescriptionConverter;
import org.eclipse.sirius.components.view.emf.form.converters.RadioDescriptionConverter;
import org.eclipse.sirius.components.view.emf.form.converters.RichTextDescriptionConverter;
import org.eclipse.sirius.components.view.emf.form.converters.SelectDescriptionConverter;
import org.eclipse.sirius.components.view.emf.form.converters.SliderDescriptionConverter;
import org.eclipse.sirius.components.view.emf.form.converters.SplitButtonDescriptionConverter;
import org.eclipse.sirius.components.view.emf.form.converters.TextareaDescriptionConverter;
import org.eclipse.sirius.components.view.emf.form.converters.TextfieldDescriptionConverter;
import org.eclipse.sirius.components.view.emf.form.converters.TreeDescriptionConverter;
import org.eclipse.sirius.components.view.form.FormElementFor;
import org.eclipse.sirius.components.view.form.FormElementIf;
import org.eclipse.sirius.components.view.form.RichTextDescription;
import org.eclipse.sirius.components.view.form.TextAreaDescription;
import org.eclipse.sirius.components.view.form.WidgetDescription;
import org.eclipse.sirius.components.view.form.util.FormSwitch;

/**
 * A switch to dispatch View Form Widget Descriptions conversion.
 *
 * @author fbarbin
 */
public class ViewFormDescriptionConverterSwitch extends FormSwitch<Optional<AbstractControlDescription>> {

    public static final String VARIABLE_MANAGER = "variableManager";

    private final AQLInterpreter interpreter;

    private final IEditService editService;

    private final IObjectService objectService;

    private final Switch<Optional<AbstractWidgetDescription>> customWidgetConverters;

    private final IFeedbackMessageService feedbackMessageService;

    private final IFormIdProvider widgetIdProvider;

    public ViewFormDescriptionConverterSwitch(AQLInterpreter interpreter, IEditService editService, IObjectService objectService, Switch<Optional<AbstractWidgetDescription>> customWidgetConverters,
            IFeedbackMessageService feedbackMessageService, IFormIdProvider idProvider) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.editService = Objects.requireNonNull(editService);
        this.objectService = Objects.requireNonNull(objectService);
        this.customWidgetConverters = Objects.requireNonNull(customWidgetConverters);
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
        this.widgetIdProvider = Objects.requireNonNull(idProvider);
    }

    @Override
    public Optional<AbstractControlDescription> caseTextfieldDescription(org.eclipse.sirius.components.view.form.TextfieldDescription viewTextfieldDescription) {
        return Optional.of(new TextfieldDescriptionConverter(this.interpreter, this.objectService, this.editService, this.feedbackMessageService, this.widgetIdProvider).convert(viewTextfieldDescription));
    }

    @Override
    public Optional<AbstractControlDescription> caseCheckboxDescription(org.eclipse.sirius.components.view.form.CheckboxDescription viewCheckboxDescription) {
        return Optional.of(new CheckboxDescriptionConverter(this.interpreter, this.objectService, this.editService, this.feedbackMessageService, this.widgetIdProvider).convert(viewCheckboxDescription));
    }

    @Override
    public Optional<AbstractControlDescription> caseSelectDescription(org.eclipse.sirius.components.view.form.SelectDescription viewSelectDescription) {
        return Optional.of(new SelectDescriptionConverter(this.interpreter, this.objectService, this.editService, this.feedbackMessageService, this.widgetIdProvider).convert(viewSelectDescription));
    }

    @Override
    public Optional<AbstractControlDescription> caseTextAreaDescription(TextAreaDescription viewTextAreaDescription) {
        return Optional.of(new TextareaDescriptionConverter(this.interpreter, this.objectService, this.editService, this.feedbackMessageService, this.widgetIdProvider).convert(viewTextAreaDescription));
    }

    @Override
    public Optional<AbstractControlDescription> caseRichTextDescription(RichTextDescription viewRichTextDescription) {
        return Optional.of(new RichTextDescriptionConverter(this.interpreter, this.objectService, this.editService, this.feedbackMessageService, this.widgetIdProvider).convert(viewRichTextDescription));
    }

    @Override
    public Optional<AbstractControlDescription> caseMultiSelectDescription(org.eclipse.sirius.components.view.form.MultiSelectDescription viewMultiSelectDescription) {
        return Optional.of(new MultiSelectDescriptionConverter(this.interpreter, this.objectService, this.editService, this.feedbackMessageService, this.widgetIdProvider).convert(viewMultiSelectDescription));
    }

    @Override
    public Optional<AbstractControlDescription> caseRadioDescription(org.eclipse.sirius.components.view.form.RadioDescription viewRadioDescription) {
        return Optional.of(new RadioDescriptionConverter(this.interpreter, this.objectService, this.editService, this.feedbackMessageService, this.widgetIdProvider).convert(viewRadioDescription));
    }

    @Override
    public Optional<AbstractControlDescription> caseBarChartDescription(org.eclipse.sirius.components.view.form.BarChartDescription viewBarChartDescription) {
        return Optional.of(new BarChartDescriptionConverter(this.interpreter, this.objectService, this.widgetIdProvider).convert(viewBarChartDescription));
    }

    @Override
    public Optional<AbstractControlDescription> casePieChartDescription(org.eclipse.sirius.components.view.form.PieChartDescription viewPieChartDescription) {
        return Optional.of(new PieChartDescriptionConverter(this.interpreter, this.objectService, this.widgetIdProvider).convert(viewPieChartDescription));
    }

    @Override
    public Optional<AbstractControlDescription> caseFlexboxContainerDescription(org.eclipse.sirius.components.view.form.FlexboxContainerDescription viewFlexboxContainerDescription) {
        return Optional.of(new FlexboxContainerDescriptionConverter(this.interpreter, this.objectService, this.widgetIdProvider, this).convert(viewFlexboxContainerDescription));
    }

    @Override
    public Optional<AbstractControlDescription> caseButtonDescription(org.eclipse.sirius.components.view.form.ButtonDescription viewButtonDescription) {
        return Optional.of(new ButtonDescriptionConverter(this.interpreter, this.objectService, this.editService, this.feedbackMessageService, this.widgetIdProvider).convert(viewButtonDescription));
    }

    @Override
    public Optional<AbstractControlDescription> caseSplitButtonDescription(org.eclipse.sirius.components.view.form.SplitButtonDescription viewSplitButtonDescription) {
        return Optional.of(new SplitButtonDescriptionConverter(this.interpreter, this.objectService, this.widgetIdProvider, this).convert(viewSplitButtonDescription));
    }

    @Override
    public Optional<AbstractControlDescription> caseLabelDescription(org.eclipse.sirius.components.view.form.LabelDescription viewLabelDescription) {
        return Optional.of(new LabelDescriptionConverter(this.interpreter, this.objectService, this.widgetIdProvider).convert(viewLabelDescription));
    }

    @Override
    public Optional<AbstractControlDescription> caseLinkDescription(org.eclipse.sirius.components.view.form.LinkDescription viewLinkDescription) {
        return Optional.of(new LinkDescriptionConverter(this.interpreter, this.objectService, this.widgetIdProvider).convert(viewLinkDescription));
    }

    @Override
    public Optional<AbstractControlDescription> caseListDescription(org.eclipse.sirius.components.view.form.ListDescription viewListDescription) {
        return Optional.of(new ListDescriptionConverter(this.interpreter, this.objectService, this.editService, this.feedbackMessageService, this.widgetIdProvider).convert(viewListDescription));
    }

    @Override
    public Optional<AbstractControlDescription> caseImageDescription(org.eclipse.sirius.components.view.form.ImageDescription viewImageDescription) {
        return Optional.of(new ImageDescriptionConverter(this.interpreter, this.objectService, this.widgetIdProvider).convert(viewImageDescription));
    }

    @Override
    public Optional<AbstractControlDescription> caseFormElementFor(FormElementFor formElementFor) {
        return Optional.of(new FormElementForDescriptionConverter(this.interpreter, this.objectService, this.widgetIdProvider, this).convert(formElementFor));
    }

    @Override
    public Optional<AbstractControlDescription> caseFormElementIf(FormElementIf formElementIf) {
        return Optional.of(new FormElementIfDescriptionConverter(this.interpreter, this.objectService, this.widgetIdProvider, this).convert(formElementIf));
    }

    @Override
    public Optional<AbstractControlDescription> caseTreeDescription(org.eclipse.sirius.components.view.form.TreeDescription viewTreeDescription) {
        return Optional.of(new TreeDescriptionConverter(this.interpreter, this.objectService, this.editService, this.feedbackMessageService, this.widgetIdProvider).convert(viewTreeDescription));
    }

    @Override
    public Optional<AbstractControlDescription> caseWidgetDescription(WidgetDescription widgetDescription) {
        return this.customWidgetConverters.doSwitch(widgetDescription).map(AbstractControlDescription.class::cast);
    }

    @Override
    public Optional<AbstractControlDescription> caseDateTimeDescription(org.eclipse.sirius.components.view.form.DateTimeDescription viewDateTimeDescription) {
        return Optional.of(new DateTimeDescriptionConverter(this.interpreter, this.objectService, this.editService, this.feedbackMessageService, this.widgetIdProvider).convert(viewDateTimeDescription));
    }

    @Override
    public Optional<AbstractControlDescription> caseSliderDescription(org.eclipse.sirius.components.view.form.SliderDescription viewSliderDescription) {
        return Optional.of(new SliderDescriptionConverter(this.interpreter, this.objectService, this.editService, this.feedbackMessageService, this.widgetIdProvider).convert(viewSliderDescription));
    }
}
