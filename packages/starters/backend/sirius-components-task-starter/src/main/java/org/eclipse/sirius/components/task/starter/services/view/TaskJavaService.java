/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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
package org.eclipse.sirius.components.task.starter.services.view;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.StreamSupport;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.interpreter.SimpleCrossReferenceProvider;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.MessageLevel;

import pepper.peppermm.AbstractTask;
import pepper.peppermm.DependencyLink;
import pepper.peppermm.KeyResult;
import pepper.peppermm.Objective;
import pepper.peppermm.PepperFactory;
import pepper.peppermm.Project;
import pepper.peppermm.StartOrEnd;
import pepper.peppermm.TagFolder;
import pepper.peppermm.Task;
import pepper.peppermm.TaskTag;
import pepper.peppermm.Workpackage;

/**
 * Java Service for the task related views.
 *
 * @author lfasani
 */
public class TaskJavaService {

    private static final String NEW_TASK = "New Task";

    private final SimpleCrossReferenceProvider simpleCrossReferenceProvider = new SimpleCrossReferenceProvider();

    private final IFeedbackMessageService feedbackMessageService;

    public TaskJavaService(IFeedbackMessageService feedbackMessageService) {
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
    }

    public void editTask(EObject eObject, String name, String description, Instant startTime, Instant endTime, Integer progress) {
        if (eObject instanceof Task task) {
            if (name != null) {
                task.setName(name);
            }
            if (description != null) {
                task.setDescription(description);
            }
            if (endTime != null && startTime != null) {
                long differenceEnd = task.getEndTime().getEpochSecond() - endTime.getEpochSecond();
                long differenceStart = task.getStartTime().getEpochSecond() - startTime.getEpochSecond();
                boolean endPointed = false;
                boolean startPointed = false;
                List<DependencyLink> dependencies = task.getDependencies();
                for (DependencyLink dep : dependencies) {
                    if (dep.getTarget() == StartOrEnd.END) {
                        endPointed = true;
                    } else {
                        startPointed = true;
                    }
                }
                if (dependencies.isEmpty() || differenceEnd != differenceStart) {
                    if (startPointed && !endPointed) {
                        task.setEndTime(endTime.plus(differenceStart, ChronoUnit.SECONDS));
                    } else if (endPointed && !startPointed) {
                        task.setStartTime(startTime.plus(differenceEnd, ChronoUnit.SECONDS));
                    } else if (!startPointed && !endPointed) {
                        task.setStartTime(startTime);
                        task.setEndTime(endTime);
                    }
                    if (!startPointed || !endPointed) {
                        followTaskMoveDependency(task, new ArrayList<>());
                    }
                }
            }
            if (progress != null) {
                task.setProgress(progress);
            }
        }
    }

    public void createTask(EObject context) {
        Task task = PepperFactory.eINSTANCE.createTask();
        task.setName(NEW_TASK);
        if (context instanceof AbstractTask abstractTask) {
            // The new task follows the context task and has the same duration than the context task.
            if (abstractTask.getEndTime() != null && abstractTask.getStartTime() != null) {
                task.setStartTime(abstractTask.getEndTime());
                task.setEndTime(Instant.ofEpochSecond(2 * abstractTask.getEndTime().getEpochSecond() - abstractTask.getStartTime().getEpochSecond()));
            }

            EObject parent = context.eContainer();
            if (parent instanceof Workpackage workpackage) {
                int index = workpackage.getOwnedTasks().indexOf(context);
                workpackage.getOwnedTasks().add(index + 1, task);
            } else if (parent instanceof AbstractTask parentTask) {
                int index = parentTask.getSubTasks().indexOf(context);
                parentTask.getSubTasks().add(index + 1, task);
            }
        } else if (context instanceof Workpackage workpackage) {
            long epochSecondStartTime = Instant.now().getEpochSecond();
            task.setStartTime(Instant.ofEpochMilli(epochSecondStartTime));
            task.setEndTime(Instant.ofEpochMilli(epochSecondStartTime + 3600 * 4));

            workpackage.getOwnedTasks().add(task);
        }
    }

