/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo and others.
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
import { WorkbenchViewComponentProps } from '@eclipse-sirius/sirius-components-core';
import { makeStyles } from '@material-ui/core/styles';
import {
  GQLForm,
  GQLList,
  GQLSubscriber,
  GQLTree,
  GQLWidget,
  GQLWidgetSubscription,
} from '../form/FormEventFragments.types';
import { ListPropertySection } from '../propertysections/ListPropertySection';
import { TreePropertySection } from '../propertysections/TreePropertySection';
import { FormBasedView } from './FormBasedView';

const useRepresentationsViewStyles = makeStyles((theme) => ({
  content: {
    padding: theme.spacing(1),
  },
}));

const isList = (widget: GQLWidget | undefined): widget is GQLList => widget && widget.__typename === 'List';
const isTree = (widget: GQLWidget | undefined): widget is GQLTree => widget && widget.__typename === 'TreeWidget';

export const RepresentationsView = (props: WorkbenchViewComponentProps) => {
  const classes = useRepresentationsViewStyles();

  const extractPlainList = (
    props: WorkbenchViewComponentProps,
    form: GQLForm,
    widgetSubscriptions: GQLWidgetSubscription[]
  ): JSX.Element => {
    const widget: GQLWidget | undefined = form.pages[0]?.groups[0]?.widgets[0];
    if (isList(widget)) {
      const uniqueSubscribers: Set<GQLSubscriber> = new Set();
      widgetSubscriptions.forEach((subscription) =>
        subscription.subscribers.forEach((subscriber) => uniqueSubscribers.add(subscriber))
      );
      return (
        <div className={classes.content}>
          <ListPropertySection
            editingContextId={props.editingContextId}
            formId={form.id}
            readOnly={props.readOnly}
            widget={widget}
            subscribers={[...uniqueSubscribers.values()]}
          />
        </div>
      );
    } else if (isTree(widget)) {
      const uniqueSubscribers: Set<GQLSubscriber> = new Set();
      widgetSubscriptions.forEach((subscription) =>
        subscription.subscribers.forEach((subscriber) => uniqueSubscribers.add(subscriber))
      );
      return (
        <div className={classes.content}>
          <TreePropertySection
            editingContextId={props.editingContextId}
            formId={form.id}
            readOnly={props.readOnly}
            widget={widget}
            subscribers={[...uniqueSubscribers.values()]}
          />
        </div>
      );
    } else {
      return <div className={classes.content} />;
    }
  };

  return <FormBasedView {...props} subscriptionName="representationsEvent" postProcessor={extractPlainList} />;
};
