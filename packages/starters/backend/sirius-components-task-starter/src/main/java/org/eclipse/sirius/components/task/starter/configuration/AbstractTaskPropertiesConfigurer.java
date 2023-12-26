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
package org.eclipse.sirius.components.task.starter.configuration;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.components.collaborative.forms.services.api.IPropertiesDescriptionRegistry;
import org.eclipse.sirius.components.collaborative.forms.services.api.IPropertiesDescriptionRegistryConfigurer;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.forms.description.AbstractControlDescription;
import org.eclipse.sirius.components.forms.description.CheckboxDescription;
import org.eclipse.sirius.components.forms.description.GroupDescription;
import org.eclipse.sirius.components.forms.description.IfDescription;
import org.eclipse.sirius.components.forms.description.TextfieldDescription;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.task.AbstractTask;
import org.eclipse.sirius.components.task.Person;
import org.eclipse.sirius.components.task.Project;
import org.eclipse.sirius.components.task.Task;
import org.eclipse.sirius.components.task.TaskPackage;
import org.eclipse.sirius.components.task.Team;
import org.eclipse.sirius.components.view.emf.ICustomImageMetadataSearchService;
import org.eclipse.sirius.components.view.emf.compatibility.IPropertiesWidgetCreationService;
import org.eclipse.sirius.components.view.emf.compatibility.PropertiesConfigurerService;
import org.springframework.stereotype.Component;

/**
 * Customizes the properties view for {@link AbstractTask} sub classes.
 *
 * @author lfasani
 */
@Component
public class AbstractTaskPropertiesConfigurer implements IPropertiesDescriptionRegistryConfigurer {

    private final IPropertiesWidgetCreationService propertiesWidgetCreationService;

    private final IObjectService objectService;

    public AbstractTaskPropertiesConfigurer(ICustomImageMetadataSearchService customImageSearchService, PropertiesConfigurerService propertiesConfigurerService,
            IPropertiesWidgetCreationService propertiesWidgetCreationService, IObjectService objectService) {
        this.propertiesWidgetCreationService = Objects.requireNonNull(propertiesWidgetCreationService);
        this.objectService = Objects.requireNonNull(objectService);
    }

    @Override
    public void addPropertiesDescriptions(IPropertiesDescriptionRegistry registry) {

        String formDescriptionId = UUID.nameUUIDFromBytes("abstractTask".getBytes()).toString();

        List<AbstractControlDescription> controls = new ArrayList<>(this.getGeneralControlDescription());

        Predicate<VariableManager> canCreatePagePredicate = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .filter(AbstractTask.class::isInstance)
                .isPresent();

        GroupDescription groupDescription = this.propertiesWidgetCreationService.createSimpleGroupDescription(controls);

        registry.add(this.propertiesWidgetCreationService.createSimplePageDescription(formDescriptionId, groupDescription, canCreatePagePredicate));

    }

    private List<AbstractControlDescription> getGeneralControlDescription() {
        List<AbstractControlDescription> controls = new ArrayList<>();

        var name = this.propertiesWidgetCreationService.createTextField("abstractTask.name", "Name",
                task -> String.valueOf(Optional.ofNullable(((AbstractTask) task).getName()).orElse("")),
                (task, newValue) -> {
                    ((AbstractTask) task).setName(newValue);
                },
                TaskPackage.Literals.ABSTRACT_TASK__NAME);
        controls.add(name);

        var description = this.propertiesWidgetCreationService.createExpressionField("abstractTask.description", "Description",
                task -> String.valueOf(Optional.ofNullable(((AbstractTask) task).getDescription()).orElse("")),
                (task, newValue) -> {
                    ((AbstractTask) task).setDescription(newValue);
                },
                TaskPackage.Literals.ABSTRACT_TASK__DESCRIPTION);
        controls.add(description);

        var startTime = this.getStartTimeWidget();
        controls.add(startTime);

        var endTime = this.getEndTimeWidget();
        controls.add(endTime);

        var progress = this.propertiesWidgetCreationService.createTextField("abstractTask.progress", "Progress",
                task -> String.valueOf(Optional.ofNullable(((AbstractTask) task).getProgress()).orElse(0)),
                (task, newValue) -> {
                    try {
                        int intValue = Integer.parseInt(newValue);
                        ((AbstractTask) task).setProgress(intValue);
                    } catch (NumberFormatException e) {
                        // Ignore
                    }
                },
                TaskPackage.Literals.ABSTRACT_TASK__PROGRESS);
        controls.add(progress);


        IfDescription ifComputeDynamically = this.createComputeDynamicallyWidget();
        controls.add(ifComputeDynamically);

        var dependencies = this.propertiesWidgetCreationService.createReferenceWidget("abstractTask.dependencies", "Dependencies",
                TaskPackage.Literals.ABSTRACT_TASK__DEPENDENCIES, this.getDependenciesProvider());
        controls.add(dependencies);

        var tags = this.propertiesWidgetCreationService.createReferenceWidget("abstractTask.tags", "Tags",
                TaskPackage.Literals.ABSTRACT_TASK__TAGS, this.getTagsProvider());
        controls.add(tags);

        var persons = this.propertiesWidgetCreationService.createReferenceWidget("abstractTask.persons", "Assigned People",
                TaskPackage.Literals.ABSTRACT_TASK__ASSIGNED_PERSONS, this.getPersonsProvider());
        controls.add(persons);

        var teams = this.propertiesWidgetCreationService.createReferenceWidget("abstractTask.teams", "Assigned Teams",
                TaskPackage.Literals.ABSTRACT_TASK__ASSIGNED_TEAMS, this.getTeamsProvider());
        controls.add(teams);

        return controls;
    }