    public void deleteTask(EObject context) {
        if (context instanceof Task sourceTask) {
            deleteTasksRecursive(sourceTask);
            EcoreUtil.delete(sourceTask, true);
        }
    }

    private void deleteTasksRecursive(Task task) {

        Collection<EStructuralFeature.Setting> inverseReferences = simpleCrossReferenceProvider.getInverseReferences(task);
        for (EStructuralFeature.Setting inverseReference : inverseReferences) {
            if (inverseReference.getEObject() instanceof DependencyLink dep) {
                EcoreUtil.delete(dep, true);
            }
        }
        for (Task subTask : task.getSubTasks()) {
            this.deleteTasksRecursive(subTask);
        }
    }

    public void deleteDependencyLink(EObject target, EObject source) {
        if (target instanceof Task targetTask) {
            if (source instanceof Task sourceTask) {
                targetTask.getDependencies().removeIf(dep -> (dep.getDependency() instanceof Task dependency) && dependency.equals(sourceTask));
            }
        }
    }


    public void createDependencyLink(EObject target, EObject source, org.eclipse.sirius.components.gantt.StartOrEnd sourceStartOrEnd, org.eclipse.sirius.components.gantt.StartOrEnd targetStartOrEnd) {
        DependencyLink dependencyLink = PepperFactory.eINSTANCE.createDependencyLink();
        if (sourceStartOrEnd.equals(org.eclipse.sirius.components.gantt.StartOrEnd.END)) {
            dependencyLink.setSource(StartOrEnd.END);
        } else {
            dependencyLink.setSource(StartOrEnd.START);
        }
        if (targetStartOrEnd.equals(org.eclipse.sirius.components.gantt.StartOrEnd.START)) {
            dependencyLink.setTarget(StartOrEnd.START);
        } else {
            dependencyLink.setTarget(StartOrEnd.END);
        }
        if (source instanceof Task sourceTask) {
            dependencyLink.setDependency(sourceTask);
            if (target instanceof Task targetTask) {
                //Ensure no dependency already exists between source and target to prevent duplicates or cycles
                if (!isDuplicateOrCycle(sourceTask, targetTask)) {
                    targetTask.getDependencies().add(dependencyLink);
                    this.followTaskMoveDependency(sourceTask, new ArrayList<>());
                } else {
                    this.feedbackMessageService.addFeedbackMessage(new Message("Creating a dependency that is duplicate or cyclic is not possible.", MessageLevel.ERROR));
                }
            }
        }
    }

    private static boolean isCycle(Task sourceTask, Task targetTask, List<Task> parsedTasks) {
        boolean isCycle = false;
        for (DependencyLink dep : sourceTask.getDependencies()) {
            Task sourceDependency = (Task) dep.getDependency();
            if (!parsedTasks.contains(sourceDependency)) {
                if (sourceDependency.equals(targetTask)) {
                    isCycle = true;
                } else if (!isCycle) {
                    parsedTasks.add(sourceDependency);
                    isCycle = isCycle(sourceDependency, targetTask, parsedTasks);
                }
            }
        }
        return isCycle;
    }

    private static boolean isDuplicateOrCycle(Task sourceTask, Task targetTask) {
        //to prevent cycles
        boolean isCycle = isCycle(sourceTask, targetTask, new ArrayList<>());
        //to prevent duplicates
        boolean isDuplicate = false;
        for (DependencyLink dep : targetTask.getDependencies()) {
            if (dep.getDependency().equals(sourceTask)) {
                isDuplicate = true;
                break;
            }
        }
        return isDuplicate || isCycle;
    }


