/*******************************************************************************
 * Copyright (c) 2021, 2025 Obeo.
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
package org.eclipse.sirius.components.view.emf;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DialogDescription;
import org.eclipse.sirius.components.view.emf.api.IDialogDescriptionConverter;
import org.eclipse.sirius.components.view.emf.api.IViewInterpreterProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * Converts a View into an equivalent list of {@link RepresentationDescription}.
 *
 * @author pcdavid
 */
@Service
public class ViewConverter implements IViewConverter {

    private final Logger logger = LoggerFactory.getLogger(ViewConverter.class);

    private final IViewInterpreterProvider viewInterpreterProvider;

    private final List<IRepresentationDescriptionConverter> representationDescriptionConverters;

    private final List<IDialogDescriptionConverter> dialogDescriptionConverts;

    public ViewConverter(IViewInterpreterProvider viewInterpreterProvider, List<IRepresentationDescriptionConverter> representationDescriptionConverters, ApplicationContext applicationContext, List<IDialogDescriptionConverter> dialogDescriptionConverts) {
        this.viewInterpreterProvider = Objects.requireNonNull(viewInterpreterProvider);
        this.representationDescriptionConverters = Objects.requireNonNull(representationDescriptionConverters);
        this.dialogDescriptionConverts = Objects.requireNonNull(dialogDescriptionConverts);
    }

    /**
     * Extract and convert the {@link IRepresentationDescription} from a list of {@link View} models by delegating to provided
     * {@link IRepresentationDescriptionConverter}.
     */
    @Override
    public List<ViewConverterResult> convert(IEMFEditingContext editingContext, List<View> views) {
        List<ViewConverterResult> viewConverterResult = new ArrayList<>();
        List<RepresentationDescription> allViewsRepresentationDescriptions = views.stream().flatMap(v -> v.getDescriptions().stream()).toList();
        views.forEach(view -> {

            AQLInterpreter interpreter = this.viewInterpreterProvider.createInterpreter(editingContext, view);
            try {
                viewConverterResult.addAll(view.getDescriptions().stream()
                        .map(representationDescription -> this.convert(representationDescription, allViewsRepresentationDescriptions, interpreter))
                        .flatMap(Optional::stream)
                        .toList());

                viewConverterResult.addAll(this.convertDialogDescriptions(view, interpreter));

            } catch (NullPointerException exception) {
                // Can easily happen if the View model is currently invalid/inconsistent, typically because it is
                // currently being created or edited.
                this.logger.debug("Exception while converting view", exception);
            }
        });

        return viewConverterResult;
    }

    private List<ViewConverterResult> convertDialogDescriptions(View view, AQLInterpreter interpreter) {
        List<ViewConverterResult> viewConverterResult = new ArrayList<>();
        view.getDescriptions().stream()
                .filter(DiagramDescription.class::isInstance)
                .flatMap(this::getAllContent)
                .filter(org.eclipse.sirius.components.view.diagram.DialogDescription.class::isInstance)
                .map(org.eclipse.sirius.components.view.diagram.DialogDescription.class::cast)
                .forEach(dialogDescription -> this.convertDialogDescription(dialogDescription, interpreter, viewConverterResult));
        return viewConverterResult;
    }

    private void convertDialogDescription(DialogDescription dialogDescription, AQLInterpreter interpreter, List<ViewConverterResult> viewConverterResult) {
        this.dialogDescriptionConverts.stream()
                .filter(converter -> converter.canConvert(dialogDescription))
                .findFirst()
                .map(converter -> converter.convert(dialogDescription, interpreter))
                .stream()
                .flatMap(List::stream)
                .forEach(representationDescription -> viewConverterResult.add(new ViewConverterResult(representationDescription, Optional.empty())));
    }

    private Stream<EObject> getAllContent(EObject representationDescription) {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(representationDescription.eAllContents(), Spliterator.ORDERED), false);
    }

    private Optional<ViewConverterResult> convert(RepresentationDescription representationDescription, List<RepresentationDescription> allViewsRepresentationDescriptions, AQLInterpreter aqlInterpreter) {
        return this.representationDescriptionConverters.stream()
                .filter(converter -> converter.canConvert(representationDescription))
                .map(converter -> converter.convert(representationDescription, allViewsRepresentationDescriptions, aqlInterpreter))
                .findFirst();
    }

}
