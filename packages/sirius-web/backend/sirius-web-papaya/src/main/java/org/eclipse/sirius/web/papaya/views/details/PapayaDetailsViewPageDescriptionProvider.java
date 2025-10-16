/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
package org.eclipse.sirius.web.papaya.views.details;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.forms.services.api.IPropertiesDescriptionRegistry;
import org.eclipse.sirius.components.collaborative.forms.services.api.IPropertiesDescriptionRegistryConfigurer;
import org.eclipse.sirius.components.forms.description.FormDescription;
import org.eclipse.sirius.components.view.ColorPalette;
import org.eclipse.sirius.components.view.builder.generated.form.FormBuilders;
import org.eclipse.sirius.components.view.builder.generated.view.ViewBuilders;
import org.eclipse.sirius.components.view.builder.providers.DefaultColorProvider;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.emf.ViewConverterResult;
import org.eclipse.sirius.web.papaya.views.details.api.IFormDescriptionConverter;
import org.eclipse.sirius.web.papaya.views.details.api.IPageDescriptionProvider;
import org.springframework.stereotype.Service;

/**
 * Used to provide support for a custom details view for Papaya objects.
 *
 * @author sbegaudeau
 */
@Service
public class PapayaDetailsViewPageDescriptionProvider implements IPropertiesDescriptionRegistryConfigurer {

    public static final String NAME_COLOR = "Name - blue100";

    public static final String QUALIFIED_NAME_COLOR = "Qualified Name - blue900";

    public static final String WHITE_COLOR = "White";

    private final IFormDescriptionConverter formDescriptionConverter;

    private final List<IPageDescriptionProvider> pageDescriptionProviders;


    public PapayaDetailsViewPageDescriptionProvider(IFormDescriptionConverter formDescriptionConverter, List<IPageDescriptionProvider> pageDescriptionProviders) {
        this.formDescriptionConverter = Objects.requireNonNull(formDescriptionConverter);
        this.pageDescriptionProviders = Objects.requireNonNull(pageDescriptionProviders);
    }

    @Override
    public void addPropertiesDescriptions(IPropertiesDescriptionRegistry registry) {
        var view = new ViewBuilders().newView()
                .colorPalettes(this.getColorPalette())
                .build();

        var viewFormDescription = new FormBuilders().newFormDescription()
                .name("Papaya Details View")
                .domainType("papaya:NamedElement")
                .titleExpression("")
                .build();

        view.getDescriptions().add(viewFormDescription);

        IColorProvider colorProvider = new DefaultColorProvider(view);

        var pages = this.pageDescriptionProviders.stream()
                .map(pageDescriptionProvider -> pageDescriptionProvider.getPageDescription(colorProvider))
                .toList();
        viewFormDescription.getPages().addAll(pages);

        this.formDescriptionConverter.convert(view).stream()
                .map(ViewConverterResult::representationDescription)
                .filter(FormDescription.class::isInstance)
                .map(FormDescription.class::cast)
                .map(FormDescription::getPageDescriptions)
                .flatMap(Collection::stream)
                .forEach(registry::add);
    }

    private ColorPalette getColorPalette() {
        return new ViewBuilders().newColorPalette()
                .colors(
                        new ViewBuilders().newFixedColor()
                                .name(WHITE_COLOR)
                                .value("#ffffff")
                                .build(),
                        new ViewBuilders().newFixedColor()
                                .name(NAME_COLOR)
                                .value("#bbdefb")
                                .build(),
                        new ViewBuilders().newFixedColor()
                                .name(QUALIFIED_NAME_COLOR)
                                .value("#0d47a1")
                                .build()
                )
                .build();
    }
}