    private void followTaskMoveDependency(Task sourceTask, List<Task> taskMoved) {
        List<Task> dependencies = new ArrayList<>();
        List<Task> targetTasks = new ArrayList<>();
        //get all tasks pointed by sourceTask
        for (var inverseReference : simpleCrossReferenceProvider.getInverseReferences(sourceTask)) {
            if (inverseReference.getEObject() instanceof DependencyLink dep) {
                for (var inverseReferenceDependencyLink : simpleCrossReferenceProvider.getInverseReferences(dep)) {
                    if (inverseReferenceDependencyLink.getEObject() instanceof Task targetTask) {
                        targetTasks.add(targetTask);
                    }
                }
            }
        }
        for (Task task : targetTasks) {
            //Get the strongest dependency link
            DependencyLink winner = null;
            Instant latterInstant = null;
            for (DependencyLink dep : task.getDependencies()) {
                Instant newInstant = getLatterInstant(dep);
                if (latterInstant == null || latterInstant.compareTo(newInstant) < 0) {
                    latterInstant = newInstant;
                    winner = dep;
                }
            }
            if (!taskMoved.contains(task)) {
                for (DependencyLink dep : task.getDependencies()) {
                    Task bestSourceTask = (Task) dep.getDependency();
                    if (dep.equals(winner)) {
                        Instant sourceStart = bestSourceTask.getStartTime();
                        Instant sourceEnd = bestSourceTask.getEndTime();
                        Instant oldTaskStart = task.getStartTime();
                        Instant oldTaskEnd = task.getEndTime();
                        int delay = dep.getDuration();
                        StartOrEnd sourceStartOrEnd = dep.getSource();
                        StartOrEnd targetStartOrEnd = dep.getTarget();
                        if (sourceStartOrEnd == StartOrEnd.END && targetStartOrEnd == StartOrEnd.START) {
                            Instant newTaskStart = sourceEnd.plus(delay, ChronoUnit.HOURS);
                            Instant newTaskEnd = Instant.ofEpochSecond(newTaskStart.getEpochSecond() + oldTaskEnd.getEpochSecond() - oldTaskStart.getEpochSecond());
                            task.setEndTime(newTaskEnd);
                            task.setStartTime(newTaskStart);
                        } else if (sourceStartOrEnd == StartOrEnd.START && targetStartOrEnd == StartOrEnd.START) {
                            Instant newTaskStart = sourceStart.plus(delay, ChronoUnit.HOURS);
                            Instant newTaskEnd = Instant.ofEpochSecond(newTaskStart.getEpochSecond() + oldTaskEnd.getEpochSecond() - oldTaskStart.getEpochSecond());
                            task.setEndTime(newTaskEnd);
                            task.setStartTime(newTaskStart);
                        } else if (sourceStartOrEnd == StartOrEnd.END && targetStartOrEnd == StartOrEnd.END) {
                            Instant newTaskEnd = sourceEnd.plus(delay, ChronoUnit.HOURS);
                            Instant newTaskStart = Instant.ofEpochSecond(newTaskEnd.getEpochSecond() - (oldTaskEnd.getEpochSecond() - oldTaskStart.getEpochSecond()));
                            task.setEndTime(newTaskEnd);
                            task.setStartTime(newTaskStart);
                        } else if (sourceStartOrEnd == StartOrEnd.START && targetStartOrEnd == StartOrEnd.END) {
                            Instant newTaskEnd = sourceStart.plus(delay, ChronoUnit.HOURS);
                            Instant newTaskStart = Instant.ofEpochSecond(newTaskEnd.getEpochSecond() - (oldTaskEnd.getEpochSecond() - oldTaskStart.getEpochSecond()));
                            task.setEndTime(newTaskEnd);
                            task.setStartTime(newTaskStart);
                        }
                        if (bestSourceTask == sourceTask) {
                            dependencies.add(task);
                            taskMoved.add(task);
                        }
                    }
                }
            }
        }
        for (Task task : dependencies) {
            followTaskMoveDependency(task, taskMoved);
        }
    }

