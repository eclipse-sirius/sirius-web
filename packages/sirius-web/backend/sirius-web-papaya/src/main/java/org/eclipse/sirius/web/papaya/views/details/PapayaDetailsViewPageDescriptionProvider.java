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
package org.eclipse.sirius.web.papaya.views.details;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.components.collaborative.forms.services.api.IPropertiesDescriptionRegistry;
import org.eclipse.sirius.components.collaborative.forms.services.api.IPropertiesDescriptionRegistryConfigurer;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.forms.WidgetIdProvider;
import org.eclipse.sirius.components.forms.description.FormDescription;
import org.eclipse.sirius.components.forms.description.GroupDescription;
import org.eclipse.sirius.components.forms.description.PageDescription;
import org.eclipse.sirius.components.forms.description.TableWidgetDescription;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.ColorPalette;
import org.eclipse.sirius.components.view.builder.generated.form.FormBuilders;
import org.eclipse.sirius.components.view.builder.generated.view.ViewBuilders;
import org.eclipse.sirius.components.view.builder.providers.DefaultColorProvider;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
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

    private final IIdentityService identityService;

    private final AttributesTableDescriptionProvider attributesTableDescriptionProvider;

    public PapayaDetailsViewPageDescriptionProvider(IFormDescriptionConverter formDescriptionConverter, List<IPageDescriptionProvider> pageDescriptionProviders, IIdentityService identityService, AttributesTableDescriptionProvider attributesTableDescriptionProvider) {
        this.formDescriptionConverter = Objects.requireNonNull(formDescriptionConverter);
        this.pageDescriptionProviders = Objects.requireNonNull(pageDescriptionProviders);
        this.identityService = Objects.requireNonNull(identityService);
        this.attributesTableDescriptionProvider = Objects.requireNonNull(attributesTableDescriptionProvider);
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
                .map(FormDescription::getPageDescriptions)
                .flatMap(Collection::stream)
                .map(this::customize)
                .forEach(registry::add);
    }

    private PageDescription customize(PageDescription pageDescription) {
        if (pageDescription.getId().equals("siriusComponents://formElementDescription?kind=PageDescription&sourceKind=view&sourceId=papaya_details&sourceElementId=3e0650c4-57c9-308c-b35f-be1d9bff9ee6")) {
            Function<VariableManager, String> targetObjectIdProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                    .map(this.identityService::getId)
                    .orElse(null);

            var tableWidgetDescription = TableWidgetDescription.newTableWidgetDescription("table")
                    .idProvider(new WidgetIdProvider())
                    .labelProvider(variableManager -> "Attributes")
                    .targetObjectIdProvider(targetObjectIdProvider)
                    .diagnosticsProvider(variableManager -> List.of())
                    .kindProvider(object -> "")
                    .messageProvider(object -> "")
                    .tableDescription(this.attributesTableDescriptionProvider.getTableDescription())
                    .build();

            if (pageDescription.getGroupDescriptions().size() > 1) {
                var groupDescription = pageDescription.getGroupDescriptions().get(1);
                var controlDescriptions = new ArrayList<>(groupDescription.getControlDescriptions());
                controlDescriptions.add(tableWidgetDescription);

                var updatedGroupDescription = GroupDescription.newGroupDescription(groupDescription)
                        .controlDescriptions(controlDescriptions)
                        .build();

                return PageDescription.newPageDescription(pageDescription)
                        .groupDescriptions(List.of(pageDescription.getGroupDescriptions().get(0), updatedGroupDescription))
                        .build();
            }
        }
        return pageDescription;
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
