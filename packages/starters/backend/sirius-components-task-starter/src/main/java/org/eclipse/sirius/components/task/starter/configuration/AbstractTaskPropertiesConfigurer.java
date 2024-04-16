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
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.components.collaborative.forms.services.api.IPropertiesDescriptionRegistry;
import org.eclipse.sirius.components.collaborative.forms.services.api.IPropertiesDescriptionRegistryConfigurer;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.forms.DateTimeType;
import org.eclipse.sirius.components.forms.description.AbstractControlDescription;
import org.eclipse.sirius.components.forms.description.CheckboxDescription;
import org.eclipse.sirius.components.forms.description.DateTimeDescription;
import org.eclipse.sirius.components.forms.description.GroupDescription;
import org.eclipse.sirius.components.forms.description.IfDescription;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.task.AbstractTask;
import org.eclipse.sirius.components.task.Person;
import org.eclipse.sirius.components.task.Project;
import org.eclipse.sirius.components.task.Task;
import org.eclipse.sirius.components.task.TaskPackage;
import org.eclipse.sirius.components.task.TaskTag;
import org.eclipse.sirius.components.task.Team;
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
    private final PropertiesConfigurerService propertiesConfigurerService;

    public AbstractTaskPropertiesConfigurer(PropertiesConfigurerService propertiesConfigurerService,
            IPropertiesWidgetCreationService propertiesWidgetCreationService, IObjectService objectService) {
        this.propertiesConfigurerService = Objects.requireNonNull(propertiesConfigurerService);
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
                task -> Optional.ofNullable(((AbstractTask) task).getName()).orElse(""),
                (task, newValue) -> {
                    ((AbstractTask) task).setName(newValue);
                },
                TaskPackage.Literals.ABSTRACT_TASK__NAME);
        controls.add(name);

        var description = this.propertiesWidgetCreationService.createExpressionField("abstractTask.description", "Description",
                task -> Optional.ofNullable(((AbstractTask) task).getDescription()).orElse(""),
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

    private DateTimeDescription getStartTimeWidget() {
        Function<VariableManager, String> valueProvider = variableManager -> variableManager.get(VariableManager.SELF, AbstractTask.class)
                .map(task -> {
                    Instant startInstant = task.getStartTime();
                    try {
                        return DateTimeFormatter.ISO_INSTANT.format(startInstant);
                    } catch (DateTimeException | NullPointerException e) {
                        // Ignore
                    }
                    return "";
                })
                .orElse("");
        BiFunction<VariableManager, String, IStatus> newValueHandler = (variableManager, newValue) -> {
            var taskOpt = variableManager.get(VariableManager.SELF, AbstractTask.class);
            if (taskOpt.isPresent()) {
                if (newValue == null || newValue.isBlank()) {
                    taskOpt.get().setStartTime(null);
                } else {
                    try {
                        Instant instant = Instant.parse(newValue);
                        taskOpt.get().setStartTime(instant);
                    } catch (DateTimeParseException e) {
                        // Ignore
                    }
                }
                return new Success();
            } else {
                return new Failure("");
            }
        };
        String id = "abstractTask.startTime";
        return DateTimeDescription.newDateTimeDescription(id)
                .idProvider(variableManager -> id)
                .targetObjectIdProvider(this.propertiesConfigurerService.getSemanticTargetIdProvider())
                .labelProvider(variableManager -> "Start Time")
                .stringValueProvider(valueProvider)
                .newValueHandler(newValueHandler)
                .diagnosticsProvider(this.propertiesConfigurerService.getDiagnosticsProvider(TaskPackage.Literals.ABSTRACT_TASK__START_TIME))
                .kindProvider(this.propertiesConfigurerService.getKindProvider())
                .messageProvider(this.propertiesConfigurerService.getMessageProvider())
                .type(DateTimeType.DATE_TIME)
                .build();
    }

    private DateTimeDescription getEndTimeWidget() {
        Function<VariableManager, String> valueProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .map(task -> {
                    Instant endInstant = ((AbstractTask) task).getEndTime();
                    try {
                        return DateTimeFormatter.ISO_INSTANT.format(endInstant);
                    } catch (DateTimeException | NullPointerException e) {
                        // Ignore
                    }
                    return "";
                })
                .orElse("");
        BiFunction<VariableManager, String, IStatus> newValueHandler = (variableManager, newValue) -> {
            var taskOpt = variableManager.get(VariableManager.SELF, Object.class);
            if (taskOpt.isPresent()) {
                if (newValue == null || newValue.isBlank()) {
                    ((AbstractTask) taskOpt.get()).setEndTime(null);
                } else {
                    try {
                        Instant instant = Instant.parse(newValue);
                        ((AbstractTask) taskOpt.get()).setEndTime(instant);
                    } catch (DateTimeParseException e) {
                        // Ignore
                    }
                }
                return new Success();
            } else {
                return new Failure("");
            }
        };
        String id = "abstractTask.endTime";
        return DateTimeDescription.newDateTimeDescription(id)
                .idProvider(variableManager -> id)
                .targetObjectIdProvider(this.propertiesConfigurerService.getSemanticTargetIdProvider())
                .labelProvider(variableManager -> "End Time")
                .stringValueProvider(valueProvider)
                .newValueHandler(newValueHandler)
                .diagnosticsProvider(this.propertiesConfigurerService.getDiagnosticsProvider(TaskPackage.Literals.ABSTRACT_TASK__END_TIME))
                .kindProvider(this.propertiesConfigurerService.getKindProvider())
                .messageProvider(this.propertiesConfigurerService.getMessageProvider())
                .type(DateTimeType.DATE_TIME)
                .build();
    }

    private Function<VariableManager, List<?>> getDependenciesProvider() {
        return variableManager -> {
            List<Task> dependencies =  variableManager.get(VariableManager.SELF, AbstractTask.class)
                .map(this::getProject)
                .stream()
                .flatMap(this::getAllContentStream)
                .filter(Task.class::isInstance)
                .map(Task.class::cast)
                .toList();

            return dependencies;
        };
    }

    private Project getProject(EObject eObject) {
        EObject parent = eObject.eContainer();
        while (parent != null) {
            if (parent instanceof Project project) {
                return project;
            }
            parent = parent.eContainer();
        }
        return null;
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
            List<TaskTag> tags = variableManager.get(VariableManager.SELF, EObject.class)
                .map(this::getProject)
                .stream()
                .flatMap(project -> project.getOwnedTags().stream())
                .toList();

            return tags;
        };
    }

    private Function<VariableManager, List<?>> getPersonsProvider() {
        return variableManager -> {
            List<Person> persons = variableManager.get(VariableManager.SELF, EObject.class)
                .map(EObject::eResource)
                .stream()
                .flatMap(this::getAllResourceContentStream)
                .filter(Person.class::isInstance)
                .map(Person.class::cast)
                .toList();

            return persons;
        };
    }

    private Stream<EObject> getAllResourceContentStream(Resource resource) {
        Iterable<EObject> content = () -> resource.getAllContents();
        return StreamSupport.stream(content.spliterator(), false);
    }

    private Stream<EObject> getAllContentStream(EObject eObject) {
        Iterable<EObject> content = () -> eObject.eAllContents();
        return StreamSupport.stream(content.spliterator(), false);
    }

    private Function<VariableManager, List<?>> getTeamsProvider() {
        return variableManager -> {
            List<Team> teams = variableManager.get(VariableManager.SELF, EObject.class)
                .map(EObject::eResource)
                .stream()
                .flatMap(this::getAllResourceContentStream)
                .filter(Team.class::isInstance)
                .map(Team.class::cast)
                .toList();

            return teams;
        };
    }
}