    private static Instant getLatterInstant(DependencyLink dep) {
        Instant laterInstant = null;
        Task source = (Task) dep.getDependency();
        if (dep.getSource() == StartOrEnd.END) {
            laterInstant = source.getEndTime().plus(dep.getDuration(), ChronoUnit.HOURS);
        } else if (dep.getSource() == StartOrEnd.START) {
            laterInstant = source.getStartTime().plus(dep.getDuration(), ChronoUnit.HOURS);
        }
        return laterInstant;
    }

    public List<Task> getTasksWithTag(TaskTag tag, Workpackage workpackage) {
        return Optional.of(workpackage).stream()
                .flatMap(wkP -> {
                    Iterable<EObject> content = () -> wkP.eAllContents();
                    return StreamSupport.stream(content.spliterator(), false);
                })
                .filter(Task.class::isInstance)
                .map(Task.class::cast)
                .filter(task -> task.getTags().contains(tag))
                .toList();
    }

    public String computeTaskDurationDays(Task task) {
        String value = "";
        Instant startTime = task.getStartTime();
        Instant endTime = task.getEndTime();
        if (startTime != null && endTime != null) {
            Duration timeElapsed = Duration.between(startTime, endTime);
            long dd = timeElapsed.toDaysPart();
            Duration minusDays = timeElapsed.minusDays(dd);
            long hh = minusDays.toHoursPart();
            long mm = minusDays.minusHours(hh).toMinutesPart();
            value = String.format("%02dd%02dh%02dm", dd, hh, mm);
        }
        return value;
    }

    public void createCard(EObject context) {
        Task task = PepperFactory.eINSTANCE.createTask();
        task.setName(NEW_TASK);
        task.setDescription("new description");
        if (context instanceof TaskTag tag) {
            task.getTags().add(tag);

            EObject parent = context.eContainer();
            if (parent instanceof TagFolder tagFolder) {
                EObject parent2 = tagFolder.eContainer();
                if (parent2 instanceof Project project) {
                    var workpackages = project.getOwnedWorkpackages();
                    if (!workpackages.isEmpty()) {
                        workpackages.get(0).getOwnedTasks().add(task);
                    }
                }
            }
        }
    }

    public void editCard(EObject eObject, String title, String description, String label) {
        if (eObject instanceof AbstractTask task) {
            if (title != null) {
                task.setName(title);
            }
            if (description != null) {
                task.setDescription(description);
            }
        }
    }

    public void moveTaskIntoTarget(Task sourceTask, EObject target, int indexInTarget) {
        if (target instanceof Task targetTask) {
            // check that the target is not a child of the dropped task
            boolean targetIsChildOfTheDroppedTask = false;
            EObject container = target.eContainer();
            while (container != null) {
                if (container.equals(sourceTask)) {
                    targetIsChildOfTheDroppedTask = true;
                    break;
                }
                container = container.eContainer();
            }
            if (targetIsChildOfTheDroppedTask) {
                this.feedbackMessageService.addFeedbackMessage(new Message("Moving a task inside a sub-task is not possible.", MessageLevel.WARNING));
            } else {
                this.moveTaskInSubTasks(sourceTask, indexInTarget, targetTask);
            }
        } else if (target instanceof Workpackage workpackage) {
            EList<Task> ownedTasks = workpackage.getOwnedTasks();
            if (ownedTasks.contains(sourceTask)) {
                int indexOfSource = ownedTasks.indexOf(sourceTask);
                if (indexOfSource < indexInTarget) {
                    ownedTasks.move(indexInTarget - 1, sourceTask);
                } else {
                    ownedTasks.move(indexInTarget, sourceTask);
                }
            } else {
                workpackage.getOwnedTasks().add(indexInTarget, sourceTask);
            }
        }
    }

    public void moveWorkpackageInProject(Workpackage sourceWorkpackage, Project project, int indexInTarget) {
        EList<Workpackage> ownedWorkpackages = project.getOwnedWorkpackages();
        if (ownedWorkpackages.contains(sourceWorkpackage)) {
            int indexOfSource = ownedWorkpackages.indexOf(sourceWorkpackage);
            if (indexOfSource < indexInTarget) {
                ownedWorkpackages.move(indexInTarget - 1, sourceWorkpackage);
            } else {
                ownedWorkpackages.move(indexInTarget, sourceWorkpackage);
            }
        } else {
            project.getOwnedWorkpackages().add(indexInTarget, sourceWorkpackage);
        }
    }

