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
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.MessageLevel;

import pepper.peppermm.AbstractTask;
import pepper.peppermm.KeyResult;
import pepper.peppermm.Objective;
import pepper.peppermm.PepperFactory;
import pepper.peppermm.Project;
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

    private final IFeedbackMessageService feedbackMessageService;

    public TaskJavaService(IFeedbackMessageService feedbackMessageService) {
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
    }

    public void editTask(EObject eObject, String name, String description, Instant startTime, Instant endTime, Integer progress) {
        if (eObject instanceof AbstractTask task) {
            if (name != null) {
                task.setName(name);
            }
            if (description != null) {
                task.setDescription(description);
            }
            if (startTime != null) {
                task.setStartTime(startTime);
            }
            if (endTime != null) {
                task.setEndTime(endTime);
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

    public void createWorkpackage(EObject context) {
        Workpackage newWorkpackage = PepperFactory.eINSTANCE.createWorkpackage();
        newWorkpackage.setName("New Workpackage");
        if (context instanceof Workpackage workpackage) {
            // The new task follows the context task and has the same duration than the context task.
            if (workpackage.getEndDate() != null && workpackage.getStartDate() != null) {
                newWorkpackage.setStartDate(workpackage.getEndDate().plusDays(1));
                newWorkpackage.setEndDate(workpackage.getEndDate().plusDays(workpackage.getEndDate().toEpochDay() - workpackage.getStartDate().toEpochDay()));
            }

            EObject parent = context.eContainer();
            if (parent instanceof Project project) {
                int index = project.getOwnedWorkpackages().indexOf(context);
                project.getOwnedWorkpackages().add(index + 1, newWorkpackage);
            }
        } else if (context instanceof Project project) {
            LocalDate now = LocalDate.now();
            newWorkpackage.setStartDate(now);
            newWorkpackage.setEndDate(now.plusDays(28));

            project.getOwnedWorkpackages().add(newWorkpackage);
        }
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