    private TextfieldDescription getEndTimeWidget() {
        var endTime = this.propertiesWidgetCreationService.createTextField("abstractTask.endTime", "End Time",
                task -> {
                    Instant endInstant = ((AbstractTask) task).getEndTime();
                    try {
                        return DateTimeFormatter.ISO_INSTANT.format(endInstant);
                    } catch (DateTimeException  | NullPointerException e) {
                        // Ignore
                    }
                    return "";
                },
                (task, newValue) -> {
                    if (newValue != null && newValue.isBlank()) {
                        ((AbstractTask) task).setEndTime(null);
                    } else {
                        try {
                            Instant instant =  Instant.parse(newValue);
                            ((AbstractTask) task).setEndTime(instant);
                        } catch (DateTimeParseException e) {
                            // Ignore
                        }
                    }
                },
                TaskPackage.Literals.ABSTRACT_TASK__END_TIME);
        return endTime;
    }

    private TextfieldDescription getStartTimeWidget() {
        var startTime = this.propertiesWidgetCreationService.createTextField("abstractTask.startTime", "Start Time",
                task -> {
                    Instant startInstant = ((AbstractTask) task).getStartTime();
                    try {
                        return DateTimeFormatter.ISO_INSTANT.format(startInstant);
                    } catch (DateTimeException | NullPointerException e) {
                        // Ignore
                    }
                    return "";
                },
                (task, newValue) -> {
                    if (newValue != null && newValue.isBlank()) {
                        ((AbstractTask) task).setStartTime(null);
                    } else {
                        try {
                            Instant instant =  Instant.parse(newValue);
                            ((AbstractTask) task).setStartTime(instant);
                        } catch (DateTimeParseException e) {
                            // Ignore
                        }
                    }
                },
                TaskPackage.Literals.ABSTRACT_TASK__START_TIME);
        return startTime;
    }

    private Function<VariableManager, List<?>> getDependenciesProvider() {
        return variableManager -> {
            List<Task> dependencies = new ArrayList<>();
            variableManager.get(VariableManager.SELF, AbstractTask.class)
                .map(AbstractTask::getDependencies)
                .stream()
                .flatMap(List::stream)
                .toList();
            return dependencies;
        };
    }

    private IfDescription createComputeDynamicallyWidget() {
        CheckboxDescription computeDynamically = this.propertiesWidgetCreationService.createCheckbox("abstractTask.computeDynamically", "Compute Start/End Dynamically",
                task -> ((AbstractTask) task).isComputeStartEndDynamically(),
                (task, newValue) -> ((AbstractTask) task).setComputeStartEndDynamically(newValue),
                TaskPackage.Literals.ABSTRACT_TASK__COMPUTE_START_END_DYNAMICALLY,
                Optional.empty());

        IfDescription ifComputeDynamically = IfDescription.newIfDescription("if.abstractTask.computeDynamically")
            .targetObjectIdProvider(variableManager -> variableManager.get(VariableManager.SELF, Object.class).map(this.objectService::getId).orElse(null))
            .predicate(variableManager-> {
                return variableManager.get(VariableManager.SELF, AbstractTask.class)
                    .filter(task -> !task.getSubTasks().isEmpty())
                    .isPresent();
            })
            .controlDescriptions(List.of(computeDynamically))
            .build();
        return ifComputeDynamically;
    }

    private Function<VariableManager, List<?>> getTagsProvider() {
        return variableManager -> {
            Optional<EObject> eObject = variableManager.get(VariableManager.SELF, EObject.class);
            if (eObject.isPresent()) {
                EObject parent = eObject.get().eContainer();
                while (parent != null) {
                    parent = eObject.get().eContainer();
                    if (parent instanceof Project project) {
                        return project.getOwnedTags();
                    }
                }
            }

            return List.of();
        };
    }

    private Function<VariableManager, List<?>> getPersonsProvider() {
        return variableManager -> {
            List<Person> persons = variableManager.get(VariableManager.SELF, EObject.class)
                .map(EObject::eResource)
                .map(Resource::getContents)
                .stream()
                .flatMap(List::stream)
                .filter(Person.class::isInstance)
                .map(Person.class::cast)
                .toList();

            return persons;
        };
    }

    private Function<VariableManager, List<?>> getTeamsProvider() {
        return variableManager -> {
            List<Team> teams = variableManager.get(VariableManager.SELF, EObject.class)
                .map(EObject::eResource)
                .map(Resource::getContents)
                .stream()
                .flatMap(List::stream)
                .filter(Team.class::isInstance)
                .map(Team.class::cast)
                .toList();

            return teams;
        };
    }
}