    public void moveKeyResultIntoObjective(KeyResult sourceKeyResult, Objective targetObjective, int indexInTarget) {
        EList<KeyResult> ownedKeyResults = targetObjective.getOwnedKeyResults();
        if (sourceKeyResult.eContainer().equals(targetObjective)) {
            ownedKeyResults.move(indexInTarget, sourceKeyResult);
        } else {
            ownedKeyResults.add(sourceKeyResult);
            ownedKeyResults.move(indexInTarget, sourceKeyResult);
        }
    }

    private void moveTaskInSubTasks(Task sourceTask, int indexInTarget, Task targetTask) {
        List<Task> subTasks = targetTask.getSubTasks();
        if (subTasks.contains(sourceTask)) {
            if (indexInTarget >= 0 && indexInTarget <= subTasks.size()) {
                int indexOfSource = subTasks.indexOf(sourceTask);
                if (indexOfSource < indexInTarget) {
                    targetTask.getSubTasks().move(indexInTarget - 1, sourceTask);
                } else {
                    targetTask.getSubTasks().move(indexInTarget, sourceTask);
                }
            } else {
                targetTask.getSubTasks().move(subTasks.size() - 1, sourceTask);
            }
        } else {
            boolean targetHadNoChild = subTasks.isEmpty();
            if (targetHadNoChild) {
                targetTask.setComputeStartEndDynamically(true);
            }
            if (indexInTarget >= 0 && indexInTarget <= targetTask.getSubTasks().size()) {
                targetTask.getSubTasks().add(indexInTarget, sourceTask);
            } else {
                targetTask.getSubTasks().add(sourceTask);
            }
        }
    }

    public Task moveTaskInTag(Task moveTask, int index, TaskTag targetTag) {
        Optional<Workpackage> workPackageOpt = getParent(moveTask, Workpackage.class);

        if (workPackageOpt.isPresent()) {
            // We retrieve all tasks with the same tag (in the same lane).
            List<Task> allTaskInTheLane = this.getTasksWithTag(targetTag, workPackageOpt.get());
            Optional<Task> firstTaskAfterTheDroppedTaskWithSameParent = allTaskInTheLane.subList(index, allTaskInTheLane.size()).stream().filter(task -> task.eContainer().equals(moveTask.eContainer())).findFirst();

            List<Task> tasksBeforeTheDroppedTaskWithSameParent = allTaskInTheLane.subList(0, index).stream().filter(task -> task.eContainer().equals(moveTask.eContainer())).toList();
            Optional<Task> lastTaskBeforeTheDroppedTaskWithSameParent = Optional.empty();
            if (!tasksBeforeTheDroppedTaskWithSameParent.isEmpty()) {
                lastTaskBeforeTheDroppedTaskWithSameParent = Optional.of(tasksBeforeTheDroppedTaskWithSameParent.get(tasksBeforeTheDroppedTaskWithSameParent.size() - 1));
            }

            if (lastTaskBeforeTheDroppedTaskWithSameParent.isPresent() || firstTaskAfterTheDroppedTaskWithSameParent.isPresent()) {
                EObject eContainer = moveTask.eContainer();
                if (eContainer instanceof Workpackage workpackage) {
                    int indexInParent = 0;
                    if (lastTaskBeforeTheDroppedTaskWithSameParent.isPresent()) {
                        indexInParent = workpackage.getOwnedTasks().indexOf(lastTaskBeforeTheDroppedTaskWithSameParent.get()) + 1;
                    } else {
                        indexInParent = workpackage.getOwnedTasks().indexOf(firstTaskAfterTheDroppedTaskWithSameParent.get());
                    }
                    workpackage.getOwnedTasks().move(indexInParent, moveTask);
                } else if (eContainer instanceof AbstractTask parentTask) {
                    int indexInParent = 0;
                    if (lastTaskBeforeTheDroppedTaskWithSameParent.isPresent()) {
                        indexInParent = parentTask.getSubTasks().indexOf(lastTaskBeforeTheDroppedTaskWithSameParent.get()) + 1;
                    } else {
                        indexInParent = parentTask.getSubTasks().indexOf(firstTaskAfterTheDroppedTaskWithSameParent.get());
                    }
                    parentTask.getSubTasks().move(indexInParent, moveTask);
                }
            }
        }
        return moveTask;
    }

