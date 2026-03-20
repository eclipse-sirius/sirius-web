/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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

package org.eclipse.sirius.web.services.gantt;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.interpreter.SimpleCrossReferenceProvider;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.MessageLevel;

import pepper.peppermm.AbstractTask;
import pepper.peppermm.DependencyLink;
import pepper.peppermm.PepperFactory;
import pepper.peppermm.StartOrEnd;
import pepper.peppermm.Task;
import pepper.peppermm.Workpackage;

/**
 * Java Service for the task related views, for tests.
 *
 * @author ncouvert
 */
public class PepperJavaService {

    private static final String NEW_TASK = "New Task";

    private final SimpleCrossReferenceProvider simpleCrossReferenceProvider = new SimpleCrossReferenceProvider();

    private final IFeedbackMessageService feedbackMessageService;

    public PepperJavaService(IFeedbackMessageService feedbackMessageService) {
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
        var inverseReferences = simpleCrossReferenceProvider.getInverseReferences(task);
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

}
