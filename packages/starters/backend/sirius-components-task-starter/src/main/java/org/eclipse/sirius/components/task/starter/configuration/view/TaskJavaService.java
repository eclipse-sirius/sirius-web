/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
package org.eclipse.sirius.components.task.starter.configuration.view;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.gantt.TaskDetail;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.MessageLevel;
import org.eclipse.sirius.components.task.AbstractTask;
import org.eclipse.sirius.components.task.Project;
import org.eclipse.sirius.components.task.Task;
import org.eclipse.sirius.components.task.TaskFactory;
import org.eclipse.sirius.components.task.TaskTag;

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

    public TaskDetail getTaskDetail(Task task) {

        String name = Optional.ofNullable(task.getName()).orElse("");
        String description = Optional.ofNullable(task.getDescription()).orElse("");
        Instant startTime = task.getStartTime();
        Instant endTime = task.getEndTime();
        int progress = task.getProgress();
        boolean computeStartEndDynamically = task.isComputeStartEndDynamically();

        return new TaskDetail(name, description, startTime, endTime, progress, computeStartEndDynamically);
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
        Task task = TaskFactory.eINSTANCE.createTask();
        task.setName(NEW_TASK);
        if (context instanceof AbstractTask abstractTask) {
            // The new task follows the context task and has the same duration than the context task.
            if (abstractTask.getEndTime() != null && abstractTask.getStartTime() != null) {
                task.setStartTime(abstractTask.getEndTime());
                task.setEndTime(Instant.ofEpochSecond(2 * abstractTask.getEndTime().getEpochSecond() - abstractTask.getStartTime().getEpochSecond()));
            }

            EObject parent = context.eContainer();
            if (parent instanceof Project project) {
                project.getOwnedTasks().add(task);
            } else if (parent instanceof AbstractTask parentTask) {
                parentTask.getSubTasks().add(task);
            }
        } else if (context instanceof Project project) {
            long epochSecondStartTime = Instant.now().getEpochSecond();
            task.setStartTime(Instant.ofEpochMilli(epochSecondStartTime));
            task.setEndTime(Instant.ofEpochMilli(epochSecondStartTime + 3600 * 4));

            project.getOwnedTasks().add(task);
        }
    }

    public List<Task> getTasksWithTag(TaskTag tag) {
        return Optional.ofNullable(tag.eContainer())
                .filter(Project.class::isInstance)
                .map(Project.class::cast).stream()
                .map(Project::getOwnedTasks)
                .flatMap(List::stream)
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
        Task task = TaskFactory.eINSTANCE.createTask();
        task.setName(NEW_TASK);
        task.setDescription("new description");
        task.setName(NEW_TASK);
        if (context instanceof TaskTag tag) {
            task.getTags().add(tag);

            EObject parent = context.eContainer();
            if (parent instanceof Project project) {
                project.getOwnedTasks().add(task);
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
        } else if (target instanceof Project project) {
            EList<Task> ownedTasks = project.getOwnedTasks();
            if (ownedTasks.contains(sourceTask)) {
                int indexOfSource = ownedTasks.indexOf(sourceTask);
                if (indexOfSource < indexInTarget) {
                    ownedTasks.move(indexInTarget - 1, sourceTask);
                } else {
                    ownedTasks.move(indexInTarget, sourceTask);
                }
            } else {
                project.getOwnedTasks().add(indexInTarget, sourceTask);
            }
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
            if (indexInTarget >= 0 && indexInTarget <= targetTask.getSubTasks().size()) {
                targetTask.getSubTasks().add(indexInTarget, sourceTask);
            } else {
                targetTask.getSubTasks().add(sourceTask);
            }
        }
    }

    public Task moveCardAtIndex(Task task, int index, TaskTag targetTag) {
        // We retrieve all tasks with the same tag (in the same lane).
        List<Task> targetLaneTaskList = this.getTasksWithTag(targetTag);
        if (!targetLaneTaskList.isEmpty()) {
            EObject eContainer = task.eContainer();
            if (eContainer instanceof Project project) {
                int newIndex = this.computeIndexOfTaskToReplace(task, index, targetLaneTaskList, project);
                // We move the current task before the taskToReplace in the project ownTasks list.
                int oldIndex = project.getOwnedTasks().indexOf(task);
                // If the moved task was located before the new location, the index after having remove the task is
                // decremented.
                if (oldIndex < newIndex) {
                    newIndex--;
                }
                project.getOwnedTasks().move(newIndex, task);
            }
        }
        return task;
    }

    /**
     * When a card is moved, we change the underlying task ordering.
     *
     * @param task
     *            the task to move.
     * @param index
     *            the new index in the lane task list.
     * @param targetLaneTaskList
     *            the current lane task list.
     * @param project
     *            the project owning the tasks.
     * @return the index on which the task should be moved in the project task list to match the new index in the lane.
     */
    private int computeIndexOfTaskToReplace(Task task, int index, List<Task> targetLaneTaskList, Project project) {
        int newIndex;
        List<Task> unmovedLaneTasks = targetLaneTaskList.stream().filter(currentTask -> currentTask != task).toList();
        if (index < unmovedLaneTasks.size()) {
            // We retrieve the Task that will be located after the moved one.
            Task taskToMoveAround = unmovedLaneTasks.get(index);
            newIndex = project.getOwnedTasks().indexOf(taskToMoveAround);
        } else {
            // We need to locate the task after the last one in the lane
            Task lastTask = unmovedLaneTasks.get(unmovedLaneTasks.size() - 1);
            newIndex = project.getOwnedTasks().indexOf(lastTask) + 1;
        }
        return newIndex;
    }

    public void moveLaneAtIndex(TaskTag movedTag, int index) {
        EObject eContainer = movedTag.eContainer();
        if (eContainer instanceof Project project) {
            String prefix = movedTag.getPrefix();
            List<TaskTag> tagList = project.getOwnedTags().stream().filter(tag -> tag.getPrefix().equals(prefix)).toList();

            int newIndex = this.computeIndexOfTagToMove(movedTag, index, tagList, project);
            // We move the current tag before the tagToReplace in the project ownTags list.
            int oldIndex = project.getOwnedTags().indexOf(movedTag);
            // If the moved tag was located before the new location, the index after having remove the tag is
            // decremented.
            if (oldIndex < newIndex) {
                newIndex--;
            }
            project.getOwnedTags().move(newIndex, movedTag);

        }
    }

    /**
     * When a lane is moved, we change the underlying tag ordering. We need to compute the new index in the project tag
     * list.
     *
     * @param tag
     *            the tag to move.
     * @param index
     *            the new index in the project tag list.
     * @param tagList
     *            the current deck representation tag list (might be a sub set of the project tag list).
     * @param project
     *            the project owning the tags.
     * @return the index on which the tag should be moved in the project tag list to match the new index in the deck
     *         representation.
     */
    private int computeIndexOfTagToMove(TaskTag tag, int index, List<TaskTag> tagList, Project project) {
        int newIndex;
        List<TaskTag> unmovedLaneTags = tagList.stream().filter(currentTag -> currentTag != tag).toList();
        if (index < unmovedLaneTags.size()) {
            // We retrieve the tag that will be located after the moved one.
            TaskTag tagToMoveAround = unmovedLaneTags.get(index);
            newIndex = project.getOwnedTags().indexOf(tagToMoveAround);
        } else {
            // We need to locate the tag after the last one in the deck representation
            TaskTag lastTag = unmovedLaneTags.get(unmovedLaneTags.size() - 1);
            newIndex = project.getOwnedTags().indexOf(lastTag) + 1;
        }
        return newIndex;
    }
}