    <T> Optional<T> getParent(EObject eObject, Class<T> clazz) {
        Optional<T> objectOpt = Optional.empty();
        EObject parent = eObject.eContainer();
        while (parent != null) {
            if (clazz.isInstance(parent)) {
                objectOpt = Optional.of(clazz.cast(parent));
                break;
            }
            parent = parent.eContainer();
        }

        return objectOpt;
    }

    public void moveObjectiveAtIndex(Objective objective, int index) {
        if (objective.eContainer() instanceof Project project) {
            project.getOwnedObjectives().move(index, objective);
        }
    }

    public void moveTagAtIndex(TaskTag movedTag, int index) {
        EObject eContainer = movedTag.eContainer();
        if (eContainer instanceof TagFolder tagFolder) {
            String prefix = movedTag.getPrefix();
            List<TaskTag> tagList = tagFolder.getOwnedTags().stream().filter(tag -> tag.getPrefix().equals(prefix)).toList();

            int newIndex = this.computeIndexOfTagToMove(movedTag, index, tagList, tagFolder);
            // We move the current tag before the tagToReplace in the project ownTags list.
            int oldIndex = tagFolder.getOwnedTags().indexOf(movedTag);
            // If the moved tag was located before the new location, the index after having remove the tag is
            // decremented.
            if (oldIndex < newIndex) {
                newIndex--;
            }
            tagFolder.getOwnedTags().move(newIndex, movedTag);

        }
    }


    /**
     * When a lane is moved, we change the underlying tag ordering. We need to compute the new index in the project tag
     * list.
     *
     * @param tag
     *         the tag to move.
     * @param index
     *         the new index in the project tag list.
     * @param tagList
     *         the current deck representation tag list (might be a sub set of the project tag list).
     * @param tagFolder
     *         the TagFolder owning the tags.
     * @return the index on which the tag should be moved in the project tag list to match the new index in the deck
     * representation.
     */
    private int computeIndexOfTagToMove(TaskTag tag, int index, List<TaskTag> tagList, TagFolder tagFolder) {
        int newIndex;
        List<TaskTag> unmovedLaneTags = tagList.stream().filter(currentTag -> currentTag != tag).toList();
        if (index < unmovedLaneTags.size()) {
            // We retrieve the tag that will be located after the moved one.
            TaskTag tagToMoveAround = unmovedLaneTags.get(index);
            newIndex = tagFolder.getOwnedTags().indexOf(tagToMoveAround);
        } else {
            // We need to locate the tag after the last one in the deck representation
            TaskTag lastTag = unmovedLaneTags.get(unmovedLaneTags.size() - 1);
            newIndex = tagFolder.getOwnedTags().indexOf(lastTag) + 1;
        }
        return newIndex;
    }

    public void editWorkpackage(EObject eObject, String name, String description, LocalDate startDate, LocalDate endDate, Integer progress) {
        if (eObject instanceof Workpackage workpackage) {
            if (name != null) {
                workpackage.setName(name);
            }
            if (description != null) {
                workpackage.setDescription(description);
            }
            if (startDate != null) {
                workpackage.setStartDate(startDate);
            }
            if (endDate != null) {
                workpackage.setEndDate(endDate);
            }
            if (progress != null) {
                workpackage.setProgress(progress);
            }
        }
    }
}
